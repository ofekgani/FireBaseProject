package com.example.firebaseproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class FBref {
    /**
     * @auther Ofek gani
     * @Version 1.0
     * @since 14/7/2020
     */
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refStudents=FBDB.getReference("Student");
}
