package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarComentarioFragment#} factory method to
 * create an instance of this fragment.
 */
public class AdicionarComentarioFragment extends Fragment {

    EditText editTextTitulo, editTextComentario;
    RatingBar ratingBarAvaliacao;
    Button btnAdicionarComentario;

    int fornecedorId;

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar_comentario, container, false);

        editTextTitulo = view.findViewById(R.id.editTextTitulo);
        editTextComentario = view.findViewById(R.id.editTextComentario);
        ratingBarAvaliacao = view.findViewById(R.id.ratingBarAvaliacao);
        btnAdicionarComentario = view.findViewById(R.id.btnAdicionarComentario);

        btnAdicionarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitulo.getText().toString().trim();
                String descricao = editTextComentario.getText().toString().trim();
                int classificacao = (int) ratingBarAvaliacao.getRating();

                // Verificar se o título e a descrição estão vazios
                if (titulo.isEmpty() || descricao.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                fornecedorId = getFornecedorId();

                // Chamada à função para adicionar o comentário através da API
                SingletonGestorLusitaniaTravel.getInstance(getContext())
                        .adicionarComentarioAPI(titulo, descricao, classificacao, getContext(), fornecedorId);

                // Limpar os campos após adicionar o comentário
                editTextTitulo.setText("");
                editTextComentario.setText("");
                ratingBarAvaliacao.setRating(0);
            }
        });

        return view;
    }
}