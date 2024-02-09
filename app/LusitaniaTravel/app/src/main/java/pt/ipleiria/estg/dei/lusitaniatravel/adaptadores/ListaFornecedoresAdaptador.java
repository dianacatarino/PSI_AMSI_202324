package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.graphics.Color;
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
import java.util.List;

import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesFornecedorFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Imagem;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class ListaFornecedoresAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fornecedor> fornecedores;
    private Carrinho novoCarrinho;

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
        viewHolder.update(fornecedores.get(position), position);

        return convertView;
    }

    private class ViewHolderLista {
        private ImageView imgFornecedor;
        private TextView tvTipo, tvNomeAlojamento, tvLocalizacao, tvAcomodacoes, tvPrecoPorNoite;
        private ImageButton btnAdicionarCarrinho, btnAdicionarFavorito;
        private Button btnDetalhes;

        public ViewHolderLista(View view) {
            imgFornecedor = view.findViewById(R.id.imgFornecedor);
            tvTipo = view.findViewById(R.id.tvTipo);
            tvNomeAlojamento = view.findViewById(R.id.tvNomeAlojamento);
            tvLocalizacao = view.findViewById(R.id.tvLocalizacao);
            tvAcomodacoes = view.findViewById(R.id.tvAcomodacoes);
            tvPrecoPorNoite = view.findViewById(R.id.tvPrecoPorNoite);
            btnAdicionarCarrinho = view.findViewById(R.id.btnAdicionarCarrinho);
            btnAdicionarFavorito = view.findViewById(R.id.btnAdicionarFavoritos);
            btnDetalhes = view.findViewById(R.id.btnDetalhes);

            btnAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) btnAdicionarCarrinho.getTag();
                    if (fornecedores != null && fornecedores.size() > position) {
                        Fornecedor fornecedorClicado = fornecedores.get(position);

                        int fornecedorId = fornecedorClicado.getId();

                        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(context);

                        singleton.adicionarCarrinhoAPI(fornecedorId, context);

                        Toast.makeText(context, "Item adicionado ao carrinho", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnAdicionarFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) btnAdicionarFavorito.getTag();
                    if (fornecedores != null && fornecedores.size() > position) {
                        Fornecedor fornecedorClicado = fornecedores.get(position);

                        // Obtenha uma instância válida de SingletonGestorLusitaniaTravel
                        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(context);

                        // Verifique se o fornecedor já é um favorito
                        if (fornecedorClicado.isFavorito()) {
                            // Se for um favorito, remova-o da lista de favoritos
                            singleton.removerFavoritoAPI(fornecedorClicado.getId(), context);
                            fornecedorClicado.setFavorito(false);
                            Toast.makeText(context, "Alojamento removido dos favoritos", Toast.LENGTH_SHORT).show();
                            btnAdicionarFavorito.clearColorFilter();
                        } else {
                            // Se não for um favorito, adicione-o à lista de favoritos
                            singleton.adicionarFavoritoAPI(fornecedorClicado.getId(), context);
                            fornecedorClicado.setFavorito(true);
                            Toast.makeText(context, "Alojamento adicionado aos favoritos", Toast.LENGTH_SHORT).show();
                            btnAdicionarFavorito.setColorFilter(Color.parseColor("#E91E63"));
                        }

                        // Notifique o adaptador sobre a mudança no estado do fornecedor
                        notifyDataSetChanged();
                    }
                }
            });
            btnDetalhes.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = (int) btnDetalhes.getTag();
                    if (fornecedores != null && fornecedores.size() > position) {
                        Fornecedor fornecedorClicado = fornecedores.get(position);

                        int fornecedorId = fornecedorClicado.getId();

                        DetalhesFornecedorFragment detalhesFornecedorFragment = new DetalhesFornecedorFragment();
                        detalhesFornecedorFragment.setFornecedorId(fornecedorId);

                        // Acesso à Activity pai para atualizar o título da Action Bar
                        FragmentActivity activity = (FragmentActivity) context;
                        if (activity != null) {
                            activity.setTitle("Detalhes Alojamento");
                        }

                        // Inicia a transação para substituir o Fragment
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainer, detalhesFornecedorFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

        public void update(Fornecedor fornecedor, int position) {
            tvTipo.setText(fornecedor.getTipo());
            tvNomeAlojamento.setText(fornecedor.getNomeAlojamento());
            tvLocalizacao.setText(fornecedor.getLocalizacaoAlojamento());
            tvAcomodacoes.setText(fornecedor.getAcomodacoesAlojamento());

            // Formatando o preço sem casas decimais
            DecimalFormat decimalFormat = new DecimalFormat("#");
            String precoFormatado = decimalFormat.format(fornecedor.getPrecoPorNoite());
            tvPrecoPorNoite.setText(precoFormatado + "€");

            // Verificar se o fornecedor está na lista de favoritos
            boolean isFavorito = fornecedor.isFavorito();

            // Mudar a cor do botão dos favoritos conforme necessário
            if (isFavorito) {
                btnAdicionarFavorito.setColorFilter(Color.parseColor("#E91E63")); // Cor rosa
            } else {
                btnAdicionarFavorito.clearColorFilter(); // Remover a cor
            }

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

            // Definir as tags dos botões com a posição do fornecedor
            btnAdicionarCarrinho.setTag(position);
            btnAdicionarFavorito.setTag(position);
            btnDetalhes.setTag(position);
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
