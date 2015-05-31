package com.threatconnect.sdk.util;

import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractWriterAdapter;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.entity.File;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Url;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dtineo on 5/17/15.
 */
public class IndicatorUtil
{

    private static final Map<String,Pattern> indicatorMap = new HashMap<>();
    private static final String HOST = "Host";
    private static final String IPv4_ADDRESS = "IPv4 Address";
    private static final String IPv6_ADDRESS = "IPv6 Address";
    private static final String EMAIL = "Email Address";
    private static final String URL = "URL";
    private static final String MD5 = "MD5";
    private static final String SHA_1 = "SHA-1";
    private static final String SHA_256 = "SHA-256";


    static {

            indicatorMap.put(HOST, Pattern.compile("\\b(([a-zA-Z0-9\\-]+)\\.)+(?!exe|php|dll|doc|docx|txt|rtf|odt|xls|xlsx|ppt|pptx|bin|pcap|ioc|pdf|mdb|asp|html|xml|jpg|gif|png|lnk|log|vbs|lco|bat|shell|quit|pdb|vbp|bdoda|bsspx|save|cpl|wav|tmp|close|pl|py|ico|ini|sleep|run|dat|scr|jar|jxr|apt|w32|css|js|xpi|class|apk)([a-zA-Z]{2,5})\\b", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(IPv4_ADDRESS,Pattern.compile("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(IPv6_ADDRESS,Pattern.compile("(S*([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}S*|S*([0-9a-fA-F]{1,4}:){1,7}:S*|S*([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}S*|S*([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}S*|S*([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}S*|S*([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}S*|S*([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}S*|S*[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})S*|S*:((:[0-9a-fA-F]{1,4}){1,7}|:)S*|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(EMAIL,Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(URL,Pattern.compile("\\b(https?|sftp|ftp|file)://[-a-z0-9+&@#/%?=~_|!:,.;]*[a-z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(MD5,Pattern.compile("\\b([a-fA-F\\d]{32})\\b", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(SHA_1,Pattern.compile("\\b([a-fA-F\\d]{40})\\b", Pattern.CASE_INSENSITIVE));
            indicatorMap.put(SHA_256,Pattern.compile("\\b([a-fA-F\\d]{64})\\b", Pattern.CASE_INSENSITIVE));

    }


    public static Indicator.Type getIndicatorType(String text)
    {
        String key = getIndicatorKey(text);
        return getIndicatorTypeFor(key);
    }

    private static String getIndicatorKey(String text)
    {

        for(Map.Entry<String,Pattern> entry : indicatorMap.entrySet())
        {
            Matcher matcher = entry.getValue().matcher(text);
            if ( matcher.matches() )
            {
                return entry.getKey();
            }
        }

        return null;
    }

    private static Indicator.Type getIndicatorTypeFor(String key)
    {
        if ( key == null )
        {
            return null;
        }

        switch(key)
        {
            case HOST:
                return Indicator.Type.Host;
            case IPv4_ADDRESS:
            case IPv6_ADDRESS:
                return Indicator.Type.Address;
            case EMAIL:
                return Indicator.Type.EmailAddress;
            case URL:
                return Indicator.Type.Url;
            case MD5:
            case SHA_1:
            case SHA_256:
                return Indicator.Type.File;

        }

        return null;
    }

    public static Indicator createIndicator(Indicator.Type type)
    {
        switch(type)
        {
            case Host:
                return new Host();
            case EmailAddress:
                return new EmailAddress();
            case Address:
                return new Address();
            case File:
                return new File();
            case Url:
                return new Url();
        }

        return null;
    }

    public static String getUniqueId(Indicator indicator)
    {
        if ( indicator instanceof Host )
        {
            return ((Host) indicator).getHostName();

        } else if ( indicator instanceof EmailAddress )
        {

            return ((EmailAddress) indicator).getAddress();

        } else if ( indicator instanceof Address )
        {

            return ((Address) indicator).getIp();

        } else if ( indicator instanceof  Url )
        {

            return ((Url) indicator).getText();

        } else if ( indicator instanceof  File )
        {
            File file = (File)indicator;

            if ( file.getMd5() != null )
            {
                return file.getMd5();
            } else if ( file.getSha1() != null )
            {
                return file.getSha1();
            } else if ( file.getSha256() != null )
            {
                return file.getSha256();
            }

        }

        return null;
    }

    public static void setUniqueId(Indicator indicator, String uniqueId)
    {

        if ( indicator instanceof Host )
        {

            ((Host) indicator).setHostName(uniqueId);

        } else if ( indicator instanceof EmailAddress )
        {

            ((EmailAddress) indicator).setAddress(uniqueId);

        } else if ( indicator instanceof Address )
        {

            ((Address) indicator).setIp(uniqueId);

        } else if ( indicator instanceof  Url )
        {

            ((Url) indicator).setText( uniqueId );

        } else if ( indicator instanceof  File )
        {

            String key = getIndicatorKey( uniqueId );
            switch (key)
            {
                case MD5:
                    ((File) indicator).setMd5(uniqueId);
                    break;
                case SHA_1:
                    ((File) indicator).setSha1(uniqueId);
                    break;
                case SHA_256:
                    ((File) indicator).setSha256(uniqueId);
                    break;
            }
        }

    }

    public static void associate(AbstractIndicatorWriterAdapter writer, Indicator source, Indicator target, String owner) throws IOException, FailedResponseException
    {

        if ( source == null || target == null )
        {
            return;
        }

        if ( target instanceof Host )
        {
            writer.associateIndicatorHost(getUniqueId(source), ((Host) target).getHostName(), owner);

        } else if ( target instanceof EmailAddress )
        {

            writer.associateIndicatorEmailAddress(getUniqueId(source), ((EmailAddress) target).getAddress(), owner);

        } else if ( target instanceof Address )
        {

            writer.associateIndicatorAddress(getUniqueId(source), ((Address) target).getIp(), owner);

        } else if ( target instanceof  Url )
        {

            writer.associateIndicatorUrl(getUniqueId(source), ((Url) target).getText(), owner);

        } else if ( target instanceof  File )
        {

            String hash = ((File)target).getMd5();
            if ( hash == null )
            {
                hash = ((File)target).getSha1();
            }
            if ( hash == null )
            {
                hash = ((File)target).getSha256();
            }
            writer.associateIndicatorFile(getUniqueId(source), hash, owner);

        }


    }
}
