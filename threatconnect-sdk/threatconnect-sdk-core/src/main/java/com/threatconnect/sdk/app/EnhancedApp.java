package com.threatconnect.sdk.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.client.fluent.AdversaryBuilder;
import com.threatconnect.sdk.client.fluent.AttributeBuilder;
import com.threatconnect.sdk.client.fluent.TagBuilder;
import com.threatconnect.sdk.client.fluent.ThreatBuilder;
import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.DataStoreReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.reader.TagReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.DataStoreWriterAdapter;
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
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.threatconnect.sdk.app.AppUtil.createClientBuilder;
import static com.threatconnect.sdk.util.IndicatorUtil.getUniqueId;
import static com.threatconnect.sdk.util.IndicatorUtil.setUniqueId;

/**
 * Use of this class is deprecated and unsupported. Functionality is not guaranteed
 */
@Deprecated
public abstract class EnhancedApp extends App
{
	private static final Logger logger = LoggerFactory.getLogger(EnhancedApp.class);
	
	private Connection conn;
	private String owner;
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
	private TagReaderAdapter tagReader;
	private DataStoreReaderAdapter dataStoreReader;
	private DataStoreWriterAdapter dataStoreWriter;
	private HttpClient externalClient;
        private List<String> cookies = new ArrayList();
        
	protected Integer getRateLimit()
	{
		return Integer.MAX_VALUE;
	}

	protected Long getRateLimitIntervalMs()
	{
		return 60 * 1000L;
	}

	public EnhancedApp()
	{
		this(SdkAppConfig.getInstance());
	}

	public EnhancedApp(AppConfig appConfig)
	{
	    init(appConfig);

		Configuration config = new Configuration(appConfig, appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
				appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg(),
				appConfig.getApiMaxResults(getResultLimit()));

		this.owner = appConfig.getApiDefaultOrg();

		try
		{
			conn = new Connection(config);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	protected void debug(String msg, Object... fmtArgs)
	{
		logger.debug(String.format(msg, fmtArgs));
	}

	protected void warn(String msg, Object... fmtArgs)
	{
		logger.warn(String.format(msg, fmtArgs) );
	}

	protected void info(String msg, Object... fmtArgs)
	{
		logger.info(String.format(msg, fmtArgs) );
	}

	protected void error(Exception e, String msg, Object... fmtArgs)
	{
		logger.error(e.getMessage(), msg, fmtArgs);
	}

	protected void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
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

	public AbstractIndicatorReaderAdapter getReader(Indicator.Type type)
	{
		AbstractIndicatorReaderAdapter reader = indReaderMap.get(type);
		if (reader == null)
		{
			reader = ReaderAdapterFactory.createIndicatorReader(type, getConn());
			indReaderMap.put(type, reader);
		}

		return reader;
	}

	public AbstractIndicatorWriterAdapter getWriter(Indicator.Type type)
	{
		AbstractIndicatorWriterAdapter writer = indWriterMap.get(type);
		if (writer == null)
		{
			writer = WriterAdapterFactory.createIndicatorWriter(type, getConn());
			indWriterMap.put(type, writer);
		}

		return writer;
	}

	public AbstractGroupReaderAdapter getReader(Group.Type type)
	{
		AbstractGroupReaderAdapter reader = groupReaderMap.get(type);
		if (reader == null)
		{
			reader = ReaderAdapterFactory.createGroupReader(type, getConn());
			groupReaderMap.put(type, reader);
		}

		return reader;
	}

	public AbstractGroupWriterAdapter getWriter(Group.Type type)
	{
		AbstractGroupWriterAdapter writer = groupWriterMap.get(type);
		if (writer == null)
		{
			writer = WriterAdapterFactory.createGroupWriter(type, getConn());
			groupWriterMap.put(type, writer);
		}

		return writer;
	}

	public TagReaderAdapter getTagReader()
	{
		if (this.tagReader == null)
		{
			this.tagReader = ReaderAdapterFactory.createTagReader(conn);
		}

		return this.tagReader;
	}

	public DataStoreReaderAdapter getDataStoreReader()
	{
		if (this.dataStoreReader == null)
		{
			this.dataStoreReader = ReaderAdapterFactory.createDataStoreReaderAdapter(conn);
		}

		return this.dataStoreReader;
	}

	public DataStoreWriterAdapter getDataStoreWriter()
	{
		if (this.dataStoreWriter == null)
		{
			this.dataStoreWriter = WriterAdapterFactory.createDataStoreWriterAdapter(conn);
		}

		return this.dataStoreWriter;
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

	public HttpResponse getHttpResponse(String url)
	{
		return getHttpResponse(url, new HashMap<String, String>(), getMaxRetries(), 0);
	}

	public HttpResponse getHttpResponse(String url, Map<String, String> headerMap)
	{
		return getHttpResponse(url, headerMap, getMaxRetries(), 0);
	}

	public HttpResponse getHttpResponse(String url, Map<String, String> headerMap, Object entity)
	{
		return getHttpResponse(url, headerMap, getMaxRetries(), 0, entity);
	}

	public HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int retryNum)
	{
		return getHttpResponse(url, headerMap, getMaxRetries(), retryNum);
	}

	protected void addHeaders(HttpRequest request, Map<String, String> headerMap)
	{
            if (request instanceof HttpEntityEnclosingRequest)
            {
                    request.addHeader("Accept", ContentType.APPLICATION_JSON.toString());
            }
            for (Map.Entry<String, String> entry : headerMap.entrySet())
            {
                    request.setHeader(entry.getKey(), entry.getValue());
            }
                
            //now add in any cookies saved from the last request
            for (int i = 0; i < cookies.size(); i++)
            {
                String cookie = cookies.get(i);
                request.setHeader("cookie", cookie);
            }                
	}

	protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum)
	{
		HttpRequestBase request = new HttpGet(url);
		return getHttpResponse(url, headerMap, maxRetries, retryNum, request);
	}

	protected HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum,
		Object entity)
	{
		if (entity != null)
		{
			HttpEntityEnclosingRequestBase request = new HttpPost(url);
			String jsonData;
			try
			{
				jsonData = StringUtil.toJSON(entity);
				debug("jsonEntity=%s", jsonData);
			}
			catch (JsonProcessingException e)
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

	private HttpResponse getHttpResponse(String url, Map<String, String> headerMap, int maxRetries, int retryNum,
		HttpRequestBase request)
	{
		//MetricUtil.tick("getResponse");
		debug("getResponse.URL=%s, retryNum=%d", url, retryNum);
		// debug("getResponse.headers=%s", headerMap);
		HttpClient client = getExternalClient();

		addHeaders(request, headerMap);
		HttpResponse response;

		checkRateLimit();

		try
		{
			response = client.execute(request);
		}
		catch (IOException e)
		{
			//MetricUtil.tockUpdate("getResponse");
			error(e, "URL Failed to return data: %s", url);
			throw new RuntimeException("URL failed to return data: " + url);
		}

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		{
			//MetricUtil.tockUpdate("getResponse");
                        
                        //save any cookies that are set.  
                        Header[] headers = response.getAllHeaders();
                        for (Header header : headers) {
                            if (header.getName().equals("Set-Cookie"))
                            {
                                cookies.add(header.getValue());
                                debug("Cookie to be saved: Key : " + header.getName() + " ,Value : " + header.getValue());                        
                            }
                        }                                        
			return response;
		}
		else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_SERVICE_UNAVAILABLE)
		{
			if (retryNum < maxRetries)
			{
				sleep(getRetrySleepMs());
				return getHttpResponse(url, headerMap, maxRetries, retryNum + 1);
			}
		}

		//MetricUtil.tockUpdate("getResponse");
		debug("URL failed to return data: response_code=" + response.getStatusLine().getStatusCode()
			+ " response=" + response.toString());
		throw new RuntimeException(
			"URL failed to return data: response_code=" + response.getStatusLine().getStatusCode()
				+ "reason=" + response.getStatusLine().getReasonPhrase());
	}

	protected void closeExternalClient()
	{
		if ( externalClient == null )
		{
			return;
		}

		HttpClientUtils.closeQuietly(externalClient);
		externalClient = null;
	}

	protected HttpClient getExternalClient()
	{
		if (externalClient == null)
		{
			externalClient = createClientBuilder(getAppConfig().isProxyExternal(),
				getAppConfig().isVerifySSL(), getAppConfig()).build();
		}
		return externalClient;
	}

	private void checkRateLimit()
	{

		requestTimerCounter++;
		info("Request counter=%d", requestTimerCounter);

		if (requestTimerCounter == 1)
		{
			requestLimitResetTimeMs = System.currentTimeMillis() + getRateLimitIntervalMs();
			info("On first counter, set request expire to: %s", new Date(requestLimitResetTimeMs));
			return;
		}

		if (requestTimerCounter >= getRateLimit())
		{
			long sleepMs = requestLimitResetTimeMs - System.currentTimeMillis() + getRateLimitIntervalMs();
			if (sleepMs >= 0)
			{
				info("RequestTimer hit rate limit, throttling requests. Limit=%d, Now=%s, ResetTime=%s, SleepMs=%d",
					getRateLimit(), new Date(), new Date(requestLimitResetTimeMs), sleepMs);

				sleep(sleepMs);
			}

			requestTimerCounter = 0;
		}

	}

	public void dissociateTags(AbstractIndicatorReaderAdapter reader, AbstractIndicatorWriterAdapter writer,
		Indicator indicator)
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
		}
		catch (IOException | FailedResponseException e)
		{
			return;
		}

		for (Tag tag : associatedTags)
		{
			try
			{
				writer.dissociateTag(uniqueId, tag.getName());
			}
			catch (IOException | FailedResponseException e)
			{
				e.printStackTrace();
			}
		}

	}

	private void deleteAttributes(AbstractIndicatorReaderAdapter reader, AbstractIndicatorWriterAdapter writer,
		Indicator indicator)
	{

		IterableResponse<Attribute> attributes;
		try
		{
			attributes = reader.getAttributes(getUniqueId(indicator), this.owner);
		}
		catch (IOException | FailedResponseException e)
		{
			return;
		}

		debug("Removing attributes for indicator %s", getUniqueId(indicator));

		List<Attribute> list = new ArrayList<>();
		for (Attribute a : attributes)
		{
			list.add(a);
		}
		try
		{
			writer.deleteAttributes(getUniqueId(indicator), list, this.owner);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public Attribute setAttribute(AbstractIndicatorWriterAdapter writer, Indicator indicator,
		Attribute currentAttribute,
		String type, String value)
	{

		if (currentAttribute == null)
		{
			info("CREATE attribute: %s=%s", type, value);
			return addAttribute(writer, indicator, type, value);
		}
		else
		{
			currentAttribute.setValue(value);
			info("UPDATE attribute: %s", currentAttribute);
			return updateAttribute(writer, indicator, currentAttribute);
		}
	}

	public Attribute setAttribute(AbstractGroupWriterAdapter writer, Group group, Attribute currentAttribute,
		String type, String value)
	{

		if (currentAttribute == null)
		{
			info("CREATE attribute: %s=%s", type, value);
			return addAttribute(writer, group, type, value);
		}
		else
		{
			currentAttribute.setValue(value);
			info("UPDATE attribute: %s", currentAttribute);
			return updateAttribute(writer, group, currentAttribute);
		}
	}

	public Attribute updateAttribute(AbstractIndicatorWriterAdapter writer, Indicator indicator,
		Attribute currentAttribute)
	{
		try
		{
			String uniqueId = getUniqueId(indicator);
			if (uniqueId != null)
			{
				return (Attribute) writer.updateAttribute(uniqueId, currentAttribute).getItem();
			}
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Failed to add attribute: %s, error: %s", currentAttribute.getType(), e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public Attribute updateAttribute(AbstractGroupWriterAdapter writer, Group group, Attribute currentAttribute)
	{
		try
		{
			Long uniqueId = group.getId();
			if (uniqueId != null)
			{
				return (Attribute) writer.updateAttribute(uniqueId, currentAttribute).getItem();
			}
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Failed to add attribute: %s, error: %s", currentAttribute.getType(), e.toString());
			e.printStackTrace();
		}

		return null;
	}

	public Attribute addAttribute(AbstractGroupWriterAdapter writer, Group group, String type, String value)
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
			
			Long uniqueId = group.getId();
			if (uniqueId != null)
			{
				return (Attribute) writer.addAttribute(uniqueId, attribute, getOwner()).getItem();
			}
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Failed to add attribute: %s, error: %s", attribute.getType(), e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public Attribute addAttribute(AbstractIndicatorWriterAdapter writer, Indicator indicator, String type, String value)
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
				return (Attribute) writer.addAttribute(uniqueId, attribute, getOwner()).getItem();
			}
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Failed to add attribute: %s, error: %s", attribute.getType(), e.toString());
			e.printStackTrace();
		}

		return null;
	}

	public Indicator getIndicator(AbstractIndicatorReaderAdapter reader, String indText)
	{

		try
		{
			Indicator indicator = (Indicator) reader.getById(indText, getOwner());
			info("Found indicator: text=%s, ind=%s", indText, indicator);
			return indicator;

		}
		catch (IOException | FailedResponseException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public Indicator createIndicator(Indicator.Type type, String indText, double rating)
	{
		Indicator ind = IndicatorUtil.createIndicator(type);
		setUniqueId(ind, indText);
		ind.setType(type.toString());
		ind.setRating(rating);
		ind.setSummary(indText);

		return ind;
	}

	public void addTags(AbstractGroupWriterAdapter<Group> writer, Long groupId, List<String> tagLabels)
	{
		List<Tag> tags = new ArrayList<>();
		for (String label : tagLabels)
		{
			Tag tag = new Tag();
			tag.setName(label);
			tags.add(tag);
		}

		addFullTags(writer, groupId, tags);
	}

	public void addFullTags(AbstractGroupWriterAdapter<Group> writer, Long groupId, List<Tag> tags)
	{

		if (tagMap == null)
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
			}
			catch (IOException | FailedResponseException e)
			{
				warn("Failed to associate tag %s to group id %d", tag.getName(), groupId);
				e.printStackTrace();
			}
		}

	}

	public void addTags(AbstractIndicatorWriterAdapter<Indicator> writer, Indicator indicator, List<String> tagLabels)
	{

		if (tagMap == null)
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
					debug("Associating uniqueId " + uniqueId + " to tag: " + tagLabel);
					writer.associateTag(uniqueId, tagLabel, getOwner());
				}
			}
			catch (IOException | FailedResponseException e)
			{
				warn("Failed to associate tag %s to indicator %s", tagLabel, uniqueId);
				e.printStackTrace();
			}
		}

	}

	public boolean deleteTag(String tagLabel)
	{
		try
		{
			ApiEntitySingleResponse response = getTagWriter().delete(tagLabel, getOwner());
			if (response.isSuccess())
			{
				return true;
			}

		}
		catch (IOException | FailedResponseException e)
		{
			// ignore
		}

		return false;
	}

	public Tag createTag(String tagLabel)
	{
		Tag tag = new TagBuilder().withName(tagLabel).createTag();
		return createTag(tag);
	}

	public TagWriterAdapter getTagWriter()
	{
		if (tagWriter == null)
		{
			tagWriter = WriterAdapterFactory.createTagWriter(getConn());
		}

		return tagWriter;
	}

	public Tag createTag(Tag tag)
	{

		ApiEntitySingleResponse response;
		try
		{
			if (getTagMap().containsKey(tag.getName()))
			{
				response = getTagWriter().update(tag, getOwner());
			}
			else
			{
				response = getTagWriter().create(tag, getOwner());
			}

			if (response.isSuccess())
			{
				getTagMap().put(tag.getName(), tag);
				return (Tag) response.getItem();
			}

		}
		catch (IOException | FailedResponseException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private void loadTagMap()
	{
		tagMap = new HashMap<>();
		TagReaderAdapter tagReader = getTagReader();
		try
		{
			for (Tag t : tagReader.getAll(getOwner()))
			{
				tagMap.put(t.getName(), t);
			}
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Unable to cache tagMap");
		}

	}

	protected void associateThreat(AbstractIndicatorReaderAdapter<Indicator> reader,
		AbstractIndicatorWriterAdapter<Indicator> writer, Indicator indicator, List<String> actors)
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
			}
			catch (IOException | FailedResponseException e)
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

		Threat threat =
			new ThreatBuilder().withOwnerName(getOwner()).withName(actor).withDateAdded(new Date()).createThreat();

		try
		{
			ApiEntitySingleResponse response = threatWriter.create(threat, getOwner());
			threat = null;
			if (response.isSuccess())
			{
				threat = (Threat) response.getItem();
			}
		}
		catch (IOException | FailedResponseException e)
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
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Unable to cache threatMap");
		}

	}

	protected void associateThreat(AbstractGroupReaderAdapter<Group> reader, AbstractGroupWriterAdapter<Group> writer,
		Long groupId, List<String> actors)
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
			}
			catch (IOException | FailedResponseException e)
			{
				warn("Unable to associate threat %s to group id %d", threat.getName(), groupId);
			}
		}

	}

	protected Adversary createAdversary(String actor)
	{
		AbstractGroupWriterAdapter adversaryWriter = getWriter(Group.Type.Adversary);
		if (adversaryWriter == null)
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
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Unable to create adversary %s", actor);
			return null;
		}

		return adversary;
	}

	private void loadAdversaryMap()
	{
		adversaryMap = new HashMap<>();
		AbstractGroupReaderAdapter<Adversary> adversaryReader =
			ReaderAdapterFactory.createAdversaryGroupReader(getConn());
		try
		{
			for (Adversary a : adversaryReader.getAll(getOwner()))
			{
				adversaryMap.put(a.getName(), a);
			}
		}
		catch (IOException | FailedResponseException e)
		{
			warn("Unable to cache adversaryMap");
		}

	}

	protected void associateAdversary(AbstractGroupReaderAdapter<Group> reader,
		AbstractGroupWriterAdapter<Group> writer, Long groupId, List<String> actors)
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
				info("Associating adversary %s [id=%d] to group id %d", adversary.getName(), adversary.getId(),
					groupId);
				writer.associateGroupAdversary(groupId, adversary.getId());
			}
			catch (IOException | FailedResponseException e)
			{
				warn("Unable to associate adversary %s to group id %d", adversary.getName(), groupId);
			}
		}

	}

	public Map<String, Threat> getThreatMap()
	{
		if (threatMap == null)
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
		if (adversaryMap == null)
		{
			loadAdversaryMap();
		}

		return adversaryMap;
	}

	public void setAdversaryMap(Map<String, Adversary> adversaryMap)
	{
		this.adversaryMap = adversaryMap;
	}

	public String basicEncoded(String user, String password)
	{
		String encoded = Base64.getEncoder().encodeToString(String.format("%s:%s", user, password).getBytes());

		return "Basic " + encoded;
	}

	public Map<String, Tag> getTagMap()
	{
		return tagMap;
	}
}
