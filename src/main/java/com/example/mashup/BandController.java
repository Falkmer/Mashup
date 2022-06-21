package com.example.mashup;

import com.example.mashup.DTOs.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BandController {
    @Autowired
    private BandHandler bandHandler;

    @GetMapping ("/musician")
    public Band getMusician(@RequestParam String mbid){
        return bandHandler.getBandDiscography(mbid);
    }
}
