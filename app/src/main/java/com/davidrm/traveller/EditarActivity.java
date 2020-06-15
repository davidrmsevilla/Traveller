package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {

    private String actividadId;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private ImageButton btnEditarFecha, btnEditarHora;
    private EditText txtEditarActividad,txtEditarFecha,txtEditarHora;
    private Button btnActualizarDatos, btnBorrarActividad;
    private int dia, mes, ano, hora, minutos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        actividadId = getIntent().getStringExtra("actividadId");

        txtEditarActividad = findViewById(R.id.editTextActividadEditar);
        txtEditarFecha = findViewById(R.id.editTextFechaEditar);
        txtEditarHora = findViewById(R.id.editTextHoraEditar);
        btnActualizarDatos = findViewById(R.id.buttonActualizarDatos);
        btnEditarFecha = findViewById(R.id.imageButtonFechaEditar);
        btnEditarHora = findViewById(R.id.imageButtonHoraEditar);
        btnBorrarActividad = findViewById(R.id.buttonEliminarDatos);

        obtenerDatos();

        btnActualizarDatos.setOnClickListener(this);
        btnEditarFecha.setOnClickListener(this);
        btnEditarHora.setOnClickListener(this);
        btnBorrarActividad.setOnClickListener(this);


    }

    private void obtenerDatos(){
        mFirestore.collection(mAuth.getUid()).document(actividadId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String actividad = documentSnapshot.getString("actividad");
                    String fecha = documentSnapshot.getString("fecha");
                    String hora = documentSnapshot.getString("hora");

                    txtEditarActividad.setText(actividad);
                    txtEditarFecha.setText(fecha);
                    txtEditarHora.setText(hora);


                }

            }
        });
    }

    private void borrarDatos(){
        mFirestore.collection(mAuth.getUid()).document(actividadId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditarActivity.this, "La actividad se ha borrado correctamente"
                        ,Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarActivity.this, "La actividad no se ha podido borrar"
                        ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void actualizarDatos(){

        String actividad = txtEditarActividad.getText().toString();
        String fecha = txtEditarFecha.getText().toString();
        String hora = txtEditarHora.getText().toString();

        Map<String, Object> map = new HashMap<>();

        map.put("actividad", actividad);
        map.put("fecha", fecha);
        map.put("hora", hora);

        mFirestore.collection(mAuth.getUid()).document(actividadId).update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditarActivity.this, "Los datos se han actualizado correctamente"
                        ,Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditarActivity.this, "Los datos no se pudieron actualizar"
                        ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {

        final Calendar c = Calendar.getInstance();

        switch (v.getId()){

            case R.id.buttonActualizarDatos:
                actualizarDatos();
                startActivity(new Intent(EditarActivity.this, MostrarDatosActivity.class));
                break;

            case R.id.buttonEliminarDatos:
                borrarDatos();
                startActivity(new Intent(EditarActivity.this, MostrarDatosActivity.class));
                break;

            case R.id.imageButtonFechaEditar:

                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtEditarFecha.setText(String.format("%02d/%02d/%02d",dayOfMonth, (month+1), year));

                    }
                }, ano, mes, dia);
                datePickerDialog.show();
                break;

            case R.id.imageButtonHoraEditar:
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtEditarHora.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },hora,minutos,true);
                timePickerDialog.show();






        }


    }
}