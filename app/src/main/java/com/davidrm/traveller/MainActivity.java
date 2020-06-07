package com.davidrm.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //definimos los view objets

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegistro,btnLogin, btnGoogle;
    private ProgressDialog progressDialog;

    //declaramos un objeto firebaseAuth

    private FirebaseAuth firebaseAuth;

    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;



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
        btnLogin = findViewById(R.id.buttonLogin);
        btnGoogle = findViewById(R.id.buttonGoogle);

        progressDialog = new ProgressDialog(this);

        btnRegistro.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);


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
                                    ,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = txtEmail.getText().toString().trim();
        String password  = txtPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //login a new user
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            int pos = email.indexOf("@");
                            String user = email.substring(0,pos);

                            Toast.makeText(MainActivity.this
                                    ,"Bienvenido "
                                            + txtEmail.getText(),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(),HomeActivity.class);
                            startActivity(intent);

                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){

                                Toast.makeText(MainActivity.this
                                        ,"El usuario ya existe",Toast.LENGTH_LONG).show();

                            }else

                                Toast.makeText(MainActivity.this
                                        ,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();//
                    }
                });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonRegistro:
                registrarUsuario();
                break;
            case R.id.buttonLogin:
                loguearUsuario();
                break;

        }

    }
}
