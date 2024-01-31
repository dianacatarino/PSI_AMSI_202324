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
        viewHolder.update(comentarios.get(position),fornecedores, context);

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvUsername, tvTitulo, tvComentario, tvAvaliacao, tvData;
        private String fornecedorNome;
        private int fornecedorId;

        public ViewHolderLista(View view) {
            tvUsername = view.findViewById(R.id.tvUsername);
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvComentario = view.findViewById(R.id.tvComentario);
            tvAvaliacao = view.findViewById(R.id.tvAvaliacao);
            tvData = view.findViewById(R.id.tvData);
        }

        public void update(Comentario comentario, ArrayList<Fornecedor> fornecedores, Context context) {
            fornecedorNome = comentario.getNomeFornecedor();
            fornecedorId = findFornecedorIdByNome(fornecedorNome, fornecedores);
            tvUsername.setText(comentario.getNomeCliente());
            tvTitulo.setText(comentario.getTitulo());
            tvComentario.setText(comentario.getDescricao());

            List<Avaliacao> avaliacoes = comentario.getAvaliacoes();
            if (!avaliacoes.isEmpty()) {
                // Assume que estamos mostrando apenas a primeira avaliação associada ao comentário
                Avaliacao primeiraAvaliacao = avaliacoes.get(0);
                int classificacao = primeiraAvaliacao.getClassificacao();
                // Define o texto da TextView para a classificação da avaliação
                tvAvaliacao.setText(String.valueOf(classificacao));
            } else {
                // Se não houver avaliações associadas ao comentário, define o texto como vazio
                tvAvaliacao.setText("");
            }

            tvData.setText(comentario.getDataComentario());

            Bundle bundle = new Bundle();
            bundle.putInt("fornecedorId", fornecedorId);

            ListaComentariosAlojamentoFragment listaComentariosAlojamentoFragment = new ListaComentariosAlojamentoFragment();
            listaComentariosAlojamentoFragment.setFornecedorId(fornecedorId);

            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, listaComentariosAlojamentoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    private int findFornecedorIdByNome(String nomeFornecedor, ArrayList<Fornecedor> fornecedores) {
        for (Fornecedor fornecedor : fornecedores) {
            if (fornecedor.getNomeAlojamento().equals(nomeFornecedor)) {
                return fornecedor.getId();
            }
        }
        return -1; // Retorna -1 se o fornecedor não for encontrado
    }
}
