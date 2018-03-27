package com.example.user.xolostyak20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    TextView text;
    ListView ingridients_lv;
    List<String> list;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        text = (TextView) findViewById(R.id.text);
        ingridients_lv = (ListView) findViewById(R.id.ingr_list);

        user = auth.getInstance().getCurrentUser();

        rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference searchRef = rootRef.child("Search");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getValue(String.class);
                    list.add(value);

                }
                Log.d("TAG",list.toString());
                ingridients_lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                        android.R.layout.simple_list_item_multiple_choice,list);
                ingridients_lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        searchRef.addListenerForSingleValueEvent(valueEventListener);

        ingridients_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray sp=ingridients_lv.getCheckedItemPositions();

                String selectedItems="";
                for(int i=0;i < list.size();i++){
                        if(sp.get(i)) {
                            selectedItems += list.get(i) + ",";
                        }
                }
                text.setText(selectedItems);

            }
        });
/*
        myRef = FirebaseDatabase.getInstance().getReference("Рецепты");
        Query query = myRef.orderByChild("Ингридиенты").equalTo("Морковь, сахар, редис");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String test = dataSnapshot.child("Имя").getValue(String.class);
                text.setText(test);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
    }
}