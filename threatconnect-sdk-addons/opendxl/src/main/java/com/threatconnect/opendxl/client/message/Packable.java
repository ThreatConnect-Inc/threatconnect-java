package com.threatconnect.opendxl.client.message;

import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

/**
 * Allows the implementing object to serialize itself using the msgpack format
 *
 * @author Greg Marut
 */
public interface Packable
{
	void pack(MessagePacker packer) throws IOException;
	
	void unpack(MessageUnpacker unpacker) throws IOException;
}
