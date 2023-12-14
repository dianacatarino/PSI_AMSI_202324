package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Carrinho {
    private int id;
    private int quantidade;
    private double preco;
    private double subtotal;
    private String nomeCliente, nomeFornecedor;
    private int reservaId;

    public Carrinho(int quantidade, double preco, double subtotal, String nomeCliente, String nomeFornecedor, int reservaId) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.subtotal = subtotal;
        this.nomeCliente = nomeCliente;
        this.nomeFornecedor = nomeFornecedor;
        this.reservaId = reservaId;
    }

    // Getters e Setters
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }
}
