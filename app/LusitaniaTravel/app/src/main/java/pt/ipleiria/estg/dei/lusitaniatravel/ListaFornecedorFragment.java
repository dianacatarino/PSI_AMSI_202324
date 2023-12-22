package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaFornecedoresAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaFornecedorFragment} factory method to
 * create an instance of this fragment.
 */
public class ListaFornecedorFragment extends Fragment implements FornecedoresListener {

    private ListView lvFornecedores;
    private SearchView searchView;

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
                // Implemente a ação desejada ao clicar em um fornecedor
            }
        });

        // Configurar a barra de pesquisa
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);

        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Fornecedor> tempListaFornecedor = new ArrayList<>();
                SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllFornecedoresAPI(new FornecedoresListener() {
                    @Override
                    public void onRefreshListaFornecedores(ArrayList<Fornecedor> listaFornecedores) {
                        if (listaFornecedores != null) {
                            tempListaFornecedor.addAll(listaFornecedores);
                            lvFornecedores.setAdapter(new ListaFornecedoresAdaptador(getContext(), tempListaFornecedor));
                        }
                    }
                }, getContext());
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshListaFornecedores(ArrayList<Fornecedor> listaFornecedores) {
        if (listaFornecedores != null) {
            lvFornecedores.setAdapter(new ListaFornecedoresAdaptador(getContext(), listaFornecedores));
        }
    }
}