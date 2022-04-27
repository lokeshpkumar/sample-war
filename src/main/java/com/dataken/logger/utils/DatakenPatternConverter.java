package com.dataken.logger.utils;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

public class DatakenPatternConverter extends PatternConverter {

    @Override
    protected String convert(LoggingEvent loggingEvent) {
        return "<TenantId>";
    }
}
