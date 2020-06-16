package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnCrearDatos;
    private EditText txtActividad;
    private EditText txtFecha;
    private EditText txtHora;
    private FirebaseAuth mAuth;
    private ImageButton btnFecha, btnHora;
    private int dia, mes, ano, hora, minutos;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        btnCrearDatos = findViewById(R.id.buttonCrearDatos);
        txtActividad = findViewById(R.id.editTextActividad);
        txtFecha = findViewById(R.id.editTextFecha);
        txtHora = findViewById(R.id.editTextHora);
        btnFecha = findViewById(R.id.imageButtonFecha);
        btnHora = findViewById(R.id.imageButtonHora);

        btnCrearDatos.setOnClickListener(this);
        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);


    }

    private void crearDatos(){

        String actividad = txtActividad.getText().toString();
        String fecha = txtFecha.getText().toString();
        String hora = txtHora.getText().toString();

        if (TextUtils.isEmpty(actividad)) {
            Toast.makeText(this, "Se debe ingresar un nombre", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(fecha)) {
            Toast.makeText(this, "Se debe ingresar una fecha", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(hora)) {
            Toast.makeText(this, "Se debe ingresar una hora", Toast.LENGTH_LONG).show();
            return;
        }


        Map<String,Object> map = new HashMap<>();
        map.put("actividad", actividad);
        map.put("fecha", fecha);
        map.put("hora", hora);

        db.collection(mAuth.getUid()).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(HomeActivity.this, "La actividad se registró correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, MostrarDatosActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "La actividad no se registró correctamente", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {

        final Calendar c = Calendar.getInstance();

        switch (v.getId()){

                case R.id.buttonCrearDatos:
                crearDatos();
                break;



            case R.id.imageButtonFecha:

                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFecha.setText(String.format("%02d/%02d/%02d",dayOfMonth, (month+1), year));

                    }
                }, ano, mes, dia);
                datePickerDialog.show();
                break;

            case R.id.imageButtonHora:
                hora = c.get(Calendar.HOUR_OF_DAY);
                mes = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtHora.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },hora,minutos,true);
                timePickerDialog.show();

        }

    }




}