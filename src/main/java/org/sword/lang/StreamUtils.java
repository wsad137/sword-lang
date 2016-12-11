/**
 *
 */
package org.sword.lang;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ChengNing
 * @date 2014年12月11日
 */
public class StreamUtils {

    /**
     * stream to string
     *
     * @param is
     * @return
     */
    public static String streamToString(InputStream is) {
        String text = null;
        try {
            text = IOUtils.toString(is, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
//    public static String streamToString(InputStream is) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int i = -1;
//        try {
//            while ((i = is.read()) != -1) {
//                baos.write(i);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return baos.toString();
//    }

    /**
     * string to stream
     *
     * @param str
     * @return
     */
    public static InputStream strToStream(String str) {
        InputStream is = null;
        try {
            is = IOUtils.toInputStream(str, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
//    /**
//     * string to stream
//     *
//     * @param str
//     * @return
//     */
//    public static InputStream strToStream(String str) {
//        InputStream is = new ByteArrayInputStream(str.getBytes());
//        InputStream inputStream = IOUtils.toInputStream(str, "utf-8");
//        return is;
//    }

}
