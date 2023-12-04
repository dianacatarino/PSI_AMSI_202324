package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LusitaniaTravelBDHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LusitaniaTravelDB";

    // Adicione aqui as tabelas e colunas específicas do Lusitania Travel conforme necessário

    private final SQLiteDatabase db;

    public LusitaniaTravelBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Crie suas tabelas aqui conforme necessário
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Atualize suas tabelas aqui conforme necessário
    }

    // Adicione métodos para manipulação de dados conforme necessário
}
