package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Avaliacao;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Comentario;

public class ComentarioJsonParser {
    public static ArrayList<Comentario> parserJsonComentarios(JSONArray response) {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject comentarioJson = response.getJSONObject(i);
                int id = comentarioJson.getInt("id");
                String titulo = comentarioJson.getString("titulo");
                String descricao = comentarioJson.getString("descricao");
                String data_comentario = comentarioJson.getString("data_comentario");
                String fornecedor = comentarioJson.getString("fornecedor_id");

                // Parse das avaliações
                JSONArray avaliacoesJsonArray = comentarioJson.getJSONArray("avaliacoes");
                List<Avaliacao> avaliacoes = new ArrayList<>();
                for (int j = 0; j < avaliacoesJsonArray.length(); j++) {
                    JSONObject avaliacaoJson = avaliacoesJsonArray.getJSONObject(j);
                    int classificacao = avaliacaoJson.getInt("classificacao");
                    String data_avaliacao = avaliacaoJson.getString("filename");
                    String fornecedorNome = avaliacaoJson.getString("fornecedor_id");

                    Avaliacao avaliacao = new Avaliacao(id,classificacao, data_avaliacao, fornecedorNome);
                    avaliacoes.add(avaliacao);
                }

                Comentario comentario = new Comentario(id, titulo, descricao, data_comentario, fornecedor, avaliacoes);
                comentarios.add(comentario);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return comentarios;
    }

    public static Comentario parserJsonComentario(JSONObject response) {
        Comentario comentario = null;
        try {
            int id = response.getInt("id");
            String titulo = response.getString("titulo");
            String descricao = response.getString("descricao");
            String data_comentario = response.getString("data_comentario");
            String fornecedor = response.getString("fornecedor_id");

            JSONArray avaliacoesJsonArray = response.getJSONArray("avaliacoes");
            List<Avaliacao> avaliacoes = new ArrayList<>();
            for (int j = 0; j < avaliacoesJsonArray.length(); j++) {
                JSONObject avaliacaoJson = avaliacoesJsonArray.getJSONObject(j);
                int classificacao = avaliacaoJson.getInt("classificacao");
                String data_avaliacao = avaliacaoJson.getString("filename");
                String fornecedorNome = avaliacaoJson.getString("fornecedor_id");

                Avaliacao avaliacao = new Avaliacao(id,classificacao, data_avaliacao, fornecedorNome);
                avaliacoes.add(avaliacao);
            }

            comentario = new Comentario(id, titulo, descricao, data_comentario, fornecedor, avaliacoes);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return comentario;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

}
