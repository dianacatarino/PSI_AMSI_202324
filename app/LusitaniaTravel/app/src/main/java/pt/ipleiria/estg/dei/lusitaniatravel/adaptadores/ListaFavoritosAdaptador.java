package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesFavoritoFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesFornecedorFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FavoritoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FavoritosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Imagem;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class ListaFavoritosAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fornecedor> fornecedores;

    public ListaFavoritosAdaptador(Context context, ArrayList<Fornecedor> fornecedores) {
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
            convertView = inflater.inflate(R.layout.item_lista_favorito, null);

        // Otimização
        ListaFavoritosAdaptador.ViewHolderLista viewHolder = (ListaFavoritosAdaptador.ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ListaFavoritosAdaptador.ViewHolderLista(convertView,position);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(fornecedores.get(position), context);

        return convertView;
    }

    private class ViewHolderLista {
        private ImageView imgFornecedor;
        private TextView tvTipo, tvNomeAlojamento, tvLocalizacao, tvAcomodacoes, tvPrecoPorNoite;
        private ImageButton btnRemoverFavoritos;
        private Button btnDetalhes;

        public ViewHolderLista(View view, final int position) {
            imgFornecedor = view.findViewById(R.id.imgFornecedor);
            tvTipo = view.findViewById(R.id.tvTipo);
            tvNomeAlojamento = view.findViewById(R.id.tvNomeAlojamento);
            tvLocalizacao = view.findViewById(R.id.tvLocalizacao);
            tvAcomodacoes = view.findViewById(R.id.tvAcomodacoes);
            tvPrecoPorNoite = view.findViewById(R.id.tvPrecoPorNoite);
            btnRemoverFavoritos = view.findViewById(R.id.btnRemoverFavoritos);
            btnDetalhes = view.findViewById(R.id.btnDetalhes);

            btnRemoverFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fornecedores != null && fornecedores.size() > position) {
                        Fornecedor favoritoClicado = fornecedores.get(position);

                        int fornecedorId = favoritoClicado.getId();

                        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(context);

                        singleton.removerFavoritoAPI(fornecedorId, context);

                        Toast.makeText(context, "Alojamento removido dos favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnDetalhes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (fornecedores != null && fornecedores.size() > position) {
                        Fornecedor fornecedorClicado = fornecedores.get(position);

                        int fornecedorId = fornecedorClicado.getId();

                        DetalhesFavoritoFragment detalhesFavoritoFragment = new DetalhesFavoritoFragment();
                        detalhesFavoritoFragment.setFornecedorId(fornecedorId);

                        // Acesso à Activity pai para atualizar o título da Action Bar
                        FragmentActivity activity = (FragmentActivity) context;
                        if (activity != null) {
                            activity.setTitle("Detalhes Favorito");
                        }

                        // Inicia a transação para substituir o Fragment
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainer, detalhesFavoritoFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

        public void update(Fornecedor fornecedor, Context context) {
            tvTipo.setText(fornecedor.getTipo());
            tvNomeAlojamento.setText(fornecedor.getNomeAlojamento());
            tvLocalizacao.setText(fornecedor.getLocalizacaoAlojamento());
            tvAcomodacoes.setText(fornecedor.getAcomodacoesAlojamento());

            // Formatando o preço sem casas decimais
            DecimalFormat decimalFormat = new DecimalFormat("#");
            String precoFormatado = decimalFormat.format(fornecedor.getPrecoPorNoite());
            tvPrecoPorNoite.setText(precoFormatado + "€");

            // Obtendo a primeira imagem do fornecedor
            Imagem primeiraImagem = fornecedor.getImagens().size() > 0 ? fornecedor.getImagens().get(0) : null;

            if (primeiraImagem != null) {
                // Construindo a URL completa para a primeira imagem
                String imageUrl = buildImageUrl(primeiraImagem);

                // Usando o Glide para carregar a imagem
                Glide.with(context)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgFornecedor);
            } else {
                // Caso não haja imagem, você pode definir uma imagem de placeholder ou fazer algo adequado ao seu caso
                imgFornecedor.setImageResource(R.drawable.logo_horizontal); // Substitua com o seu recurso de imagem de placeholder
            }
        }

        private String buildImageUrl(Imagem imagem) {
            // Obtendo o caminho relativo da imagem
            String relativePath = imagem.getFilename();

            // Construindo a URL completa usando o domínio base
            String baseUrl = "http://172.22.21.204";
            return baseUrl + relativePath;
        }
    }
}
