package com.dataken.logger.utils;

import org.apache.log4j.helpers.PatternParser;

public class DatakenPatternParser extends PatternParser {

    private static final char TENANT_ID_CHAR = 'T';

    public DatakenPatternParser(String pattern) {
        super(pattern);
    }

    @Override
    protected void finalizeConverter(char c) {
        switch (c) {
            case TENANT_ID_CHAR:
                currentLiteral.setLength(0);
                addConverter(new DatakenPatternConverter());
                break;
            default:
                super.finalizeConverter(c);
        }
    }
}
