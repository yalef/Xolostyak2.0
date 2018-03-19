package com.example.user.xolostyak20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    TextView text;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        text = (TextView) findViewById(R.id.text);
        myRef = FirebaseDatabase.getInstance().getReference();
        
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<>();
                String value = dataSnapshot.child("Рецепты").child("1").child("Имя").getValue(String.class);
                text.setText(value);
                //Recept recept = dataSnapshot.child("Рецепты").getValue(Recept.class);

                //updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}