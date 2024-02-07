package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Imagem;

public class FavoritoJsonParser {
    public static ArrayList<Fornecedor> parserJsonFavoritos(JSONArray response) {
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

                // Parse das imagens
                JSONArray imagensJsonArray = fornecedorJson.getJSONArray("imagens");
                List<Imagem> imagens = new ArrayList<>();
                for (int j = 0; j < imagensJsonArray.length(); j++) {
                    JSONObject imagemJson = imagensJsonArray.getJSONObject(j);
                    int imagemId = imagemJson.getInt("id");
                    String filename = imagemJson.getString("filename");
                    int fornecedorId = imagemJson.getInt("fornecedor_id");

                    Imagem imagem = new Imagem(imagemId, filename, fornecedorId);
                    imagens.add(imagem);
                }

                Fornecedor fornecedor = new Fornecedor(id, responsavel, tipo, nomeAlojamento, localizacaoAlojamento, acomodacoesAlojamento, tipoQuartos, numeroQuartos, precoPorNoite, imagens);
                fornecedores.add(fornecedor);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return fornecedores;
    }

    public static Fornecedor parserJsonFavorito(JSONObject fornecedorJson) {
        try {
            int id = fornecedorJson.getInt("id");
            String responsavel = fornecedorJson.getString("responsavel");
            String tipo = fornecedorJson.getString("tipo");
            String nomeAlojamento = fornecedorJson.getString("nome_alojamento");
            String localizacaoAlojamento = fornecedorJson.getString("localizacao_alojamento");
            String acomodacoesAlojamento = fornecedorJson.getString("acomodacoes_alojamento");
            String tipoQuartos = fornecedorJson.getString("tipoquartos");
            int numeroQuartos = fornecedorJson.getInt("numeroquartos");
            double precoPorNoite = fornecedorJson.getDouble("precopornoite");

            // Parse das imagens
            JSONArray imagensJsonArray = fornecedorJson.getJSONArray("imagens");
            List<Imagem> imagens = new ArrayList<>();
            for (int j = 0; j < imagensJsonArray.length(); j++) {
                JSONObject imagemJson = imagensJsonArray.getJSONObject(j);
                int imagemId = imagemJson.getInt("id");
                String filename = imagemJson.getString("filename");
                int fornecedorId = imagemJson.getInt("fornecedor_id");

                Imagem imagem = new Imagem(imagemId, filename, fornecedorId);
                imagens.add(imagem);
            }

            return new Fornecedor(id, responsavel, tipo, nomeAlojamento, localizacaoAlojamento, acomodacoesAlojamento, tipoQuartos, numeroQuartos, precoPorNoite, imagens);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
