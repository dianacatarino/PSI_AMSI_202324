package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOrSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sign_up);

    }

    public void onClickSignIn(View view) {
        // Adicione a lógica para lidar com o clique no botão de login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Método chamado quando o botão de registro é clicado
    public void onClickSignUp(View view) {
        // Adicione a lógica para lidar com o clique no botão de registro
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}