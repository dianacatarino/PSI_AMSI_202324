package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.Context;

import java.util.ArrayList;

public class SingletonGestorLusitaniaTravel {
    private ArrayList<Reserva> reservas;
    private ArrayList<Profile> profiles;
    private static SingletonGestorLusitaniaTravel instance = null;
    private LusitaniaTravelBDHelper lusitaniaTravelBDHelper = null;

    /*private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILivros = "http://amsi.dei.estg.ipleiria.pt/api/livros";
    private static final String mUrlAPILogin = "http://amsi.dei.estg.ipleiria.pt/api";
    private static final String TOKEN = "AMSI-TOKEN";
    private LivrosListener livrosListener;
    private LivroListener livroListener;*/

    // Método que garante apenas uma instância do Singleton
    public static synchronized SingletonGestorLusitaniaTravel getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorLusitaniaTravel(context);
        }
        return instance;
    }

    private SingletonGestorLusitaniaTravel(Context context) {
        lusitaniaTravelBDHelper = new LusitaniaTravelBDHelper(context);
    }

    public LusitaniaTravelBDHelper getLusitaniaTravelBDHelper() {
        return lusitaniaTravelBDHelper;
    }

    //region PEDIDOS BDLOCAL

    public ArrayList<Reserva> getReservasBD() {
        reservas = lusitaniaTravelBDHelper.getAllReservasBD();
        return new ArrayList<>(reservas);
    }

    public void adicionarAllReservasBD(ArrayList<Reserva> reservas) {
        // Remover todas as reservas da BD -> LusitaniaTravelBDHelper e depois adicionar todas as reservas à BD
        lusitaniaTravelBDHelper.removerAllReservasBD();
        for (Reserva reserva : reservas)
            adicionarReservaBD(reserva);
    }

    public Reserva getReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id)
                return r;
        }
        return null;
    }

    public void adicionarReservaBD(Reserva reserva) {
        lusitaniaTravelBDHelper.adicionarReservaBD(reserva);
    }

    public void editarReservaBD(Reserva reserva) {
        if (reserva != null)
            lusitaniaTravelBDHelper.editarReservaBD(reserva);
    }

    public void removerReservaBD(int idReserva) {
        lusitaniaTravelBDHelper.removerReservaBD(idReserva);
    }

    public ArrayList<Profile> getProfilesBD() {
        profiles = lusitaniaTravelBDHelper.getAllProfilesBD();
        return new ArrayList<>(profiles);
    }

    public void adicionarAllProfilesBD(ArrayList<Profile> profiles) {
        // Remover todos os perfis da BD -> LusitaniaTravelBDHelper e depois adicionar todos os perfis à BD
        lusitaniaTravelBDHelper.removerAllProfilesBD();
        for (Profile profile : profiles)
            adicionarProfileBD(profile);
    }

    public Profile getProfile(int id) {
        for (Profile p : profiles) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public void adicionarProfileBD(Profile profile) {
        lusitaniaTravelBDHelper.adicionarProfileBD(profile);
    }

    public void editarProfileBD(Profile profile) {
        if (profile != null)
            lusitaniaTravelBDHelper.editarProfileBD(profile);
    }

    public void removerProfileBD(int idProfile) {
        lusitaniaTravelBDHelper.removerProfileBD(idProfile);
    }
    //endregion

    //region PEDIDOS API

    //endregion
}
