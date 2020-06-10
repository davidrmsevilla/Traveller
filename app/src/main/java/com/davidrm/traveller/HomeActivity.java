package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private Button btnCrearDatos;
    private EditText txtActividad;
    private EditText txtFecha;
    private FirebaseAuth mAuth;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.buttonLogout);
        btnCrearDatos = findViewById(R.id.buttonCrearDatos);
        txtActividad = findViewById(R.id.editTextActividad);
        txtFecha = findViewById(R.id.editTextFecha);

        btnLogout.setOnClickListener(this);
        btnCrearDatos.setOnClickListener(this);
    }

    private void crearDatos(){

        String actividad = txtActividad.getText().toString();
        String fecha = txtActividad.getText().toString();


        Map<String,Object> map = new HashMap<>();
        map.put("actividad", actividad);
        map.put("fecha", new Date().getTime());
        db.collection(mAuth.getUid()).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(HomeActivity.this, "La actividad se registró correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "La actividad no se registró correctamente", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void logoutUsuario() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplication(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonLogout:
                logoutUsuario();
                break;

            case R.id.buttonCrearDatos:
                crearDatos();
                break;
        }

    }

    @Override
    public void onBackPressed() {

    }


}