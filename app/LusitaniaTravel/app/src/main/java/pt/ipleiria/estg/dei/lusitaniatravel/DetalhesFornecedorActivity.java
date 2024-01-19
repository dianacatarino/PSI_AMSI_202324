package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedorListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class DetalhesFornecedorActivity extends AppCompatActivity implements FornecedorListener {

    private TextView tvNomeAlojamento, tvTipo, tvLocalizacao, tvAcomodacoes, tvPrecoPorNoite, tvTipoQuartos, tvNumeroQuartos;

    public static final String ID_FORNECEDOR = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_fornecedor);

        tvNomeAlojamento = findViewById(R.id.textViewNomeAlojamento);
        tvTipo = findViewById(R.id.textViewTipo);
        tvLocalizacao = findViewById(R.id.textViewLocalizacao);
        tvAcomodacoes = findViewById(R.id.textViewAcomodacoes);
        tvPrecoPorNoite = findViewById(R.id.textViewPrecoPorNoite);
        tvTipoQuartos = findViewById(R.id.textViewTipoQuartos);
        tvNumeroQuartos = findViewById(R.id.textViewNumeroQuartos);

        // Obtenha o ID do fornecedor da intenção
        int fornecedorId = getIntent().getIntExtra(ID_FORNECEDOR, -1);

        // Verifique se o ID do fornecedor é válido
        if (fornecedorId != -1) {
            // Chame o método para obter os detalhes do fornecedor
            SingletonGestorLusitaniaTravel.getInstance(this).getFornecedorAPI(fornecedorId, this, this);
        }
    }

    @Override
    public void onRefreshDetalhes(Fornecedor fornecedor) {
        if (fornecedor != null) {
            tvNomeAlojamento.setText(fornecedor.getNomeAlojamento());
            tvTipo.setText(fornecedor.getTipo());
            tvLocalizacao.setText(fornecedor.getLocalizacaoAlojamento());
            tvAcomodacoes.setText(fornecedor.getAcomodacoesAlojamento());
            tvPrecoPorNoite.setText(String.valueOf(fornecedor.getPrecoPorNoite()));
            tvTipoQuartos.setText(fornecedor.getTipoQuartos());
            tvNumeroQuartos.setText(fornecedor.getNumeroQuartos());
        }
        if (fornecedor.getNumeroQuartos() != null) {
            tvNumeroQuartos.setText(fornecedor.getNumeroQuartos());
        } else {
            tvNumeroQuartos.setText("N/A");
        }
    } else {
         Toast.makeText(this, "Erro ao obter detalhes do fornecedor: " + errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
