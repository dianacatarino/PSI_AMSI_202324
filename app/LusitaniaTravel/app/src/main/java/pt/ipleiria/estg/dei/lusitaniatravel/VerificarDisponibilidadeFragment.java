package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Date;

import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class VerificarDisponibilidadeFragment extends Fragment implements ReservaListener {
    private Spinner spinnerNumeroClientes, spinnerNumeroQuartos, spinnerTipoQuartos, spinnerNumeroCamas;
    private Button btnVerificarDisponibilidade;
    private DatePicker datePickerCheckIn, datePickerCheckOut;
    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    private int carrinhoId;

    public VerificarDisponibilidadeFragment() {
        // Required empty public constructor
    }

    public void setCarrinhoId(int carrinhoId) {
        this.carrinhoId = carrinhoId;
    }

    public int getCarrinhoId() {
        return carrinhoId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verificar_disponibilidade, container, false);

        datePickerCheckIn = view.findViewById(R.id.datePickerCheckIn);
        datePickerCheckOut = view.findViewById(R.id.datePickerCheckOut);
        spinnerNumeroClientes = view.findViewById(R.id.spinnerNumeroClientes);
        spinnerNumeroQuartos = view.findViewById(R.id.spinnerNumeroQuartos);
        spinnerNumeroCamas = view.findViewById(R.id.spinnerNumeroCamas);
        spinnerTipoQuartos = view.findViewById(R.id.spinnerTipoQuartos);
        btnVerificarDisponibilidade = view.findViewById(R.id.btnVerificarDisponibilidade);

        // Set an onClickListener for the button
        btnVerificarDisponibilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrinhoId = getCarrinhoId();
                Log.d("CarrinhoId", "CarrinhoId: " + carrinhoId);

                // Obter as datas selecionadas do DatePicker
                int checkInDay = datePickerCheckIn.getDayOfMonth();
                int checkInMonth = datePickerCheckIn.getMonth() + 1;
                int checkInYear = datePickerCheckIn.getYear();

                int checkOutDay = datePickerCheckOut.getDayOfMonth();
                int checkOutMonth = datePickerCheckOut.getMonth() + 1;
                int checkOutYear = datePickerCheckOut.getYear();

                // Criar as strings de data no formato desejado
                String checkin = checkInYear + "/" + checkInMonth + "/" + checkInDay;
                String checkout = checkOutYear + "/" + checkOutMonth + "/" + checkOutDay;

                int numeroClientes = Integer.parseInt(spinnerNumeroClientes.getSelectedItem().toString());
                int numeroQuartos = Integer.parseInt(spinnerNumeroQuartos.getSelectedItem().toString());
                String tipoQuarto = spinnerTipoQuartos.getSelectedItem().toString();
                int numeroCamas = Integer.parseInt(spinnerNumeroCamas.getSelectedItem().toString());

                // Call the singleton to verify the reservation
                SingletonGestorLusitaniaTravel.getInstance(getContext())
                        .verificarReservaAPI(checkin, checkout, numeroClientes, numeroQuartos, tipoQuarto, numeroCamas, getContext(), carrinhoId);
            }
        });

        return view;
    }

    @Override
    public void onRefreshDetalhes(int op) {
        // Handle the result of the reservation verification
        if (op == VerificarDisponibilidadeFragment.ADD) {
            Toast.makeText(getContext(), "Reserva verificada com sucesso", Toast.LENGTH_SHORT).show();
            // Redirecionar para o CarrinhoFragment
            CarrinhoFragment carrinhoFragment = new CarrinhoFragment();

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, carrinhoFragment);
            transaction.addToBackStack(null);  // Adiciona a transação à pilha de volta
            transaction.commit();
        } else {
            Toast.makeText(getContext(), "Reserva não foi verificada com sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}
