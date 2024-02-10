package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaCarrinhoAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FinalizarListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaCarrinhoFragment#} factory method to
 * create an instance of this fragment.
 */
public class ListaCarrinhoFragment extends Fragment implements CarrinhosListener, FinalizarListener {

    private ListView lvCarrinho;
    private ListaCarrinhoAdaptador adaptador;
    private ArrayList<Carrinho> listaCarrinho;

    public ListaCarrinhoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_carrinho, container, false);

        lvCarrinho = view.findViewById(R.id.lvcarrinho);

        // Setar o listener para atualização da lista
        SingletonGestorLusitaniaTravel.getInstance(getContext()).setCarrinhosListener(this);

        // Carregar a lista inicial de reservas
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllCarrinhoAPI(getContext());

        // Configurar o clique em um item da lista
        lvCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Implemente a ação desejada ao clicar em uma reserva
            }
        });

        Button btnFinalizarCarrinho = view.findViewById(R.id.btnFinalizarCarrinho);

        btnFinalizarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaCarrinho != null && !listaCarrinho.isEmpty()) {
                    Carrinho itemValido = null;
                    int index = 0;

                    // Encontrar o primeiro item não nulo na lista de carrinho
                    while (index < listaCarrinho.size()) {
                        Carrinho carrinho = listaCarrinho.get(index);
                        if (carrinho != null) {
                            itemValido = carrinho;
                            break;
                        }
                        index++;
                    }

                    if (itemValido != null) {
                        String estado = itemValido.getEstado();
                        int reservaId = itemValido.getReservaId();
                        double subtotal = itemValido.getSubtotal();

                        if ("Confirmado".equals(estado)) {
                            // Se o estado for confirmado, você pode prosseguir com a lógica para finalizar o carrinho
                            SingletonGestorLusitaniaTravel.getInstance(getContext()).finalizarCarrinhoAPI(reservaId, getContext());
                            Toast.makeText(getContext(), "Carrinho finalizado com sucesso", Toast.LENGTH_SHORT).show();

                            atualizarListaCarrinho();

                            // Criar e exibir o diálogo de pagamento
                            PagamentoDialog pagamentoDialog = new PagamentoDialog();
                            Bundle args = new Bundle();
                            args.putInt("reservaId", reservaId);
                            args.putDouble("subtotal", subtotal);
                            pagamentoDialog.setArguments(args);
                            pagamentoDialog.show(getParentFragmentManager(), "dialog_pagamento");
                        } else {
                            // Se o estado não for confirmado, exibir um Toast informando que a reserva precisa estar confirmada
                            Toast.makeText(getContext(), "A reserva deve estar confirmada para finalizar o carrinho.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Lidar com o caso em que não há itens válidos na lista de carrinho
                        Toast.makeText(getContext(), "Nenhum item válido no carrinho.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Se a lista de carrinhos estiver vazia, exibir Toast informando que o carrinho não está disponível
                    Toast.makeText(getContext(), "Carrinho não disponível. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Remover a inflação do menu de pesquisa
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaCarrinho(ArrayList<Carrinho> listaCarrinho) {
        if (listaCarrinho != null) {
            // Atribuir a lista de carrinhos recebida à variável de instância carrinhos
            this.listaCarrinho = listaCarrinho;

            // Verificar se a atividade ainda está disponível antes de atualizar a UI
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvCarrinho.setAdapter(new ListaCarrinhoAdaptador(getContext(), listaCarrinho));
                        atualizarListaCarrinho();
                    }
                });
            }
        }
    }

    @Override
    public void onRefreshDetalhes(String mensagem) {
        // Método não utilizado no contexto atual
    }

    public void atualizarListaCarrinho() {
        // Método para atualizar a lista de carrinhos
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllCarrinhoAPI(getContext());
    }
}