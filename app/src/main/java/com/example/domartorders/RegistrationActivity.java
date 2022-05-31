package com.example.domartorders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class RegistrationActivity extends Activity implements View.OnClickListener {

    private final static String TAG = RegistrationActivity.class.getSimpleName();
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout password1;
    TextInputEditText password1EditText;
    FirebaseAuth firebaseAuth;
    Button registerBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        registerBttn = findViewById(R.id.registrationPrzycisk);

        email = findViewById(R.id.input_email_registration);
        password = findViewById(R.id.input_password_registration);
        password1 = findViewById(R.id.input_password1_registration);
        password1EditText = findViewById(R.id.password1EditText);

        registerBttn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            //miejsce na kod, gdy użytkownik jest zalogowany
            Log.d(TAG, "onComplete: success");
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            RegistrationActivity.this.finish();
        } else {
            //miejsce na kod, gdy użytkownik nie jest zalogwany - zrobić z kodu na logowanie(poniżej) metodę i nią to obsługiwać
            Log.d(TAG, "onComplete: failed");
            registrationMethod();
        }
    }

    @Override
    public void onClick(View v) {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(findViewById(R.id.registrationPrzycisk));

        if (!emailValidation() | !passwordValidation() | !password1Validation()) {
            return;
        }

        registrationMethod();
    }

    private void registrationMethod() {
        String registerLogin = email.getEditText().getText().toString();
        String registerPassword = password.getEditText().getText().toString();
        if (registerLogin.trim().length() > 0 && registerPassword.trim().length() > 0) {
            firebaseAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (emailValidation() | passwordValidation() | password1Validation()) {
                        Log.d(TAG, "onComplete: success");
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Utworzono pomyślnie" + "   : " + uid, Toast.LENGTH_LONG).show();
                        RegistrationActivity.this.startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        RegistrationActivity.this.finish();
                    } else {
                        Log.d(TAG, "onComplete: failed");
                        Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    public boolean emailValidation() {
        String emailString = email.getEditText().getText().toString().trim();
        String checkEmail = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";



        if (emailString.isEmpty()) {
            email.setError("Wprowadź email");
            return false;
    }else if (!emailString.isEmpty()){
            firebaseAuth.fetchSignInMethodsForEmail(emailString)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                            if (isNewUser) {
                                Log.e("TAG", "Is New User!");
                            } else {
                                Log.e("TAG", "Is Old User!");
                                email.setError("Email już istnieje");
                            }
                        }
                    });
            return false;
    }else if (!emailString.matches(checkEmail)) {
            email.setError("Nieprawidłowy email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }


    public boolean passwordValidation() {
        String passwordString = password.getEditText().getText().toString().trim();
        String checkPassword = "^\\w+([.-]?\\w+)";

        if (passwordString.isEmpty()) {
            password.setError("Wprowadź hasło");
            return false;
        } else if (!passwordString.matches(checkPassword)) {
            password.setError("Nieprawidłowe hasło");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public boolean password1Validation() {
        String password1String = password1.getEditText().getText().toString().trim();
        String checkPassword1 = "^\\w+([.-]?\\w+)";
        String passwordString = password.getEditText().getText().toString().trim();

        if (password1String.isEmpty()) {
            password1.setError("Potwierdź hasło");
            return false;
        } else if (!password1String.matches(checkPassword1)) {
            password1.setError("Nieprawidłowe hasło");
            return false;
        } else if (!passwordString.equals(password1String) | !password1String.equals(passwordString)) {
            password1.setError("Hasła nie są zgodne");
            password1EditText.setText("");
            return false;
        } else {
            password1.setError(null);
            password1.setErrorEnabled(false);
            return true;
        }
    }
}
