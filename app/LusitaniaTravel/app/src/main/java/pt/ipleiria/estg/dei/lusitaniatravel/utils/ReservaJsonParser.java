package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class ReservaJsonParser {

    public static ArrayList<Reserva> parserJsonReservas(JSONArray response) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject reservaJson = response.getJSONObject(i);
                int id = reservaJson.optInt("id");
                String tipo = reservaJson.getString("tipo");
                String checkinStr = reservaJson.getString("checkin");
                String checkoutStr = reservaJson.getString("checkout");
                int numeroQuartos = reservaJson.getInt("numeroquartos");
                int numeroClientes = reservaJson.getInt("numeroclientes");
                double valor = reservaJson.getDouble("valor");
                String nomecliente = reservaJson.optString("cliente_id");
                String nomefuncionario = reservaJson.optString("funcionario_id");
                String nomefornecedor = reservaJson.getString("fornecedor_id");

                // Parse das datas diretamente no método
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date checkin = dateFormat.parse(checkinStr);
                Date checkout = dateFormat.parse(checkoutStr);

                Reserva reserva = new Reserva(id, tipo, formatDateToString(checkin), formatDateToString(checkout), numeroQuartos, numeroClientes, valor, nomecliente, nomefuncionario, nomefornecedor);
                reservas.add(reserva);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    private static String formatDateToString(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
