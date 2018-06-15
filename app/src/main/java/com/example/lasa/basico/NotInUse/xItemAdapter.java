package com.example.lasa.basico.NotInUse;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class xItemAdapter extends ArrayAdapter<xObItem> {
    private Activity myContext;
    private xObItem[] datas;

    @Deprecated
    public xItemAdapter(Context context, int textViewResourceId,
                        xObItem[] objects) {
        super(context, textViewResourceId, objects);

        myContext = (Activity) context;
        datas = objects;
    }

    static class ViewHolder{
        TextView title;
        TextView artist;
        TextView time;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.
                    findViewById(R.id.title);

            viewHolder.artist = (TextView) convertView.
                    findViewById(R.id.artist);

            viewHolder.time = (TextView) convertView.
                    findViewById(R.id.time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(datas[position].titleMain);
        viewHolder.artist.setText(datas[position].artistMain);
        viewHolder.time.setText(datas[position].locationMain);

        /*LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item, null);
        //ImageView musicPic = (ImageView) rowView.findViewById(R.id.musicPic);

        TextView titleMain = (TextView) rowView
                .findViewById(R.id.title);
        titleMain.setText(datas[position].titleMain);

        TextView artistMain = (TextView) rowView
                .findViewById(R.id.artist);
        artistMain.setText(datas[position].artistMain);

        TextView locationMain = (TextView) rowView
                .findViewById(R.id.time);
        locationMain.setText(datas[position].locationMain);

        /*TextView timeMain = (TextView) rowView
                .findViewById(R.id.time);
        timeMain.setText(datas[position].timeMain);*/

        return convertView;
    }

}
