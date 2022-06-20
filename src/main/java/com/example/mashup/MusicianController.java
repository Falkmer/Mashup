package com.example.mashup;

import com.example.mashup.DTOs.Musician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicianController {
    @Autowired
    private MusicianHandler musicianHandler;

    @GetMapping ("/musician")
    public Musician getMusician(@RequestParam String mbid){
        return musicianHandler.getMusician(mbid);
    }
}
