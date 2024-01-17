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

    public static List<Carrinho> parserJsonCarrinho(JSONObject response) {
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

                Carrinho carrinho = new Carrinho(quantidade, preco, subtotal, nomeCliente, nomeFornecedor, reservaId, estado);
                carrinhos.add(carrinho);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carrinhos;
    }


    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
