package com.example.spring_nasa_mars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


/*
Create a new Spring Boot app
Build an endpoint GET /pictures/{sol}/largest that redirects the client to the largest picture
it should accept sol as a path variable
given sol should be used to construct a query and call NASA
This is a base API URL
It accepts api_key and sol as query params (e.g. a complete URL)
Use RestTemplate and implement redirect logic manually
Make sure it works

API URL: https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos

complete URL: https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=DEMO_KEY

Тут ты можеш сгенерировать апи ключ https://api.nasa.gov/
 */
@SpringBootApplication
public class SpringNasaMarsApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringNasaMarsApplication.class, args);
        //Controller ourController = new Controller();
       //ourController.getLargetstPicture(15);
    }

}
