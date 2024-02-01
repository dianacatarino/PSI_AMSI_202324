package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.LinhaFatura;

public class FaturaJsonParser {

    public static ArrayList<Fatura> parserJsonFaturas(JSONArray response) {
        ArrayList<Fatura> faturas = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject faturaJson = response.getJSONObject(i);
                int id = faturaJson.getInt("id");
                double totalf = faturaJson.getDouble("totalf");
                double totalsi = faturaJson.getDouble("totalsi");
                double iva = faturaJson.getDouble("iva");
                int empresaId = faturaJson.optInt("empresa_id");
                int reservaId = faturaJson.getInt("reserva_id");
                String dataStr = faturaJson.getString("data");

                // Parse da data diretamente no método
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date data = dateFormat.parse(dataStr);

                // Obter as linhas de fatura
                ArrayList<LinhaFatura> linhasFatura = new ArrayList<>();
                JSONArray linhasFaturaJson = faturaJson.optJSONArray("linhasFatura");
                if (linhasFaturaJson != null) {
                    for (int j = 0; j < linhasFaturaJson.length(); j++) {
                        JSONObject linhaFaturaJson = linhasFaturaJson.optJSONObject(j);
                        int itemId = linhaFaturaJson.optInt("id");
                        int quantidade = linhaFaturaJson.optInt("quantidade");
                        double precoUnitario = linhaFaturaJson.optDouble("precounitario");
                        double subtotal = linhaFaturaJson.optDouble("subtotal");
                        double itemIva = linhaFaturaJson.optDouble("iva");
                        int faturaId = linhaFaturaJson.optInt("fatura_id");
                        int linhasReservasId = linhaFaturaJson.optInt("linhasreservas_id");

                        // Criar a linha de fatura e adicioná-la à lista
                        LinhaFatura linhaFatura = new LinhaFatura(itemId, quantidade, precoUnitario, subtotal, itemIva, faturaId, linhasReservasId);
                        linhasFatura.add(linhaFatura);
                    }
                }

                // Criar a instância de Fatura com as linhas de fatura
                Fatura fatura = new Fatura(id, totalf, totalsi, iva, empresaId, reservaId, formatDateToString(data), linhasFatura);
                faturas.add(fatura);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return faturas;
    }

    public static Fatura parserJsonFatura(JSONObject response) {
        Fatura fatura = null;
        try {
            JSONObject faturaJson = response.getJSONObject("fatura");
            int id = faturaJson.getInt("id");
            double totalf = faturaJson.getDouble("totalf");
            double totalsi = faturaJson.getDouble("totalsi");
            double iva = faturaJson.getDouble("iva");
            int empresaId = faturaJson.optInt("empresa_id");
            int reservaId = faturaJson.getInt("reserva_id");
            String dataStr = faturaJson.getString("data");

            // Parse da data diretamente no método
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date data = dateFormat.parse(dataStr);

            // Criar lista para linhas de fatura
            List<LinhaFatura> linhasFatura = new ArrayList<>();

            // Obter o array de linhas de fatura
            JSONArray linhasFaturaJsonArray = response.getJSONArray("linhasFatura");
            for (int i = 0; i < linhasFaturaJsonArray.length(); i++) {
                JSONObject itemJson = linhasFaturaJsonArray.getJSONObject(i);
                int itemId = itemJson.optInt("id");
                int quantidade = itemJson.getInt("quantidade");
                double precoUnitario = itemJson.getDouble("precounitario");
                double subtotal = itemJson.optDouble("subtotal");
                double itemIva = itemJson.optDouble("iva");
                int faturaId = itemJson.optInt("fatura_id");
                int linhasReservasId = itemJson.optInt("linhasreservas_id");

                // Criar a linha de fatura e adicioná-la à lista
                LinhaFatura linhaFatura = new LinhaFatura(itemId, quantidade, precoUnitario, subtotal, itemIva, faturaId, linhasReservasId);
                linhasFatura.add(linhaFatura);
            }

            // Criar a instância de Fatura com as linhas de fatura
            fatura = new Fatura(id, totalf, totalsi, iva, empresaId, reservaId, formatDateToString(data), linhasFatura);

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return fatura;
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
