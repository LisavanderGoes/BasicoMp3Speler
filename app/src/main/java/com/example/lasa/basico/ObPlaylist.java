package com.example.lasa.basico;

/**
 * Created by Lasa on 14-3-2018.
 */

public class ObPlaylist {
    private String table;

    public ObPlaylist(){

    }

    public ObPlaylist(String table) {
        this.table = table;
    }

    public void setTable(String table){
        this.table = table;
    }
    public String getTable(){
        return table;
    }
}
