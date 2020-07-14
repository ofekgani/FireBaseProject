package com.example.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.firebaseproject.FBref.refStudents;

public class EditStudent extends AppCompatActivity {
    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */
    EditText ed_name, ed_address, ed_phone, ed_fatherName, ed_fatherPhone, ed_motherName, ed_motherPhone ,ed_homePhone;
    DatabaseReference db;

    Student student;
    String key= "";

    Intent gi;
    boolean itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_student );

        ed_name = findViewById(R.id.ed_name);
        ed_address = findViewById(R.id.ed_address);
        ed_phone = findViewById(R.id.ed_phone);
        ed_homePhone = findViewById(R.id.ed_phoneHome);
        ed_fatherName = findViewById(R.id.ed_fatherName);
        ed_fatherPhone = findViewById(R.id.ed_fatherPhone);
        ed_motherName = findViewById(R.id.ed_motherName);
        ed_motherPhone = findViewById(R.id.ed_motherPhone);

        gi = getIntent ();
        itemSelected = gi.getBooleanExtra ( "update",false );

        Toast.makeText (EditStudent.this,""+itemSelected,Toast.LENGTH_SHORT).show ();
        if(itemSelected)
        {
            ed_name.setText ( ""+ gi.getStringExtra ( "name" ) );
            ed_address.setText ( ""+ gi.getStringExtra ( "address" ) );
            ed_phone.setText ( ""+ gi.getStringExtra ( "phone" ) );
            ed_homePhone.setText ( ""+ gi.getStringExtra ( "homePhone" ) );
            ed_fatherName.setText ( ""+ gi.getStringExtra ( "fatherName" ) );
            ed_fatherPhone.setText ( ""+ gi.getStringExtra ( "fatherPhone" ) );
            ed_motherName.setText ( ""+ gi.getStringExtra ( "motherName" ) );
            ed_motherPhone.setText ( ""+ gi.getStringExtra ( "motherPhone" ) );
        }
        else
        {

        }
    }


    public void onClick_back1(View view) {
       finish ();
    }

    public void onClick_save(View view) {
        student = new Student (  );
        db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" );

        student.setName ( ed_name.getText ().toString ().trim () );
        student.setAddress ( ed_address.getText ().toString ().trim () );
        student.setPhone ( ed_phone.getText ().toString ().trim () );
        student.setHomePhone ( ed_homePhone.getText ().toString ().trim () );
        student.setFatherName ( ed_fatherName.getText ().toString ().trim () );
        student.setFatherPhone ( ed_fatherPhone.getText ().toString ().trim () );
        student.setMotherName ( ed_motherName.getText ().toString ().trim () );
        student.setMotherPhone ( ed_motherPhone.getText ().toString ().trim () );

        if(!itemSelected){
            db.push ().setValue ( student );
            Toast.makeText (EditStudent.this,""+key,Toast.LENGTH_SHORT).show ();
            ValueEventListener stuListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dS) {
                    db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" );
                    for (DataSnapshot childSnapshot: dS.getChildren()) {
                         key = childSnapshot.getKey ();
                    }
                    db.child ( key ).child ( "studentID" ).setValue ( key );

                }
                @Override
                public void onCancelled(DatabaseError databaseError) { }
            };
            refStudents.addValueEventListener(stuListener);
        }
        else
        {
            student.setStudentID ( gi.getStringExtra("id") );
            db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child ( gi.getStringExtra("id"));
            db.setValue ( student );
        }
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

        if(st.equals ( "Home" ))
        {
            Intent intent = new Intent ( this,MainActivity.class );
            startActivity ( intent );
        }
        return true;
    }
}
