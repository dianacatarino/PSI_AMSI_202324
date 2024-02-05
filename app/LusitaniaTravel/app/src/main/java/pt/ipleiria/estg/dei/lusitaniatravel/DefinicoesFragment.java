package pt.ipleiria.estg.dei.lusitaniatravel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    public static final int ADD = 100, EDIT = 200, DELETE = 300;
    public static final String OP_CODE = "op_detalhes";
    FloatingActionButton fabEdit;

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
        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(getContext());

        // Set the listener
        singleton.setUserListener(this);

        // Now, you can call the getUserDefinicoesAPI method
        singleton.getUserDefinicoesAPI(getContext());

        // Find the FloatingActionButton
        fabEdit = view.findViewById(R.id.fabEdit);

        // Set a click listener for the FloatingActionButton
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the AlterarUserFragment
                AlterarUserFragment alterarUserFragment = new AlterarUserFragment();

                // Replace the current fragment with the AlterarUserFragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, alterarUserFragment)
                        .addToBackStack(null) // Add the transaction to the back stack
                        .commit();
            }
        });

        return view;
    }

    // Add the updateUserDetails method to update the UI with user details
    public void updateUserDetails(final User user, View view) {
        if (view != null && user != null) {
            TextView textViewUsername = view.findViewById(R.id.textViewUsername);
            TextView textViewEmail = view.findViewById(R.id.textViewEmail);
            final TextView textViewProfileName = view.findViewById(R.id.textViewProfileName);
            final TextView textViewProfileMobile = view.findViewById(R.id.textViewProfileMobile);
            final TextView textViewProfileStreet = view.findViewById(R.id.textViewProfileStreet);
            final TextView textViewProfileLocale = view.findViewById(R.id.textViewProfileLocale);
            final TextView textViewProfilePostalCode = view.findViewById(R.id.textViewProfilePostalCode);

            // Update user details
            textViewUsername.setText(user.getUsername());
            textViewEmail.setText("Email: " + user.getEmail());

            // Update profile details if available
            final Profile profile = user.getProfile();
            if (profile != null) {
                textViewProfileName.setText("Nome: " + profile.getName());
                textViewProfileMobile.setText("Telemóvel: " + profile.getMobile());
                textViewProfileStreet.setText("Rua: " + profile.getStreet());
                textViewProfileLocale.setText("Localidade: " + profile.getLocale());
                textViewProfilePostalCode.setText("Código Postal: " + profile.getPostalCode());
            }
        }
    }

    /*private void openEditProfileFieldDialog(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile_field, null);
        builder.setView(dialogView);

        // Adicione todas as EditText do layout
        final EditText editTextFieldNome = dialogView.findViewById(R.id.editTextFieldNome);
        final EditText editTextFieldTelemovel = dialogView.findViewById(R.id.editTextFieldTelemovel);
        final EditText editTextFieldRua = dialogView.findViewById(R.id.editTextFieldRua);
        final EditText editTextFieldLocalidade = dialogView.findViewById(R.id.editTextFieldLocalidade);
        final EditText editTextFieldCodPostal = dialogView.findViewById(R.id.editTextFieldCodPostal);

        // Defina os valores iniciais com base nos detalhes do usuário
        Profile profile = user.getProfile();
        if (profile != null) {
            editTextFieldNome.setText(profile.getName());
            editTextFieldTelemovel.setText(profile.getMobile());
            editTextFieldRua.setText(profile.getStreet());
            editTextFieldLocalidade.setText(profile.getLocale());
            editTextFieldCodPostal.setText(profile.getPostalCode());
        }

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtenha os valores inseridos pelo usuário
                String newFieldValueNome = editTextFieldNome.getText().toString();
                String newFieldValueTelemovel = editTextFieldTelemovel.getText().toString();
                String newFieldValueRua = editTextFieldRua.getText().toString();
                String newFieldValueLocalidade = editTextFieldLocalidade.getText().toString();
                String newFieldValueCodPostal = editTextFieldCodPostal.getText().toString();

                // Atualize o perfil do usuário com os novos valores
                updateProfileField(user, "name", newFieldValueNome);
                updateProfileField(user, "mobile", newFieldValueTelemovel);
                updateProfileField(user, "street", newFieldValueRua);
                updateProfileField(user, "locale", newFieldValueLocalidade);
                updateProfileField(user, "postalCode", newFieldValueCodPostal);

                // Atualize a interface do usuário
                if (getView() != null && user != null) {
                    updateUserDetails(user, getView());
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateProfileField(User user, String fieldKey, String newValue) {
        // Atualize o perfil do usuário com o novo valor
        Profile profile = user.getProfile();
        if (profile != null) {
            switch (fieldKey) {
                case "name":
                    profile.setName(newValue);
                    break;
                case "mobile":
                    profile.setMobile(newValue);
                    break;
                case "street":
                    profile.setStreet(newValue);
                    break;
                case "locale":
                    profile.setLocale(newValue);
                    break;
                case "postalCode":
                    profile.setPostalCode(newValue);
                    break;
            }
        }
    }*/

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
