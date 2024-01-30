package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;


public class MainActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Menu Principal");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SingletonGestorLusitaniaTravel.getInstance(getApplicationContext());

        // Adiciona o fragmento ListaFornecedorFragment à MainActivity
        fragmentManager = getSupportFragmentManager();
        // if (savedInstanceState == null) {
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ListaFornecedorFragment()).commit();

        // Configurar o BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Chame o método onOptionsItemSelected para processar a seleção do item
                int itemId = item.getItemId();
                Fragment fragment = null;
                if (itemId == R.id.menu_item_home) {
                    fragment = new ListaFornecedorFragment();
                    setTitle(item.getTitle());
                }
                if (itemId == R.id.menu_item_pesquisa) {
                    fragment = new PesquisaFragment();
                    setTitle(item.getTitle());
                }
                if (itemId == R.id.menu_item_favoritos) {
                    fragment = new FavoritosFragment();
                    setTitle(item.getTitle());
                }
                if (itemId == R.id.menu_item_reservas) {
                    fragment = new ReservaFragment();
                    setTitle(item.getTitle());
                }
                if (itemId == R.id.menu_item_conta) {
                    fragment = new UserFragment();
                    setTitle(item.getTitle());
                }

                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

                return onOptionsItemSelected(item);
            }
        });

        // Configurar o OnClickListener para o botão de carrinho
        ImageButton carrinhoButton = findViewById(R.id.imageButton);
        carrinhoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ao clicar no carrinho, direcione para o CarrinhoFragment
                CarrinhoFragment carrinhoFragment = new CarrinhoFragment();

                setTitle("Carrinho");fragmentManager.beginTransaction().replace(R.id.fragmentContainer, carrinhoFragment).commit();
            }
        });

        // Configurar o OnClickListener para o botão de logout
        ImageButton logoutButton = findViewById(R.id.imageButtonSair);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criar uma caixa de diálogo de confirmação
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Tem a certeza que deseja sair?");
                builder.setCancelable(true);

                // Adicionar um botão "Sim" para confirmar o logout
                builder.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Ao clicar em "Sim", direcione para o LoginActivity
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                // Adicionar um botão "Não" para cancelar o logout
                builder.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Cancelar a caixa de diálogo e não fazer nada
                                dialog.cancel();
                            }
                        });

                // Criar e exibir a caixa de diálogo
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }


}