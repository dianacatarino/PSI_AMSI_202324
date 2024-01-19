import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pt.ipleiria.estg.dei.lusitaniatravel.MainActivity;

public class PagamentoFragment extends Fragment {

    private TextView tvEntidade, tvReferencia, tvValor;
    private Button btnFinalizarPagamento;

    public PagamentoFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagamento, container, false);

        tvEntidade = view.findViewById(R.id.tvEntidade);
        tvReferencia = view.findViewById(R.id.tvReferencia);
        tvValor = view.findViewById(R.id.tvValor);
        btnFinalizarPagamento = view.findViewById(R.id.btnFinalizarPagamento);

        // Configurar valores fictícios para a entidade, referência e valor total
        tvEntidade.setText("Entidade: 21223");
        int reservaID = getArguments().getInt("idReserva", -1);
        tvReferencia.setText("REF0000: " + reservaID);
        double valorTotalReserva = getArguments().getDouble("valorTotalReserva", 0.0);
        tvValor.setText("Valor Total: " + valorTotalReserva);

        btnFinalizarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarPagamento();
            }
        });

        return view;
    }

    private void finalizarPagamento() {
        Toast.makeText(getActivity(), "Pagamento finalizado com sucesso!", Toast.LENGTH_SHORT).show();
        navigateBackToMenuPrincipal();
    }

    private void navigateBackToMenuPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
