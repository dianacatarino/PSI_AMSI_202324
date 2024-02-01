package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesFaturaFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesReservaFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;

public class ListaFaturasAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fatura> faturas;

    public ListaFaturasAdaptador(Context context, ArrayList<Fatura> faturas) {
        this.context = context;
        this.faturas = faturas;
    }

    @Override
    public int getCount() {
        return faturas.size();
    }

    @Override
    public Object getItem(int position) {
        return faturas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return faturas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_fatura, null);

        // Otimização
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView,position);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(faturas.get(position), context);

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvFaturaId, tvTotalFatura, tvTotalSI, tvIva, tvReserva, tvData;
        private Button btnDetalhes;

        public ViewHolderLista(View view, final int position) {
            tvFaturaId = view.findViewById(R.id.tvFaturaId);
            tvTotalFatura = view.findViewById(R.id.tvTotalFatura);
            tvTotalSI = view.findViewById(R.id.tvTotalSI);
            tvIva = view.findViewById(R.id.tvIva);
            tvReserva = view.findViewById(R.id.tvReserva);
            tvData = view.findViewById(R.id.tvData);
            btnDetalhes = view.findViewById(R.id.btnDetalhes);

            btnDetalhes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (faturas != null && faturas.size() > position) {
                        Fatura faturaClicada = faturas.get(position);

                        int faturaId = faturaClicada.getId();

                        Bundle bundle = new Bundle();
                        bundle.putInt("faturaId", faturaId);

                        DetalhesFaturaFragment detalhesFaturaFragment = new DetalhesFaturaFragment();

                        detalhesFaturaFragment.setFaturaId(faturaId);

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainer, detalhesFaturaFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

        public void update(Fatura fatura, Context context) {
            tvFaturaId.setText("" + fatura.getId());
            // Formatando os valores da fatura
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String totalFaturaFormatado = decimalFormat.format(fatura.getTotalF());
            String totalSIFormatado = decimalFormat.format(fatura.getTotalSI());

            DecimalFormat ivaFormat = new DecimalFormat(".00");
            String ivaFormatado = ivaFormat.format(fatura.getIva());

            tvTotalFatura.setText("" + totalFaturaFormatado + "€");
            tvTotalSI.setText("" + totalSIFormatado + "€");
            tvIva.setText("" + ivaFormatado + "%");
            tvReserva.setText("" + fatura.getReservaId());
            tvData.setText( "" + fatura.getData());
        }
    }
}
