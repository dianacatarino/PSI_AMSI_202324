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
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaCarrinhoFragment#} factory method to
 * create an instance of this fragment.
 */
public class ListaCarrinhoFragment extends Fragment implements CarrinhosListener {

    private ListView lvCarrinho;
    private ListaCarrinhoAdaptador adaptador;
    private ArrayList<Carrinho> carrinhos;

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
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllCarrinhoAPI( getContext());

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
                if (carrinhos != null && !carrinhos.isEmpty()) {
                    Carrinho carrinho = carrinhos.get(0);
                    int reservaId = carrinho.getReservaId();
                    double subtotal = carrinho.getSubtotal();

                    Log.d("ReservaID", String.valueOf(reservaId));

                    SingletonGestorLusitaniaTravel.getInstance(getContext()).finalizarCarrinhoAPI(reservaId, getContext());

                    Toast.makeText(getContext(), "Carrinho finalizado com sucesso", Toast.LENGTH_SHORT).show();
                }
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
    public void onRefreshListaCarrinho(ArrayList<Carrinho> listaCarrinho) {
        if (listaCarrinho != null) {
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

    public void atualizarListaCarrinho() {
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllCarrinhoAPI(getContext());
    }
}