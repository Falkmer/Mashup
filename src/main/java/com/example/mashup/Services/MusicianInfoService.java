package com.example.mashup.Services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MusicianInfoService {
    private final RestTemplate restTemplate;

    public MusicianInfoService(RestTemplateBuilder builder){
        restTemplate = builder.build();
    }

    public String getMusicianInfo(){
        String url = "http://musicbrainz.org/ws/2";
        restTemplate.getForObject(url, String.class);
        return null;
    }
}
