package com.threatconnect.sdk.app;


import java.io.IOException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by dtineo on 5/15/15.
 */
public class AppUtil
{
    private Type type = Type.System;
    private Map<String,String> map = null;

    private static enum Type {
        System, Map
    }

    public static AppUtil getMapInstance(Map<String,String> map)
    {
        AppUtil appUtil = new AppUtil();
        appUtil.type = Type.Map;
        appUtil.map = map;

        return appUtil;
    }

    public static AppUtil getSystemInstance()
    {
        AppUtil appUtil = new AppUtil();
        appUtil.type = Type.System;

        return appUtil;
    }

    protected AppUtil() {}

    protected String p(String propName)
    {
        return this.type==Type.Map ? map.get(propName) : System.getProperty(propName);
    }

    public String getTcLogPath() {
        return p("tc_log_path");
    }

    public String getTcTempPath() {
        return p("tc_temp_path");
    }

    public String getTcOutPath() {
        return p("tc_out_path");
    }

    public String getTcApiPath() {
        return p("tc_api_path");
    }

    public Integer getTcSpaceElementId() {

        String id = p("tc_space_element_id");

        return id == null ? null : Integer.valueOf(id);
    }

    public String getTcApiAccessID() {
        return p("api_access_id");
    }

    public String getTcApiUserSecretKey() {
        return p("api_secret_key");
    }

    public String getTcApiToken() {
        return p("tc_api_token");
    }


   public String getApiDefaultOrg() {
        return p("api_default_org");
    }

    public Integer getApiMaxResults() {
        return getApiMaxResults(350);
    }

    public Integer getApiMaxResults(int defaultMax) {
        String p = p("api_max_results");
        return p == null ? defaultMax : Integer.valueOf(defaultMax);
    }

    public String getOwner() {
        return p("owner");
    }

    public Level getTcLogLevel() {
        return getTcLogLevel("INFO");
    }

    public Level getTcLogLevel(String defaultLevel) {
        String level = p("tc_log_level");

        return level == null ? Level.parse(defaultLevel) : Level.parse(level.toUpperCase());
    }

    public static void configureLogger(String logFilename, Level level) throws IOException
    {

        System.err.println("configureLogger....");
        // suppress the logging output to the console
        /*
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }
        */

        Logger logger = Logger.getLogger("");
        logger.setLevel(level);
        FileHandler fileHandler = new FileHandler(logFilename);

        Formatter formatterTxt = new SimpleFormatter();
        fileHandler.setFormatter(formatterTxt);
        logger.addHandler(fileHandler);

    }

}
