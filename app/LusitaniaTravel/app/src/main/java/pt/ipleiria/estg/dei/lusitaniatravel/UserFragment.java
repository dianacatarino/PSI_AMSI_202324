package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.UserListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Profile;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements UserListener {


    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        Button btnComentarios = view.findViewById(R.id.btnComentarios);
        Button btnDefinicoes = view.findViewById(R.id.btnDefinicoes);
        Button btnFaturas = view.findViewById(R.id.btnFaturas);

        btnComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para o fragmento de Comentários
                Fragment fragment = new ComentariosFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }
        });

        btnDefinicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para o fragmento de Definições
                Fragment fragment = new DefinicoesFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }
        });

        btnFaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar para o fragmento de Faturas
                Fragment fragment = new FaturaFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }
        });

        // Initialize the singleton if not already initialized
        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(requireContext());

        // Set the listener
        singleton.setUserListener(this);

        // Now, you can call the getUserDefinicoesAPI method
        singleton.getUserDefinicoesAPI(requireContext());

        return view;
    }

    public void updateUserDetails(User user, View view) {
        if (view != null && user != null) {
            TextView textViewNomeUser = view.findViewById(R.id.textViewNomeUser);
            textViewNomeUser.setText("" + user.getUsername());
        }
    }

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