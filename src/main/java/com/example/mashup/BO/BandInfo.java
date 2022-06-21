package com.example.mashup.BO;

import java.util.ArrayList;

public class BandInfo {
    private String mbid;
    private ArrayList<ReleaseGroup> releaseGroups;
    private ArrayList<Relation> relations;

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public ArrayList<ReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(ArrayList<ReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    public void addReleaseGroup(ReleaseGroup releaseGroup){
        this.releaseGroups.add(releaseGroup);
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<Relation> relations) {
        this.relations = relations;
    }

    public void addRelation(Relation relation){
        this.relations.add(relation);
    }

    @Override
    public String toString() {
        return "BandInfo{" +
                "mbid='" + mbid + '\'' +
                ", releaseGroups=" + releaseGroups +
                ", relations=" + relations +
                '}';
    }
}
