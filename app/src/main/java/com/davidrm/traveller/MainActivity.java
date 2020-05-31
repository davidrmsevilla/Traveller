package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //definimos los view objets

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegistro;
    private ProgressDialog progressDialog;

    //declaramos un objeto firebaseAuth

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //referenciamos los views
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtPassword = (EditText) findViewById(R.id.editTextpassword);
        btnRegistro = (Button) findViewById(R.id.buttonRegistro);

        progressDialog = new ProgressDialog(this);

        btnRegistro.setOnClickListener(this);

    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = txtEmail.getText().toString().trim(); //trim para eliminar espacios
        String password = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en línea...");
        progressDialog.show();

        //creamos el nuevo usuario

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //comprobamos que la operacion se realiza correctamente
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Se ha registrado el email"
                                    ,Toast.LENGTH_LONG).show();;
                        }else{
                            Toast.makeText(MainActivity.this
                                    ,"No se pudo tegistrar el usuario",Toast.LENGTH_LONG).show();
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
