package com.nayanzin.configurationbasics.profiles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Configuration
public class Application {

    private Log log = LogFactory.getLog(getClass());

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.getEnvironment().setActiveProfiles("dev");
        ac.register(Application.class);
        ac.refresh();
    }

    // <3> dev or no profile
    @Bean
    InitializingBean witch(
            Environment env,
            @Value("@{configuration.projectName}") String projectName) {
        return () -> {
            log.info("activeProfiles: '" +
                    StringUtils.arrayToCommaDelimitedString(env.getActiveProfiles()));
            log.info("configuration.projectName: " + projectName);
        };
    }

    // <1> register profile specific configuration bean with prod profile.
    @Configuration
    @Profile("prod")
    @PropertySource("some-prod.properties")
    public static class ProdConfiguration {
        @Bean
        InitializingBean init() {
            return () -> LogFactory.getLog(getClass()).info("prod InitializingBean");
        }
    }

    // <2> dev or no profile
    @Configuration
    @Profile({"dev", "no"})
    @PropertySource("some.properties")
    public static class DefaultConfiguration {
        @Bean
        InitializingBean init() {
            return () -> LogFactory.getLog(getClass()).info("default InitializingBean");
        }
    }
}
