package com.threatconnect.sdk.app;

import com.threatconnect.sdk.client.fluent.AttributeBuilder;
import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.util.IndicatorUtil;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
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

    public abstract ExitStatus process() throws IOException;

    public abstract String getLogFilename();

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

    protected void sleep(int millis)
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

    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int retryNum)
    {
        return getHttpResponse(url, headerMap, getMaxRetries(), retryNum);
    }

    protected void addHeaders(HttpRequest request, Map<String, String> headerMap)
    {
        for (Map.Entry<String, String> entry : headerMap.entrySet())
        {
            request.setHeader(entry.getKey(), entry.getValue());
        }
    }


    protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum)
    {

        MetricUtil.tick("getResponse");
        debug("getResponse.URL=%s, retryNum=%d", url, retryNum);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        addHeaders(request, headerMap);
        HttpResponse response;

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
                + " reason=" + response.getStatusLine().getReasonPhrase());
        throw new RuntimeException("URL failed to return data: response_code=" + response.getStatusLine().getStatusCode()
                + "reason=" + response.getStatusLine().getReasonPhrase());
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
                // ignore
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
        } catch (IOException e) { }

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
        }
    }

    protected Indicator getIndicator(AbstractIndicatorReaderAdapter reader, String indText)
    {

        try
        {
            Indicator indicator = (Indicator) reader.getById(indText, getOwner());
            info("Found indicator: text=%s, ind=%s", indText, indicator);
            return indicator;

        } catch (IOException | FailedResponseException e) {}

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



}
