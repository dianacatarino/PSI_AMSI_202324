package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class PagamentoFragment extends Fragment implements CarrinhoListener {

    private ImageView imageView;
    private TextView tvEntidade, tvReferencia, tvValor;
    private int entidade = 21223;
    private int reservaId;
    private double subtotal;

    public PagamentoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pagamento, container, false);

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
            tvValor.setText("Valor Total: " + subtotal);
        }

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
