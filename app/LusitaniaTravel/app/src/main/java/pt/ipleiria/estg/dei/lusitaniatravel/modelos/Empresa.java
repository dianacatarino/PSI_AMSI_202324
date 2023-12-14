package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Empresa {
    private int id;
    private String sede;
    private double capitalSocial;
    private String email, localidade, nif, morada;

    public Empresa(int id, String sede, double capitalSocial, String email, String localidade, String nif, String morada) {
        this.id = id;
        this.sede = sede;
        this.capitalSocial = capitalSocial;
        this.email = email;
        this.localidade = localidade;
        this.nif = nif;
        this.morada = morada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
}
