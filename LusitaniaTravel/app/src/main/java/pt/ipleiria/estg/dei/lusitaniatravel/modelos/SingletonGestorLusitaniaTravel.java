package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.Context;

public class SingletonGestorLusitaniaTravel {
    private static SingletonGestorLusitaniaTravel instance = null;
    private LusitaniaTravelBDHelper lusitaniaTravelBDHelper = null;

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

    // Adicione métodos para manipulação de dados conforme necessário

    // Exemplo de método para obter instância do BD Helper
    public LusitaniaTravelBDHelper getLusitaniaTravelBDHelper() {
        return lusitaniaTravelBDHelper;
    }
}
