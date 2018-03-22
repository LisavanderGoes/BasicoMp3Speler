package com.example.lasa.basico;

/**
 * Created by Lasa on 14-3-2018.
 */

public class ObSonglist {
    private String title;
    private String artist;
    private String path;
    private String album;
    private String album_id;

    public ObSonglist(){

    }

    public ObSonglist(String title, String artist, String path, String album, String album_id) {
        this.title = title;
        this.artist = artist;
        this.path = path;
        this.album = album;
        this.album_id = album_id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }
    public String getArtist(){
        return artist;
    }

    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return path;
    }

    public void setAlbum(String album){
        this.album = album;
    }
    public String getAlbum(){
        return album;
    }

    public void setId(String album_id){
        this.album_id = album_id;
    }
    public String getId(){
        return album_id;
    }
}
