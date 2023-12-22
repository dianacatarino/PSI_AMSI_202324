package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;

public class FornecedorJsonParser {

    public static ArrayList<Fornecedor> parserJsonFornecedores(JSONArray response) {
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject fornecedorJson = response.getJSONObject(i);
                int id = fornecedorJson.getInt("id");
                String responsavel = fornecedorJson.getString("responsavel");
                String tipo = fornecedorJson.getString("tipo");
                String nomeAlojamento = fornecedorJson.getString("nome_alojamento");
                String localizacaoAlojamento = fornecedorJson.getString("localizacao_alojamento");
                String acomodacoesAlojamento = fornecedorJson.getString("acomodacoes_alojamento");
                String tipoQuartos = fornecedorJson.getString("tipoquartos");
                int numeroQuartos = fornecedorJson.getInt("numeroquartos");
                double precoPorNoite = fornecedorJson.getDouble("precopornoite");

                Fornecedor fornecedor = new Fornecedor(id, responsavel, tipo, nomeAlojamento, localizacaoAlojamento, acomodacoesAlojamento, tipoQuartos, numeroQuartos, precoPorNoite);
                fornecedores.add(fornecedor);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return fornecedores;
    }

    public static Fornecedor parserJsonFornecedor(String response) {
        Fornecedor fornecedor = null;
        try {
            JSONObject fornecedorJson = new JSONObject(response);
            int id = fornecedorJson.getInt("id");
            String responsavel = fornecedorJson.getString("responsavel");
            String tipo = fornecedorJson.getString("tipo");
            String nomeAlojamento = fornecedorJson.getString("nome_alojamento");
            String localizacaoAlojamento = fornecedorJson.getString("localizacao_alojamento");
            String acomodacoesAlojamento = fornecedorJson.getString("acomodacoes_alojamento");
            String tipoQuartos = fornecedorJson.getString("tipoquartos");
            int numeroQuartos = fornecedorJson.getInt("numeroquartos");
            double precoPorNoite = fornecedorJson.getDouble("precopornoite");

            fornecedor = new Fornecedor(id, responsavel, tipo, nomeAlojamento, localizacaoAlojamento, acomodacoesAlojamento, tipoQuartos, numeroQuartos, precoPorNoite);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return fornecedor;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}


