package com.example.lasa.basico;


import android.net.Uri;
import android.provider.MediaStore;
import com.example.lasa.basico.*;

import java.util.ArrayList;

public class ObObject {
    Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Uri genreUri = android.provider.MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    final ArrayList<ObSonglist> songList = PlashScreen.songList;
    public ArrayList<ObWachtrij> wachtrij = PlashScreen.wachtrij;

    public void setWachtrij(ArrayList<ObWachtrij> list){
        wachtrij = list;
    }

}


