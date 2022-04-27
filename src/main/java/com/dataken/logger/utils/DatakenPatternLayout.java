package com.dataken.logger.utils;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class DatakenPatternLayout extends PatternLayout {

    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new DatakenPatternParser(pattern);
    }
}
