package com.davidrm.traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarDatosActivity extends AppCompatActivity {

    RecyclerView recyclerViewActividades;
    BookingAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);


        recyclerViewActividades = findViewById(R.id.recyclerActividades);
        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Query query = mFirestore.collection(mAuth.getUid());

        FirestoreRecyclerOptions<Actividad> firestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<Actividad>().setQuery(query,Actividad.class).build();

        mAdapter = new BookingAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewActividades.setAdapter(mAdapter);

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
}