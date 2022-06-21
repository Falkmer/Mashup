package com.example.mashup;

import com.example.mashup.BO.BandInfo;
import com.example.mashup.BO.ReleaseGroup;
import com.example.mashup.DTOs.Album;
import com.example.mashup.DTOs.Band;
import com.example.mashup.Services.AlbumCoverService;
import com.example.mashup.Services.BandDescriptionService;
import com.example.mashup.Services.BandInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Component
public class BandHandler {
    @Autowired
    private BandInfoService bandInfoService;
    @Autowired
    private AlbumCoverService albumCoverService;
    @Autowired
    private BandDescriptionService bandDescriptionService;

    public Band getBandDiscography(String mbid){
        Band band = new Band();
        BandInfo bandInfo = bandInfoService.getBandInfoByMBID(mbid);
        band.setMbid(bandInfo.getMbid());

        try {
            final ExecutorService executorService = newFixedThreadPool(20);

            executorService.execute(() -> {
                band.setDescription(bandDescriptionService.getBandDescription(bandInfo.getRelations()));

            });


            for (ReleaseGroup releaseGroup: bandInfo.getReleaseGroups()) {
                executorService.execute(() -> {
                    Album album = new Album();
                    album.setId(releaseGroup.getId());
                    album.setTitle(releaseGroup.getTitle());

                    album.setImages(albumCoverService.getAlbumCoversByID(releaseGroup.getId()));

                    band.addAlbum(album);
                });

            }
            executorService.shutdown();
            executorService.awaitTermination(1000, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedException) {
            System.out.println(interruptedException.getMessage());
        }
        System.out.println(band);

        return band;
    }
}
