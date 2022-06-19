package com.example.mashup;

import com.example.mashup.DTOs.MusicianDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicianController {


    @GetMapping ("/musician")
    public MusicianDTO getMusician(){
        return new MusicianDTO();
    }
}
