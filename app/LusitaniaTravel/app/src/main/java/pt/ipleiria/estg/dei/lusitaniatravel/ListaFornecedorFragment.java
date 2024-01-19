package pt.ipleiria.estg.dei.lusitaniatravel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaFornecedoresAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class ListaFornecedorFragment extends Fragment implements FornecedoresListener {

    private ListView lvFornecedores;
    private int fornecedorId;
    private Carrinho carrinho;

    public ListaFornecedorFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_fornecedor, container, false);

        lvFornecedores = view.findViewById(R.id.lvfornecedores);

        // Setar o listener para atualização da lista
        SingletonGestorLusitaniaTravel.getInstance(getContext()).setFornecedoresListener(this);

        // Carregar a lista inicial de fornecedores
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllFornecedoresAPI(this, getContext());

        // Configurar o clique em um item da lista
        lvFornecedores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                fornecedorId = (int) id;
                //Toast.makeText(getContext(),fornecedores.get(position).getFornecedor(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), DetalhesFornecedorActivity.class);
                intent.putExtra(DetalhesFornecedorActivity.ID_FORNECEDOR, (int) id);
                startActivity(intent);
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
            lvFornecedores.setAdapter(new ListaFornecedoresAdaptador(getContext(), listaFornecedores));
        }
    }
}
