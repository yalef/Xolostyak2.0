package com.example.user.xolostyak20;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    TextView text;
    EditText inputSearch;
    View v;
    Button search_btn;
    Toolbar tb;
    ListView ingridients_lv;
    List<String> list = new ArrayList<>();
    String filter;
    int number_child;
    String selectedItems; //Выбранные элементы в листе
    ArrayAdapter<String> adapter;
    private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        v = (View) findViewById(R.id.activity_search);
        tb = (Toolbar) findViewById(R.id.toolbar);
        text = (TextView) findViewById(R.id.text);
        ingridients_lv = (ListView) findViewById(R.id.ingr_list);
        search_btn = (Button) findViewById(R.id.search_btn);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

/*        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        user = auth.getInstance().getCurrentUser();

        filter = "free";

        rootRef = FirebaseDatabase.getInstance().getReference(); //Общая ссылка на бд
        DatabaseReference searchRef = rootRef.child("List").child("Ingridients");
        getItem(searchRef);

/*        DatabaseReference searchRef = rootRef.child("Search"); //Ссылка на данные для

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getValue(String.class);
                    if (!list.contains(value)){
                        list.add(value);
                    }
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
        searchRef.addListenerForSingleValueEvent(valueEventListener);*/


        ingridients_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray sp=ingridients_lv.getCheckedItemPositions();

                selectedItems="";
                for(int i=0;i < list.size();i++){
                        if(sp.get(i)) {
                            selectedItems += list.get(i) + " ";
                        }
                }
                text.setText(selectedItems);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, ResultActivity.class);
                i.putExtra("select",selectedItems);
                i.putExtra("filter",filter);
                startActivity(i);
            }

        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.specialThanks:
                i = new Intent(SearchActivity.this,SpecialThanksActivity.class);
                startActivity(i);
                return true;
            case R.id.favor_item:
                i = new Intent(SearchActivity.this,FavoriteActivity.class);
                startActivity(i);
                return true;
            case R.id.strict_filter:
                item.setChecked(true);
                if(item.isChecked()) {
                    filter = "strict";
                    Snackbar.make(v, "Выбран строгий поиск", Snackbar.LENGTH_LONG).show();
                }
                return true;
            case R.id.free_filter:
                item.setChecked(true);
                if(item.isChecked()) {
                    filter = "free";
                    Snackbar.make(v, "Выбран гибкий поиск", Snackbar.LENGTH_LONG).show();
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    void addItem(String item){
        if (!list.contains(item)){
            list.add(item);
        }
    }
    void getItem(final DatabaseReference ref){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                number_child = ((int) dataSnapshot.getChildrenCount())-1;
                for (int i = 0; i <= number_child; i++) {
                    ref.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String ingridient = ds.getKey();
                                Log.d("TAG", ingridient);
                                addItem(ingridient);
                                ingridients_lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                adapter = new ArrayAdapter<String>(getBaseContext(),
                                        android.R.layout.simple_list_item_multiple_choice,list);
                                ingridients_lv.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(valueEventListener);
    }
}