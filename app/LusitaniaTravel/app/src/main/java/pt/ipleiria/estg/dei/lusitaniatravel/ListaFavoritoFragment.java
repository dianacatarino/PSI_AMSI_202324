package pt.ipleiria.estg.dei.lusitaniatravel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaCarrinhoAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaFavoritosAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaFornecedoresAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FavoritosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaFavoritoFragment#} factory method to
 * create an instance of this fragment.
 */
public class ListaFavoritoFragment extends Fragment implements FavoritosListener {

    private ListView lvFavoritos;
    public ListaFavoritoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_favorito, container, false);

        lvFavoritos = view.findViewById(R.id.lvfavoritos);

        // Setar o listener para atualização da lista
        SingletonGestorLusitaniaTravel.getInstance(getContext()).setFavoritosListener(this);

        // Carregar a lista inicial de fornecedores
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllFavoritosAPI(this, getContext());

        // Configurar o clique em um item da lista
        lvFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Implemente a ação desejada ao clicar em uma reserva
            }
        });

        // Habilitar o menu para o fragmento
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Remover a inflação do menu de pesquisa
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaFornecedores(ArrayList<Fornecedor> listaFornecedores) {
        if (listaFornecedores != null) {
            // Verificar se a atividade ainda está disponível antes de atualizar a UI
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvFavoritos.setAdapter(new ListaFavoritosAdaptador(getContext(), listaFornecedores));
                        atualizarListaFavorito();
                    }
                });
            }
        }
    }

    public void atualizarListaFavorito() {
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllFavoritosAPI(this, getContext());
    }
}