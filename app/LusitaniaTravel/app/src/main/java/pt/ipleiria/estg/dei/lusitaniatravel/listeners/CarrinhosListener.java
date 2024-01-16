package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;

public interface CarrinhosListener {
    void onRefreshListaCarrinho(ArrayList<Carrinho> listaCarrinho);
}
