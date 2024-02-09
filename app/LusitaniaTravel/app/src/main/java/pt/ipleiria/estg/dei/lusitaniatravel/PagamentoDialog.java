package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;

public class PagamentoDialog extends DialogFragment implements CarrinhoListener {

    private ImageView imageView;
    private TextView tvEntidade, tvReferencia, tvValor;
    private int entidade = 21223;
    private int reservaId;
    private double subtotal;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pagamento, container, false);

        // Initialize views
        imageView = view.findViewById(R.id.imageView2);
        tvEntidade = view.findViewById(R.id.tvEntidade);
        tvReferencia = view.findViewById(R.id.tvReferencia);
        tvValor = view.findViewById(R.id.tvValor);

        // Retrieve the reservation ID from arguments
        Bundle args = getArguments();
        if (args != null) {
            reservaId = args.getInt("reservaId", -1);
            subtotal = args.getDouble("subtotal", 0.0);
            tvEntidade.setText("Entidade: " + entidade);
            tvReferencia.setText("Referência: REF000000" + reservaId);
            tvValor.setText("Valor Total: " + subtotal + "€");
        }


        ImageButton btnFechar = view.findViewById(R.id.btnFechar);
        ImageButton btnDownload = view.findViewById(R.id.btnDownload);

        btnFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Fecha o diálogo
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para o download
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Remover a inflação do menu de pesquisa
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshDetalhes(int op) {
        if (op == CarrinhoFragment.ADD) {
            Toast.makeText(getContext(), "Carrinho finalizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Carrinho não foi finalizado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}
