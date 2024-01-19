package pt.ipleiria.estg.dei.lusitaniatravel;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/*public class DetalhesReservaActivity extends AppCompatActivity implements ReservaListener {

    private TextView tvCheckIn, tvCheckOut, tvTipo, tvValor, tvNumeroClientes, tvNumeroQuartos;
    public static final String ID_RESERVA = "id";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reserva);

        tvCheckIn = findViewById(R.id.textViewCheckin);
        tvCheckOut = findViewById(R.id.textViewCheckout);
        tvTipo = findViewById(R.id.textViewTipo);
        tvValor = findViewById(R.id.textViewValor);
        tvNumeroClientes = findViewById(R.id.textViewNumeroClientes);
        tvNumeroQuartos = findViewById(R.id.textViewNumeroQuartos);

        // Obtenha o ID do fornecedor da intenção
        int reservaId = getIntent().getIntExtra(ID_RESERVA, -1);

        // Verifique se o ID do fornecedor é válido
        if (reservaId != -1) {
            // Chame o método para obter os detalhes do fornecedor
            SingletonGestorLusitaniaTravel.getInstance(this).getFornecedorAPI(reservaId, this, this);
        }
    }

    @Override
    public void onRefreshDetalhes(Reserva reserva) {
        if (reserva != null) {
            tvCheckIn.setText(reserva.getCheckin());
            tvCheckOut.setText(reserva.getCheckout());
            tvTipo.setText(reserva.getTipo());
            tvValor.setText(reserva.getValor());
            tvNumeroClientes.setText(reserva.getNumeroClientes());
            tvNumeroQuartos.setText(reserva.getNumeroQuartos());
        } else {
            Toast.makeText(this, "Detalhes da reserva não disponíveis", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}*/
