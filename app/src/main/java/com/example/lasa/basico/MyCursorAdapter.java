package com.example.lasa.basico;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Lasa on 7-3-2018.
 */

public class MyCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;
    Context mContext;
    ImageView albumArt;


    public MyCursorAdapter(Context context, Cursor cursor,
                           int flags){
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null){
            holder = new ViewHolder();
            holder.albumArt = (ImageView) view.findViewById(R.id.musicPic);
            holder.songTitle = (TextView) view.findViewById(R.id.title);
            holder.artistName =(TextView) view.findViewById(R.id.artist);
            holder.title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            holder.artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            holder.data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            holder.albumID = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        }

        holder.songTitle.setText(cursor.getString(holder.title));
        holder.artistName.setText(cursor.getString(holder.artist));
        albumArt = holder.albumArt;
        mContext = context;
        //getArtistImage(cursor.getString(holder.albumID)); //SUPER LAG

    }

    static class ViewHolder {
        ImageView albumArt;
        TextView songTitle;
        TextView artistName;
        TextView time;
        int title;
        int artist;
        int data;
        int albumID;
    }

    private Bitmap getArtistImage(String albumid) {
        Bitmap artwork = null;
        try {
            Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri,
                    Long.valueOf(albumid));

            ContentResolver res = mContext.getContentResolver();
            InputStream in = res.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            int srcWidth = options.outWidth;
            int srcHeigth = options.outHeight;

            int inSampleSize = 1;
                final int halfHeight = srcHeigth / 2;
                final int halfWidth = srcWidth / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) >= 60
                        && (halfWidth / inSampleSize) >= 60) {
                    inSampleSize *= 2;
                }

                options.inSampleSize = inSampleSize;


            //int dstWidth = options.outHeight;
            options.inJustDecodeBounds = false;
            options.inScaled = true;
            //options.inSampleSize = 4;
            //options.inDensity = srcWidth;
            //options.inTargetDensity = dstWidth * options.inSampleSize;
            artwork = BitmapFactory.decodeStream(in, null, options);


            albumArt.setImageBitmap(artwork);
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return artwork;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
