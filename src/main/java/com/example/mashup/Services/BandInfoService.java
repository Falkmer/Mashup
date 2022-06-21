package com.example.mashup.Services;

import com.example.mashup.BO.BandInfo;
import com.example.mashup.BO.Relation;
import com.example.mashup.BO.ReleaseGroup;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Objects;

@Service
public class BandInfoService {
    private final RestTemplate restTemplate;


    public BandInfoService(RestTemplateBuilder builder){
        restTemplate = builder.build();
    }

    public BandInfo getBandInfoByMBID(String mbid){
        String url = "http://musicbrainz.org/ws/2/artist/"+mbid+"?&fmt=json&inc=url-rels+release-groups";
        ObjectMapper objectMapper = new ObjectMapper();
        BandInfo bandInfo = new BandInfo();

        try {
            JsonNode root = objectMapper.readTree(restTemplate.getForObject(url, String.class));
            for (JsonNode relationNode : root.path("relations")) {
                String type = relationNode.path("type").asText();
                switch (type) {
                    case "wikidata", "wikipedia" -> {
                        Relation relation = new Relation(type, relationNode.path("url").path("resource").asText());
                        bandInfo.addRelation(relation);
                    }
                    default -> {
                    }
                }
            }

            for (JsonNode releaseGroupNode: root.path("release-groups")){
                ReleaseGroup releaseGroup = new ReleaseGroup(
                        releaseGroupNode.path("id").asText(),
                        releaseGroupNode.path("title").asText());

                bandInfo.addReleaseGroup(releaseGroup);
            }

        } catch (JsonProcessingException jsonProcessingException) {
            System.out.println(jsonProcessingException.getMessage());
        }

        return bandInfo;
    }

    public String getBandInfoByMBID2(String mbid){
        String url = "http://musicbrainz.org/ws/2/artist/"+mbid+"?&fmt=json&inc=url-rels+release-groups";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode root = objectMapper.readTree(restTemplate.getForObject(url, String.class));
            for (JsonNode node: root.path("relations")) {

                if (Objects.equals(node.path("type").asText(), "wikidata")){
                    String resource = node.path("url").path("resource").asText();
                    System.out.println(resource);
                    String wikidata = "https://www.wikidata.org/wiki/";
                    String wikiID = resource.substring(wikidata.length());
                    System.out.println(wikiID);
                    JsonNode wikiNode = objectMapper.readTree(restTemplate.getForObject(
                            "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+wikiID+"&format=json&props=sitelinks",String.class));

                    String title = wikiNode.path("entities").path(wikiID).path("sitelinks").path("enwiki").path("title").asText();
                    System.out.println(title);
                    System.out.println("SUCCESS");

                    JsonNode wikipediaNode = objectMapper.readTree(restTemplate.getForObject(
                            "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true&titles="+title ,String.class));

                    Iterator<String> i = wikipediaNode.path("query").path("pages").fieldNames();
                    String pageID = null;
                    while (i.hasNext()){
                        pageID = i.next();
                    }
                    System.out.println("HERE:" + wikipediaNode.path("query").path("pages").path(pageID).path("extract").asText());
                }
            }

            for (JsonNode rn: root.path("release-groups")){
                String albumID = rn.path("id").asText();
                System.out.println(albumID);
                System.out.println(rn.path("title").asText());
                try{
                    JsonNode imageNode = objectMapper.readTree(restTemplate.getForObject(
                            "http://coverartarchive.org/release-group/"+ albumID,String.class));
                    for (JsonNode in: imageNode.path("images")){
                        System.out.println(in.path("image").asText());
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }

        } catch (JsonProcessingException e) {
            System.out.println("FAIL");
            throw new RuntimeException(e);
        }

        return null;
    }
}
