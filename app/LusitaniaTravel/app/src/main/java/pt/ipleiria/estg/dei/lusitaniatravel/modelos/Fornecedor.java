package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Fornecedor {
    private int id;
    private String responsavel, tipo, nomeAlojamento, localizacaoAlojamento, acomodacoesAlojamento, tipoQuartos;
    private int numeroQuartos;
    private double precoPorNoite;

    public Fornecedor(int id, String responsavel, String tipo, String nomeAlojamento,
                      String localizacaoAlojamento, String acomodacoesAlojamento,
                      String tipoQuartos, int numeroQuartos, double precoPorNoite) {
        this.id = id;
        this.responsavel = responsavel;
        this.tipo = tipo;
        this.nomeAlojamento = nomeAlojamento;
        this.localizacaoAlojamento = localizacaoAlojamento;
        this.acomodacoesAlojamento = acomodacoesAlojamento;
        this.tipoQuartos = tipoQuartos;
        this.numeroQuartos = numeroQuartos;
        this.precoPorNoite = precoPorNoite;
    }

    public int getId() {
        return id;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeAlojamento() {
        return nomeAlojamento;
    }

    public void setNomeAlojamento(String nomeAlojamento) {
        this.nomeAlojamento = nomeAlojamento;
    }

    public String getLocalizacaoAlojamento() {
        return localizacaoAlojamento;
    }

    public void setLocalizacaoAlojamento(String localizacaoAlojamento) {
        this.localizacaoAlojamento = localizacaoAlojamento;
    }

    public String getAcomodacoesAlojamento() {
        return acomodacoesAlojamento;
    }

    public void setAcomodacoesAlojamento(String acomodacoesAlojamento) {
        this.acomodacoesAlojamento = acomodacoesAlojamento;
    }

    public String getTipoQuartos() {
        return tipoQuartos;
    }

    public void setTipoQuartos(String tipoQuartos) {
        this.tipoQuartos = tipoQuartos;
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(int numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }

    public double getPrecoPorNoite() {
        return precoPorNoite;
    }

    public void setPrecoPorNoite(double precoPorNoite) {
        this.precoPorNoite = precoPorNoite;
    }
}
