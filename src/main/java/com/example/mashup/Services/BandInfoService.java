package com.example.mashup.Services;

import com.example.mashup.BO.BandInfo;
import com.example.mashup.BO.Relation;
import com.example.mashup.BO.ReleaseGroup;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BandInfoService {
    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<BandInfo> getBandInfoByMBID(String mbid) {
        String url = "http://musicbrainz.org/ws/2/artist/"+mbid+"?&fmt=json&inc=url-rels+release-groups";
        ObjectMapper objectMapper = new ObjectMapper();
        BandInfo bandInfo = new BandInfo(mbid);

        String result = null;
        try {
            result = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(result);

            if (root.path("relations") != null) {
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
            } else {
                return Optional.empty();
            }

            if (root.path("release-groups") != null){
                for (JsonNode releaseGroupNode: root.path("release-groups")){
                    ReleaseGroup releaseGroup = new ReleaseGroup(
                            releaseGroupNode.path("id").asText(),
                            releaseGroupNode.path("title").asText());

                    bandInfo.addReleaseGroup(releaseGroup);
                }
            } else {
                return Optional.empty();
            }

            return Optional.of(bandInfo);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
