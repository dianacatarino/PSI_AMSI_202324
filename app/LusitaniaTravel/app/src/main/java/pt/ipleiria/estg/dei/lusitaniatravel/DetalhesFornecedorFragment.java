package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedorListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Imagem;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhesFornecedorFragment#} factory method to
 * create an instance of this fragment.
 */
public class DetalhesFornecedorFragment extends Fragment implements FornecedorListener {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    TextView tvTipo, tvNomeAlojamento, tvLocalizacao, tvAcomodacoes, tvPrecoPorNoite, tvTipoQuartos, tvNumeroQuartos;
    ImageView imgFornecedor;
    private int fornecedorId;
    FragmentManager fragmentManager;

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ListaComentariosAlojamentoFragment()).commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_fornecedor, container, false);

        tvTipo = view.findViewById(R.id.tvTipo);
        tvNomeAlojamento = view.findViewById(R.id.tvNomeAlojamento);
        tvLocalizacao = view.findViewById(R.id.tvLocalizacao);
        tvAcomodacoes = view.findViewById(R.id.tvAcomodacoes);
        tvPrecoPorNoite = view.findViewById(R.id.tvPrecoPorNoite);
        tvTipoQuartos = view.findViewById(R.id.tvTipoQuartos);
        tvNumeroQuartos = view.findViewById(R.id.tvNumeroQuartos);
        imgFornecedor = view.findViewById(R.id.imgFornecedor);

        SingletonGestorLusitaniaTravel.getInstance(getContext()).setFornecedorListener(this);

        fornecedorId = getFornecedorId();

        // Make the API call
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getFornecedorAPI(fornecedorId, getContext());

        return view;
    }

    public void onRefreshDetalhes(Fornecedor fornecedor){
        if (getView() != null && fornecedor != null) {
            tvTipo.setText(fornecedor.getTipo());
            tvNomeAlojamento.setText(fornecedor.getNomeAlojamento());
            tvLocalizacao.setText(fornecedor.getLocalizacaoAlojamento());
            tvAcomodacoes.setText(fornecedor.getAcomodacoesAlojamento());

            // Formatando o preço sem casas decimais
            DecimalFormat decimalFormat = new DecimalFormat("#");
            String precoFormatado = decimalFormat.format(fornecedor.getPrecoPorNoite());
            tvPrecoPorNoite.setText(precoFormatado + "€");
            tvTipoQuartos.setText(fornecedor.getTipoQuartos());
            tvNumeroQuartos.setText(String.valueOf(fornecedor.getNumeroQuartos()));

            // Obtendo a primeira imagem do fornecedor
            Imagem primeiraImagem = fornecedor.getImagens().size() > 0 ? fornecedor.getImagens().get(0) : null;

            if (primeiraImagem != null) {
                // Construindo a URL completa para a primeira imagem
                String imageUrl = buildImageUrl(primeiraImagem);

                // Usando o Glide para carregar a imagem
                Glide.with(getContext())
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgFornecedor);
            } else {
                // Caso não haja imagem, você pode definir uma imagem de placeholder ou fazer algo adequado ao seu caso
                imgFornecedor.setImageResource(R.drawable.hotel7); // Substitua com o seu recurso de imagem de placeholder
            }
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