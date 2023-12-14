package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Reserva {
    private int id;
    private String tipo,checkin, checkout;
    private int numeroQuartos, numeroClientes;
    private double valor;
    private String nomeCliente, nomeFuncionario, nomeFornecedor;

    public Reserva(int id, String tipo, String checkin, String checkout, int numeroQuartos, int numeroClientes, double valor, String nomeCliente, String nomeFuncionario, String nomeFornecedor) {
        this.id = id;
        this.tipo = tipo;
        this.checkin = checkin;
        this.checkout = checkout;
        this.numeroQuartos = numeroQuartos;
        this.numeroClientes = numeroClientes;
        this.valor = valor;
        this.nomeCliente = nomeCliente;
        this.nomeFuncionario = nomeFuncionario;
        this.nomeFornecedor = nomeFornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(int numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }

    public void setNumeroClientes(int numeroClientes) {
        this.numeroClientes = numeroClientes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }
}
