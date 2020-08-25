package com.uae.tambolaapp.helper_classes;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Rohan Kumar on 08/03/17.
 * Copyright © 2016 Appster. All rights reserved.
 */

public class StringUtils {


    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().length() == 0 || s.equalsIgnoreCase("null");
    }

    public static boolean isNullOrEmpty(ArrayList arraylist) {
        return arraylist == null || arraylist.size() == 0;
    }

    public static boolean isNullOrEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNullOrEmpty(Arrays[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isValidEmail(String s) {
        if (isNullOrEmpty(s)) {
            return false;
        }
        return Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$").matcher(s).find();
    }

    public static String trim(String s, String s1) {
        String s2;
        do {
            s2 = s;
            if (!s.endsWith(s1)) {
                break;
            }
            s = s.substring(0, s.length() - s1.length());
        } while (true);
        for (; s2.startsWith(s1); s2 = s2.substring(s1.length(), s2.length())) {
        }
        return s2;
    }

    public static String toCamelCase(String s) {
        String[] parts = s.split(" ");
        String camelCaseString = "";
        for (String part : parts) {
            camelCaseString = camelCaseString + " " + toProperCase(part);
        }
        return camelCaseString.trim();
    }

    static String toProperCase(String s) {
        if (!StringUtils.isNullOrEmpty(s)) {
            return s.substring(0, 1).toUpperCase() +
                    s.substring(1).toLowerCase();
        }
        return "";
    }

    public static String trimFirst(String s) {
        if (!StringUtils.isNullOrEmpty(s) && s.startsWith("/"))
            return s.substring(1);

        return s;
    }
//
//    public static String decodeStringBase64(String s) {
//        String decodedString = "";
//        try {
//            String afterDecode = URLDecoder.decode(s, "UTF-8");
//            byte[] valueDecoded = new byte[0];
//            valueDecoded = Base64.decode(afterDecode.getBytes("UTF-8"), Base64.DEFAULT);
//            decodedString = new String(valueDecoded);
//        } catch (UnsupportedEncodingException ex) {
//            LogUtils.LOGE("Splash", "Failed to decode");
//        } catch (Exception ex) {
//            LogUtils.LOGE("Splash", "Failed to decode");
//        }
//        return decodedString;
//    }

    public static boolean isNullOrEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static String dataFormat(String no_share) {
        String noFormat = "";
        if (Double.parseDouble(String.valueOf(no_share)) >= 1000 && Double.parseDouble(String.valueOf(no_share)) < 1000000) {
            Double f = Double.parseDouble(String.valueOf(no_share)) / 1000;
            String test = String.valueOf(f);
            String[] finalVal = new String[2];
            finalVal = test.split("E");
            f = Double.parseDouble(String.valueOf(no_share));
            if (f % 1000 == 0) {
                noFormat = String.valueOf(Integer.parseInt(String.valueOf(no_share)) / 1000) + "K ";
            } else {
                noFormat = roundOff(Double.parseDouble(finalVal[0])) + "K ";
            }
        }

        return noFormat;
    }

    private static String roundOff(Double f) {
        String result = "";
        DecimalFormat decimalformat = new DecimalFormat("#.#");
        result = String.valueOf(decimalformat.format(f));
        return result;

    }

    public static long getGameID() {

        int len = 6;
        String random = "0";
        Random r = new Random();
        HashSet<Integer> set = new HashSet<Integer>();
        while (set.size() < 1) {
            int ran = r.nextInt(99) + 100000;
            set.add(ran);
        }
        random = String.valueOf(len);
        for (int random1 : set) {
            System.out.println(random1);
            random = Integer.toString(random1);

        }

        return Long.parseLong(random);
    }

}
