/*
 * Object used in transorming api url query parameters into sql query parameters
 */
package com.threatconnect.sdk.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author mbrown
 */
public abstract class ApiFilterType <T> {
    private String name;
    private String value;
    private OPERATOR operator;

    public enum OPERATOR {
        GT(">"), LT("<"), EQ("="), NOT("^");

        public String representation;

        OPERATOR(String representation)
        {
            this.representation = representation;
        }
    }

    private ApiFilterType(String filterName)
    {
        name = filterName;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

    public OPERATOR getOperator() {
        return operator;
    }


    private ApiFilterType greaterThan(T value)
    {
        this.operator = OPERATOR.GT;
        this.value = stringifyValue(value);
        return this;
    }

    private ApiFilterType lessThan(T value)
    {
        this.operator = OPERATOR.LT;
        this.value = stringifyValue(value);

        return this;
    }

    private ApiFilterType equal(T value)
    {
        this.operator = OPERATOR.EQ;
        this.value = stringifyValue(value);

        return this;
    }

    private ApiFilterType not(T value)
    {
        this.operator = OPERATOR.NOT;
        this.value = stringifyValue(value);

        return this;
    }

    private String stringifyValue(T value)
    {
        if (value instanceof Date)
        {
            TimeZone tz = TimeZone.getDefault();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            df.setTimeZone(tz);
            return df.format((Date)value);
        } else
        {
            return "" + value;
        }
    }


    /*
     * Intializers...intialize with filter name
     *
     */
    public static _GTLTEQFilter<Integer> filterConfidence()
    {
        return new _GTLTEQFilter<>("confidence");
    }

    public static _GTLTFilter<BigDecimal> filterRating()
    {
        return new _GTLTFilter<>("rating");
    }

    public static _GTLTFilter<Date> filterDateAdded()
    {
        return new _GTLTFilter<>("dateAdded");

    }

    public static _EQNOTFilter<String> filterSummary()
    {
        return new _EQNOTFilter<>("summary");

    }

    public static _GTLTFilter<Double> filterThreatAssessRating()
    {
        return new _GTLTFilter<>("threatAssessRating");

    }

    public static _GTLTFilter<Double> filterThreatAssessConfidence()
    {
        return new _GTLTFilter<>("threatAssessConfidence");

    }

    public static _EQFilter<String> filterCountryCode()
    {
        return new _EQFilter<>("countryCode");

    }

    public static _EQFilter<String> filterOrganizaton()
    {
        return new _EQFilter<>("organization");

    }

    public static _EQFilter<Integer> filterASN()
    {
        return new _EQFilter<>("asn");
    }

    public static _EQFilter<Boolean> filterWhoisActive()
    {
        return new _EQFilter<>("whoisActive");
    }

    public static _EQFilter<Boolean> filterDnsActive()
    {
        return new _EQFilter<>("dnsActive");

    }

    public static _EQNOTFilter<String> filterName()
    {
        return new _EQNOTFilter<>("name");

    }

    public static _EQFilter<String> filterFileType()
    {
        return new _EQFilter<>("fileType");
    }

    public static _GTLTEQFilter<Integer> filterWeight()
    {
        return new _GTLTEQFilter<>("weight");
    }

    public static class _GTLTEQFilter<T> extends ApiFilterType<T>
    {
        public _GTLTEQFilter(String filterName)
        {
            super(filterName);
        }

        public ApiFilterType greaterThan(T value)
        {
            return super.greaterThan(value);
        }

        public ApiFilterType lessThan(T value)
        {
            return super.lessThan(value);
        }

        public ApiFilterType equal(T value)
        {
            return super.equal(value);
        }
    }

    public static class _GTLTFilter<T> extends ApiFilterType<T>
    {
        public _GTLTFilter(String filterName)
        {
            super(filterName);
        }

        public ApiFilterType greaterThan(T value)
        {
            return super.greaterThan(value);
        }

        public ApiFilterType lessThan(T value)
        {
            return super.lessThan(value);
        }
    }

    public static class _EQFilter<T> extends ApiFilterType<T>
    {
        public _EQFilter(String filterName)
        {
            super(filterName);
        }

        public ApiFilterType equal(T value)
        {
            return super.equal(value);
        }
    }

    public static class _EQNOTFilter<T> extends ApiFilterType<T>
    {
        public _EQNOTFilter(String filterName)
        {
            super(filterName);
        }

        public ApiFilterType equal(T value)
        {
            return super.equal(value);
        }

        public ApiFilterType not(T value)
        {
            return super.not(value);
        }
    }

}
