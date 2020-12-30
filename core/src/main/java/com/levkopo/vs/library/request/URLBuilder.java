package com.levkopo.vs.library.request;

import com.levkopo.vs.value.Value;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class URLBuilder {


    /**
     * Build URL string from Map of params. Nested Map and Collection is also supported
     *
     * @param params   Map of params for constructing the URL Query String
     * @return String of type key=value&...key=value
     * if encoding isnot supported
     */
    public static String httpBuildQuery(Map<Object, Value> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Object, Value> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }

            Object name = entry.getKey();
            Object value = entry.getValue().value();
            if(value instanceof Number){
                Number number = (Number) value;
                if(number.doubleValue()==number.intValue())
                    value = number.intValue();
            }


            sb.append(encodeParam(name));
            sb.append("=");
            sb.append(encodeParam(value));
        }
        return sb.toString();
    }

    private static String encodeParam(Object param) {
        try {
            return URLEncoder.encode(String.valueOf(param), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
