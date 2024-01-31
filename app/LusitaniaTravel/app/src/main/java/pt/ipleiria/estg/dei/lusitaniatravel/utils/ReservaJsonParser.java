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
                String nomeCliente = reservaJson.optString("cliente_id");
                String nomeFuncionario = reservaJson.optString("funcionario_id");
                String nomeFornecedor = reservaJson.getString("fornecedor_id");

                // Parse das datas diretamente no método
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date checkin = dateFormat.parse(checkinStr);
                Date checkout = dateFormat.parse(checkoutStr);

                String estado = reservaJson.optString("estado");

                // Processar as linhas de reserva
                JSONArray linhasReservaArray = reservaJson.optJSONArray("linha_reserva");
                List<LinhaReserva> linhasReserva = new ArrayList<>();
                if (linhasReservaArray != null) {
                    for (int j = 0; j < linhasReservaArray.length(); j++) {
                        JSONObject linhaReservaJson = linhasReservaArray.optJSONObject(j);
                        int linhaReservaId = linhaReservaJson.optInt("id");
                        String tipoQuarto = linhaReservaJson.optString("tipoquarto");
                        int numeroNoites = linhaReservaJson.optInt("numeronoites");
                        int numeroCamas = linhaReservaJson.optInt("numerocamas");
                        double subtotal = linhaReservaJson.optDouble("subtotal");
                        int reservaId = linhaReservaJson.optInt("reservas_id");
                        LinhaReserva linhaReserva = new LinhaReserva(linhaReservaId, tipoQuarto, numeroNoites, numeroCamas, subtotal, reservaId);
                        linhasReserva.add(linhaReserva);
                    }
                }

                // Criar a reserva com as linhas de reserva
                Reserva reserva = new Reserva(id, tipo, formatDateToString(checkin), formatDateToString(checkout), numeroQuartos, numeroClientes, valor, nomeCliente, nomeFuncionario, nomeFornecedor, estado, linhasReserva);
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
            String estado = ""; // Adicione um campo de estado para reserva e inicialize com uma string vazia

            // Obtenha o objeto de confirmação, se existir
            JSONObject confirmacaoJson = response.optJSONObject("confirmacao");
            if (confirmacaoJson != null) {
                estado = confirmacaoJson.optString("estado");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date checkin = dateFormat.parse(checkinStr);
            Date checkout = dateFormat.parse(checkoutStr);

            // Obtenha o objeto JSON "linha_reserva"
            JSONObject linhaReservaJson = response.optJSONObject("linha_reserva");

            // Crie uma lista de linhas de reserva, mesmo que vazia
            List<LinhaReserva> linhasReserva = new ArrayList<>();

            // Verifique se o objeto linha_reserva existe
            if (linhaReservaJson != null) {
                // Extraia os dados da linha de reserva
                int linhaReservaId = linhaReservaJson.optInt("id");
                String tipoQuarto = linhaReservaJson.getString("tipoquarto");
                int numeroNoites = linhaReservaJson.getInt("numeronoites");
                int numeroCamas = linhaReservaJson.getInt("numerocamas");
                double subtotal = linhaReservaJson.getDouble("subtotal");
                int reservaId = linhaReservaJson.getInt("reservas_id");

                // Crie uma instância de LinhaReserva com os dados extraídos
                LinhaReserva linhaReserva = new LinhaReserva(linhaReservaId, tipoQuarto, numeroNoites, numeroCamas, subtotal, reservaId);

                // Adicione a linha de reserva à lista de linhas de reserva
                linhasReserva.add(linhaReserva);
            }

            // Crie a reserva com as linhas de reserva, mesmo que a lista esteja vazia
            reserva = new Reserva(id, tipo, formatDateToString(checkin), formatDateToString(checkout), numeroQuartos, numeroClientes, valor, nomeCliente, nomeFuncionario, nomeFornecedor, estado, linhasReserva);

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
