package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.UserListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Profile;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefinicoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefinicoesFragment extends Fragment implements UserListener {

    public DefinicoesFragment() {
        // Required empty public constructor
    }

    public static DefinicoesFragment newInstance() {
        DefinicoesFragment fragment = new DefinicoesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definicoes, container, false);

        // Initialize the singleton if not already initialized
        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(requireContext());

        // Set the listener
        singleton.setUserListener(this);

        // Now, you can call the getUserDefinicoesAPI method
        singleton.getUserDefinicoesAPI(requireContext());

        return view;
    }

    // Add the updateUserDetails method to update the UI with user details
    public void updateUserDetails(User user, View view) {
        if (view != null && user != null) {
            TextView textViewUsername = view.findViewById(R.id.textViewUsername);
            TextView textViewEmail = view.findViewById(R.id.textViewEmail);
            TextView textViewProfileName = view.findViewById(R.id.textViewProfileName);
            TextView textViewProfileMobile = view.findViewById(R.id.textViewProfileMobile);
            TextView textViewProfileStreet = view.findViewById(R.id.textViewProfileStreet);
            TextView textViewProfileLocale = view.findViewById(R.id.textViewProfileLocale);
            TextView textViewProfilePostalCode = view.findViewById(R.id.textViewProfilePostalCode);

            // Update user details
            textViewUsername.setText(user.getUsername());
            textViewEmail.setText("Email: " + user.getEmail());

            // Update profile details if available
            Profile profile = user.getProfile();
            if (profile != null) {
                textViewProfileName.setText("Nome: " + profile.getName());
                textViewProfileMobile.setText("Telemóvel: " + profile.getMobile());
                textViewProfileStreet.setText("Rua: " + profile.getStreet());
                textViewProfileLocale.setText("Localidade: " + profile.getLocale());
                textViewProfilePostalCode.setText("Código Postal: " + profile.getPostalCode());
            }
        }
    }

    // Implement the missing method from UserListener
    @Override
    public void onRefreshDetalhes(User user) {
        View view = getView();
        if (view != null && user != null) {
            updateUserDetails(user, view);
        } else {
            // Additional handling, e.g., show an error message to the user
            Toast.makeText(requireContext(), "Failed to refresh details", Toast.LENGTH_SHORT).show();
        }
    }

}