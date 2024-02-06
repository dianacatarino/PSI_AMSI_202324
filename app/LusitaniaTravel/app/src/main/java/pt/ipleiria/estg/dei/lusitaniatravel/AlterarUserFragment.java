package pt.ipleiria.estg.dei.lusitaniatravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.UserListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Profile;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlterarUserFragment#} factory method to
 * create an instance of this fragment.
 */
public class AlterarUserFragment extends Fragment implements UserListener {

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    EditText editTextFieldUsername, editTextFieldEmail,editTextFieldNome,editTextFieldTelemovel,editTextFieldRua,
            editTextFieldLocalidade,editTextFieldCodPostal;

    public AlterarUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alterar_user, container, false);

        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(getContext());

        // Set the listener
        singleton.setUserListener(this);

        // Obtém uma referência para os EditTexts no layout
        editTextFieldUsername = view.findViewById(R.id.editTextFieldUsername);
        editTextFieldEmail = view.findViewById(R.id.editTextFieldEmail);
        editTextFieldNome = view.findViewById(R.id.editTextFieldNome);
        editTextFieldTelemovel = view.findViewById(R.id.editTextFieldTelemovel);
        editTextFieldRua = view.findViewById(R.id.editTextFieldRua);
        editTextFieldLocalidade = view.findViewById(R.id.editTextFieldLocalidade);
        editTextFieldCodPostal = view.findViewById(R.id.editTextFieldCodPostal);

        singleton.getUserDefinicoesAPI(getContext());

        Button btnSalvar = view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Coloque aqui a lógica para salvar as alterações
                // Por exemplo, você pode obter os valores dos EditTexts
                /*String novoNome = editTextFieldNome.getText().toString();
                String novoTelemovel = editTextFieldTelemovel.getText().toString();*/
                // E então, você pode atualizar o perfil do usuário com os novos valores
                // updateProfile(user, novoNome, novoTelemovel, ...);
            }
        });

        // Configurar o evento de clique para o botão "Cancelar"
        Button btnCancelar = view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public void updateUserDetails(User user, View view) {
        if (view != null && user != null) {
            // Obtém as referências para os EditTexts
            editTextFieldUsername = view.findViewById(R.id.editTextFieldUsername);
            editTextFieldEmail = view.findViewById(R.id.editTextFieldEmail);
            editTextFieldNome = view.findViewById(R.id.editTextFieldNome);
            editTextFieldTelemovel = view.findViewById(R.id.editTextFieldTelemovel);
            editTextFieldRua = view.findViewById(R.id.editTextFieldRua);
            editTextFieldLocalidade = view.findViewById(R.id.editTextFieldLocalidade);
            editTextFieldCodPostal = view.findViewById(R.id.editTextFieldCodPostal);

            // Define os valores nos EditTexts com base nos detalhes do usuário
            editTextFieldUsername.setText(user.getUsername());
            editTextFieldEmail.setText(user.getEmail());
            // Supondo que você tenha um objeto Profile no objeto User
            Profile profile = user.getProfile();
            if (profile != null) {
                editTextFieldNome.setText(profile.getName());
                editTextFieldTelemovel.setText(profile.getMobile());
                editTextFieldRua.setText(profile.getStreet());
                editTextFieldLocalidade.setText(profile.getLocale());
                editTextFieldCodPostal.setText(profile.getPostalCode());
            }
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