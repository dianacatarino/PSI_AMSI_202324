package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FinalizarListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrinhoFragment#} factory method to
 * create an instance of this fragment.
 */
public class CarrinhoFragment extends Fragment {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ListaCarrinhoFragment()).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carrinho, container, false);

        /*Button btnFinalizarCarrinho = view.findViewById(R.id.btnFinalizarCarrinho);
        btnFinalizarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buscar o carrinho atual e finalizá-lo
                ListaCarrinhoFragment listaCarrinhoFragment = (ListaCarrinhoFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (listaCarrinhoFragment != null) {
                    Carrinho carrinhoAtual = listaCarrinhoFragment.getCarrinhoAtual();
                    if (carrinhoAtual != null) {
                        int reservaId = carrinhoAtual.getReservaId();
                        String estado = carrinhoAtual.getEstado();

                        Log.d("Carrinho", "Reserva ID: " + reservaId + ", Estado: " + estado);
                        if (estado.equals("pendente")) {
                            // Se o estado for pendente, exibir um Toast informando que a reserva ainda não foi confirmada
                            Toast.makeText(getContext(), "A reserva ainda não foi confirmada", Toast.LENGTH_SHORT).show();
                        } else if (estado.equals("confirmado")) {
                            // Se o estado for confirmado, finalizar o carrinho
                            SingletonGestorLusitaniaTravel.getInstance(getContext()).finalizarCarrinhoAPI(reservaId, getContext());
                            Toast.makeText(getContext(), "Carrinho finalizado com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Não há carrinho disponível", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
    }
}