package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private EditText txtForgotPass;
    private Button btnForgotPass;
    private String email="";
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        txtForgotPass = (EditText) findViewById(R.id.editTextForgotPass);
        btnForgotPass = (Button) findViewById(R.id.buttonSend);

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = txtForgotPass.getText().toString();

                if(!email.isEmpty()){
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(ForgotActivity.this,"Debe ingresar un email"
                            , Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void resetPassword(){
        mAuth.setLanguageCode("en");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this
                            , "Se ha enviado un correo para restablecer contraseña a " + email
                            , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);

                }

                else{
                    Toast.makeText(ForgotActivity.this, "No se pudo enviar password"
                            , Toast.LENGTH_SHORT).show();

                }

                progressDialog.dismiss();
            }
        });
    }

}