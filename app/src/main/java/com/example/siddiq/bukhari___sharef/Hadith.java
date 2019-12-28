package com.example.siddiq.bukhari___sharef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import java.util.List;

public class Hadith extends Fragment {

    static String hadith,book,hadith_name,b;
    DatabaseHelper databaseHelper;
    RadioGroup rg;
    RadioButton eng,urd,ara;
    Button favadd,share;
    static boolean fav =true;
    TextView textView,bookheading,hadithno;
    List<String> h;

    public Hadith() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hadith, container, false);

        getActivity().setTitle("Hadith");
        textView = (TextView) view.findViewById(R.id.hadithview);
        databaseHelper = new DatabaseHelper(getActivity());
        rg = (RadioGroup) view.findViewById(R.id.radioGroup);
        ara = (RadioButton) view.findViewById(R.id.radioarabic);
        urd = (RadioButton) view.findViewById(R.id.radiourdu);
        eng = (RadioButton) view.findViewById(R.id.radioeng);
        bookheading = (TextView) view.findViewById(R.id.booknametextview);
        hadithno =(TextView) view.findViewById(R.id.hadithnotextview);
        favadd = (Button) view.findViewById(R.id.favclick);
        share =(Button) view.findViewById(R.id.sharee);

        bookheading.setText(book);
        hadithno.setText(hadith);

        MainActivity.notmain=true;
        if(fav)
        MainActivity.lastloc="hadith";
        fav=true;

        h = databaseHelper.gethadith(book, hadith, 2);
        b = h.get(1);
        if(b.equals("1"))
        {
            favadd.setBackgroundResource(R.drawable.fav_added);
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(h.get(0));

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(urd.isChecked()){
                    h = databaseHelper.gethadith(book,hadith,4);
                    textView.setText(h.get(0));
                    textView.setMovementMethod(new ScrollingMovementMethod());

                }
                else if(eng.isChecked()){
                    h = databaseHelper.gethadith(book,hadith,3);
                    textView.setText(h.get(0));
                    textView.setMovementMethod(new ScrollingMovementMethod());

                }else if(ara.isChecked()){
                    h = databaseHelper.gethadith(book,hadith,2);
                    textView.setText(h.get(0));
                    textView.setMovementMethod(new ScrollingMovementMethod());
                }

            }
        });

        favadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(b.equals("1"))
                {
                    databaseHelper.del(bookheading.getText().toString(), hadithno.getText().toString());

                    favadd.setBackgroundResource(R.drawable.favorite);
                    b="0";
                }
                 else {
                    databaseHelper.fav(bookheading.getText().toString(), hadithno.getText().toString());

                    favadd.setBackgroundResource(R.drawable.fav_added);
                    b="1";
                }

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                         textView.getText().toString()+"\n Book Name :"+bookheading.getText().toString()+"\n Hadith No :"+hadithno.getText().toString()+
                        "\n\n http://play.google.com/store/apps/details?id=" + getActivity().getPackageName() );

                startActivity(Intent.createChooser(shareIntent, "Share"));
            }
        });
        return view;
    }



}
