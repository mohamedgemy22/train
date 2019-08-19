package com.enggemy22.mytrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesShos extends AppCompatActivity {

private RecyclerView recyclerView;   // recycle View
private imageAdapter adapter;       // classImageAdpter
private DatabaseReference mReference; //database
private List<upload> uploads; //list
private ProgressBar progressBar; // progressBar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_shos);

        //recyle View
        recyclerView= findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
          //array List
        uploads= new ArrayList<>();
         //progressbar
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        //database get item from databas;
        mReference= FirebaseDatabase.getInstance().getReference("uploads");
        //EventVAlueEventListener as set on click listner
        //take to function on changes as contain for loop to get Image
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    upload up= snapshot.getValue(upload.class);
                    uploads.add(up);
                }
                adapter= new imageAdapter(ImagesShos.this,uploads);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

}
