package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;
import java.util.List;

public class Comentario {
    private int id;
    private String titulo;
    private String descricao;
    private String dataComentario, nomeFornecedor, nomeCliente;
    private List<Avaliacao> avaliacoes;
    public Comentario(int id, String titulo, String descricao, String dataComentario, String nomeFornecedor, String nomeCliente, List<Avaliacao> avaliacoes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataComentario = dataComentario;
        this.nomeFornecedor = nomeFornecedor;
        this.nomeCliente = nomeCliente;
        this.avaliacoes = avaliacoes;
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

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}
