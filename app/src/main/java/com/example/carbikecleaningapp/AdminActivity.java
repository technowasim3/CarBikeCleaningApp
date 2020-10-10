package com.example.carbikecleaningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.carbikecleaningapp.Data.DataUser;
import com.example.carbikecleaningapp.Data.UserAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<DataUser> options;
    private FirebaseRecyclerAdapter<DataUser, RecyclerView.ViewHolder> recyclerAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private UserAdapter adapter;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("messages");
        recyclerView = findViewById(R.id.rv_admin);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        FirebaseRecyclerOptions<DataUser> options = new FirebaseRecyclerOptions.Builder<DataUser>()
                .setQuery(databaseReference,DataUser.class).build();

        adapter=new UserAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }
}