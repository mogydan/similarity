package com.mogydan.similarity.utils;

import com.google.common.io.Resources;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.charset.Charset;

import static com.google.common.io.Resources.getResource;

public class Utils {
    @SneakyThrows
    public static String resourceAsString(String resourcePath) {
        URL url = getResource(resourcePath);
        return Resources.toString(url, Charset.defaultCharset());
    }
}
