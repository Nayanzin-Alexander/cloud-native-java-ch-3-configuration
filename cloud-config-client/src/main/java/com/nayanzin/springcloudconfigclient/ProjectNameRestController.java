package com.nayanzin.springcloudconfigclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/* The @RefreshScope annotation makes this bean refreshable. This lets this bin to recreate itself
and reread configuration from the configuration service. Life cycle callbacks will be honored, and
@Value and @Autowired injects will be reestablished.

Fundamentally, all refresh-scoped beans will refresh themselves when they receive a Spring ApplicationContext event
of the type RefreshScopeRefreshedEvent.

There are various ways to trigger refreshing. You can trigger the refresh by sending an empty POST request to /refresh,
which is Spring Boot Actuator endpoint that is exposed automatically.

Alternatively, you can use the auto-configured Spring Boot Actuator JMX refresh endpoint.
 */
@RefreshScope
@RestController
public class ProjectNameRestController {

    private final String projectName;
    private final String serverPort;
    private final String anotherServerPort;

    @Autowired
    public ProjectNameRestController(
            @Value("${configuration.projectName}") String projectName,
            @Value("${server.port}") String serverPort,
            @Value("${another.server.port}") String anotherServerPort) {
        this.projectName = projectName;
        this.serverPort = serverPort;
        this.anotherServerPort = anotherServerPort;
    }

    @GetMapping("/env")
    public Map<String, String> getProjectName() {
        Map<String, String> env = new HashMap<>();
        env.put("projectName", this.projectName);
        env.put("serverPort", this.serverPort);
        env.put("anotherServerPort", this.anotherServerPort);
        return env;
    }
}
