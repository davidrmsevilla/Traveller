package com.davidrm.traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private FirebaseAuth mAuth;// ...



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        btnLogout = findViewById(R.id.buttonLogout);

        btnLogout.setOnClickListener(this);



    }

    private void logoutUsuario() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplication(),MainActivity.class);
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {
        logoutUsuario();
    }


}