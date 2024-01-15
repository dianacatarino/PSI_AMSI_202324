package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;

public interface FaturasListener {
    void onRefreshListaFaturas(ArrayList<Fatura> listaFaturas);
}
