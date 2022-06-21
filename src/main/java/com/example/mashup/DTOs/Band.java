package com.example.mashup.DTOs;

import java.util.ArrayList;

public class Band {
    private String mbid;
    private String description;
    private ArrayList<Album> albums;

    public Band() {
        this.albums = new ArrayList<>();
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album){
        this.albums.add(album);
    }

    @Override
    public String toString() {
        return "Band{" +
                "mbid='" + mbid + '\'' +
                ", description='" + description + '\'' +
                ", albums=" + albums +
                '}';
    }
}
