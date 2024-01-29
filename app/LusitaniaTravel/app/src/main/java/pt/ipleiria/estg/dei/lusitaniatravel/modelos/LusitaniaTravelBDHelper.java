package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LusitaniaTravelBDHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "LusitaniaTravelDB";

    // Tabela Reserva
    private static final String TABLE_RESERVA = "Reservas";
    private static final String RESERVA_ID = "id";
    private static final String RESERVA_TIPO = "tipo";
    private static final String RESERVA_CHECKIN = "checkin";
    private static final String RESERVA_CHECKOUT = "checkout";
    private static final String RESERVA_NUMERO_QUARTOS = "numeroquartos";
    private static final String RESERVA_NUMERO_CLIENTES = "numeroclientes";
    private static final String RESERVA_VALOR = "valor";
    private static final String RESERVA_CLIENTE_ID = "cliente_id";
    private static final String RESERVA_FUNCIONARIO_ID = "funcionario_id";
    private static final String RESERVA_FORNECEDOR_ID = "fornecedor_id";
    private static final String RESERVA_ESTADO = "estado";

    // Tabela User
    private static final String TABLE_USER = "User";
    private static final String USER_ID = "id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_REPEAT_PASSWORD = "repeatpassword";
    private static final String USER_EMAIL = "email";
    private static final String USER_PROFILE = "profile";

    // Tabela Profile
    private static final String TABLE_PROFILE = "Profile";
    private static final String PROFILE_ID = "id";
    private static final String PROFILE_NAME = "name";
    private static final String PROFILE_MOBILE = "mobile";
    private static final String PROFILE_STREET = "street";
    private static final String PROFILE_LOCALE = "locale";
    private static final String PROFILE_POSTAL_CODE = "postalCode";
    private static final String PROFILE_ROLE = "role";
    private static final String PROFILE_FAVORITES = "favorites";

    private final SQLiteDatabase db;

    public LusitaniaTravelBDHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateReservaTable = "CREATE TABLE " + TABLE_RESERVA +
                "(" + RESERVA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RESERVA_TIPO + " TEXT, " +
                RESERVA_CHECKIN + " DATE NOT NULL, " +
                RESERVA_CHECKOUT + " DATE NOT NULL, " +
                RESERVA_NUMERO_QUARTOS + " INT NOT NULL, " +
                RESERVA_NUMERO_CLIENTES + " INT NOT NULL, " +
                RESERVA_VALOR + " DECIMAL(10, 2) NOT NULL, " +
                RESERVA_CLIENTE_ID + " INT NOT NULL, " +
                RESERVA_FUNCIONARIO_ID + " INT NOT NULL, " +
                RESERVA_FORNECEDOR_ID + " INT NOT NULL, " +
                RESERVA_ESTADO + " TEXT);";
        sqLiteDatabase.execSQL(sqlCreateReservaTable);

        String sqlCreateUserTable = "CREATE TABLE " + TABLE_USER +
                "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_USERNAME + " VARCHAR(25) NOT NULL, " +
                USER_PASSWORD + " VARCHAR(50) NOT NULL, " +
                USER_REPEAT_PASSWORD + " VARCHAR(50) NOT NULL, " +
                USER_EMAIL + " VARCHAR(50) NOT NULL, " +
                USER_PROFILE + " INT NOT NULL);";
        sqLiteDatabase.execSQL(sqlCreateUserTable);

        String sqlCreateProfileTable = "CREATE TABLE " + TABLE_PROFILE +
                "(" + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PROFILE_NAME + " VARCHAR(25) NOT NULL, " +
                PROFILE_MOBILE + " VARCHAR(9) NOT NULL, " +
                PROFILE_STREET + " VARCHAR(30) NOT NULL, " +
                PROFILE_LOCALE + " VARCHAR(20) NOT NULL, " +
                PROFILE_POSTAL_CODE + " VARCHAR(10) NOT NULL, " +
                PROFILE_ROLE + " TEXT DEFAULT NULL, " +
                PROFILE_FAVORITES + " TEXT DEFAULT NULL);";
        sqLiteDatabase.execSQL(sqlCreateProfileTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        this.onCreate(sqLiteDatabase);
    }

    public void adicionarReservaBD(Reserva reserva) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(RESERVA_TIPO, reserva.getTipo());
        values.put(RESERVA_CHECKIN, reserva.getCheckin());
        values.put(RESERVA_CHECKOUT, reserva.getCheckout());
        values.put(RESERVA_NUMERO_QUARTOS, reserva.getNumeroQuartos());
        values.put(RESERVA_NUMERO_CLIENTES, reserva.getNumeroClientes());
        values.put(RESERVA_VALOR, reserva.getValor());
        values.put(RESERVA_CLIENTE_ID, reserva.getNomeCliente());
        values.put(RESERVA_FUNCIONARIO_ID, reserva.getNomeFuncionario());
        values.put(RESERVA_FORNECEDOR_ID, reserva.getNomeFornecedor());
        values.put(RESERVA_ESTADO, reserva.getEstado());

        long result = this.db.insert(TABLE_RESERVA, null, values);

        if (result == -1) {
            throw new SQLException("Erro ao adicionar reserva.");
        }
    }

    public boolean editarReservaBD(Reserva reserva) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(RESERVA_TIPO, reserva.getTipo());
        values.put(RESERVA_CHECKIN, reserva.getCheckin());
        values.put(RESERVA_CHECKOUT, reserva.getCheckout());
        values.put(RESERVA_NUMERO_QUARTOS, reserva.getNumeroQuartos());
        values.put(RESERVA_NUMERO_CLIENTES, reserva.getNumeroClientes());
        values.put(RESERVA_VALOR, reserva.getValor());
        values.put(RESERVA_CLIENTE_ID, reserva.getNomeCliente());
        values.put(RESERVA_FUNCIONARIO_ID, reserva.getNomeFuncionario());
        values.put(RESERVA_FORNECEDOR_ID, reserva.getNomeFornecedor());
        values.put(RESERVA_ESTADO, reserva.getEstado());

        int nLinhasUpdate = this.db.update(TABLE_RESERVA, values, RESERVA_ID + "=?", new String[]{String.valueOf(reserva.getId())});
        return nLinhasUpdate > 0;
    }

    public boolean removerReservaBD(int idReserva) throws SQLException {
        int nLinhasDel = this.db.delete(TABLE_RESERVA, RESERVA_ID + "=?", new String[]{String.valueOf(idReserva)});
        return nLinhasDel > 0;
    }

    public ArrayList<Reserva> getAllReservasBD() {
        ArrayList<Reserva> reservas = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_RESERVA, new String[]{RESERVA_ID, RESERVA_TIPO, RESERVA_CHECKIN, RESERVA_CHECKOUT, RESERVA_NUMERO_QUARTOS, RESERVA_NUMERO_CLIENTES, RESERVA_VALOR, RESERVA_CLIENTE_ID, RESERVA_FUNCIONARIO_ID, RESERVA_FORNECEDOR_ID, RESERVA_ESTADO}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Reserva reserva = new Reserva(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getDouble(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10)
                );
                reservas.add(reserva);
            } while (cursor.moveToNext());
        }
        return reservas;
    }

    public void removerAllReservasBD() {
        this.db.delete(TABLE_RESERVA, null, null);
    }

    public void adicionarUserBD(User user) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_REPEAT_PASSWORD, user.getRepeatPassword());
        values.put(USER_EMAIL, user.getEmail());

        long result = this.db.insert(TABLE_USER, null, values);

        if (result == -1) {
            throw new SQLException("Erro ao adicionar user.");
        }
    }

    public boolean editarUserBD(User user) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_REPEAT_PASSWORD, user.getRepeatPassword());
        values.put(USER_EMAIL, user.getEmail());

        int nLinhasUpdate = this.db.update(TABLE_USER, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
        return nLinhasUpdate > 0;
    }

    public boolean removerUserBD(int idUser) throws SQLException {
        int nLinhasDel = this.db.delete(TABLE_USER, USER_ID + "=?", new String[]{String.valueOf(idUser)});
        return nLinhasDel > 0;
    }

    public ArrayList<User> getAllUsersBD() {
        ArrayList<User> users = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_USER, new String[]{USER_ID, USER_USERNAME, USER_PASSWORD, USER_REPEAT_PASSWORD, USER_EMAIL, USER_PROFILE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String repeatPassword = cursor.getString(3);
                String email = cursor.getString(4);

                // Agora, crie um objeto Profile com as informações do banco de dados
                Profile profile = new Profile(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getString(9)
                );

                // Crie o objeto User passando o Profile como último parâmetro
                User user = new User(userId, username, password, repeatPassword, email, profile);
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    public void adicionarProfileBD(Profile profile) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(PROFILE_NAME, profile.getName());
        values.put(PROFILE_MOBILE, profile.getMobile());
        values.put(PROFILE_STREET, profile.getStreet());
        values.put(PROFILE_LOCALE, profile.getLocale());
        values.put(PROFILE_POSTAL_CODE, profile.getPostalCode());
        values.put(PROFILE_ROLE, profile.getRole().toString());
        values.put(PROFILE_FAVORITES, profile.getFavorites());

        long result = this.db.insert(TABLE_PROFILE, null, values);

        if (result == -1) {
            throw new SQLException("Erro ao adicionar perfil.");
        }
    }

    public void removerAllUsersBD() {
        this.db.delete(TABLE_USER, null, null);
    }

    public boolean editarProfileBD(Profile profile) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(PROFILE_NAME, profile.getName());
        values.put(PROFILE_MOBILE, profile.getMobile());
        values.put(PROFILE_STREET, profile.getStreet());
        values.put(PROFILE_LOCALE, profile.getLocale());
        values.put(PROFILE_POSTAL_CODE, profile.getPostalCode());
        values.put(PROFILE_ROLE, profile.getRole().toString());

        int nLinhasUpdate = this.db.update(TABLE_PROFILE, values, PROFILE_ID + "=?", new String[]{String.valueOf(profile.getId())});
        return nLinhasUpdate > 0;
    }

    public boolean removerProfileBD(int idProfile) throws SQLException {
        int nLinhasDel = this.db.delete(TABLE_PROFILE, PROFILE_ID + "=?", new String[]{String.valueOf(idProfile)});
        return nLinhasDel > 0;
    }

    public ArrayList<Profile> getAllProfilesBD() {
        ArrayList<Profile> profiles = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_PROFILE, new String[]{PROFILE_ID, PROFILE_NAME, PROFILE_MOBILE, PROFILE_STREET, PROFILE_LOCALE, PROFILE_POSTAL_CODE, PROFILE_ROLE,PROFILE_FAVORITES}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        cursor.getString(9)
                );
                profiles.add(profile);
            } while (cursor.moveToNext());
        }
        return profiles;
    }

    public void removerAllProfilesBD() {
        this.db.delete(TABLE_PROFILE, null, null);
    }
}
