package com.threatconnect.app.playbooks.content.serialize;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author Greg Marut
 */
public class PlaybookVariableTypeJsonSerializer extends StdDeserializer<PlaybookVariableType>
{
	public PlaybookVariableTypeJsonSerializer()
	{
		super(PlaybookVariableType.class);
	}
	
	@Override
	public PlaybookVariableType deserialize(final JsonParser jp, final DeserializationContext ctxt)
		throws IOException
	{
		JsonNode node = jp.getCodec().readTree(jp);
		return new PlaybookVariableType(node.get("type").getTextValue());
	}
}