package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Avaliacao {
    private int id;
    private int classificacao;
    private Date dataAvaliacao;
    private int clienteId, fornecedorId;


    public Avaliacao(int id, int classificacao, Date dataAvaliacao, int clienteId, int fornecedorId) {
        this.id = id;
        this.classificacao = classificacao;
        this.dataAvaliacao = dataAvaliacao;
        this.clienteId = clienteId;
        this.fornecedorId = fornecedorId;
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

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
}
