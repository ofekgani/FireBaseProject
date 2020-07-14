package com.example.firebaseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.firebaseproject.FBref.refStudents;

public class EditGrades extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */
    EditText ed_subject, ed_grade, ed_quarter;
    ListView listView;

    ArrayList<String> tbl = new ArrayList<String> ();
    ArrayList<Grade> gradeValues = new ArrayList<Grade>();
    ArrayAdapter<String> adp;

    DatabaseReference db;
    Grade grade;

    Intent gi;
    String stuKey, key;

    String str_subject = "", str_quarter = "", str_grade = "";

    int idPosition;
    boolean itemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_grades );

        ed_grade = findViewById(R.id.ed_grade);
        ed_subject = findViewById(R.id.ed_subject);
        ed_quarter = findViewById(R.id.ed_quarter);

        listView = findViewById(R.id.gradeList);
        gi = getIntent ();

        stuKey = gi.getStringExtra ( "id" );
        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                dS.child ( stuKey ).child ( "Grade" ).getChildren ();
                tbl.clear();
                gradeValues.clear();

                for(DataSnapshot data : dS.child ( stuKey ).child ( "Grade" ).getChildren ()) {
                    db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child (stuKey).child ( "Grade" );
                    db.child (data.getValue ().toString ());
                    Grade grade = data.getValue (Grade.class);
                    gradeValues.add(grade);

                    if(data.child ( "subject" ).getValue (  ) != null)
                        str_subject = data.child ( "subject" ).getValue ().toString ();
                    if(data.child ( "quarter" ).getValue (  ) != null)
                        str_quarter = data.child ( "quarter" ).getValue ().toString ();
                    if(data.child ( "grade" ).getValue (  ) != null)
                        str_grade = data.child ( "grade" ).getValue ().toString ();
                    if(data.child ( "grade" ).getValue (  ) != null && data.child ( "subject" ).getValue (  )!= null && data.child ( "quarter" ).getValue (  ) != null)
                        tbl.add("Subject: "+str_subject+" Quarter: "+str_quarter+" Grade: "+str_grade);

                }
                listView.setOnItemClickListener(EditGrades.this);
                listView.setChoiceMode( AbsListView.CHOICE_MODE_SINGLE);
                adp = new ArrayAdapter<String>(EditGrades.this,R.layout.support_simple_spinner_dropdown_item, tbl);
                listView.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        refStudents.addValueEventListener(stuListener);

    }

    public void onClick_addGrade(View view) {
        grade = new Grade (  );
        db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child (stuKey).child ( "Grade" );
        grade.setSubject ( ed_subject.getText ().toString ().trim () );
        grade.setQuarter ( ed_quarter.getText ().toString ().trim () );
        grade.setGrade ( ed_grade.getText ().toString ().trim () );
        db.push ().setValue ( grade );

        ValueEventListener stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dS) {
                dS.child ( stuKey ).child ( "Grade" ).getChildren ();
                db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child (stuKey).child ( "Grade" );
                for (DataSnapshot childSnapshot: dS.child ( stuKey ).child ( "Grade" ).getChildren ()) {
                    key = childSnapshot.getKey ();
                }
                db.child ( key ).child ( "studentID" ).setValue ( key );

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        refStudents.addValueEventListener(stuListener);

    }

    public void onClick_saveGrade(View view) {
        if(itemSelected)
        {
            Grade grade = new Grade ();
            grade.setSubject ( ed_subject.getText ().toString ().trim () );
            grade.setQuarter ( ed_quarter.getText ().toString ().trim () );
            grade.setGrade ( ed_grade.getText ().toString ().trim () );
            grade.setStudentID ( gradeValues.get ( idPosition ).getStudentID () );

            db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child (stuKey).child ( "Grade" ).child ( gradeValues.get ( idPosition ).getStudentID () );
            db.setValue ( grade );
        }
    }

    public void onClick_deleteGrade(View view) {
        if(itemSelected)
        {
            db = FirebaseDatabase.getInstance ().getReference ().child ( "Student" ).child ( stuKey ).child ( "Grade" ).child ( gradeValues.get ( idPosition ).getStudentID () );
            db.removeValue ();
            adp.notifyDataSetChanged ();
            itemSelected = false;
        }
    }

    public void onClick_backToStudent(View view) {
        finish ();
    }

    public void onClick_order(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        idPosition = i; //student`s id by position in list
        itemSelected = true; //item in list selected.

        if(itemSelected)
        {
            ed_subject.setText ( ""+ gradeValues.get ( idPosition ).getSubject ()  );
            ed_quarter.setText ( ""+ gradeValues.get ( idPosition ).getQuarter () );
            ed_grade.setText ( ""+ gradeValues.get ( idPosition ).getGrade () );
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
