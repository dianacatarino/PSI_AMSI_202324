package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;

public class Reserva {
    private int id;
    private String tipo;
    private Date checkin, checkout;
    private int numeroQuartos, numeroClientes;
    private double valor;
    private int clienteId, funcionarioId, fornecedorId;

    public Reserva(int id, String tipo, Date checkin, Date checkout, int numeroQuartos, int numeroClientes, double valor, int clienteId, int funcionarioId, int fornecedorId) {
        this.id = id;
        this.tipo = tipo;
        this.checkin = checkin;
        this.checkout = checkout;
        this.numeroQuartos = numeroQuartos;
        this.numeroClientes = numeroClientes;
        this.valor = valor;
        this.clienteId = clienteId;
        this.funcionarioId = funcionarioId;
        this.fornecedorId = fornecedorId;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }

    public double getValor() {
        return valor;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }
}
