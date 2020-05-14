package com.base.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import okhttp3.Headers;

public class GzipUtil {
    private static final String TAG = "GzipUtil";

    public static final String COMPRESS_KEY = "isCompress";
    private static final String COMPRESS_VALUE = "1";


    /**
     * 使用gzip进行压缩
     */

    public static String compress(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return new String(out.toByteArray(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用gzip进行解压缩
     */

    public static byte[] uncompress(byte[] compressed) {
        byte[] result = new byte[1];
        if (compressed == null) {
            return result;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        try {
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            result = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
        return result;

    }

    public static boolean isGzip(Headers headers) {

        List<String> headerValue = headers.values(COMPRESS_KEY);
        for (String value : headerValue) {
            return value.equals(COMPRESS_VALUE);
        }
        return false;
    }
}

