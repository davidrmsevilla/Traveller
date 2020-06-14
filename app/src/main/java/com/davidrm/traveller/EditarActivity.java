package com.davidrm.traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditarActivity extends AppCompatActivity {

    private String actividadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        actividadId = getIntent().getStringExtra("articuloId");
    }
}