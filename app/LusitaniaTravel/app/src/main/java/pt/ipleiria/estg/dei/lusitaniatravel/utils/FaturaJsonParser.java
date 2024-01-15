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

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;

public class FaturaJsonParser {

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray response) {
        ArrayList<Fatura> faturas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject faturaJson = response.getJSONObject(i);
                int id = faturaJson.optInt("id");
                double totalf = faturaJson.getDouble("totalf");
                double totalsi = faturaJson.getDouble("totalsi");
                double iva = faturaJson.getDouble("iva");
                int empresaId = faturaJson.optInt("empresa_id");
                int reservaId = faturaJson.getInt("reserva_id");
                String dataStr = faturaJson.getString("data");

                // Parse da data diretamente no método
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date data = dateFormat.parse(dataStr);

                Fatura fatura = new Fatura(id, totalf, totalsi, iva, empresaId, reservaId, formatDateToString(data));
                faturas.add(fatura);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return faturas;
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
