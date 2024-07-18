package com.github.phpple.errorcode.lib.util;

import java.util.HashMap;
import java.util.Map;

public class SimpleTpl {
    public static final String BEGIN_FLAG = "${";
    public static final String END_FLAG = "}";
    public static final int BEGIN_FLAG_LEN = BEGIN_FLAG.length();
    public static final int END_FLAG_LEN = END_FLAG.length();

    /**
     * compile the source by args
     * @param source
     * @param args
     * @return
     */
    public static String compile(String source, Map<String, String> args) {
        if (source == null || source.length() == 0) {
            return source;
        }
        if (args == null) {
            args = new HashMap<>();
        }
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (true) {
            int startPos = source.indexOf(BEGIN_FLAG, pos);
            if (startPos == -1) {
                sb.append(source.substring(pos));
                break;
            }
            int endPos = source.indexOf(END_FLAG, startPos + BEGIN_FLAG_LEN);
            if (endPos == -1) {
                sb.append(source.substring(pos));
            }
            sb.append(source.substring(pos, startPos));
            String key = source.substring(startPos + BEGIN_FLAG_LEN, endPos);

            if (args.containsKey(key)) {
                sb.append(args.get(key));
            } else {
                sb.append("");
            }
            pos = endPos + END_FLAG_LEN;
        }
        return sb.toString();
    }
}
