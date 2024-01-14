package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import java.util.Objects;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.SignUpListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class SignUpActivity extends AppCompatActivity implements SignUpListener {

    private EditText etUsername, etNome, etTelemovel, etRua, etLocalidade, etCodPostal, etEmail, etPassword, etRepeatPassword;
    public static final int MAX_CHAR = 4;
    public static final String EMAIL = "EMAIL";
    private SingletonGestorLusitaniaTravel singletonGestorLusitaniaTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Obter a instância do SingletonGestorLusitaniaTravel
        singletonGestorLusitaniaTravel = SingletonGestorLusitaniaTravel.getInstance(this);
        // Configurar o listener de SignUp
        singletonGestorLusitaniaTravel.setSignUpListener(this);
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

        if (!isCampoValido(username) || !isCampoValido(nome) || !isCampoValido(telemovel) ||
                !isCampoValido(rua) || !isCampoValido(localidade) || !isCampoValido(codPostal)) {
            return;
        }

        if (!isEmailValido(email) || !isPasswordValida(pass) || !pass.equals(confirmPass)) {
            return;
        }

        // Chamar o método registerAPI do SingletonGestorLusitaniaTravel
        singletonGestorLusitaniaTravel.registerAPI(username, pass, email, nome, telemovel, rua, localidade, codPostal, this);
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

    @Override
    public void onUpdateSignUp(User user) {
        // Este método será chamado após o registro bem-sucedido
        Toast.makeText(this, "User registado com sucesso", Toast.LENGTH_SHORT).show();
        // Você pode adicionar lógica aqui, como navegar para a próxima tela
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(EMAIL, user.getEmail());
        startActivity(intent);
        finish();
    }
}
