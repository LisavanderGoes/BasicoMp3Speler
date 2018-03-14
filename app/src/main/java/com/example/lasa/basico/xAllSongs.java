package com.example.lasa.basico;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class xAllSongs extends Fragment {

    ArrayList<String> arrayList;
    ListView listview;
    ArrayAdapter<String> adapter;
    int i;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_all_songs,container,false);
        ListView listview = (ListView) view.findViewById(R.id.ListView);


//DOOOOOOOD!!!!!!!!!!

        arrayList = new ArrayList<>();
        //getMusic();
        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                arrayList
        );
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Do something when click
            }
        });

        //Inflate the layout for this fragment
        return view;
    }

    //region [Prut dat je miss nog nodig hebt]

    /*public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) { //checks if permission is already given
                Toast.makeText(this, "Permission 1!", Toast.LENGTH_SHORT).show();
                //Permission is given
                //Do stuff
                doStuff();

                return true;
            } else {
                //calls on request permission
                Toast.makeText(this, "Permission not 2!", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this, "Permission 3!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /*public void test(){
        TextView Test = (TextView) findViewById(R.id.Test);

        Test.setText("Gelukt!");
    }

    /* Werkt niet!!!!
    public void getImage() {
        ImageView albumImage = (ImageView) findViewById(R.id.ImageView);

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

        mediaMetadataRetriever.setDataSource(uri);
        byte[] image = mediaMetadataRetriever.getEmbeddedPicture();
        Bitmap imageAlbum = BitmapFactory.decodeByteArray(image, 0, image.length);
        albumImage.setImageBitmap(imageAlbum);
    }

    public void doStuff(){
        listview = (ListView) findViewById(R.id.ListView);
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Do something
            }
        });
    }*/

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Toast.makeText(this, "External storage2", Toast.LENGTH_SHORT).show();
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0]+" ", Toast.LENGTH_SHORT).show();
                    //test();
                }else{
                    // test();
                }
                break;

            case 3:
                Toast.makeText(this, "External storage1", Toast.LENGTH_SHORT).show();
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0]+" ", Toast.LENGTH_SHORT).show();
                    // test();
                }else{
                    // test();
                }
                break;
        }
    }*/

    //endregion

    public void getMusic() {
        Context applicationContext = MainActivity.getContextOfApplication();

        ContentResolver contentResolver = applicationContext.getContentResolver();;
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentLocation = songCursor.getString(songLocation);
                arrayList.add(i + "\n" + currentTitle + "\n" + currentArtist + "\n"
                        + "Location:" + currentLocation);
                i++;
            } while (songCursor.moveToNext());
        }
    }

}
