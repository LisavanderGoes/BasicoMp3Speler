package com.example.lasa.basico;

public class ObSong {
    private int id;
    private String song_path;
    private String table;

public ObSong() {

}
public ObSong(int id, String path, String table){
    this.id=id;
    this.song_path=path;
    this.table = table;
}
public void setID(int id){
    this.id = id;
}
public void setTable(String table){
    this.table = table;
}
public void setPath(String path){
    this.song_path = path;
}
public int getId() {
    return id;
}
public String getTable(){
        return table;
}
public String getPath(){
    return song_path;
}

}
