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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Date;

import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.VerificarListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class VerificarDisponibilidadeFragment extends Fragment implements VerificarListener {
    private Spinner spinnerNumeroClientes, spinnerNumeroQuartos, spinnerTipoQuartos, spinnerNumeroCamas;
    private Button btnVerificarDisponibilidade;
    private DatePicker datePickerCheckIn, datePickerCheckOut;
    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    private int reservaId;

    public VerificarDisponibilidadeFragment() {
        // Required empty public constructor
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getReservaId() {
        return reservaId;
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
                reservaId = getReservaId();

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

                // Verificar se há campos vazios
                if (checkin.isEmpty() || checkout.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, preencha todas as datas.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obter o número de clientes, quartos e camas
                int numeroClientes, numeroQuartos, numeroCamas;
                try {
                    numeroClientes = Integer.parseInt(spinnerNumeroClientes.getSelectedItem().toString());
                    numeroQuartos = Integer.parseInt(spinnerNumeroQuartos.getSelectedItem().toString());
                    numeroCamas = Integer.parseInt(spinnerNumeroCamas.getSelectedItem().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar se o número de quartos e o número de camas são maiores que zero
                if (numeroQuartos <= 0 || numeroCamas <= 0) {
                    Toast.makeText(getContext(), "O número de quartos e camas deve ser maior que zero.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar se o número de quartos é maior que o número de clientes
                if (numeroQuartos > numeroClientes) {
                    Toast.makeText(getContext(), "O número de quartos não pode ser maior que o número de clientes.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar se a data de checkout é menor que a data de checkin
                if (checkOutYear < checkInYear || (checkOutYear == checkInYear && checkOutMonth < checkInMonth)
                        || (checkOutYear == checkInYear && checkOutMonth == checkInMonth && checkOutDay <= checkInDay)) {
                    Toast.makeText(getContext(), "A data de checkout deve ser posterior à data de checkin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar se o tipo de quarto está selecionado
                String tipoQuarto = spinnerTipoQuartos.getSelectedItem().toString();
                if (tipoQuarto.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, selecione o tipo de quarto.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the singleton to verify the reservation
                SingletonGestorLusitaniaTravel.getInstance(getContext())
                        .verificarReservaAPI(checkin, checkout, numeroClientes, numeroQuartos, tipoQuarto, numeroCamas, getContext(), reservaId);

                Toast.makeText(getContext(), "Reserva verificada com sucesso", Toast.LENGTH_SHORT).show();

                CarrinhoFragment carrinhoFragment = new CarrinhoFragment();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, carrinhoFragment);
                transaction.addToBackStack(null);  // Adiciona a transação à pilha de volta
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onRefreshDetalhes(Reserva reserva) {

    }
}
