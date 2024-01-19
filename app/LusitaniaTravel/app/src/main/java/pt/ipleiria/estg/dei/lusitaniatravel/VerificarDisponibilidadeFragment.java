import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;

import pt.ipleiria.estg.dei.lusitaniatravel.R;

public class VerificarDisponibilidadeFragment extends Fragment {

    private DatePicker dpCheckin, dpCheckout;
    private EditText etNumeroQuartos, etNumeroClientes;
    private Button btnVerificarDisponibilidade;

    public VerificarDisponibilidadeFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verificar_disponibilidade, container, false);

        dpCheckin = view.findViewById(R.id.dpCheckin);
        dpCheckout = view.findViewById(R.id.dpCheckout);
        etNumeroQuartos = view.findViewById(R.id.etNumeroQuartos);
        etNumeroClientes = view.findViewById(R.id.etNumeroClientes);
        btnVerificarDisponibilidade = view.findViewById(R.id.btnVerificarDisponibilidade);

        btnVerificarDisponibilidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDisponibilidade();
            }
        });

        return view;
    }

    private void verificarDisponibilidade() {

        int diaCheckin = dpCheckin.getDayOfMonth();
        int mesCheckin = dpCheckin.getMonth();
        int anoCheckin = dpCheckin.getYear();

        int diaCheckout = dpCheckout.getDayOfMonth();
        int mesCheckout = dpCheckout.getMonth();
        int anoCheckout = dpCheckout.getYear();

        Date checkin = new Date(anoCheckin, mesCheckin, diaCheckin);
        Date checkout = new Date(anoCheckout, mesCheckout, diaCheckout);

        int numeroQuartos = Integer.parseInt(etNumeroQuartos.getText().toString());
        int numeroClientes = Integer.parseInt(etNumeroClientes.getText().toString());

        // Chama o método para verificar a disponibilidade
        if (verificarDisponibilidadeNoFragmento(checkin, checkout, numeroQuartos, numeroClientes)) {
            Toast.makeText(getActivity(), "Quartos disponíveis para as datas que selecionou.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Quartos não disponíveis para as datas selecionadas.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para verificar a disponibilidade (você precisará implementar a lógica real)
    private boolean verificarDisponibilidadeNoFragmento(Date checkin, Date checkout, int numeroQuartos, int numeroClientes) {
        // Implemente a lógica real para verificar a disponibilidade aqui
        // Retorne true se os quartos estiverem disponíveis, false caso contrário
        return true;  // Por enquanto, sempre retorna true para fins de exemplo
    }
}
