package pt.ipleiria.estg.dei.lusitaniatravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;

public class DetalhesAlojamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_alojamento);

        //Recebe os dados através do Intent
        Intent intent = getIntent();
        Fornecedor alojamento = (Fornecedor) intent.getSerializableExtra("fornecedor");

        TextView tvLocalizacao = findViewById(R.id.tvLocalizacao);
        TextView tvAcomodacoes = findViewById(R.id.tvAcomodacoes);
        TextView tvPrecoPorNoite = findViewById(R.id.tvPrecoPorNoite);

        tvLocalizacao.setText(fornecedor.getLocalizacaoAlojamento());
        tvAcomodacoes.setText(fornecedor.getAcomodacoesAlojamento());
        tvPrecoPorNoite.setText(fornecedor.getPrecoPorNoite);

    }


    public void onRefreshDetalhes(int op) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.OP_CODE,op);
        setResult(RESULT_OK,intent);
    }
}