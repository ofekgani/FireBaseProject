package com.example.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.firebaseproject.FBref.refStudents;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */

    ListView listView;
    ArrayList<String> tbl = new ArrayList<String> ();
    ArrayList<Student> stuValues = new ArrayList<Student>();
    ArrayAdapter<String> adp;
    DatabaseReference db;
    Intent updateDelete;

    String str_name, str_phone;

    int idPosition;
    boolean itemSelected = false;

    AlertDialog.Builder adb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        listView = findViewById(R.id.list);
        updateDelete = new Intent ( this,EditStudent.class );

        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                tbl.clear();
                stuValues.clear();

                for(DataSnapshot data : dS.getChildren()) {
                    db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child ( data.getValue ().toString () );
                    Student student = data.getValue (Student.class);
                    stuValues.add(student);
                    str_name = data.child ( "name" ).getValue ().toString ();
                    str_phone = data.child ( "phone" ).getValue ().toString ();
                    tbl.add("Name: "+str_name+" Phone: "+str_phone);
                }
                listView.setOnItemClickListener(MainActivity.this);
                listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                adp = new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item, tbl);
                listView.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        refStudents.addValueEventListener(stuListener);

    }

    public void onClick_show(View view) {
        if(itemSelected) {
            adb = new AlertDialog.Builder (this);

            adb.setTitle ("Information");

            StringBuffer buffer = new StringBuffer ();
            buffer.append ("Name: " + stuValues.get ( idPosition ).getName () + "\n");
            buffer.append ("Phone: " + stuValues.get ( idPosition ).getPhone () + "\n");
            buffer.append ("Home Phone: " + stuValues.get ( idPosition ).getHomePhone () + "\n");
            buffer.append ("Address: " + stuValues.get ( idPosition ).getAddress () + "\n");
            buffer.append ("Father name: " + stuValues.get ( idPosition ).getFatherName () + "\n");
            buffer.append ("Father phone: " + stuValues.get ( idPosition ).getFatherPhone () + "\n");
            buffer.append ("Mother name: " + stuValues.get ( idPosition ).getMotherName () + "\n");
            buffer.append ("Mother phone: " + stuValues.get ( idPosition ).getMotherPhone () + "\n");
            adb.setMessage ("" + buffer.toString ());

            adb.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel ();
                }
            });
            AlertDialog ad = adb.create ();
            ad.show ();
        }
    }

    public void onClick_showGrades(View view) {
        Intent editGrades = new Intent ( this,EditGrades.class );
        editGrades.putExtra ( "id", stuValues.get ( idPosition ).getStudentID () );
        startActivity ( editGrades );
    }

    public void onClick_new(View view) {
        updateDelete.putExtra ( "update",false );
        startActivity(updateDelete);
    }

    public void onClick_edit(View view) {
        if(itemSelected && updateDelete != null){
            startActivity (updateDelete);
        }
    }

    public void onClick_delete(View view) {
        if (itemSelected)
        {
            db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child ( stuValues.get ( idPosition ).getStudentID () );
            db.removeValue ();
            adp.notifyDataSetChanged ();
            itemSelected = false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        idPosition = i; //student`s id by position in list
        itemSelected = true; //item in list selected.
        updateDelete.putExtra ( "update", true );
        updateDelete.putExtra ( "name", stuValues.get ( i ).getName () );
        updateDelete.putExtra ( "address", stuValues.get ( i ).getAddress () );
        updateDelete.putExtra ( "phone", stuValues.get ( i ).getPhone () );
        updateDelete.putExtra ( "homePhone", stuValues.get ( i ).getHomePhone () );
        updateDelete.putExtra ( "fatherName", stuValues.get ( i ).getFatherName () );
        updateDelete.putExtra ( "fatherPhone", stuValues.get ( i ).getFatherPhone () );
        updateDelete.putExtra ( "motherName", stuValues.get ( i ).getMotherName () );

        updateDelete.putExtra ( "id", stuValues.get ( i ).getStudentID () );

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater ().inflate (R.menu.main,menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        String st = item.getTitle ().toString ();
        if(st.equals ( "Credits" ))
        {
            Intent intent = new Intent ( this,Credits.class );
            startActivity ( intent );
        }

        return true;
    }
}
