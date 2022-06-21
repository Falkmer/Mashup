package com.example.mashup.Services;

import com.example.mashup.BO.Relation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class BandDescriptionService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getBandDescription(ArrayList<Relation> relations) {

        String wikipediaUrl = null;

        for (Relation relation: relations) {
            if (relation.getType().equals("wikipedia")){
                wikipediaUrl = relation.getResource();
            } else if (relation.getType().equals("wikidata")){
                if (wikipediaUrl == null){
                    String wikidata = "https://www.wikidata.org/wiki/";
                    String wikiID = relation.getResource().substring(wikidata.length());
                    System.out.println(wikiID);
                    try {
                        JsonNode wikiNode = objectMapper.readTree(restTemplate.getForObject(
                                "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+wikiID+"&format=json&props=sitelinks",String.class));

                        String title = wikiNode.path("entities").path(wikiID).path("sitelinks").path("enwiki").path("title").asText();
                        System.out.println(title);
                        wikipediaUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles="+title;
                    } catch (JsonProcessingException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }

                }
            }
        }
        if (wikipediaUrl != null){
            return getWikipediaDescription(wikipediaUrl);
        }
        return null;
    }

    private String getWikipediaDescription(String url){
        try {
            JsonNode wikipediaNode = objectMapper.readTree(restTemplate.getForObject(url ,String.class));

            Iterator<String> i = wikipediaNode.path("query").path("pages").fieldNames();
            String pageID = null;
            while (i.hasNext()){
                pageID = i.next();
            }

            return wikipediaNode.path("query").path("pages").path(pageID).path("extract").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
