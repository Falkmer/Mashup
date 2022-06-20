package com.example.mashup;

import com.example.mashup.DTOs.Musician;
import com.example.mashup.Services.MusicianInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicianHandler {
    @Autowired
    private MusicianInfoService musicianInfoService;
    public Musician getMusician(String mbid){
        musicianInfoService.getMusicianInfo(mbid);


        return new Musician();
    }
}
