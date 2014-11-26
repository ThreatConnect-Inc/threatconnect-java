/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.conn;

import com.cyber2.api.lib.config.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author dtineo
 */
public class ConnectionUtil {

    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);

    public static Properties loadProperties(String fileName) throws IOException {

        Properties props = new Properties();
        InputStream in = ConnectionUtil.class.getClass().getResourceAsStream(fileName);
        props.load(in);

        return props;
    }



    public static String getHmacSha256Signature(String signature, String apiSecretKey) {

        try {

            String calculatedSignature;
            SecretKeySpec spec = new SecretKeySpec(apiSecretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(spec);
            byte[] rawSignature = mac.doFinal(signature.getBytes());
            calculatedSignature = Base64.encodeBase64String(rawSignature);

            return calculatedSignature;

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException ex) {
            logger.error("Error creating HMAC SHA256 signature", ex);
            return null;
        }

    }

    private static String getSignature(Long headerTimestamp, String httpMethod, String urlPath, String urlQuery) {
        String query = (urlQuery == null ? "" : "?" + urlQuery);
        return String.format("%s%s:%s:%d", urlPath, query, httpMethod, headerTimestamp);
    }

    static void applyHeaders(Configuration config, AbstractHttpMessage message, String httpMethod, String urlPath, String urlQuery) {

        Long ts = System.currentTimeMillis()/1000L;
        String sig = getSignature( ts, httpMethod, urlPath, urlQuery);
        String hmacSig = getHmacSha256Signature(sig, config.getTcApiUserSecretKey());
        String auth = String.format("TC %s:%s", config.getTcApiAccessID(), hmacSig);

        message.addHeader("timestamp", ""+ts);
        message.addHeader("authorization", auth);
        message.addHeader("Accept", config.getContentType());

    }

}
