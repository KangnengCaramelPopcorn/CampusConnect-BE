package com.caramelpopcorn.campusconnect.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {
    @Value("${server.env}")
    private String env;
    @Value("${server.port")
    private String port;
    @Value("${server.serverAddress}")
    private String serverAddress;
    @Value("${serverName}")
    private String serverName;

    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("env", env);
        response.put("port", port);
        response.put("serverAddress", serverAddress);
        response.put("serverName", serverName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/env")
    public ResponseEntity<?> healthCheckEnv() {
        return ResponseEntity.ok(env);
    }
}
