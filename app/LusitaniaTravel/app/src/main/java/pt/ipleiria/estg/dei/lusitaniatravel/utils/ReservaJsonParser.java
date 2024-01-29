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
import java.util.List;
import java.util.Locale;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.LinhaReserva;
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

                String estado = reservaJson.getString("estado");

                Reserva reserva = new Reserva(id, tipo, formatDateToString(checkin), formatDateToString(checkout), numeroQuartos, numeroClientes, valor, nomecliente, nomefuncionario, nomefornecedor,estado);
                reservas.add(reserva);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public static Reserva parserJsonReserva(JSONObject response) {
        Reserva reserva = null;
        try {
            JSONObject reservaJson = response.getJSONObject("reserva");
            int id = reservaJson.optInt("id");
            String tipo = reservaJson.getString("tipo");
            String checkinStr = reservaJson.getString("checkin");
            String checkoutStr = reservaJson.getString("checkout");
            int numeroQuartos = reservaJson.getInt("numeroquartos");
            int numeroClientes = reservaJson.getInt("numeroclientes");
            double valor = reservaJson.getDouble("valor");
            String nomeCliente = reservaJson.optString("cliente_id");
            String nomeFuncionario = reservaJson.optString("funcionario_id");
            String nomeFornecedor = reservaJson.getString("fornecedor_id");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date checkin = dateFormat.parse(checkinStr);
            Date checkout = dateFormat.parse(checkoutStr);

            String estado = reservaJson.optString("estado");

            reserva = new Reserva(id, tipo, formatDateToString(checkin), formatDateToString(checkout), numeroQuartos, numeroClientes, valor, nomeCliente, nomeFuncionario, nomeFornecedor,estado);

            // Processar as linhas de reserva, se houver
            JSONArray linhasReservaArray = response.getJSONArray("linha_reserva");
                List<LinhaReserva> linhasReserva = new ArrayList<>();
                for (int i = 0; i < linhasReservaArray.length(); i++) {
                    JSONObject linhaReservaJson = linhasReservaArray.getJSONObject(i);
                    int linhaReservaId = linhaReservaJson.optInt("id");
                    String tipoQuarto = linhaReservaJson.getString("tipoquarto");
                    int numeroNoites = linhaReservaJson.getInt("numeronoites");
                    int numeroCamas = linhaReservaJson.getInt("numerocamas");
                    double subtotal = linhaReservaJson.getDouble("subtotal");
                    int reservaId = linhaReservaJson.getInt("reservas_id");
                    LinhaReserva linhaReserva = new LinhaReserva(linhaReservaId, tipoQuarto, numeroNoites, numeroCamas, subtotal, reservaId);
                    linhasReserva.add(linhaReserva);
                }
                reserva.setLinhasReservas(linhasReserva);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return reserva;
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
