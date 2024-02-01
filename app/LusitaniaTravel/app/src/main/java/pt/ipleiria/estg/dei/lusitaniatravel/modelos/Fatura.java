package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import java.util.List;

public class Fatura {
    private int id;
    private double totalF;
    private double totalSI;
    private double iva;
    private int empresaId, reservaId;

    private String data;

    private List<LinhaFatura> linhasfatura;

    public Fatura(int id, double totalF, double totalSI, double iva, int empresaId, int reservaId, String data, List<LinhaFatura> linhasfatura) {
        this.id = id;
        this.totalF = totalF;
        this.totalSI = totalSI;
        this.iva = iva;
        this.empresaId = empresaId;
        this.reservaId = reservaId;
        this.data = data;
        this.linhasfatura = linhasfatura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalF() {
        return totalF;
    }

    public void setTotalF(double totalF) {
        this.totalF = totalF;
    }

    public double getTotalSI() {
        return totalSI;
    }

    public void setTotalSI(double totalSI) {
        this.totalSI = totalSI;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<LinhaFatura> getLinhasfatura() {
        return linhasfatura;
    }

    public void setLinhasfatura(List<LinhaFatura> linhasfatura) {
        this.linhasfatura = linhasfatura;
    }
}
