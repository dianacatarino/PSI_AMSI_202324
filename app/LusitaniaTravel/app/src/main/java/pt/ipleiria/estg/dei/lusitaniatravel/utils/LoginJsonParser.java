package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class LoginJsonParser {

    public static User parserJsonLogin(String response) {
        User user = null;

        try {
            JSONObject loginJson = new JSONObject(response);

            // Verificar se as chaves existem antes de obter os valores
            String username = loginJson.has("username") ? loginJson.getString("username") : null;
            String password = loginJson.has("password") ? loginJson.getString("password") : null;

            user = new User(username, password);

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return user;
    }


    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
