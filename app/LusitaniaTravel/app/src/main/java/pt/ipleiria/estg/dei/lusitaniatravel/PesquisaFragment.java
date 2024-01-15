package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PesquisaFragment#} factory method to
 * create an instance of this fragment.
 */
public class PesquisaFragment extends Fragment {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    FragmentManager fragmentManager;

    private EditText editTextSearch;


    public PesquisaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesquisa, container, false);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLocalizacao(view);
            }
        });
        return view;
    }

    public void onClickLocalizacao(View view) {
        Log.d("PesquisaFragment", "onClickLocalizacao: Clicked");

        // Obtém a localização do EditText
        String localizacao = editTextSearch.getText().toString().trim();
        Log.d("PesquisaFragment", "onClickLocalizacao: Localizacao: " + localizacao);

        // Verifica se a localização não está vazia
        if (!localizacao.isEmpty()) {
            // Chama o método que faz a busca na API com base na localização
            fragmentManager = getChildFragmentManager();
            ListaLocalizacaoFragment listaLocalizacaoFragment = new ListaLocalizacaoFragment();
            listaLocalizacaoFragment.setLocalizacao(localizacao);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, listaLocalizacaoFragment).commit();
        } else {
            // Exibe uma mensagem indicando que a localização está vazia
            Toast.makeText(getContext(), "Digite uma localização para pesquisar", Toast.LENGTH_SHORT).show();
        }

    }
}