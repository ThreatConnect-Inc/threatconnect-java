/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.service;


/**
 *
 * @author James
 */
public class ApiServiceResponse
{
    public enum ErrorCode { NOT_FOUND };
    private final String message;
    private final boolean success;
    private final ErrorCode errorCode;
    private Integer startingAt = null;
    private Integer maxPerPage = null;

   protected ApiServiceResponse()
    {
        this.message = null;
        this.success = true;
        this.errorCode = null;
    }

    protected ApiServiceResponse(String message, boolean success, ErrorCode errorCode)
    {
        this.message = message;
        this.success = success;
        this.errorCode = errorCode;
    }

    protected ApiServiceResponse(String message, boolean success)
    {
        this(message, success, success ? ErrorCode.NOT_FOUND : null);
    }

    protected ApiServiceResponse(String message)
    {
        this(message, false);
    }

    protected ApiServiceResponse(String message, ErrorCode errorCode)
    {
        this(message, errorCode == null, errorCode);
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getMessage()
    {
        return message;
    }

    public ErrorCode getErrorCode()
    {
        return errorCode;
    }



    public Integer getStartingAt()
    {
        return startingAt;
    }

    public void setStartingAt(Integer startingAt)
    {
        this.startingAt = startingAt;
    }

    public Integer getMaxPerPage()
    {
        return maxPerPage;
    }

    public void setMaxPerPage(Integer maxPerPage)
    {
        this.maxPerPage = maxPerPage;
    }
    
    protected static String getIndefiniteArticle(String string)
    {
        if ((string == null) || (string.isEmpty()))
        {
            return "";
        }
        else
        {
            String firstLetter = string.substring(0, 1);
            
            if ((firstLetter.equalsIgnoreCase("A")) || (firstLetter.equalsIgnoreCase("E")) || (firstLetter.equalsIgnoreCase("I")) || (firstLetter.equalsIgnoreCase("O")) || (firstLetter.equalsIgnoreCase("U")))
            {
                return "an";
            }
            else
            {
                return "a";
            }
        }
    }
}
