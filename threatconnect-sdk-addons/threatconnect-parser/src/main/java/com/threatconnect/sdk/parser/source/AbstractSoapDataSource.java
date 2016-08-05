package com.threatconnect.sdk.parser.source;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.Service;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gshepherd
 */
public abstract class AbstractSoapDataSource implements DataSource
{

    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    protected Service service;
    private final HandlerResolver handlerResolver;

    public AbstractSoapDataSource(Service serv, HandlerResolver hr)
    {
        super();
        this.service = serv;
        this.handlerResolver = hr;
    }

    @Override
    public InputStream read() throws IOException
    {
        //here we call the soap web service and get the data back for processing
        try
        {
            //Need to set our handler here so auth values get in the header or whatever else is required.
            service.setHandlerResolver(handlerResolver);
            return callWebService();

        } catch (Exception ex)
        {
            logger.error("Calling webservice", ex);
        }
        return null;
    }

    protected abstract InputStream callWebService();
}
