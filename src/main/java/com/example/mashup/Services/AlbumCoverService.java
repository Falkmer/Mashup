package com.example.mashup.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AlbumCoverService {
    private RestTemplate restTemplate = new RestTemplate();

    public ArrayList<String> getAlbumCoversByID(String albumID){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> albumImages = new ArrayList<>();
        try{
            JsonNode imageNode = objectMapper.readTree(restTemplate.getForObject(
                    "http://coverartarchive.org/release-group/"+ albumID,String.class));
            for (JsonNode node: imageNode.path("images")){
                System.out.println(node.path("image").asText());
                albumImages.add(node.path("image").asText());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return albumImages;
    }
}
