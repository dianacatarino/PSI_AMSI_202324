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

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaComentariosAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaReservasAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ComentariosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Comentario;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.ComentarioJsonParser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaComentarioFragment#} factory method to
 * create an instance of this fragment.
 */
public class ListaComentarioFragment extends Fragment implements ComentariosListener {

    private ListView lvComentarios;

    public ListaComentarioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_comentario, container, false);

        lvComentarios = view.findViewById(R.id.lvcomentarios);

        // Setar o listener para atualização da lista
        SingletonGestorLusitaniaTravel.getInstance(getContext()).setComentariosListener(this);

        SingletonGestorLusitaniaTravel.getInstance(getContext()).getAllComentariosAPI(getContext());

        // Configurar o clique em um item da lista
        lvComentarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void onRefreshListaComentarios(ArrayList<Comentario> listaComentarios) {
        if (listaComentarios != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lvComentarios.setAdapter(new ListaComentariosAdaptador(getContext(), listaComentarios));
                }
            });
        }
    }
}