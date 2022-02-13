package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    static float points = 0;
    Button add_data, delete_data,complete_data;
    EditText add_name;
    ListView userlist;
    AutoCompleteTextView autocomplete;

    String autocompletetextview[] = {"do","complete","start","check"};
    ArrayList<String> listitem;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        listitem = new ArrayList<>();
        complete_data = (Button)findViewById(R.id.complete_task);

        add_data = (Button)findViewById(R.id.add_data);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.add_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, autocompletetextview);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
        userlist = findViewById(R.id.users_list);
        delete_data = (Button)findViewById(R.id.delete_data);
        viewData();
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todelete = autocomplete.getText().toString();
                Integer deletedrows = db.deleteData(todelete);
                if ( deletedrows > 0 ) {
                    Toast.makeText(getApplicationContext(), deletedrows + " similar task deleted", Toast.LENGTH_SHORT).show();
                    listitem.clear();
                    autocomplete.setText("");
                    viewData();
                }
                else {
                    Toast.makeText(getApplicationContext(), "No task deleted", Toast.LENGTH_SHORT).show();
                    listitem.clear();
                }
            }
        });
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = userlist.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),""+text,Toast.LENGTH_SHORT).show();

            }
        });
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = autocomplete.getText().toString();
                if(!name.equals(" ") && db.insertdata(name)){
                    Toast.makeText(getApplicationContext(),"Task Inserted",Toast.LENGTH_SHORT).show();
                    autocomplete.setText("");
                    listitem.clear();
                    viewData();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Task Not Added",Toast.LENGTH_SHORT).show();
                }
            }
        });
        complete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todelete = autocomplete.getText().toString();
                Integer deletedrows = db.deleteData(todelete);
                if ( deletedrows > 0 ) {
                    points = deletedrows + points ;
                    Toast.makeText(getApplicationContext(), deletedrows + " Task deleted \n Won "+points+" Points", Toast.LENGTH_SHORT).show();
                    listitem.clear();
                    autocomplete.setText("");
                    viewData();
                }
                else {
                    Toast.makeText(getApplicationContext(), "No task deleted", Toast.LENGTH_SHORT).show();
                    listitem.clear();
                }
            }
        });
    }
    private void viewData() {
        Cursor cursor = db.viewdata();

        if(cursor.getCount()==0){
           // Toast.makeText(getApplicationContext(),"No task pending",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                listitem.add(cursor.getString(1)); // index 0 is id and index 1 is name
            }
            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listitem);
            userlist.setAdapter(adapter);
        }
    }

}