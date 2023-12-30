package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;


public class MainActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE ="op_detalhes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Menu Principal");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adiciona o botão de volta (seta para trás) na ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //SingletonGestorLusitaniaTravel.getInstance(getApplicationContext());

        // Adiciona o fragmento ListaFornecedorFragment à MainActivity
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new ListaFornecedorFragment())
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_item_pesquisa) {
            // Handle pesquisa item click
            startActivity(new Intent(MainActivity.this, LocationActivity.class));
            Toast.makeText(this, "Pesquisa clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_item_favoritos) {
            // Handle favoritos item click
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            Toast.makeText(this, "Favoritos clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_item_reservas) {
            // Handle reservas item click
            startActivity(new Intent(MainActivity.this, BookingActivity.class));
            Toast.makeText(this, "Reservas clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.menu_item_conta) {
            // Handle conta item click
            startActivity(new Intent(MainActivity.this, UserActivity.class));
            Toast.makeText(this, "Conta clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}