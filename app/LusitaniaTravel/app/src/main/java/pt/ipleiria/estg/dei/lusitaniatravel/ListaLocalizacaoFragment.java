package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaFornecedoresAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaLocalizacaoFragment#} factory method to
 * create an instance of this fragment.
 */
public class ListaLocalizacaoFragment extends Fragment implements FornecedoresListener {

    private ListView lvFornecedores;
    private String localizacao;


    public ListaLocalizacaoFragment() {
        // Required empty public constructor
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_localizacao, container, false);

        lvFornecedores = view.findViewById(R.id.lvfornecedores);

        // Setar o listener para atualização da lista
        SingletonGestorLusitaniaTravel.getInstance(getContext()).setFornecedoresListener(this);

        // Verifica se a localização não está vazia antes de chamar a API
        if (localizacao != null && !localizacao.isEmpty()) {
            // Chama a API com base na localização
            SingletonGestorLusitaniaTravel.getInstance(getContext()).getLocalizacaoFornecedoresAPI(getContext(), localizacao);
        }

        // Configurar o clique em um item da lista
        lvFornecedores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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