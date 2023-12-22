package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Imagem {
    private int id;
    private String filename;
    private String fornecedorNome;

    public Imagem(int id, String filename, String fornecedorNome) {
        this.id = id;
        this.filename = filename;
        this.fornecedorNome = fornecedorNome;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }
}
