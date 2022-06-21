package com.example.mashup.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AlbumCoverService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ArrayList<String> getAlbumCoversByID(String albumID){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> albumImages = new ArrayList<>();

        String result = null;
        try {
            result = restTemplate.getForObject("http://coverartarchive.org/release-group/"+ albumID,String.class);
            JsonNode imageNode = objectMapper.readTree(result);
            for (JsonNode node: imageNode.path("images")){
                albumImages.add(node.path("image").asText());
            }
        } catch (Exception e) {
            albumImages.add("No image available :(");
        }

        return albumImages;
    }
}
