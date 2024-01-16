package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Profile;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class SignUpJsonParser {
    public static User parserJsonRegister(String response) {
        User user = null;

        try {
            JSONObject jsonResponse = new JSONObject(response);

            // Verificar o status da resposta
            String status = jsonResponse.getString("status");
            if ("success".equals(status)) {
                JSONObject userData = jsonResponse.getJSONObject("data").getJSONObject("user");

                // Obter os valores do objeto "user" no JSON
                int userId = userData.getInt("id");
                String username = userData.getString("username");
                String password = "";
                String repeatPassword = "";
                String email = userData.getString("email");

                // Obter os valores do objeto "profile" no JSON
                JSONObject profileData = jsonResponse.getJSONObject("data").getJSONObject("profile");
                int profileId = profileData.getInt("id");
                String name = profileData.getString("name");
                String mobile = profileData.getString("mobile");
                String street = profileData.getString("street");
                String locale = profileData.getString("locale");
                String postalCode = profileData.getString("postalCode");
                String role = profileData.optString("role");
                int profileUserId = profileData.getInt("user_id");
                String favorites = userData.optString("favorites");

                // Criar instâncias de User e Profile
                Profile profile = new Profile(profileId, name, mobile, street, locale, postalCode, role, profileUserId,favorites);
                user = new User(userId, username, password, repeatPassword, email, profile);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
