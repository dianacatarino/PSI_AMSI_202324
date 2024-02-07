package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.lusitaniatravel.ListaComentariosAlojamentoFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Avaliacao;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Comentario;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;

public class ListaComentariosAlojamentoAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Comentario> comentarios;
    private ArrayList<Fornecedor> fornecedores;


    public ListaComentariosAlojamentoAdaptador(Context context, ArrayList<Comentario> comentarios) {
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
            convertView = inflater.inflate(R.layout.item_lista_comentarios_alojamento, null);

        ListaComentariosAlojamentoAdaptador.ViewHolderLista viewHolder = (ListaComentariosAlojamentoAdaptador.ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ListaComentariosAlojamentoAdaptador.ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(comentarios.get(position), context);

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvUsername, tvTitulo, tvComentario, tvData;
        private RatingBar ratingBarAvaliacao;

        public ViewHolderLista(View view) {
            tvUsername = view.findViewById(R.id.tvUsername);
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvComentario = view.findViewById(R.id.tvComentario);
            ratingBarAvaliacao = view.findViewById(R.id.ratingBarAvaliacao);
            tvData = view.findViewById(R.id.tvData);
        }

        public void update(Comentario comentario, Context context) {
            tvUsername.setText(comentario.getNomeCliente());
            tvTitulo.setText(comentario.getTitulo());
            tvComentario.setText(comentario.getDescricao());

            List<Avaliacao> avaliacoes = comentario.getAvaliacoes();
            if (!avaliacoes.isEmpty()) {
                // Assume que estamos mostrando apenas a primeira avaliação associada ao comentário
                Avaliacao primeiraAvaliacao = avaliacoes.get(0);
                int classificacao = primeiraAvaliacao.getClassificacao();

                // Define a classificação na RatingBar
                ratingBarAvaliacao.setRating(classificacao);
            } else {
                // Se não houver avaliações associadas ao comentário, define 0 estrelas na RatingBar
                ratingBarAvaliacao.setRating(0);
            }

            tvData.setText(comentario.getDataComentario());
        }
    }
}
