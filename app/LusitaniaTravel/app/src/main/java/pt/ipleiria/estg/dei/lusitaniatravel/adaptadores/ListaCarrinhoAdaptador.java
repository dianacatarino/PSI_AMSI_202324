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
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;

public class ListaCarrinhoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Carrinho> carrinhos;

    public ListaCarrinhoAdaptador(Context context, ArrayList<Carrinho> carrinhos) {
        this.context = context;
        this.carrinhos = carrinhos;
    }

    @Override
    public int getCount() {
        return carrinhos.size();
    }

    @Override
    public Object getItem(int position) {
        return carrinhos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carrinhos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_carrinho, null);

        // Otimização
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(carrinhos.get(position), context);

        return convertView;
    }

    private static class ViewHolderLista {
        private TextView tvQuantidade, tvPreco, tvSubtotal, tvReservaId, tvEstado;

        public ViewHolderLista(View view) {
            tvQuantidade = view.findViewById(R.id.tvQuantidade);
            tvPreco = view.findViewById(R.id.tvPreco);
            tvSubtotal = view.findViewById(R.id.tvSubtotal);
            tvReservaId = view.findViewById(R.id.tvReserva);
            tvEstado = view.findViewById(R.id.tvEstado);
        }

        public void update(Carrinho carrinho, Context context) {
            tvQuantidade.setText("" + carrinho.getQuantidade());

            // Formatando o preço e subtotal
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String precoFormatado = decimalFormat.format(carrinho.getPreco());
            String subtotalFormatado = decimalFormat.format(carrinho.getSubtotal());

            tvPreco.setText("" + precoFormatado + "€");
            tvSubtotal.setText("" + subtotalFormatado + "€");

            tvReservaId.setText("" + carrinho.getReservaId());
            tvEstado.setText("" + carrinho.getEstado());
        }
    }
}
