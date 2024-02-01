package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.Date;
import java.util.List;

public class Reserva {
    private int id;
    private String tipo,checkin, checkout;
    private int numeroQuartos, numeroClientes, numeroNoites, numeroCamas;
    private double valor;
    private String nomeCliente, nomeFuncionario, nomeFornecedor, estado, tipoQuarto;
    public Reserva(int id, String tipo, String checkin, String checkout, int numeroQuartos, int numeroClientes, double valor,
                   String nomeCliente, String nomeFuncionario, String nomeFornecedor, String estado, String tipoQuarto,
                   int numeroNoites, int numeroCamas) {
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
        this.estado = estado;
        this.tipoQuarto = tipoQuarto;
        this.numeroNoites = numeroNoites;
        this.numeroCamas = numeroCamas;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumeroNoites() {
        return numeroNoites;
    }

    public void setNumeroNoites(int numeroNoites) {
        this.numeroNoites = numeroNoites;
    }

    public int getNumeroCamas() {
        return numeroCamas;
    }

    public void setNumeroCamas(int numeroCamas) {
        this.numeroCamas = numeroCamas;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }
}
