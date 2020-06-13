package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private Button btnCrearDatos;
    private Button btnMostrarDatos;
    private EditText txtActividad;
    private EditText txtFecha;
    private EditText txtHora;
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
        btnMostrarDatos = findViewById(R.id.buttonMostrarDatos);
        txtActividad = findViewById(R.id.editTextActividad);
        txtFecha = findViewById(R.id.editTextFecha);
        txtHora = findViewById(R.id.editTextHora);




        btnLogout.setOnClickListener(this);
        btnCrearDatos.setOnClickListener(this);
        btnMostrarDatos.setOnClickListener(this);

        //obtenerDatos();

    }

    private void crearDatos(){

        String actividad = txtActividad.getText().toString();
        String fecha = txtFecha.getText().toString();
        String hora = txtHora.getText().toString();


        Map<String,Object> map = new HashMap<>();
        map.put("actividad", actividad);
        map.put("fecha", fecha);
        map.put("hora", hora);

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

    /*private void obtenerDatos(){

        /*db.collection(mAuth.getUid()).document("t24J7y1qlYcFaqh0CZas").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()) {
                    
                    String actividad="Default";
                    long fecha = 0;

                    if(documentSnapshot.contains("actividad")){
                        actividad = documentSnapshot.getString("actividad");
                    }

                    if(documentSnapshot.contains("fecha")){
                        fecha = documentSnapshot.getLong("fecha");
                    }                    

                    //txtDatos.setText("Actividad: " + actividad + "\n" + "Fecha: " + fecha);
                }
            }
        });

        /*db.collection("xT4jwoZme1fa5Q1fcmZba2I6fYm1").document("70b29STVOSTyqollH12H").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String actividad = documentSnapshot.getString("actividad");
                    long fecha = documentSnapshot.getLong("fecha");

                    txtDatos.setText("actividad: " + actividad + "\n" + "fecha: " + fecha);
                }
            }
        });

    }*/

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

            case R.id.buttonMostrarDatos:
                startActivity(new Intent(HomeActivity.this, MostrarDatosActivity.class));
                break;


        }

    }

    @Override
    public void onBackPressed() {

    }


}