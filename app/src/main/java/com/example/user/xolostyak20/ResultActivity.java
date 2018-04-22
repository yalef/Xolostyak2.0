package com.example.user.xolostyak20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final String result = getIntent().getStringExtra("select");
        rootRef = FirebaseDatabase.getInstance().getReference();
        Query result_query = rootRef.child("Recepts").orderByChild("Ingridients");

        result_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String value = ds.child("Ingridients").getValue(String.class);
                    ArrayList<String> list = new ArrayList<>();
                    if(value.contains(result)){ String name_rec = ds.child("Name").getValue(String.class);
                     list.add(name_rec);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
