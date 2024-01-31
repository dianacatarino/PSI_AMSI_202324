package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ComentarioListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Avaliacao;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Comentario;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.LinhaReserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalhesComentarioFragment} factory method to
 * create an instance of this fragment.
 */
public class DetalhesComentarioFragment extends Fragment implements ComentarioListener {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    TextView tvComentarioId, tvFornecedor, tvTitulo, tvDescricao, tvData, tvAvaliacao, tvDataAvaliacao;
    private int comentarioId;

    public void setComentarioId(int comentarioId) {
        this.comentarioId = comentarioId;
    }

    public int getComentarioId() {
        return comentarioId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhes_comentario, container, false);

        tvComentarioId = view.findViewById(R.id.tvComentarioId);
        tvFornecedor = view.findViewById(R.id.tvFornecedor);
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvDescricao = view.findViewById(R.id.tvDescricao);
        tvData = view.findViewById(R.id.tvData);
        tvAvaliacao = view.findViewById(R.id.tvAvaliacao);
        tvDataAvaliacao = view.findViewById(R.id.tvDataAvaliacao);

        SingletonGestorLusitaniaTravel.getInstance(getContext()).setComentarioListener(this);

        comentarioId = getComentarioId();

        // Make the API call
        SingletonGestorLusitaniaTravel.getInstance(getContext()).getComentarioAPI(comentarioId, getContext());

        return view;
    }

    public void onRefreshDetalhes(Comentario comentario){
        if (getView() != null && comentario != null) {
            tvComentarioId.setText(String.valueOf(comentario.getId()));
            tvFornecedor.setText(comentario.getNomeFornecedor());
            tvTitulo.setText(comentario.getTitulo());
            tvDescricao.setText(comentario.getDescricao());
            tvData.setText(comentario.getDataComentario());

            // Criar strings para armazenar as classificações e datas das avaliações
            StringBuilder classificacoes = new StringBuilder();
            StringBuilder datasAvaliacoes = new StringBuilder();

            // Iterar sobre a lista de avaliações
            List<Avaliacao> avaliacoes = comentario.getAvaliacoes();
            for (Avaliacao avaliacao : avaliacoes) {
                // Adicionar a classificação e a data da avaliação às strings
                classificacoes.append(avaliacao.getClassificacao()).append("");
                datasAvaliacoes.append(avaliacao.getDataAvaliacao()).append("");
            }

            // Exibir as classificações e datas das avaliações nos TextViews correspondentes
            tvAvaliacao.setText(classificacoes.toString());
            tvDataAvaliacao.setText(datasAvaliacoes.toString());
        }
    }
}