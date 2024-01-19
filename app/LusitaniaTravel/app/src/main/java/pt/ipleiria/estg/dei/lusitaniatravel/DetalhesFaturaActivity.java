package pt.ipleiria.estg.dei.lusitaniatravel;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FaturaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class DetalhesFaturaActivity extends AppCompatActivity implements FaturaListener{

    private TextView tvTotalF, tvTotalSI, tvIva, tvData;

    public static final String ID_FATURA = "id";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_reserva);

        tvTotalF = findViewById(R.id.textViewTotalF);
        tvTotalSI = findViewById(R.id.textViewTotalSI);
        tvIva = findViewById(R.id.textViewIva);
        tvData = findViewById(R.id.textViewData);

        // Obtenha o ID do fornecedor da intenção
        int faturaId = getIntent().getIntExtra(ID_FATURA, -1);

        // Verifique se o ID do fornecedor é válido
        if (faturaId != -1) {
            // Chame o método para obter os detalhes do fornecedor
            SingletonGestorLusitaniaTravel.getInstance(this).getFornecedorAPI(faturaId, this, this);
        }
    }

    @Override
    public void onRefreshDetalhes(Fatura fatura) {
        if (fatura != null) {
            tvTotalF.setText(fatura.getTotalF());
            tvTotalSI.setText(fatura.getTotalSI());
            tvIva.setText(fatura.getIva());
            tvData.setText(fatura.getData());
        } else {
            Toast.makeText(this, "Detalhes da reserva não disponíveis", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
