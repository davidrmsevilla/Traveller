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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //definimos los view objets

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnRegistro, btnLogin, btnGoogle, btnForgot, btnExit;
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
        mAuth = FirebaseAuth.getInstance();


        //referenciamos los views
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtPassword = (EditText) findViewById(R.id.editTextpassword);

        btnRegistro = (Button) findViewById(R.id.buttonRegistro);
        btnLogin = findViewById(R.id.buttonLogin);
        btnGoogle = findViewById(R.id.buttonGoogle);
        btnForgot = findViewById(R.id.buttonForgotPassword);
        btnExit = findViewById(R.id.buttonExit);

        progressDialog = new ProgressDialog(this);

        btnRegistro.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
        btnExit.setOnClickListener(this);


        configGoogle();
    }

    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //login a new user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);

                            Toast.makeText(MainActivity.this
                                    , "Bienvenido "
                                            + user, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), MostrarDatosActivity.class);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                Toast.makeText(MainActivity.this
                                        , "El usuario ya existe", Toast.LENGTH_LONG).show();

                            } else

                                Toast.makeText(MainActivity.this
                                        , "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();//
                    }
                });

    }

    private void configGoogle() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressDialog.setMessage("Accediendo con tu cuenta de Google...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),MostrarDatosActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this
                                    , "Lo sentimos error de auntenticación"
                                    , Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {

        //Intent intent;

        switch (v.getId()) {
            case R.id.buttonRegistro:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                break;
            case R.id.buttonLogin:
                loguearUsuario();
                break;

            case R.id.buttonGoogle:
                signIn();
                break;

            case R.id.buttonForgotPassword:
                startActivity(new Intent(getApplicationContext(),ForgotActivity.class));
                break;

            case R.id.buttonExit:
                finishAffinity();

        }

    }

    @Override
    public void onBackPressed() {

    }





}