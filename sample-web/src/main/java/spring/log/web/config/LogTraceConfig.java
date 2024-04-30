package spring.log.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import spring.log.trace.core.LogTrace;
import spring.log.trace.core.LogTraceImpl;

@Profile("dev")
@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new LogTraceImpl();
    }
}
