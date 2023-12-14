package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Fornecedor {
    private int id;
    private String responsavel, tipo, nomeAlojamento, localizacaoAlojamento, acomodacoesAlojamento, filename;

    public Fornecedor(int id, String responsavel, String tipo, String nomeAlojamento, String localizacaoAlojamento, String acomodacoesAlojamento, String filename) {
        this.id = id;
        this.responsavel = responsavel;
        this.tipo = tipo;
        this.nomeAlojamento = nomeAlojamento;
        this.localizacaoAlojamento = localizacaoAlojamento;
        this.acomodacoesAlojamento = acomodacoesAlojamento;
        this.filename = filename;
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
}
