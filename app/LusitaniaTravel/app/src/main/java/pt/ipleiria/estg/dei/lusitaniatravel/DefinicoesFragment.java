package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DefinicoesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefinicoesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefinicoesFragment newInstance(String param1, String param2) {
        DefinicoesFragment fragment = new DefinicoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            textViewUsername.setText("Username: " + user.getUsername());
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
        // You can decide what to do when user details are refreshed
        // For example, update the UI by calling updateUserDetails
        View view = getView();
        if (view != null) {
            updateUserDetails(user, view);
        }
    }
}