package com.example.lasa.basico;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class infoWachtrijFragment extends Fragment {
    ObObject obObject = new ObObject();
    Context applicationContext = MainActivity.getContextOfApplication();
    ListView listview;
    TextView titleView;
    TextView artistView;
    Button shuffleBtn;
    MyCursorAdapter cursorAdapter;
    MyWachtrijAdapter mAdapter;
    ArrayList<ObWachtrij> wachtrij;

    //region [play music]
    String title;
    String artist;
    String album;
    //endregion


    public infoWachtrijFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_playlist, container, false);
        listview = (ListView) view.findViewById(R.id.PlayListView);
        titleView = (TextView) view.findViewById(R.id.titlemain);
        artistView = (TextView) view.findViewById(R.id.artistmain);
        shuffleBtn = (Button) view.findViewById(R.id.shuffleBtn);

        wachtrij = obObject.wachtrij;

        mAdapter = new MyWachtrijAdapter(applicationContext, wachtrij);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context applicationContext = MainActivity.getContextOfApplication();
                //Toast.makeText(applicationContext.getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).send(position, listview, wachtrij, false);

            }
        });

        shuffleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                wachtrij = ((MainActivity)getActivity()).shuffle(wachtrij, listview);
                mAdapter = new MyWachtrijAdapter(applicationContext, wachtrij);
                listview.setAdapter(mAdapter);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
