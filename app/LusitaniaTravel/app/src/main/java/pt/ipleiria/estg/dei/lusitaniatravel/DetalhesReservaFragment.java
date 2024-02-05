package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.ipleiria.estg.dei.lusitaniatravel.adaptadores.ListaReservasAdaptador;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Imagem;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.LinhaReserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.ReservaJsonParser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhesReservaFragment#} factory method to
 * create an instance of this fragment.
 */
public class DetalhesReservaFragment extends Fragment implements ReservaListener {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    TextView tvReservaId, tvTipoReserva, tvCheckin, tvCheckout, tvNumeroQuartos, tvNumeroClientes, tvValor, tvFornecedor, tvTipoQuartos,
            tvNumeroNoites, tvNumeroCamas;
    private int reservaId;
    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getReservaId() {
        return reservaId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_reserva, container, false);

        tvReservaId = view.findViewById(R.id.tvReservaId);
        tvTipoReserva = view.findViewById(R.id.tvTipoReserva);
        tvCheckin = view.findViewById(R.id.tvCheckin);
        tvCheckout = view.findViewById(R.id.tvCheckout);
        tvNumeroQuartos = view.findViewById(R.id.tvNumeroQuartos);
        tvNumeroClientes = view.findViewById(R.id.tvNumeroClientes);
        tvValor = view.findViewById(R.id.tvValor);
        tvFornecedor = view.findViewById(R.id.tvFornecedor);
        tvTipoQuartos = view.findViewById(R.id.tvTipoQuartos);
        tvNumeroNoites = view.findViewById(R.id.tvNumeroNoites);
        tvNumeroCamas = view.findViewById(R.id.tvNumeroCamas);

        SingletonGestorLusitaniaTravel.getInstance(getContext()).setReservaListener(this);

        reservaId = getReservaId();

        // Verificar se há conexão com a internet
        if (ReservaJsonParser.isConnectionInternet(getContext())) {
            // Se há conexão com a internet, buscar as reservas da API
            SingletonGestorLusitaniaTravel.getInstance(getContext()).getReservaAPI(reservaId, getContext());
        } else {
            // Se não há conexão com a internet, carregar as reservas da BD local
            Reserva reservaBD = SingletonGestorLusitaniaTravel.getInstance(getContext()).getReserva(reservaId);
            tvReservaId.setText(String.valueOf(reservaBD.getId()));
            tvTipoReserva.setText(reservaBD.getTipo());
            tvCheckin.setText(reservaBD.getCheckin());
            tvCheckout.setText(reservaBD.getCheckout());
            tvNumeroQuartos.setText(String.valueOf(reservaBD.getNumeroQuartos()));
            tvNumeroClientes.setText(String.valueOf(reservaBD.getNumeroClientes()));
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String valorFormatado = decimalFormat.format(reservaBD.getValor());
            tvValor.setText("" + valorFormatado  + "€");
            tvFornecedor.setText(reservaBD.getNomeFornecedor());
            tvTipoQuartos.setText(reservaBD.getTipoQuarto());
            tvNumeroNoites.setText(String.valueOf(reservaBD.getNumeroNoites()));
            tvNumeroCamas.setText(String.valueOf(reservaBD.getNumeroCamas()));
        }


        return view;
    }

    public void onRefreshDetalhes(Reserva reserva){
        if (getView() != null && reserva != null) {
            tvReservaId.setText(String.valueOf(reserva.getId()));
            tvTipoReserva.setText(reserva.getTipo());
            tvCheckin.setText(reserva.getCheckin());
            tvCheckout.setText(reserva.getCheckout());
            tvNumeroQuartos.setText(String.valueOf(reserva.getNumeroQuartos()));
            tvNumeroClientes.setText(String.valueOf(reserva.getNumeroClientes()));
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String valorFormatado = decimalFormat.format(reserva.getValor());
            tvValor.setText("" + valorFormatado  + "€");
            tvFornecedor.setText(reserva.getNomeFornecedor());
            tvTipoQuartos.setText(reserva.getTipoQuarto());
            tvNumeroNoites.setText(String.valueOf(reserva.getNumeroNoites()));
            tvNumeroCamas.setText(String.valueOf(reserva.getNumeroCamas()));
        }
    }
}