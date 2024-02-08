package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesFornecedorFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesReservaFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
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
            viewHolder = new ViewHolderLista(convertView,position);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(reservas.get(position), context);

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvReservaId, tvCheckin, tvCheckout, tvValor, tvFornecedor, tvEstado;
        private Button btnDetalhes;

        public ViewHolderLista(View view, final int position) {
            tvReservaId = view.findViewById(R.id.tvReservaId);
            tvCheckin = view.findViewById(R.id.tvCheckin);
            tvCheckout = view.findViewById(R.id.tvCheckout);
            tvValor = view.findViewById(R.id.tvValor);
            tvFornecedor = view.findViewById(R.id.tvFornecedor);
            tvEstado = view.findViewById(R.id.tvEstado);
            btnDetalhes = view.findViewById(R.id.btnDetalhes);

            btnDetalhes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (reservas != null && reservas.size() > position) {
                        Reserva reservaClicada = reservas.get(position);

                        int reservaId = reservaClicada.getId();

                        DetalhesReservaFragment detalhesReservaFragment = new DetalhesReservaFragment();
                        detalhesReservaFragment.setReservaId(reservaId); // Define os argumentos do fragmento

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainer, detalhesReservaFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

        public void update(Reserva reserva, Context context) {
            tvReservaId.setText("" + reserva.getId());
            tvCheckin.setText("" + reserva.getCheckin());
            tvCheckout.setText("" + reserva.getCheckout());

            // Formatando o valor da reserva
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String valorFormatado = decimalFormat.format(reserva.getValor());
            tvValor.setText("" + valorFormatado  + "€");
            tvFornecedor.setText("" + reserva.getNomeFornecedor());
            tvEstado.setText("" + reserva.getEstado());
        }
    }
}
