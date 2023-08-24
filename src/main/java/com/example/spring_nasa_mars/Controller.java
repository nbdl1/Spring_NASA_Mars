package com.example.spring_nasa_mars;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping//("http://127.0.0.1")
public class Controller {
   public static final String BASE_NASA_PATH = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=";
   public static final String API_KEY = "ihP4z43MG4TNFAVpF28gmRXyRiF091CLKoJV5jfN";

   @GetMapping("/pictures/{sol}/largest")
    public ResponseEntity<Void> getLargetstPicture(@PathVariable int sol) throws IOException {
        List<String> listOfPictureURIs = getListOfPictureURIs(sol,API_KEY);
        HashMap<String, Integer> picturesAndSizesMap = getPicturesAndSizesMap(listOfPictureURIs);
        int maxSize = 0;
        String maxSizePictureUrl = null;
        int pictureSize = 0;
        for(Map.Entry<String, Integer> entry: picturesAndSizesMap.entrySet()){
            pictureSize = entry.getValue();
            if(maxSize<pictureSize){
                maxSize=pictureSize;
                maxSizePictureUrl =entry.getKey();
            }
        }
       System.out.println("maxSize is "+maxSize+" bytes");
       System.out.println("Picture URL : "+maxSizePictureUrl);
       return ResponseEntity.ok().location(URI.create(maxSizePictureUrl)).build();
    }

    private List<String> getListOfPictureURIs(int sol, String key) throws IOException {
        List<String> listOfPictureURIs = new ArrayList<>();
        String finalPathString = BASE_NASA_PATH+sol+"&api_key="+key;

        String json="";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new URL(finalPathString).openStream()));){
            while(br.ready()){
                json+=br.readLine();
            }
        }catch (IOException e){
            System.out.println("Ooops, exception: "+e.getMessage());
        }
        if(json.length()>0){
            Pattern getUrlsPattern = Pattern.compile("(?<=\\\"img_src\\\"\\:\\\").[^\\,]+\\.[a-zA-Z]+");
            Matcher getUrlsMatcher = getUrlsPattern.matcher(json);
            while(getUrlsMatcher.find()){
                listOfPictureURIs.add(getUrlsMatcher.group());
            }
        }
        return listOfPictureURIs;
    }

    private HashMap<String, Integer> getPicturesAndSizesMap(List<String> listOfPictureURIs) throws IOException {
        HashMap<String, Integer> picturesAndSizesMap = new HashMap<>();
        int pictureSize = 0;
        for(String pictureUrl: listOfPictureURIs){
            pictureSize = getPictureSize(pictureUrl);
            picturesAndSizesMap.put(pictureUrl,pictureSize);
        }
        return picturesAndSizesMap;
    }

    private int getPictureSize(String url) throws IOException {
       HttpURLConnection  connectionToCurrentUrl = (HttpURLConnection )new URL(url).openConnection();
       while (connectionToCurrentUrl.getResponseCode()<200||connectionToCurrentUrl.getResponseCode()>299){
           String location =  connectionToCurrentUrl.getHeaderField("Location");
           if(location!=null){
               url = location;
               connectionToCurrentUrl = (HttpURLConnection )new URL(url).openConnection();
           }
       }
       return connectionToCurrentUrl.getContentLength();
    }
}