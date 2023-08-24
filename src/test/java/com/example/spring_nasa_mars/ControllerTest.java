package com.example.spring_nasa_mars;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ControllerTest {
    public static final String BASE_NASA_PATH = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=";
    public static final String API_KEY = "ihP4z43MG4TNFAVpF28gmRXyRiF091CLKoJV5jfN";

    TestRestTemplate restTemplate = new TestRestTemplate() ;

    @Test
    void getLargetstPicture() throws MalformedURLException {
        int sol = 15;
        String url = BASE_NASA_PATH+sol+"&api_key="+API_KEY;
        ResponseEntity<String> responseEntity =restTemplate.getForEntity(url, String.class);
        System.out.println("body: "+responseEntity.getBody());
    }
}