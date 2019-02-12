package com.nayanzin.configurationbasics.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

// <1> configures a PropertySource from a .properties file.
@PropertySource("some.properties")
@Configuration
public class Application {

    private final Log log = LogFactory.getLog(getClass());
    // <3> Don't decorate fields with the @Value, it frustrates testing.
    @Value("${configuration.projectName}")
    private String fieldValue;

    // <4> Decorate constructor parameter
    public Application(@Value("${configuration.projectName}") String fieldValue) {
        log.info("Application constructor: " + fieldValue);
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Application.class);
    }

    // <2> configured as a static bean, because must be invoked earlier in Spring initialization life cicle.
    @Bean
    static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    // <5> Use setter methods
    @Value("${configuration.projectName}")
    public void setFieldValue(String fieldValue) {
        log.info("setProjectName: " + fieldValue);
    }

    // <6> Inject Spring Environment object
    @Autowired
    void setEnvironment(Environment env) {
        log.info("setEnvironment: " + env.getProperty("configuration.projectName"));
    }

    // <7> Both
    @Bean
    InitializingBean both(Environment env,
                          @Value("${configuration.projectName}") String projectName) {
        return () -> {
            log.info("@Bean with both dependencies (projectName): " + projectName);
            log.info("@Bean with both dependencies (env): "
                    + env.getProperty("configuration.projectName"));
        };
    }

    @PostConstruct
    void afterPropertiesSet() {
        log.info("Finally fieldValue: " + this.fieldValue);
    }
}
