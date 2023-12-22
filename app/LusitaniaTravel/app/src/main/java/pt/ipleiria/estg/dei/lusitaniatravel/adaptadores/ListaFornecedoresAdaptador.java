package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;

public class ListaFornecedoresAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fornecedor> fornecedores;

    public ListaFornecedoresAdaptador(Context context, ArrayList<Fornecedor> fornecedores) {
        this.context = context;
        this.fornecedores = fornecedores;
    }

    @Override
    public int getCount() {
        return fornecedores.size();
    }

    @Override
    public Object getItem(int position) {
        return fornecedores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return fornecedores.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_fornecedor, null);

        // Otimização
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(fornecedores.get(position));

        return convertView;
    }

    private static class ViewHolderLista {
        private TextView tvResponsavel, tvTipo, tvNomeAlojamento, tvLocalizacao, tvAcomodacoes;
        private TextView tvTipoQuartos, tvNumeroQuartos, tvPrecoPorNoite;

        public ViewHolderLista(View view) {
            tvResponsavel = view.findViewById(R.id.tvResponsavel);
            tvTipo = view.findViewById(R.id.tvTipo);
            tvNomeAlojamento = view.findViewById(R.id.tvNomeAlojamento);
            tvLocalizacao = view.findViewById(R.id.tvLocalizacao);
            tvAcomodacoes = view.findViewById(R.id.tvAcomodacoes);
            tvTipoQuartos = view.findViewById(R.id.tvTipoQuartos);
            tvNumeroQuartos = view.findViewById(R.id.tvNumeroQuartos);
            tvPrecoPorNoite = view.findViewById(R.id.tvPrecoPorNoite);
        }

        public void update(Fornecedor fornecedor) {
            tvResponsavel.setText("Responsável: " + fornecedor.getResponsavel());
            tvTipo.setText("Tipo: " + fornecedor.getTipo());
            tvNomeAlojamento.setText("Nome Alojamento: " + fornecedor.getNomeAlojamento());
            tvLocalizacao.setText("Localização Alojamento: " + fornecedor.getLocalizacaoAlojamento());
            tvAcomodacoes.setText("Acomodações Alojamento: " + fornecedor.getAcomodacoesAlojamento());
            // Update the new fields
            tvTipoQuartos.setText("Tipo de Quartos: " + fornecedor.getTipoQuartos());
            tvNumeroQuartos.setText("Número de Quartos: " + String.valueOf(fornecedor.getNumeroQuartos()));
            tvPrecoPorNoite.setText("Preço por Noite: " + String.valueOf(fornecedor.getPrecoPorNoite()));
        }
    }
}

