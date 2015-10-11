package com.threatconnect.sdk.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threatconnect.sdk.client.fluent.AdversaryBuilder;
import com.threatconnect.sdk.client.fluent.AttributeBuilder;
import com.threatconnect.sdk.client.fluent.TagBuilder;
import com.threatconnect.sdk.client.fluent.ThreatBuilder;
import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.TagReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.TagWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.util.IndicatorUtil;
import com.threatconnect.sdk.util.StringUtil;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.threatconnect.sdk.util.IndicatorUtil.getUniqueId;
import static com.threatconnect.sdk.util.IndicatorUtil.setUniqueId;

/**
 * Created by dtineo on 5/15/15.
 */
public abstract class App
{

    private Logger logger = Logger.getGlobal();
    private Configuration config;
    private Connection conn;
    private String owner;
    private AppUtil appUtil;
    private int resultLimit = 500;
    private Map<Indicator.Type, AbstractIndicatorReaderAdapter> indReaderMap = new HashMap<>();
    private Map<Indicator.Type, AbstractIndicatorWriterAdapter> indWriterMap = new HashMap<>();
    private Map<Group.Type, AbstractGroupReaderAdapter> groupReaderMap = new HashMap<>();
    private Map<Group.Type, AbstractGroupWriterAdapter> groupWriterMap = new HashMap<>();
    private int maxRetries = 3;
    private int retrySleepMs = 5000;
    private int requestTimerCounter = 0;
    private long requestLimitResetTimeMs = 0;

    private Map<String, Threat> threatMap;
    private Map<String, Adversary> adversaryMap;
    private TagWriterAdapter tagWriter;
    private Map<String, Tag> tagMap;

    public abstract ExitStatus process() throws IOException;

    public abstract String getLogFilename();

    protected Integer getRateLimit() {
        return Integer.MAX_VALUE;
    }

    public App()
    {
        this(AppUtil.getSystemInstance());
    }

    public App(Map<String, String> parameterMap)
    {
        this(parameterMap == null ? AppUtil.getSystemInstance() : AppUtil.getMapInstance(parameterMap) );
    }

    public App(AppUtil appUtil)
    {
        this.appUtil = appUtil;
        this.config = new Configuration(this.appUtil.getTcApiPath(), this.appUtil.getTcApiAccessID(),
                this.appUtil.getTcApiUserSecretKey(), this.appUtil.getApiDefaultOrg(),
                this.appUtil.getApiMaxResults(getResultLimit()));

        this.owner = this.appUtil.getOwner();

        try
        {

            AppUtil.configureLogger(this.appUtil.getTcLogPath() + File.separator + getLogFilename(),
                    this.appUtil.getTcLogLevel());

            conn = new Connection(this.config);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    protected void debug(String msg, Object... fmtArgs)
    {
        //logger.log(Level.FINEST, String.format(msg, fmtArgs) );
        System.err.printf(msg + "\n", fmtArgs);
    }

    protected void warn(String msg, Object... fmtArgs)
    {
        //logger.log(Level.WARNING, String.format(msg, fmtArgs) );
        System.err.printf(msg + "\n", fmtArgs);
    }

    protected void info(String msg, Object... fmtArgs)
    {
        //logger.log(Level.INFO, String.format(msg, fmtArgs) );
        System.out.printf(msg + "\n", fmtArgs);
    }

    protected void error(Exception e, String msg, Object... fmtArgs)
    {
        logger.log(Level.SEVERE, String.format(msg, fmtArgs), e);
    }

    protected void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public AppUtil getAppUtil()
    {
        return appUtil;
    }

    public void setAppUtil(AppUtil appUtil)
    {
        this.appUtil = appUtil;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public Connection getConn()
    {
        return conn;
    }

    public void setConn(Connection conn)
    {
        this.conn = conn;
    }

    protected AbstractIndicatorReaderAdapter getReader(Indicator.Type type)
    {
        AbstractIndicatorReaderAdapter reader = indReaderMap.get(type);
        if (reader == null)
        {
            reader = ReaderAdapterFactory.createIndicatorReader(type, getConn());
            indReaderMap.put(type, reader);
        }

        return reader;
    }

    protected AbstractIndicatorWriterAdapter getWriter(Indicator.Type type)
    {
        AbstractIndicatorWriterAdapter writer = indWriterMap.get(type);
        if (writer == null)
        {
            writer = WriterAdapterFactory.createIndicatorWriter(type, getConn());
            indWriterMap.put(type, writer);
        }

        return writer;
    }

    protected AbstractGroupReaderAdapter getReader(Group.Type type)
    {
        AbstractGroupReaderAdapter reader = groupReaderMap.get(type);
        if (reader == null)
        {
            reader = ReaderAdapterFactory.createGroupReader(type, getConn());
            groupReaderMap.put(type, reader);
        }

        return reader;
    }

    protected AbstractGroupWriterAdapter getWriter(Group.Type type)
    {
        AbstractGroupWriterAdapter writer = groupWriterMap.get(type);
        if (writer == null)
        {
            writer = WriterAdapterFactory.createGroupWriter(type, getConn());
            groupWriterMap.put(type, writer);
        }

        return writer;
    }


    public int getMaxRetries()
    {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries)
    {
        this.maxRetries = maxRetries;
    }

    public int getRetrySleepMs()
    {
        return retrySleepMs;
    }

    public void setRetrySleepMs(int retrySleepMs)
    {
        this.retrySleepMs = retrySleepMs;
    }

    public int getResultLimit()
    {
        return resultLimit;
    }

    public void setResultLimit(int resultLimit)
    {
        this.resultLimit = resultLimit;
    }

    protected HttpResponse getHttpResponse(String url)
    {
        return getHttpResponse(url, null, getMaxRetries(), 0);
    }

    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap)
    {
        return getHttpResponse(url, headerMap, getMaxRetries(), 0);
    }

    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, Object entity)
    {
        return getHttpResponse(url, headerMap, getMaxRetries(), 0, entity);
    }

    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int retryNum)
    {
        return getHttpResponse(url, headerMap, getMaxRetries(), retryNum);
    }

    protected void addHeaders(HttpRequest request, Map<String, String> headerMap)
    {
        if ( request instanceof HttpEntityEnclosingRequest )
        {
            request.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
        }
        for (Map.Entry<String, String> entry : headerMap.entrySet())
        {
            request.setHeader(entry.getKey(), entry.getValue());
        }
    }


    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum)
    {
        HttpRequestBase request = new HttpGet(url);
        return getHttpResponse(url, headerMap, maxRetries, retryNum, request);
    }

    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum, Object entity)
    {
        if ( entity != null )
        {
            HttpEntityEnclosingRequestBase request = new HttpPost(url);
            String jsonData;
            try
            {
                jsonData = StringUtil.toJSON(entity);
                debug("jsonEntity=%s", jsonData);
            } catch (JsonProcessingException e)
            {
                error(e, "Failed to convert entity to JSON: %s", entity);
                throw new RuntimeException("Failed to convert entity to JSON: %s" + entity);
            }
            request.setEntity(new StringEntity(jsonData, ContentType.APPLICATION_JSON));
            return getHttpResponse(url, headerMap, maxRetries, retryNum, request);
        }
        else
        {
            return getHttpResponse(url, headerMap, maxRetries, retryNum);
        }
    }

    private HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum, HttpRequestBase request)
    {
        MetricUtil.tick("getResponse");
        debug("getResponse.URL=%s, retryNum=%d", url, retryNum);
        //debug("getResponse.headers=%s", headerMap);

        HttpClient client = HttpClientBuilder.create().build();
        addHeaders(request, headerMap);
        HttpResponse response;

        checkRateLimit();

        try
        {
            response = client.execute(request);
        } catch (IOException e)
        {
            MetricUtil.tockUpdate("getResponse");
            error(e, "URL Failed to return data: %s", url);
            throw new RuntimeException("URL failed to return data: " + url);
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        {
            MetricUtil.tockUpdate("getResponse");
            return response;
        } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE)
        {
            if (retryNum < maxRetries)
            {
                sleep(getRetrySleepMs());
                return getHttpResponse(url, headerMap, maxRetries, retryNum + 1);
            }
        }

        MetricUtil.tockUpdate("getResponse");
        debug("URL failed to return data: response_code=" + response.getStatusLine().getStatusCode()
                + " response=" + response.toString() );
        throw new RuntimeException("URL failed to return data: response_code=" + response.getStatusLine().getStatusCode()
                + "reason=" + response.getStatusLine().getReasonPhrase());
    }

    private void checkRateLimit()
    {

        requestTimerCounter++;
        System.out.println(new Date() + " - Request counter=" + requestTimerCounter);

        if ( requestTimerCounter == 1 )
        {
            requestLimitResetTimeMs = System.currentTimeMillis() + (60 * 1000L);
            System.out.println("On first counter, set request expire to: " + new Date(requestLimitResetTimeMs));
            return;
        }

        if ( requestTimerCounter >= getRateLimit() )
        {
            long sleepMs = requestLimitResetTimeMs - System.currentTimeMillis();
            if ( sleepMs >= 0 )
            {
                System.out.printf("RequestTimer hit rate limit, throttling requests. Limit=%d, Now=%s, ResetTime=%s, SleepMs=%d\n",
                        getRateLimit(), new Date(), new Date(requestLimitResetTimeMs), sleepMs);

                sleep(sleepMs);
            }

            requestTimerCounter = 0;
        }

    }

    protected void dissociateTags(AbstractIndicatorReaderAdapter reader, AbstractIndicatorWriterAdapter writer, Indicator indicator)
    {

        String uniqueId = getUniqueId(indicator);
        if (uniqueId == null)
        {
            return;
        }

        IterableResponse<Tag> associatedTags;
        try
        {
            associatedTags = reader.getAssociatedTags(uniqueId, getOwner());
        } catch (IOException | FailedResponseException e)
        {
            return;
        }

        for (Tag tag : associatedTags)
        {
            try
            {
                writer.dissociateTag(uniqueId, tag.getName());
            } catch (IOException | FailedResponseException e)
            {
                e.printStackTrace();
            }
        }

    }

    private void deleteAttributes(AbstractIndicatorReaderAdapter reader, AbstractIndicatorWriterAdapter writer, Indicator indicator)
    {

        IterableResponse<Attribute> attributes;
        try
        {
            attributes = reader.getAttributes(getUniqueId(indicator), this.owner);
        } catch (IOException | FailedResponseException e)
        {
            return;
        }

        debug("Removing attributes for indicator %s", getUniqueId(indicator));

        List<Attribute> list = new ArrayList<>();
        for(Attribute a : attributes)
        {
            list.add(a);
        }
        try
        {
            writer.deleteAttributes(getUniqueId(indicator), list, this.owner);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void setAttribute(AbstractIndicatorWriterAdapter writer, Indicator indicator, Attribute currentAttribute,
                                String type, String value)
    {

        if (currentAttribute == null)
        {
            info("CREATE attribute: %s=%s", type, value);
            addAttribute(writer, indicator, type, value);
        } else
        {
            currentAttribute.setValue(value);
            info("UPDATE attribute: %s", currentAttribute);
            updateAttribute(writer, indicator, currentAttribute);
        }
    }

    protected void updateAttribute(AbstractIndicatorWriterAdapter writer, Indicator indicator, Attribute currentAttribute)
    {
        try
        {
            String uniqueId = getUniqueId(indicator);
            if (uniqueId != null)
            {
                writer.updateAttribute(uniqueId, currentAttribute);
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Failed to add attribute: %s, error: %s", currentAttribute.getType(), e.toString());
            e.printStackTrace();
        }
    }

    protected void addAttribute(AbstractIndicatorWriterAdapter writer, Indicator indicator, String type, String value)
    {

        Attribute attribute = new AttributeBuilder()
                .withDisplayed(true)
                .withType(type)
                .withDateAdded(new Date())
                .withLastModified(new Date())
                .withValue(value)
                .createAttribute();

        try
        {

            String uniqueId = getUniqueId(indicator);
            if (uniqueId != null)
            {
                writer.addAttribute(uniqueId, attribute, getOwner());
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Failed to add attribute: %s, error: %s", attribute.getType(), e.toString());
            e.printStackTrace();
        }
    }

    protected Indicator getIndicator(AbstractIndicatorReaderAdapter reader, String indText)
    {

        try
        {
            Indicator indicator = (Indicator) reader.getById(indText, getOwner());
            info("Found indicator: text=%s, ind=%s", indText, indicator);
            return indicator;

        } catch (IOException | FailedResponseException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected Indicator createIndicator(Indicator.Type type, String indText, double rating)
    {
        Indicator ind = IndicatorUtil.createIndicator(type);
        setUniqueId(ind, indText);
        ind.setType(type.toString());
        ind.setRating(rating);
        ind.setSummary(indText);

        return ind;
    }


    protected void addTags(AbstractGroupWriterAdapter<Group> writer, Integer groupId, List<String> tagLabels)
    {
        List<Tag> tags = new ArrayList<>();
        for(String label : tagLabels)
        {
            Tag tag = new Tag();
            tag.setName( label );
            tags.add(tag);
        }

        addFullTags(writer, groupId, tags);
    }

    protected void addFullTags(AbstractGroupWriterAdapter<Group> writer, Integer groupId, List<Tag> tags)
    {

        if ( tagMap == null )
        {
            loadTagMap();
        }

        for (Tag tag : tags)
        {
            if (tag.getName().toLowerCase().contains("unknown"))
            {
                continue;
            }

            tag.setName(tag.getName().replace("/", "-"));


            Tag oldTag = tagMap.get(tag.getName());
            if (oldTag == null)
            {
                createTag(tag);
                tagMap.put(tag.getName(), tag);
            }

            try
            {
                writer.associateTag(groupId, tag.getName(), getOwner());
            } catch (IOException | FailedResponseException e)
            {
                warn("Failed to associate tag %s to group id %d", tag.getName(), groupId);
				e.printStackTrace();
            }
        }

    }

    protected void addTags(AbstractIndicatorWriterAdapter<Indicator> writer, Indicator indicator, List<String> tagLabels)
    {

        if ( tagMap == null )
        {
            loadTagMap();
        }

        String uniqueId = getUniqueId(indicator);

        for (String tagLabel : tagLabels)
        {
            if (tagLabel.toLowerCase().contains("unknown"))
            {
                continue;
            }

            tagLabel = tagLabel.replace("/", "-");


            Tag tag = tagMap.get(tagLabel);
            if (tag == null)
            {
                createTag(tagLabel);
                tagMap.put(tagLabel, tag);
            }

            try
            {
                if (uniqueId != null)
                {
                    writer.associateTag(uniqueId, tagLabel, getOwner());
                }
            } catch (IOException | FailedResponseException e)
            {
                warn("Failed to associate tag %s to indicator %s", tagLabel, uniqueId);
				e.printStackTrace();
            }
        }

    }

    protected boolean deleteTag(String tagLabel)
    {
        if (tagWriter == null)
        {
            tagWriter = WriterAdapterFactory.createTagWriter(getConn());
        }

        try
        {
            ApiEntitySingleResponse response = tagWriter.delete(tagLabel, getOwner());
            if (response.isSuccess())
            {
                return true;
            }

        } catch (IOException | FailedResponseException e)
        {
            // ignore
        }

        return false;
    }

    protected Tag createTag(String tagLabel)
    {
        Tag tag = new TagBuilder().withName(tagLabel).createTag();
        return createTag( tag );
    }

    protected Tag createTag(Tag tag)
    {

        if (tagWriter == null)
        {
            tagWriter = WriterAdapterFactory.createTagWriter(getConn());
        }

        try
        {
            ApiEntitySingleResponse response = tagWriter.create(tag, getOwner());
            if (response.isSuccess())
            {
                return (Tag) response.getItem();
            }

        } catch (IOException | FailedResponseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void loadTagMap()
    {
        tagMap = new HashMap<>();
        TagReaderAdapter tagReader = ReaderAdapterFactory.createTagReader(getConn());
        try
        {
            for (Tag t : tagReader.getAll(getOwner()))
            {
                tagMap.put(t.getName(), t);
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Unable to cache tagMap");
        }

    }

    protected void associateThreat(AbstractIndicatorReaderAdapter<Indicator> reader, AbstractIndicatorWriterAdapter<Indicator> writer, Indicator indicator, List<String> actors)
    {
        if (threatMap == null)
        {
            loadThreatMap();
        }

        String uniqueId = getUniqueId(indicator);

        for (String actor : actors)
        {

            if (actor == null || actor.equalsIgnoreCase("unknown"))
            {
                continue;
            }

            Threat threat = threatMap.get(actor);
            if (threat == null)
            {
                threat = createThreat(actor);
                if (threat == null)
                {
                    continue;
                }
                threatMap.put(threat.getName(), threat);
            }

            try
            {
                if (uniqueId != null)
                {
                    info("Associating threat %s [id=%d] to indicator %s", threat.getName(), threat.getId(), uniqueId);
                    writer.associateGroupThreat(uniqueId, threat.getId());
                }
            } catch (IOException | FailedResponseException e)
            {
                warn("Unable to associate threat %s to indicator %s", threat.getName(), uniqueId);
            }
        }

    }

    protected Threat createThreat(String actor)
    {
        AbstractGroupWriterAdapter threatWriter = getWriter(Group.Type.Threat);
        if (threatWriter == null)
        {
            threatWriter = WriterAdapterFactory.createThreatGroupWriter(getConn());
        }

        Threat threat = new ThreatBuilder().withOwnerName(getOwner()).withName(actor).withDateAdded(new Date()).createThreat();

        try
        {
            ApiEntitySingleResponse response = threatWriter.create(threat, getOwner());
            threat = null;
            if (response.isSuccess())
            {
                threat = (Threat) response.getItem();
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Unable to create threat %s", actor);
            return null;
        }

        return threat;
    }

    private void loadThreatMap()
    {
        threatMap = new HashMap<>();
        AbstractGroupReaderAdapter<Threat> threatReader = ReaderAdapterFactory.createThreatGroupReader(getConn());
        try
        {
            for (Threat t : threatReader.getAll(getOwner()))
            {
                threatMap.put(t.getName(), t);
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Unable to cache threatMap");
        }

    }

    protected void associateThreat(AbstractGroupReaderAdapter<Group> reader, AbstractGroupWriterAdapter<Group> writer, Integer groupId, List<String> actors)
    {
        if (threatMap == null)
        {
            loadThreatMap();
        }

        for (String actor : actors)
        {

            if (actor == null || actor.equalsIgnoreCase("unknown"))
            {
                continue;
            }

            Threat threat = threatMap.get(actor);
            if (threat == null)
            {
                threat = createThreat(actor);
                if (threat == null)
                {
                    continue;
                }
                threatMap.put(threat.getName(), threat);
            }

            try
            {
                info("Associating threat %s [id=%d] to group id %d", threat.getName(), threat.getId(), groupId);
                writer.associateGroupThreat(groupId, threat.getId());
            } catch (IOException | FailedResponseException e)
            {
                warn("Unable to associate threat %s to group id %d", threat.getName(), groupId);
            }
        }

    }



    protected Adversary createAdversary(String actor)
    {
        AbstractGroupWriterAdapter adversaryWriter = getWriter(Group.Type.Adversary);
        if (adversaryWriter== null)
        {
            adversaryWriter = WriterAdapterFactory.createAdversaryGroupWriter(getConn());
        }

        Adversary adversary = new AdversaryBuilder()
                .withOwnerName(getOwner())
                .withName(actor)
                .withDateAdded(new Date())
                .createAdversary();

        try
        {
            ApiEntitySingleResponse response = adversaryWriter.create(adversary, getOwner());
            adversary = null;
            if (response.isSuccess())
            {
                adversary = (Adversary) response.getItem();
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Unable to create adversary %s", actor);
            return null;
        }

        return adversary;
    }

    private void loadAdversaryMap()
    {
        adversaryMap = new HashMap<>();
        AbstractGroupReaderAdapter<Adversary> adversaryReader = ReaderAdapterFactory.createAdversaryGroupReader(getConn());
        try
        {
            for (Adversary a : adversaryReader.getAll(getOwner()))
            {
                adversaryMap.put(a.getName(), a);
            }
        } catch (IOException | FailedResponseException e)
        {
            warn("Unable to cache adversaryMap");
        }

    }

    protected void associateAdversary(AbstractGroupReaderAdapter<Group> reader, AbstractGroupWriterAdapter<Group> writer, Integer groupId, List<String> actors)
    {
        if (adversaryMap == null)
        {
            loadAdversaryMap();
        }

        for (String actor : actors)
        {

            if (actor == null || actor.equalsIgnoreCase("unknown"))
            {
                continue;
            }

            Adversary adversary = adversaryMap.get(actor);
            if (adversary == null)
            {
                adversary = createAdversary(actor);
                if (adversary == null)
                {
                    continue;
                }
                adversaryMap.put(adversary.getName(), adversary);
            }

            try
            {
                info("Associating adversary %s [id=%d] to group id %d", adversary.getName(), adversary.getId(), groupId);
                writer.associateGroupAdversary(groupId, adversary.getId());
            } catch (IOException | FailedResponseException e)
            {
                warn("Unable to associate adversary %s to group id %d", adversary.getName(), groupId);
            }
        }

    }


    public Map<String, Threat> getThreatMap()
    {
        if ( threatMap == null )
        {
            loadThreatMap();
        }

        return threatMap;
    }

    public void setThreatMap(Map<String, Threat> threatMap)
    {
        this.threatMap = threatMap;
    }

    public Map<String, Adversary> getAdversaryMap()
    {
        if ( adversaryMap == null )
        {
            loadAdversaryMap();
        }

        return adversaryMap;
    }

    public void setAdversaryMap(Map<String, Adversary> adversaryMap)
    {
        this.adversaryMap = adversaryMap;
    }

    public void writeMessageTc(String message)
    {
        String fileName = getAppUtil().getTcOutPath() + File.separator + "message.tc";
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            warn("Failed to write message.tc file");
            e.printStackTrace();
        }

        writer.println(message);
        writer.close();
    }

    public String basicEncoded(String user, String password)
    {
        String encoded = new sun.misc.BASE64Encoder().encode( String.format("%s:%s", user, password).getBytes() );

        return "Basic " + encoded;
    }

    public Map<String, Tag> getTagMap()
    {
        return tagMap;
    }
}
