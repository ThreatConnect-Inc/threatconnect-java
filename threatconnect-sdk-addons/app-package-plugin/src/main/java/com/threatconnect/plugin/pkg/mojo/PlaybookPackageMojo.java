package com.threatconnect.plugin.pkg.mojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.threatconnect.app.addons.util.JsonUtil;
import com.threatconnect.app.addons.util.config.InvalidFileException;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.ParamDataType;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.plugin.pkg.Profile;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Mojo(name = "playbook-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class PlaybookPackageMojo extends AbstractAppPackageMojo
{
	private static final String ELEMENT_PLAYBOOK_TIGGER_LIST = "playbookTriggerList";
	private static final String ELEMENT_TRIGGER_TYPE = "type";
	private static final String ELEMENT_KEY = "key";
	private static final String ELEMENT_VALUE = "value";
	
	//:TODO: need a special trigger for this, testing with http link for now
	private static final String ELEMENT_TRIGGER_PARAMS = "httpResponseHeader";
	private static final String VALID_TRIGGER_TYPE = "HttpLink";
	
	private final Gson gson;
	private final JsonParser jsonParser;
	
	public PlaybookPackageMojo()
	{
		//create a new gson object to write the install file
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.jsonParser = new JsonParser();
	}
	
	@Override
	protected void writePrimaryInstallJson(final Profile<Install> profile, final File primaryJsonDestination)
		throws IOException, ValidationException, InvalidFileException
	{
		//retrieve the install profile
		Install install = profile.getSource();
		
		//load the playbook file and read the parameters
		JsonElement playbookRoot = readPlaybookFile(primaryJsonDestination.getParentFile(), profile);
		Map<String, String> parameters = extractJobParameters(playbookRoot);
		
		//look for all of the parameters
		for (Map.Entry<String, String> entry : parameters.entrySet())
		{
			//retrieve the parameter by the key name
			Param param = getOrAddParam(install, entry.getKey());
			
			//update the param default value
			param.setDefaultValue(entry.getValue());
		}
		
		try (OutputStream outputStream = new FileOutputStream(primaryJsonDestination))
		{
			//write the install file
			outputStream.write(gson.toJson(install).getBytes());
		}
	}
	
	@Override
	protected void validateReferencedFiles(final File explodedDir, final Profile<Install> profile)
		throws ValidationException, InvalidFileException, IOException
	{
		getPlaybookFile(explodedDir, profile);
	}
	
	private Map<String, String> extractJobParameters(final JsonElement playbookRoot) throws ValidationException
	{
		//holds the map to return
		Map<String, String> parameterMap = new HashMap<String, String>();
		
		//retrieve the playbook trigger list
		JsonArray playbookTriggerList = JsonUtil.getAsJsonArray(playbookRoot, ELEMENT_PLAYBOOK_TIGGER_LIST);
		
		//validate that the trigger list is only 1 item
		if (null == playbookTriggerList || playbookTriggerList.size() != 1)
		{
			throw new ValidationException("Invalid " + ELEMENT_PLAYBOOK_TIGGER_LIST
				+ " in referenced playbook file. Array must contain exactly 1 element");
		}
		
		//retrieve the playbook trigger
		JsonObject playbookTrigger = playbookTriggerList.get(0).getAsJsonObject();
		
		//validate the trigger type
		String triggerType = JsonUtil.getAsString(playbookTrigger, ELEMENT_TRIGGER_TYPE);
		if (!VALID_TRIGGER_TYPE.equals(triggerType))
		{
			throw new ValidationException(
				"Invalid trigger in playbook. Found \"" + triggerType + "\", expected \"" + VALID_TRIGGER_TYPE + "\"");
		}
		
		//retrieve the parameters for this trigger and make sure it is not null or empty
		String parametersContent = JsonUtil.getAsString(playbookTrigger, ELEMENT_TRIGGER_PARAMS);
		if (null != parametersContent && !parametersContent.isEmpty())
		{
			//parse the parameters content as an array
			JsonArray parametersArray = jsonParser.parse(parametersContent).getAsJsonArray();
			
			//for each of the parameters in the array
			for (JsonElement parameter : parametersArray)
			{
				//parse the key/value from the array and add it to this map
				parameterMap.put(JsonUtil.getAsString(parameter, ELEMENT_KEY),
					JsonUtil.getAsString(parameter, ELEMENT_VALUE));
			}
		}
		
		return parameterMap;
	}
	
	private JsonElement readPlaybookFile(final File rootDir, final Profile<Install> profile)
		throws ValidationException, IOException
	{
		File playbookFile = getPlaybookFile(rootDir, profile);
		try (InputStream inputStream = new FileInputStream(playbookFile))
		{
			return jsonParser.parse(new InputStreamReader(inputStream));
		}
	}
	
	private File getPlaybookFile(final File rootDir, final Profile<Install> profile) throws ValidationException
	{
		//make sure this playbook file exists
		File file = new File(rootDir.getAbsolutePath() + File.separator + profile.getSource().getProgramMain());
		if (!file.exists())
		{
			throw new ValidationException(generateReferencedFileMissingMessage(profile.getSource().getProgramMain()));
		}
		
		return file;
	}
	
	private Param getOrAddParam(final Install install, final String paramName)
	{
		//for each of the params in the install object
		for (Param param : install.getParams())
		{
			//check if this is the param we are looking for
			if (param.getName().equals(paramName))
			{
				return param;
			}
		}
		
		//no param was found so create a new one and add it to the params list
		Param param = new Param();
		param.setName(paramName);
		param.setLabel(paramName);
		param.setType(ParamDataType.String);
		install.getParams().add(param);
		
		return param;
	}
}
