package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;

public interface ReservasListener {
    void onRefreshListaReservas(ArrayList<Reserva> listaReservas);
}
