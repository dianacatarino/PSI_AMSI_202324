package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_item_pesquisa) {
                    startActivity(new Intent(MainActivity.this, LocationActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_item_favoritos) {
                    startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_item_reservas) {
                    startActivity(new Intent(MainActivity.this, BookingActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_item_conta) {
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                    return true;
                }
                return false;
            }
        });

    }
}