package com.h3c.vdi.viewscreen.utils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryUtils {

    public static List<Integer> getIdListAsInteger(String ids) {
        List<Integer> returnValue = new ArrayList<Integer>();
        for (String id : ids.split(",")) {
            returnValue.add(Integer.valueOf(id));
        }
        return returnValue;
    }

    public static List<Long> getIdListAsLong(String ids) {
        List<Long> returnValue = new ArrayList<Long>();
        for (String id : ids.split(",")) {
            returnValue.add(Long.valueOf(id));
        }
        return returnValue;
    }

    public static List<String> getIdListAsString(String ids) {
        return new ArrayList<String>(Arrays.asList(ids.split(",")));
    }

}
