package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;

public interface FaturaListener {
    void onRefreshDetalhes(Fatura fatura);
}
