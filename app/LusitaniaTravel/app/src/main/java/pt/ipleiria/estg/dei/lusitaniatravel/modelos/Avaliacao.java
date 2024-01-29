package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Avaliacao {
    private int id;
    private int classificacao;
    private String dataAvaliacao, nomeFornecedor;


    public Avaliacao(int id, int classificacao, String dataAvaliacao, String nomeFornecedor) {
        this.id = id;
        this.classificacao = classificacao;
        this.dataAvaliacao = dataAvaliacao;
        this.nomeFornecedor = nomeFornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public String getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}
