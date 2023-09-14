package com.omerfurkan.studentforum.configurations;

import com.google.common.flogger.FluentLogger;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class LoggerConfiguration {

    @Bean
    public FluentLogger logger() {
        return FluentLogger.forEnclosingClass();
    }
}
