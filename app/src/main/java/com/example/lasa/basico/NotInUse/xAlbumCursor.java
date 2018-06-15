package com.example.lasa.basico.NotInUse;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lasa on 8-3-2018.
 */

//public class xAlbumCursor extends CursorAdapter {

//    @Deprecated
//    private LayoutInflater cursorInflater;
//
//
//    public xAlbumCursor(Context context, Cursor cursor,
//                        int flags){
//        super(context, cursor, flags);
//        cursorInflater = (LayoutInflater) context.getSystemService(
//                Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return cursorInflater.inflate(R.layout.item, parent, false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//
//        /*String albumpic = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//
//        ImageView albumcover = (ImageView) view.findViewById(R.id.musicPic);
//
//        Bitmap img =  BitmapFactory.decodeFile(albumpic);
//
//        //Drawable img = Drawable.createFromPath("@drawable/favorite");
//        albumcover.setImageBitmap(img);*/
//
//        ViewHolder holder = (ViewHolder) view.getTag();
//        if (holder == null) {
//            holder = new ViewHolder();
//            holder.albumArt = (ImageView) view.findViewById(R.id.musicPic);
//            holder.art = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART);
//            view.setTag(holder);
//        }
//
//        String cover = cursor.getString(holder.art);
//
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        // Get bitmap dimensions before reading...
//        opts.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(cover, opts);
//        int width = opts.outWidth;
//        int height = opts.outHeight;
//        int largerSide = Math.max(width, height);
//        opts.inJustDecodeBounds = false; // This time it's for real!
//        int sampleSize = 2; // Calculate your sampleSize here
//        opts.inSampleSize = sampleSize;
//        Bitmap bmp = BitmapFactory.decodeFile(cover, opts);
//
//        //Bitmap coverBitmap = BitmapFactory.decodeFile(cursor.getString(holder.art));
//        holder.albumArt.setImageBitmap(bmp);
//
//
//    }
//
//    static class ViewHolder {
//        ImageView albumArt;
//        int art;
//    }


//}

