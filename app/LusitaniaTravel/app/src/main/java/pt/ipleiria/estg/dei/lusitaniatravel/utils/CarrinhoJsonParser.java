package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;

public class CarrinhoJsonParser {

    public static List<Carrinho> parserJsonCarrinhos(JSONObject response) {
        List<Carrinho> carrinhos = new ArrayList<>();

        try {
            JSONArray carrinhoArray = response.getJSONArray("carrinho");

            for (int i = 0; i < carrinhoArray.length(); i++) {
                JSONObject carrinhoJson = carrinhoArray.getJSONObject(i);

                int id = carrinhoJson.optInt("id");
                String nomeFornecedor = carrinhoJson.getString("fornecedor");
                String nomeCliente = carrinhoJson.optString("cliente");
                int quantidade = carrinhoJson.getInt("quantidade");
                double preco = carrinhoJson.getDouble("preco");
                double subtotal = carrinhoJson.getDouble("subtotal");
                int reservaId = carrinhoJson.getInt("reserva_id");
                String estado = carrinhoJson.getString("estado");

                Carrinho carrinho = new Carrinho(id,quantidade, preco, subtotal, nomeCliente, nomeFornecedor, reservaId, estado);
                carrinhos.add(carrinho);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carrinhos;
    }

    public static Carrinho parserJsonCarrinho(String response){
        Carrinho carrinho = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject dataObject = jsonObject.getJSONObject("data");

            int id = dataObject.getInt("id");
            String nomeAlojamento = dataObject.getString("nome_alojamento");
            String clienteNome = dataObject.getString("cliente_nome");
            int quantidade = dataObject.getInt("quantidade");
            double preco = dataObject.getDouble("preco");
            double subtotal = dataObject.getDouble("subtotal");
            int reservaId = dataObject.getInt("reserva_id");
            String estado = dataObject.optString("estado");

            carrinho = new Carrinho(id,quantidade,preco,subtotal, nomeAlojamento, clienteNome, reservaId,estado);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return carrinho;
    }


    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
