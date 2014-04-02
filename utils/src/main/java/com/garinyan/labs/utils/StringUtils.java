package com.garinyan.labs.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

    public static String getFileName(String filename) {
        if (filename == null) {
            return null;
        }

        int index = filename.lastIndexOf("/");
        if (index != -1) {
            return filename.substring(index + 1);
        } else {
            return filename;
        }
    }

    public static String getFilePath(String filename) {
        if (filename == null) {
            return null;
        }

        int index = filename.lastIndexOf("/");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    public static String removeTrailingString(String str, String trailingStr) {
        if (str == null) {
            return null;
        }

        int index = str.lastIndexOf(trailingStr);
        int last_index = str.length() - 1;
        while (index == last_index) {
            str = str.substring(0, index);
            index = str.lastIndexOf(trailingStr);
            last_index = str.length() - 1;
        }

        return str;
    }

    public static String escapeChar(String str, String prefix, String character) {
        if (str == null) {
            return "";
        }
        return str.replace(character, prefix + character);
    }

    public static String urlEncode(String s, String format) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(s, format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    public static String format(Object obj, String format) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);

        formatter.format(format, obj);
        // Explicit argument indices may be used to re-order output.
        // formatter.format("%4$2s %3$2s %2$2s %1$2s", "a", "b", "c", "d")
        // -> " d  c  b  a"
        return sb.toString();
    }

    // gwbl700[0-2].yahoo,gwbl7005.yahoo => gwbl7000.yahoo, gwbl7001.yahoo,
    // gwbl7002.yahoo, gwbl7005.yahoo
    public static List<String> getGateways(String gateways, String szSeparator) {
        List<String> gatewaylist = new ArrayList<String>();
        if (gateways == null) {
            return gatewaylist;
        }

        String[] gatewayarr = gateways.split(szSeparator);
        for (int i = 0; i < gatewayarr.length; i++) {
            String subgateways = gatewayarr[i];
            if (subgateways == null || subgateways.equals("")) {
                continue;
            }

            Pattern p = Pattern.compile("[\\[\\]]");
            // gwbl700[0-2].yahoo
            String[] subgatewayarr = p.split(subgateways);
            if (subgatewayarr.length != 3) {
                gatewaylist.add(subgateways.trim());
                continue;
            }
            String szPrefix = subgatewayarr[0];
            String szMidPart = subgatewayarr[1];
            String szPostfix = subgatewayarr[2];
            Pattern m = Pattern.compile("[-]");
            String[] getwaynum = m.split(szMidPart);

            Integer iStart = Integer.parseInt(getwaynum[0]);
            Integer iEnd = Integer.parseInt(getwaynum[1]);

            for (int j = iStart; j <= iEnd; j++) {
                gatewaylist.add((szPrefix + j + szPostfix).trim());
            }
        }

        return gatewaylist;

    }

    public static String autocompleteEmailAddress(String email, String domain) {
        if (email == null || email.trim().equals("")) {
            return "";
        }

        String[] emails = email.split(",");
        String autocompleted = "";
        boolean bEmailConcatenated = false;
        for (int i = 0; i < emails.length; i++) {
            String address = emails[i].trim();
            if (address.equalsIgnoreCase("") || address.startsWith("@")) {
                continue;
            }
            if (!(address.contains("@"))) {
                address = address.trim() + "@" + domain;
            }
            autocompleted += (bEmailConcatenated ? "," : "") + address.trim();
            bEmailConcatenated = true;

        }
        return autocompleted;
    }

    public static String getULTGenerationString(String format, String ultformat) {
        if (format == null || ultformat == null) {
            throw new SMCommonException("The format or ultformat is null.");
        }

        // String[] ultsymbols = ultformat.split("[(,) ]+");
        String[] ultsymbols = ultformat.replaceAll("\\(", "").replaceAll("\\)", "").trim().split("[, ]+");
        if (ultsymbols == null || ultsymbols.length != 3) {
            throw new SMCommonException("The ULT format '" + ultformat + "'is incorrect.");
        }

        String s = ultsymbols[0];
        String m = ultsymbols[1];
        String l = ultsymbols[2];

        String szGenerationString = "";

        String[] formatsymbols = format.replaceAll("\\(", "").replaceAll("\\)", "").trim().split("[, ]+");
        if (formatsymbols == null) {
            throw new SMCommonException("The format '" + format + "'is incorrect.");
        }

        for (int i = 0; i < formatsymbols.length; i++) {
            if (formatsymbols[i].indexOf(":") == -1) {
                szGenerationString += s + "#'" + formatsymbols[i] + "' as " + formatsymbols[i] + ((i == formatsymbols.length - 1) ? "" : ", ");
            } else {
                String[] vals = formatsymbols[i].split(":");
                String symbol = vals[0];
                String type = vals[1];
                if (type.equals(l)) {
                    // szGenerationString += "(bag{tuple(map[])}) " + l + "#'" +
                    // symbol + "' as " + symbol + ((i == formatsymbols.length -
                    // 1) ? "" : ", ");
                    szGenerationString += l + " as l" + ((i == formatsymbols.length - 1) ? "" : ", ");
                }
            }
        }

        return szGenerationString;
    }

}
