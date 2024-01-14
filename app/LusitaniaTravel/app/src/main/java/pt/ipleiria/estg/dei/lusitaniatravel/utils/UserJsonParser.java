package pt.ipleiria.estg.dei.lusitaniatravel.utils;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Profile;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class UserJsonParser {

    public static User parserJsonUser(String response) {
        User user = null;

        try {
            JSONObject jsonUser = new JSONObject(response);
            JSONObject userData = jsonUser.getJSONObject("data");

            // Check if the "id" key is present in the "data" object
            int userId = userData.has("id") ? userData.getInt("id") : -1;

            String username = userData.getString("username");
            String password = userData.optString("password", "");
            String repeatPassword = userData.optString("repeatPassword", "");
            String email = userData.getString("email");

            int profileId = userData.has("profileId") ? userData.getInt("profileId") : -1;
            String name = userData.getString("name");
            String mobile = userData.getString("mobile");
            String street = userData.getString("street");
            String locale = userData.getString("locale");
            String postalCode = userData.getString("postalCode");
            String role = userData.optString("role");
            int profileUserId = userData.has("profileUserId") ? userData.getInt("profileUserId") : -1;

            Profile profile = new Profile(profileId, name, mobile, street, locale, postalCode, role, profileUserId);

            user = new User(userId, username, password, repeatPassword, email, profile);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}