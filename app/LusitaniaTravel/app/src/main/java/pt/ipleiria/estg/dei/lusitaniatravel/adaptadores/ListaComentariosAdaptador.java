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

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesComentarioFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Avaliacao;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Comentario;

public class ListaComentariosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Comentario> comentarios;

    public ListaComentariosAdaptador(Context context, ArrayList<Comentario> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comentarios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_comentario, null);

        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(comentarios.get(position), context);

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvFornecedor, tvComentario, tvAvaliacao;
        private Button btnDetalhes;

        public ViewHolderLista(View view) {
            tvFornecedor = view.findViewById(R.id.tvFornecedor);
            tvComentario = view.findViewById(R.id.tvComentario);
            tvAvaliacao = view.findViewById(R.id.tvAvaliacao);
            btnDetalhes = view.findViewById(R.id.btnDetalhes);

            btnDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Comentario comentarioClicado = comentarios.get(position);

                    int comentarioId = comentarioClicado.getId();

                    Bundle bundle = new Bundle();
                    bundle.putInt("comentarioId", comentarioId);

                    DetalhesComentarioFragment detalhesComentarioFragment = new DetalhesComentarioFragment();
                    detalhesComentarioFragment.setArguments(bundle);

                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragmentContainer, detalhesComentarioFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        public void update(Comentario comentario, Context context) {
            tvFornecedor.setText(comentario.getNomeFornecedor());
            tvComentario.setText(comentario.getDescricao());
            tvAvaliacao.setText(String.valueOf(comentario.getAvaliacao()));
        }
    }
}
