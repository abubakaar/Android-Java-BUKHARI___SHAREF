package com.example.siddiq.bukhari___sharef;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.io.IOException;
import java.util.List;


public class Book_name extends Fragment {


    DatabaseHelper myDbHelper;
    List<String> list;
    ListView listView;
    int start = 0;
    chapter_name chapter_name;
    public Book_name() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_book_name, container, false);

        listView =(ListView) view.findViewById(R.id.lv);
        myDbHelper = new DatabaseHelper(getActivity());
        chapter_name = new chapter_name();

        getActivity().setTitle("Books");


        if(start == 0) {

            try {
                myDbHelper.createDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            list = myDbHelper.getAllUsers();

        start++;
        }

        listView.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.main_listview_style,R.id.booklist, list));

        //open new fragment
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chapter_name.bookname=list.get(position);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout,chapter_name);
                fragmentTransaction.commit();

            }
        });
        return view;
    }



}
