package com.threatconnect.sdk.util;

/**
 * Created by cblades on 12/28/2015.
 */
public class ApiFilterParser
{
    public static final String ParseApiFilters(ApiFilterType[] filters)
    {
        String filtersValue = "";

        for (ApiFilterType filter : filters)
        {
            filtersValue += filter.getName() + filter.getOperator().representation + filter.getValue() + ",";
        }

        filtersValue = filtersValue.substring(0, filtersValue.length() - 1);

        return filtersValue;
    }
}
