/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.entity.format;

import java.io.IOException;
import java.util.Date;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author jspies
 */
public class DateSerializer extends JsonSerializer<Date>
{

    @Override
    public void serialize(Date date, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException
    {
        DateTime dt = new DateTime(date);
        DateTimeFormatter dtfmt = ISODateTimeFormat.dateTimeNoMillis();
        jg.writeString(dtfmt.print(dt));
    }
    
}
