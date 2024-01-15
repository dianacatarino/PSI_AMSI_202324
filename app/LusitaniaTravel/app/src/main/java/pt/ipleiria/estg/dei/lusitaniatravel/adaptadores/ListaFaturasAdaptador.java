package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;

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
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(faturas.get(position), context);

        return convertView;
    }

    private static class ViewHolderLista {
        private TextView tvTotalFatura, tvTotalSI, tvIva, tvReserva, tvData;

        public ViewHolderLista(View view) {
            tvTotalFatura = view.findViewById(R.id.tvTotalFatura);
            tvTotalSI = view.findViewById(R.id.tvTotalSI);
            tvIva = view.findViewById(R.id.tvIva);
            tvReserva = view.findViewById(R.id.tvReserva);
            tvData = view.findViewById(R.id.tvData);
        }

        public void update(Fatura fatura, Context context) {
            // Formatando os valores da fatura
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String totalFaturaFormatado = decimalFormat.format(fatura.getTotalF());
            String totalSIFormatado = decimalFormat.format(fatura.getTotalSI());
            String ivaFormatado = decimalFormat.format(fatura.getIva());

            tvTotalFatura.setText("" + totalFaturaFormatado + "€");
            tvTotalSI.setText("" + totalSIFormatado + "€");
            tvIva.setText("" + ivaFormatado + "%");
            tvReserva.setText("" + fatura.getReservaId());
            tvData.setText( "" + fatura.getData());
        }
    }
}
