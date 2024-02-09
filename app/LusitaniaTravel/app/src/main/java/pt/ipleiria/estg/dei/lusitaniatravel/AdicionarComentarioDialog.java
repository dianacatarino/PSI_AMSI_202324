package pt.ipleiria.estg.dei.lusitaniatravel;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class AdicionarComentarioDialog extends Dialog implements View.OnClickListener {
    private EditText editTextTitulo, editTextComentario;
    private RatingBar ratingBarAvaliacao;
    private Button btnAdicionarComentario, btnCancelar;

    int fornecedorId;

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public AdicionarComentarioDialog(@NonNull Context context, int fornecedorId) {
        super(context);
        this.fornecedorId = fornecedorId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_adicionar_comentario);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextComentario = findViewById(R.id.editTextComentario);
        ratingBarAvaliacao = findViewById(R.id.ratingBarAvaliacao);
        btnAdicionarComentario = findViewById(R.id.btnAdicionarComentario);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnAdicionarComentario.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdicionarComentario) {
            adicionarComentario();
        } else if (v.getId() == R.id.btnCancelar) {
            cancelar();
        }
    }

    private void adicionarComentario() {
        String titulo = editTextTitulo.getText().toString().trim();
        String descricao = editTextComentario.getText().toString().trim();
        int classificacao = (int) ratingBarAvaliacao.getRating();

        Log.d("AdicionarComentarioDialog", "Classificação: " + classificacao);

        // Verificar se o título, a descrição e a classificação estão preenchidos
        if (titulo.isEmpty() || descricao.isEmpty() || classificacao == 0) {
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
        dismiss();
    }

    private void cancelar() {
        dismiss();
    }
}

