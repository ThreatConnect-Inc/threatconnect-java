package com.threatconnect.sdk.util;

import com.threatconnect.sdk.server.entity.Indicator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dtineo on 5/17/15.
 */
public class IndicatorUtil
{

    private static final Map<String,String> indicatorMap = new HashMap<>();
    private static final String HOST = "Host";
    private static final String IPv4_ADDRESS = "IPv4 Address";
    private static final String IPv6_ADDRESS = "IPv6 Address";
    private static final String EMAIL = "Email Address";
    private static final String URL = "URL";
    private static final String MD5 = "MD5";
    private static final String SHA_1 = "SHA-1";
    private static final String SHA_256 = "SHA-256";


    static {

            indicatorMap.put(HOST,"\\b(([a-zA-Z0-9\\-]+)\\.)+(?!exe|php|dll|doc|docx|txt|rtf|odt|xls|xlsx|ppt|pptx|bin|pcap|ioc|pdf|mdb|asp|html|xml|jpg|gif|png|lnk|log|vbs|lco|bat|shell|quit|pdb|vbp|bdoda|bsspx|save|cpl|wav|tmp|close|pl|py|ico|ini|sleep|run|dat|scr|jar|jxr|apt|w32|css|js|xpi|class|apk)([a-zA-Z]{2,5})\\b");
            indicatorMap.put(IPv4_ADDRESS,"\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
            indicatorMap.put(IPv6_ADDRESS,"(S*([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}S*|S*([0-9a-fA-F]{1,4}:){1,7}:S*|S*([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}S*|S*([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}S*|S*([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}S*|S*([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}S*|S*([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}S*|S*[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})S*|S*:((:[0-9a-fA-F]{1,4}){1,7}|:)S*|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))");
            indicatorMap.put(EMAIL,"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            indicatorMap.put(URL,"\\b(https?|sftp|ftp|file)://[-a-z0-9+&@#/%?=~_|!:,.;]*[a-z0-9+&@#/%=~_|]");
            indicatorMap.put(MD5,"\\b([a-fA-F\\d]{32})\\b");
            indicatorMap.put(SHA_1,"\\b([a-fA-F\\d]{40})\\b");
            indicatorMap.put(SHA_256,"\\b([a-fA-F\\d]{64})\\b");

    }


    public static Indicator.Type getIndicatorType(String text)
    {
        for(Map.Entry<String,String> entry : indicatorMap.entrySet())
        {
            Pattern pattern = Pattern.compile(entry.getValue(), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if ( matcher.matches() )
            {
                return getIndicatorTypeFor(entry.getKey());
            }
        }

        return null;
    }

    private static Indicator.Type getIndicatorTypeFor(String key)
    {
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
}
