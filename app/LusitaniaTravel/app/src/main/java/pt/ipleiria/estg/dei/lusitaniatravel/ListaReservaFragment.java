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

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaReservasAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservasListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaReservaFragment#} factory method to
 * create an instance of this fragment.
 *
 */
public class ListaReservaFragment extends Fragment implements ReservasListener {

    private ListView lvReservas;

    public ListaReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_reserva, container, false);

        lvReservas = view.findViewById(R.id.lvreservas);

        // Setar o listener para atualização da lista
        SingletonGestorLusitaniaTravel.getInstance(getContext()).setReservasListener(this);

        // Carregar a lista inicial de reservas
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllReservasAPI(this, getContext());

        // Configurar o clique em um item da lista
        lvReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void onRefreshListaReservas(ArrayList<Reserva> listaReservas) {
        if (listaReservas != null) {
            lvReservas.setAdapter(new ListaReservasAdaptador(getContext(), listaReservas));
        }
    }
}