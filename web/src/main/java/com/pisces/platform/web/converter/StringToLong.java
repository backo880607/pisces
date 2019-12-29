package com.pisces.platform.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

public class StringToLong implements Converter<String, Long> {

    @Override
    public Long convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        String trimmed = StringUtils.trimAllWhitespace(source);
        if (!trimmed.isEmpty()) {
            if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
                trimmed = trimmed.substring(1, trimmed.length() - 1);
            }
        }
        return NumberUtils.parseNumber(trimmed, Long.class);
    }

}
