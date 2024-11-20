package com.rutuja.address.service;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientExample {

    private final WebClient webClient;

    public WebClientExample() {
        this.webClient = WebClient.builder()
                .baseUrl("http://192.168.0.103:8089")
                .defaultHeader("Authorization", "Basic " + encodeCredentials("username", "password")) // Replace with your credentials
                .build();
    }

    public String getCountryById(int id) {
        return webClient.get()
                .uri("/country/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String encodeCredentials(String username, String password) {
        String credentials = "addressUser" + ":" + "address";
        return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    public static void main(String[] args) {
        WebClientExample clientExample = new WebClientExample();
        String response = clientExample.getCountryById(1);
        System.out.println(response);
    }
}