package com.example.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Credits extends AppCompatActivity {
    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_credits );
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
