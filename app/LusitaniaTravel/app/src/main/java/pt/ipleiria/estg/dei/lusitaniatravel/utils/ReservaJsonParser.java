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
                String tipo = reservaJson.optString("tipo");
                String checkinStr = reservaJson.optString("checkin");
                String checkoutStr = reservaJson.optString("checkout");
                int numeroQuartos = reservaJson.optInt("numeroquartos");
                int numeroClientes = reservaJson.optInt("numeroclientes");
                double valor = reservaJson.optDouble("valor");
                String nomeCliente = reservaJson.optString("cliente_id");
                String nomeFuncionario = reservaJson.optString("funcionario_id");
                String nomeFornecedor = reservaJson.optString("fornecedor_id");
                String estado = reservaJson.optString("estado");

                // Parse dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date checkin = dateFormat.parse(checkinStr);
                Date checkout = dateFormat.parse(checkoutStr);

                String tipoQuarto = reservaJson.optString("tipoQuarto");
                int numeroNoites = reservaJson.optInt("numeronoites");
                int numeroCamas = reservaJson.optInt("numerocamas");

                // Create reserva object
                Reserva reserva = new Reserva(id, tipo, checkinStr, checkoutStr, numeroQuartos, numeroClientes, valor, nomeCliente, nomeFuncionario, nomeFornecedor, estado, tipoQuarto, numeroNoites, numeroCamas);
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

            // Obtenha o objeto de confirmação, se existir
            JSONObject confirmacaoJson = response.optJSONObject("confirmacao");
            String estado = "";
            if (confirmacaoJson != null) {
                estado = confirmacaoJson.optString("estado");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date checkin = dateFormat.parse(checkinStr);
            Date checkout = dateFormat.parse(checkoutStr);

            // Obtenha o objeto JSON "linha_reserva"
            JSONObject linhaReservaJson = response.optJSONObject("linha_reserva");

            // Crie variáveis para os campos da linha de reserva
            String tipoQuarto = null;
            int numeroNoites = 0;
            int numeroCamas = 0;

            // Verifique se o objeto linha_reserva existe
            if (linhaReservaJson != null) {
                // Extraia os dados da linha de reserva
                tipoQuarto = linhaReservaJson.optString("tipoquarto");
                numeroNoites = linhaReservaJson.optInt("numeronoites");
                numeroCamas = linhaReservaJson.optInt("numerocamas");
            }

            // Crie a reserva com as informações extraídas
            reserva = new Reserva(id, tipo, checkinStr, checkoutStr, numeroQuartos, numeroClientes, valor, nomeCliente, nomeFuncionario, nomeFornecedor, estado, tipoQuarto, numeroNoites, numeroCamas);

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return reserva;
    }

    public static Reserva parserJsonVerificar(JSONObject response) {
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

            // Obtenha o objeto de confirmação, se existir
            JSONObject confirmacaoJson = response.optJSONObject("confirmacao");
            String estado = "Pendente";
            if (confirmacaoJson != null) {
                estado = confirmacaoJson.optString("estado");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date checkin = dateFormat.parse(checkinStr);
            Date checkout = dateFormat.parse(checkoutStr);

            // Obtenha o objeto JSON "linha_reserva"
            JSONObject linhaReservaJson = response.optJSONObject("linhasreservas");

            // Crie variáveis para os campos da linha de reserva
            String tipoQuarto = null;
            int numeroNoites = 0;
            int numeroCamas = 0;

            // Verifique se o objeto linha_reserva existe
            if (linhaReservaJson != null) {
                // Extraia os dados da linha de reserva
                tipoQuarto = linhaReservaJson.optString("tipoquarto");
                numeroNoites = linhaReservaJson.optInt("numeronoites");
                numeroCamas = linhaReservaJson.optInt("numerocamas");
            }

            // Crie a reserva com as informações extraídas
            reserva = new Reserva(id, tipo, checkinStr, checkoutStr, numeroQuartos, numeroClientes, valor, nomeCliente, nomeFuncionario, nomeFornecedor, estado, tipoQuarto, numeroNoites, numeroCamas);

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
