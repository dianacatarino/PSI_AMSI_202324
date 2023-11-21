package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //declarar
    private EditText etEmail, etPassword;
    public static final int MAX_CHAR = 4;
    public static final String EMAIL="EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        //inicializar
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
    }

    public void onClickLogin(View view) {
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();

        if(!isEmailValido(email)) {
            etEmail.setError("Email inválido");
            return;
        }
        if(!isPasswordValida(pass)) {
            etPassword.setError("Password inválida");
            return;
        }
        //Toast.makeText(this,"Auth bem sucedida", Toast.LENGTH_LONG).show();

        //Ex 3.2
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(EMAIL,email);
        startActivity(intent);
        finish();
    }

    private boolean isEmailValido(String email){
        if(email == null)
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String pass) {
        if(pass == null)
            return false;
        return pass.length() >= MAX_CHAR;
    }
}