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
    public static ArrayList<Comentario> parserJsonComentarios(JSONArray comentariosArray) {
        ArrayList<Comentario> comentarios = new ArrayList<>();
        try {
            for (int i = 0; i < comentariosArray.length(); i++) {
                JSONObject item = comentariosArray.getJSONObject(i);
                JSONObject comentarioJson = item.getJSONObject("comentario");
                JSONArray avaliacoesArray = item.getJSONArray("avaliacoes");

                int id = comentarioJson.optInt("id");
                String titulo = comentarioJson.getString("titulo");
                String descricao = comentarioJson.getString("descricao");
                String dataComentario = comentarioJson.getString("data_comentario");
                String nomeFornecedor = comentarioJson.getString("fornecedor_nome");
                String nomeCliente = comentarioJson.getString("cliente_nome");

                List<Avaliacao> avaliacoes = new ArrayList<>();
                for (int j = 0; j < avaliacoesArray.length(); j++) {
                    JSONObject avaliacaoJson = avaliacoesArray.getJSONObject(j);
                    int id_avaliacao = avaliacaoJson.optInt("id");
                    int classificacao = avaliacaoJson.getInt("classificacao");
                    String dataAvaliacao = avaliacaoJson.getString("data_avaliacao");
                    Avaliacao avaliacao = new Avaliacao(id_avaliacao,classificacao, dataAvaliacao, nomeFornecedor);
                    avaliacoes.add(avaliacao);
                }

                Comentario comentario = new Comentario(id, titulo, descricao, dataComentario, nomeFornecedor, nomeCliente, avaliacoes);
                comentarios.add(comentario);
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Trate ou lance a exceção de acordo com a necessidade
        }
        return comentarios;
    }


    public static Comentario parserJsonComentario(JSONObject response) {
        Comentario comentario = null;
        try {
            JSONObject comentarioJson = response.getJSONObject("comentario");
            JSONArray avaliacoesArray = response.getJSONArray("avaliacoes");

            int id = comentarioJson.optInt("id");
            String titulo = comentarioJson.getString("titulo");
            String descricao = comentarioJson.getString("descricao");
            String dataComentario = comentarioJson.getString("data_comentario");
            String nomeFornecedor = comentarioJson.getString("fornecedor_nome");
            String nomeCliente = comentarioJson.getString("cliente_nome");

            List<Avaliacao> avaliacoes = new ArrayList<>();
            for (int j = 0; j < avaliacoesArray.length(); j++) {
                JSONObject avaliacaoJson = avaliacoesArray.getJSONObject(j);
                int id_avaliacao = avaliacaoJson.optInt("id");
                int classificacao = avaliacaoJson.getInt("classificacao");
                String dataAvaliacao = avaliacaoJson.getString("data_avaliacao");
                Avaliacao avaliacao = new Avaliacao(id_avaliacao, classificacao, dataAvaliacao, nomeFornecedor);
                avaliacoes.add(avaliacao);
            }

            comentario = new Comentario(id, titulo, descricao, dataComentario, nomeFornecedor, nomeCliente, avaliacoes);
        } catch (JSONException e) {
            e.printStackTrace(); // Trate ou lance a exceção de acordo com a necessidade
        }
        return comentario;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

}
