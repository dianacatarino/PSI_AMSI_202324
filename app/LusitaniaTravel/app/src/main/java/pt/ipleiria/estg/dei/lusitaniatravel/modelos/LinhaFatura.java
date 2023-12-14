package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class LinhaFatura {
    private int id;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;
    private double iva;
    private int faturaId, linhaReservaId;

    public LinhaFatura(int id, int quantidade, double precoUnitario, double subtotal, double iva, int faturaId, int linhaReservaId) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
        this.iva = iva;
        this.faturaId = faturaId;
        this.linhaReservaId = linhaReservaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getFaturaId() {
        return faturaId;
    }

    public void setFaturaId(int faturaId) {
        this.faturaId = faturaId;
    }

    public int getLinhaReservaId() {
        return linhaReservaId;
    }

    public void setLinhaReservaId(int linhaReservaId) {
        this.linhaReservaId = linhaReservaId;
    }
}
