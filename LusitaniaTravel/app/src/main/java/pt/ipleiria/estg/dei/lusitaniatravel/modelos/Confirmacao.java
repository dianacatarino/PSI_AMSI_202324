package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Confirmacao {
    private int id;
    private String estado; // "Pendente", "Confirmado" ou "Cancelado"
    private Date dataConfirmacao;
    private int reservaId;
    private int fornecedorId;

    public Confirmacao(int id, String estado, Date dataConfirmacao, int reservaId, int fornecedorId) {
        this.id = id;
        this.estado = estado;
        this.dataConfirmacao = dataConfirmacao;
        this.reservaId = reservaId;
        this.fornecedorId = fornecedorId;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(Date dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
}