package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Confirmacao {
    private int id;
    private String estado; // "Pendente", "Confirmado" ou "Cancelado"
    private Date dataConfirmacao;
    private int reservaId;
    private String nomeFornecedor;

    public Confirmacao(int id, String estado, Date dataConfirmacao, int reservaId, String nomeFornecedor) {
        this.id = id;
        this.estado = estado;
        this.dataConfirmacao = dataConfirmacao;
        this.reservaId = reservaId;
        this.nomeFornecedor = nomeFornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}