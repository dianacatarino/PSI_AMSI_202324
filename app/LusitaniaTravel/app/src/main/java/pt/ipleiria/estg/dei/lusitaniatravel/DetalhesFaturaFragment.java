package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FaturaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.LinhaReserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhesFaturaFragment#} factory method to
 * create an instance of this fragment.
 */
public class DetalhesFaturaFragment extends Fragment implements FaturaListener {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    TextView tvFaturaId, tvTotalFatura, tvTotalSI, tvIva, tvData, tvReserva, tvQuantidade, tvPrecoUnitario, tvSubtotal;
    private int faturaId;
    public void setFaturaId(int faturaId) {
        this.faturaId = faturaId;
    }

    public int getFaturaId() {
        return faturaId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_fatura, container, false);

        tvFaturaId = view.findViewById(R.id.tvFaturaId);
        tvTotalFatura = view.findViewById(R.id.tvTotalFatura);
        tvTotalSI = view.findViewById(R.id.tvTotalSI);
        tvIva = view.findViewById(R.id.tvIva);
        tvData = view.findViewById(R.id.tvData);
        tvReserva = view.findViewById(R.id.tvReserva);
        tvQuantidade = view.findViewById(R.id.tvQuantidade);
        tvPrecoUnitario = view.findViewById(R.id.tvPrecoUnitario);
        tvSubtotal = view.findViewById(R.id.tvSubtotal);

        SingletonGestorLusitaniaTravel.getInstance(getContext()).setFaturaListener(this);

        faturaId = getFaturaId();

        // Make the API call
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getFaturaAPI(faturaId, getContext());

        return view;
    }

    public void onRefreshDetalhes(Fatura fatura){
        if (getView() != null && fatura != null) {
            tvFaturaId.setText(String.valueOf(fatura.getId()));

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String totalFaturaFormatado = decimalFormat.format(fatura.getTotalF());
            tvTotalFatura.setText("" + totalFaturaFormatado  + "€");

            String totalSIFormatado = decimalFormat.format(fatura.getTotalSI());
            tvTotalSI.setText("" + totalSIFormatado  + "€");

            tvIva.setText(String.valueOf(fatura.getIva()));
            tvData.setText(fatura.getData());
            tvReserva.setText(String.valueOf(fatura.getReservaId()));
        }
    }
}