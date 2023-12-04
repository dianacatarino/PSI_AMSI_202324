package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class LinhaReserva {

    private int id;
    private String tipoQuarto;
    private int numeroNoites;
    private int numeroCamas;
    private double subtotal;
    private int reservaId;

    public LinhaReserva(int id, String tipoQuarto, int numeroNoites, int numeroCamas, double subtotal, int reservaId) {
        this.id = id;
        this.tipoQuarto = tipoQuarto;
        this.numeroNoites = numeroNoites;
        this.numeroCamas = numeroCamas;
        this.subtotal = subtotal;
        this.reservaId = reservaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }
}
