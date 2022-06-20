package com.example.mashup.Services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class MusicianInfoService {
    private final RestTemplate restTemplate;


    public MusicianInfoService(RestTemplateBuilder builder){
        restTemplate = builder.build();
    }

    public String getMusicianInfo(String mbid){
        String url = "http://musicbrainz.org/ws/2/artist/"+mbid+"?&fmt=json&inc=url-rels+release-groups";
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            JsonNode root = objectMapper.readTree(restTemplate.getForObject(url, String.class));
            for (JsonNode node: root.path("relations")) {
                System.out.println(node.path("type").asText());
                if (Objects.equals(node.path("type").asText(), "wikidata")){
                    System.out.println("TRUE");
                }
            }

            System.out.println("SUCCESS");
        } catch (JsonProcessingException e) {
            System.out.println("FAIL");
            throw new RuntimeException(e);
        }


        return null;
    }
}
