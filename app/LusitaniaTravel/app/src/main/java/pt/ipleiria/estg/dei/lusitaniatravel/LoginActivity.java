package pt.ipleiria.estg.dei.lusitaniatravel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.LoginListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar os elementos da interface
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        // Configurar a barra de ferramentas (Toolbar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        SingletonGestorLusitaniaTravel.getInstance(this).setLoginListener(this);
    }

    // Método chamado quando o botão de login é clicado
    public void onClickLogin(View view) {
        // Obter o username e password dos EditTexts
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Chamar o método de login da instância do Singleton
        SingletonGestorLusitaniaTravel.getInstance(this).loginAPI(username, password, this);
    }

    // Método chamado quando o token de login é recebido
    @Override
    public void onUpdateLogin(User user) {
        if (user != null) {

            String combinedCredentials = user.getUsername() + ":" + user.getPassword();

            // Base64 encode the combined credentials
            String base64EncodedToken = Base64.encodeToString(combinedCredentials.getBytes(), Base64.DEFAULT);

            // Save the Base64-encoded credentials in SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            prefs.edit()
                    .putString("token", base64EncodedToken)
                    .apply();

            Log.d(TAG,"Token saved: " + base64EncodedToken);

            // Exibir uma mensagem ou iniciar a próxima atividade após o login
            Toast.makeText(this, "Login bem-sucedido para o user: " + user.getUsername(), Toast.LENGTH_SHORT).show();

            SingletonGestorLusitaniaTravel.getInstance(this).setCredentials(user.getUsername(), user.getPassword());

            // Iniciar a próxima atividade após o login
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            // Encerrar a atividade de login após o sucesso
            finish();
        } else {
            Toast.makeText(this, "Falha no login. User não disponível.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método chamado quando um item do menu é selecionado
    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Lógica para lidar com o clique no ícone de voltar/up
            finish(); // Encerrar a atividade se o ícone de voltar for clicado
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}