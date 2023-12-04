package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    //declarar
    private EditText etUsername, etNome, etTelemovel, etRua, etLocalidade, etCodPostal, etEmail, etPassword, etRepeatPassword;
    public static final int MAX_CHAR = 4;
    public static final String EMAIL = "EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        //inicializar
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etTelemovel = findViewById(R.id.etTelemovel);
        etRua = findViewById(R.id.etRua);
        etLocalidade = findViewById(R.id.etLocalidade);
        etCodPostal = findViewById(R.id.etCodPostal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adiciona o botão de volta (seta para trás) na ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSignUp(View view) {
        String username = etUsername.getText().toString();
        String nome = etNome.getText().toString();
        String telemovel = etTelemovel.getText().toString();
        String rua = etRua.getText().toString();
        String localidade = etLocalidade.getText().toString();
        String codPostal = etCodPostal.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        String confirmPass = etRepeatPassword.getText().toString();

        if (!isCampoValido(username)) {
            etUsername.setError("Username inválido");
            return;
        }
        if (!isCampoValido(nome)) {
            etNome.setError("Nome inválido");
            return;
        }
        if (!isCampoValido(telemovel)) {
            etTelemovel.setError("Telemovel inválido");
            return;
        }
        if (!isCampoValido(rua)) {
            etRua.setError("Rua inválida");
            return;
        }
        if (!isCampoValido(localidade)) {
            etLocalidade.setError("Localidade inválida");
            return;
        }
        if (!isCampoValido(codPostal)) {
            etCodPostal.setError("Código Postal inválido");
            return;
        }

        // Validar campos de e-mail, senha e confirmação de senha
        if (!isEmailValido(email)) {
            etEmail.setError("Email inválido");
            return;
        }
        if (!isPasswordValida(pass)) {
            etPassword.setError("Password inválida");
            return;
        }
        if (!pass.equals(confirmPass)) {
            etRepeatPassword.setError("As passwords não coincidem");
            return;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EMAIL, email);
        startActivity(intent);
        finish();
    }

    private boolean isCampoValido(String campo) {
        return campo != null && !campo.isEmpty();
    }

    private boolean isEmailValido(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String pass) {
        return pass != null && pass.length() >= MAX_CHAR;
    }
}
