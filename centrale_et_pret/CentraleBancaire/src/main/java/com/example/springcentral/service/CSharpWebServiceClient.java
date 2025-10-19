package com.example.springcentral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CSharpWebServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public CSharpWebServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callCSharpService(String endpoint, Object request) {
        return restTemplate.postForObject("http://localhost:5082/api/" + endpoint, request, String.class);
    }
}