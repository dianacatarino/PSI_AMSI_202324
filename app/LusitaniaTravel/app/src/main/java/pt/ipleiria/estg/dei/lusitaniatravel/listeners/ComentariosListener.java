package pt.ipleiria.estg.dei.lusitaniatravel.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Comentario;

public interface ComentariosListener {
    void onRefreshListaComentarios(ArrayList<Comentario> listaComentarios);
}
