package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;

public interface FornecedoresListener {
    void onRefreshListaFornecedores(ArrayList<Fornecedor> listaFornecedores);
}
