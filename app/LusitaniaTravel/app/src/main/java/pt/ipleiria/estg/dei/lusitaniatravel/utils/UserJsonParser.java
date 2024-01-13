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

            int userId = userData.getInt("id");
            String username = userData.getString("username");
            String password = userData.getString("password");
            String repeatPassword = userData.getString("repeatPassword");
            String email = userData.getString("email");

            // If there are profile details in the JSON, parse them
            Profile profile = null;
            if (userData.has("profile")) {
                JSONObject profileData = userData.getJSONObject("profile");
                int profileId = profileData.getInt("id");
                String name = profileData.getString("name");
                String mobile = profileData.getString("mobile");
                String street = profileData.getString("street");
                String locale = profileData.getString("locale");
                String postalCode = profileData.getString("postalCode");
                String role = profileData.getString("role");
                int profileUserId = profileData.getInt("userId");

                profile = new Profile(profileId, name, mobile, street, locale, postalCode, role, profileUserId);
            }

            user = new User(userId, username, password, repeatPassword, email, profile);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}