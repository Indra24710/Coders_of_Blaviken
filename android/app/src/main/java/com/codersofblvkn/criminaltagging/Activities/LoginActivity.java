package com.codersofblvkn.criminaltagging.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    TextView forgot_textview;
    EditText username, password;
    CheckBox show_password,remember_me;
    public BiometricPrompt biometricPrompt;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        username.setText(sp.getString("loginuname",""));
        password.setText(sp.getString("loginpassword",""));
        remember_me.setChecked(sp.getBoolean("remembermestatus",false));
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(new ContextThemeWrapper(LoginActivity.this, R.style.MyProgressDialog));
        login_button = findViewById(R.id.login_button);
        forgot_textview = findViewById(R.id.forgot_textview);
        username = findViewById(R.id.email_edittext);
        password = findViewById(R.id.password_edittext);
        show_password = findViewById(R.id.show_password);
        remember_me=findViewById(R.id.remember_me);

        forgot_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, getString(R.string.mailsent), Toast.LENGTH_SHORT).show();
                mAuth.sendPasswordResetEmail(username.getText().toString());
            }
        });

        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(null);
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });




        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remember_me.isChecked())
                {
                    SharedPreferences sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("loginuname", username.getText().toString());
                    edit.putString("loginpassword",password.getText().toString());
                    edit.putBoolean("remembermestatus",true);
                    edit.apply();
                }
                else
                {
                    SharedPreferences sp = getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("loginuname", "");
                    edit.putString("loginpassword","");
                    edit.putBoolean("remembermestatus",false);
                    edit.apply();
                }
                dialog.setMessage("Logging In...");
                dialog.show();
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    if(InternetConnection.checkConnection(getApplicationContext()))
                    {
                        mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            currentUser = mAuth.getCurrentUser();
                                            final Intent i = new Intent(LoginActivity.this, MainActivity.class);


                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && BiometricManager.from(LoginActivity.this).canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS) {
                                                Executor executor = Executors.newSingleThreadExecutor();
                                                biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                                                    @Override
                                                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                                                        super.onAuthenticationError(errorCode, errString);
                                                    }

                                                    @Override
                                                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                                                        super.onAuthenticationSucceeded(result);
                                                        startActivity(i);


                                                    }
                                                });
                                                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                                        .setTitle(getString(R.string.loginusingfingerprint))
                                                        .setNegativeButtonText(getString(R.string.dismiss))
                                                        .build();
                                                biometricPrompt.authenticate(promptInfo);

                                            }
                                            else
                                            {
                                                startActivity(i);
                                            }
                                        }else {
                                            if (dialog.isShowing()) {
                                                dialog.dismiss();
                                            }
                                            Toast.makeText(getApplicationContext(), getString(R.string.invalidcreds), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    else
                    {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                    }



                } else {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.enterdata), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
