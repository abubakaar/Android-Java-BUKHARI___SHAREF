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

 public class Fav_Frag extends Fragment {

    DatabaseHelper databaseHelper;
    List<String> list;
     ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_fav_, container, false);

       databaseHelper = new DatabaseHelper(getContext());

        MainActivity.notmain=true;
        MainActivity.lastloc="chapter";

        list = databaseHelper.Get_Fav();
        listView = (ListView) view.findViewById(R.id.fav_list);

        listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, list));

        registerForContextMenu(listView);
        getActivity().setTitle("Favorites");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hadith hadith = new Hadith();
                String a[]=list.get(position).split("\\n");
                String b[]= a[1].split(" ");
                Hadith.book=a[0];
                Hadith.hadith=b[2];
                Hadith.fav=false;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout,hadith);
                fragmentTransaction.commit();
            }
        });


       return view;
    }
     @Override
     public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         super.onCreateContextMenu(menu, v, menuInfo);
         menu.setHeaderTitle("Select Action");
         menu.add(0, v.getId(), 0, "Remove");
     }


     @Override
     public boolean onContextItemSelected(MenuItem item) {
         if (item.getTitle() == "Remove") {
             AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
             int listPosition = info.position;
             String a[]=list.get(listPosition).split("\\n");
             String b[]= a[1].split(" ");
             databaseHelper.del(a[0],b[2]);
             list = databaseHelper.Get_Fav();
             listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, list));

            // String selectedFromList = (String) lv.getItemAtPosition(listPosition);
             //myDbHelper.recent(list3.get(listPosition), "new_fav");
            // myDbHelper.recent((String) lv.getItemAtPosition(listPosition), "new_fav");

         }
         return super.onContextItemSelected(item);
     }



 }
