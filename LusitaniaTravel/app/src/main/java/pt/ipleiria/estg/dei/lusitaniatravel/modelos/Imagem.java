package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

public class Imagem {
    private int id;
    private String filename;
    private int fornecedorId;

    public Imagem(int id, String filename, int fornecedorId) {
        this.id = id;
        this.filename = filename;
        this.fornecedorId = fornecedorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(int fornecedorId) {
        this.fornecedorId = fornecedorId;
    }
}