/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.conn;

import com.threatconnect.sdk.config.Configuration;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.message.AbstractHttpMessage;
//import org.jboss.resteasy.client.ClientRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dtineo
 */
public class ConnectionUtil
{

    private static final Logger logger = Logger.getLogger(ConnectionUtil.class.getSimpleName());

    public static Properties loadProperties(String fileName) throws IOException
    {

        Properties props = new Properties();
        try
        {
            // check classloader, if running in container, this will fail on NPE
            InputStream in = ConnectionUtil.class.getResourceAsStream(fileName);
            props.load(in);
        } catch(NullPointerException npe)
        {
            props = new Properties();
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            props.load(in);
        }

        return props;
    }


    public static String getHmacSha256Signature(String signature, String apiSecretKey)
    {

        try
        {

            String calculatedSignature;
            SecretKeySpec spec = new SecretKeySpec(apiSecretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(spec);
            byte[] rawSignature = mac.doFinal(signature.getBytes());
            calculatedSignature = Base64.encodeBase64String(rawSignature);

            return calculatedSignature;

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException ex)
        {
            logger.log(Level.SEVERE, "Error creating HMAC SHA256 signature", ex);
            return null;
        }

    }

    private static String getSignature(Long headerTimestamp, String httpMethod, String urlPath, String urlQuery)
    {
        String query = (urlQuery == null ? "" : "?" + urlQuery);
        return String.format("%s%s:%s:%d", urlPath, query, httpMethod, headerTimestamp);
    }

    static void applyHeaders(Configuration config, AbstractHttpMessage message, String httpMethod, String urlPath)
    {
        applyHeaders(config, message, httpMethod, urlPath, null);
    }

    static void applyHeaders(Configuration config, AbstractHttpMessage message, String httpMethod, String urlPath, String contentType)
    {

        Long ts = System.currentTimeMillis() / 1000L;
        String sig = getSignature(ts, httpMethod, urlPath, null);
        System.err.println("toSign=" + sig + ", secretKey=" + config.getTcApiUserSecretKey());
        String hmacSig = getHmacSha256Signature(sig, config.getTcApiUserSecretKey());
        String auth = getAuthorizationText(config,hmacSig);

        message.addHeader("timestamp", "" + ts);
        message.addHeader("authorization", auth);
        message.addHeader("Accept", config.getContentType());
        if ( contentType != null )
        {
            message.addHeader("Content-Type", contentType);
        }

    }

    /*
    static void applyHeaders(Configuration config, ClientRequest message, String httpMethod, String urlPath)
    {
        applyHeaders(config, message, httpMethod, urlPath, null);
    }

    static void applyHeaders(Configuration config, ClientRequest message, String httpMethod, String urlPath, String contentType)
    {

        Long ts = System.currentTimeMillis() / 1000L;
        String sig = getSignature(ts, httpMethod, urlPath, null);
        String hmacSig = getHmacSha256Signature(sig, config.getTcApiUserSecretKey());
        String auth = getAuthorizationText(config,hmacSig);

        message.header("timestamp", "" + ts);
        message.header("authorization", auth);
        message.header("Accept", config.getContentType());
        if ( contentType != null )
        {
            message.header("Content-Type", contentType);
        }

    }
    */

    private static String getAuthorizationText(Configuration config, String hmacSig)
    {
        return String.format("TC %s:%s", config.getTcApiAccessID(), hmacSig);
    }

}