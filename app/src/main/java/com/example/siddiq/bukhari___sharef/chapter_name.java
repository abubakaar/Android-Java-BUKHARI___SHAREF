package com.example.siddiq.bukhari___sharef;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.List;


public class chapter_name extends Fragment {
    ListView listView;
    List<String> list;
    DatabaseHelper databaseHelper;
    Hadith hadithfrag;
    static String bookname="";

    public chapter_name() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_name, container, false);


        MainActivity.notmain=true;//sedha main Activity waly par na jae, blky BOOK waly par jae
        MainActivity.lastloc="chapter";

        //CLICK BOOK NAME, AND THEN SET A BOOK NAME ON NEXT ACTIVITY AS TITLE
        String a[] = bookname.split("\\n");
        getActivity().setTitle(a[0]);

        listView = (ListView) view.findViewById(R.id.chapterlist);
        databaseHelper = new DatabaseHelper(getActivity());

//????????????????
        hadithfrag = new Hadith();



        bookname=a[0];
        Hadith.hadith_name = a[a.length-1];

        list = databaseHelper.getchapters(bookname);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.liststyle,R.id.hadithno, list));
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Hadith.book=bookname;
              Hadith.hadith=list.get(position);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout,hadithfrag);
                fragmentTransaction.commit();




            }
        });





        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, v.getId(), 0, "Add to fav");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Add to fav")) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int listPosition = info.position;
            String aa=list.get(listPosition);
            databaseHelper.fav(bookname,aa);

            // String selectedFromList = (String) lv.getItemAtPosition(listPosition);
            //myDbHelper.recent(list3.get(listPosition), "new_fav");
            // myDbHelper.recent((String) lv.getItemAtPosition(listPosition), "new_fav");

        }
        return super.onContextItemSelected(item);
    }
}
