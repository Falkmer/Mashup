package com.example.mashup;

import com.example.mashup.DTOs.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@ResponseBody
public class BandController {
    @Autowired
    private BandHandler bandHandler;

    @GetMapping ("/BandOverview")
    public Band getMusician(@RequestParam String mbid){
            Band band = bandHandler.getBandOverview(mbid);
            if (band != null) {
                return band;
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
