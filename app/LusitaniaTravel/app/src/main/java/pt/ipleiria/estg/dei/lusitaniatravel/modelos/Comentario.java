package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;
import java.util.List;

public class Comentario {
    private int id;
    private String titulo;
    private String descricao;
    private String dataComentario, nomeFornecedor;
    private List<Avaliacao> avaliacao;
    public Comentario(int id, String titulo, String descricao, String dataComentario, String nomeFornecedor, List<Avaliacao> avaliacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataComentario = dataComentario;
        this.nomeFornecedor = nomeFornecedor;
        this.avaliacao = avaliacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(String dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public List<Avaliacao> getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(List<Avaliacao> avaliacao) {
        this.avaliacao = avaliacao;
    }
}
