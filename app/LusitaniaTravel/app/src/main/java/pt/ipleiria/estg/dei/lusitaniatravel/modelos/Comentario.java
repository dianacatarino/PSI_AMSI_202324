package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Comentario {
    private int id;
    private String titulo;
    private String descricao;
    private Date dataComentario;
    private String nomeFornecedor;

    public Comentario(int id, String titulo, String descricao, Date dataComentario, String nomeFornecedor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataComentario = dataComentario;
        this.nomeFornecedor = nomeFornecedor;
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

    public Date getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(Date dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}
