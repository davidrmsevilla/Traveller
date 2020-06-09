package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();


        txtEmail = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextpassword);
        btnRegister = findViewById(R.id.buttonRegister);

        btnRegister.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);


    }

    private void registrarUsuario() {

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = txtEmail.getText().toString().trim(); //trim para eliminar espacios
        String password = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en línea...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //creamos el nuevo usuario

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //comprobamos que la operacion se realiza correctamente
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Se ha registrado el usuario"
                                    , Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegisterActivity.this
                                    , "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        registrarUsuario();

    }


}