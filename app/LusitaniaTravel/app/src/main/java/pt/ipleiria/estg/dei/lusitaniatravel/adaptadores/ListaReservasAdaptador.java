package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;

public class ListaReservasAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Reserva> reservas;

    public ListaReservasAdaptador(Context context, ArrayList<Reserva> reservas) {
        this.context = context;
        this.reservas = reservas;
    }

    @Override
    public int getCount() {
        return reservas.size();
    }

    @Override
    public Object getItem(int position) {
        return reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reservas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_reserva, null);

        // Otimização
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(reservas.get(position), context);

        return convertView;
    }

    private static class ViewHolderLista {
        private TextView tvTipoReserva, tvCheckin, tvCheckout, tvNumeroQuartos, tvNumeroClientes, tvValor, tvFornecedor;

        public ViewHolderLista(View view) {
            tvTipoReserva = view.findViewById(R.id.tvTipoReserva);
            tvCheckin = view.findViewById(R.id.tvCheckin);
            tvCheckout = view.findViewById(R.id.tvCheckout);
            tvNumeroQuartos = view.findViewById(R.id.tvNumeroQuartos);
            tvNumeroClientes = view.findViewById(R.id.tvNumeroClientes);
            tvValor = view.findViewById(R.id.tvValor);
            tvFornecedor = view.findViewById(R.id.tvFornecedor);
        }

        public void update(Reserva reserva, Context context) {
            tvTipoReserva.setText("" + reserva.getTipo());
            tvCheckin.setText("" + reserva.getCheckin());
            tvCheckout.setText("" + reserva.getCheckout());
            tvNumeroQuartos.setText("" + reserva.getNumeroQuartos());
            tvNumeroClientes.setText("" + reserva.getNumeroClientes());

            // Formatando o valor da reserva
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String valorFormatado = decimalFormat.format(reserva.getValor());
            tvValor.setText("" + valorFormatado  + "€");
            tvFornecedor.setText("" + reserva.getNomeFornecedor());
        }
    }
}
