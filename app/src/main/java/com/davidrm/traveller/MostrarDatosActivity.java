package com.davidrm.traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarDatosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerViewActividades;
    BookingAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    FloatingActionButton fltAdd, fltLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);


        fltAdd = findViewById(R.id.floatingButtonAdd);
        fltLogOut = findViewById(R.id.floatingButtonLogOut);
        recyclerViewActividades = findViewById(R.id.recyclerActividades);
        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Query query = mFirestore.collection(mAuth.getUid());

        FirestoreRecyclerOptions<Actividad> firestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<Actividad>().setQuery(query,Actividad.class).build();

        mAdapter = new BookingAdapter(firestoreRecyclerOptions,this);
        mAdapter.notifyDataSetChanged();
        recyclerViewActividades.setAdapter(mAdapter);

        fltAdd.setOnClickListener(this);
        fltLogOut.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.floatingButtonLogOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
                break;


            case R.id.floatingButtonAdd:
                startActivity(new Intent(MostrarDatosActivity.this, HomeActivity.class));
                break;


        }

    }

    @Override
    public void onBackPressed() {

    }
}