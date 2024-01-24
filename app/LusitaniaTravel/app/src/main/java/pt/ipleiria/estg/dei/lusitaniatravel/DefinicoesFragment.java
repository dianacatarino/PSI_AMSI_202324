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
        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(getContext());

        // Set the listener
        singleton.setUserListener(this);

        // Now, you can call the getUserDefinicoesAPI method
        singleton.getUserDefinicoesAPI(getContext());

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

            // Add click listeners for editing each field
            textViewProfileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditProfileFieldDialog("Nome", profile.getName(), textViewProfileName, user, "name");
                }
            });

            textViewProfileMobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditProfileFieldDialog("Telemóvel", profile.getMobile(), textViewProfileMobile, user, "mobile");
                }
            });

            textViewProfileStreet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditProfileFieldDialog("Rua", profile.getStreet(), textViewProfileStreet, user, "street");
                }
            });

            textViewProfileLocale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditProfileFieldDialog("Localidade", profile.getLocale(), textViewProfileLocale, user, "locale");
                }
            });

            textViewProfilePostalCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditProfileFieldDialog("Código Postal", profile.getPostalCode(), textViewProfilePostalCode, user, "postalCode");
                }
            });
        }
    }
}

    private void openEditProfileFieldDialog(final String fieldLabel, String fieldValue, final TextView textView, final User user, final String fieldKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile_field, null);
        builder.setView(dialogView);

        // Adicione todas as EditText do layout
        final EditText editTextFieldUsername = dialogView.findViewById(R.id.editTextFieldUsername);
        final EditText editTextFieldEmail = dialogView.findViewById(R.id.editTextFieldEmail);
        final EditText editTextFieldNome = dialogView.findViewById(R.id.editTextFieldNome);
        final EditText editTextFieldTelemovel = dialogView.findViewById(R.id.editTextFieldTelemovel);
        final EditText editTextFieldRua = dialogView.findViewById(R.id.editTextFieldRua);
        final EditText editTextFieldLocalidade = dialogView.findViewById(R.id.editTextFieldLocalidade);
        final EditText editTextFieldCodPostal = dialogView.findViewById(R.id.editTextFieldCodPostal);

        // Defina os valores iniciais
        editTextFieldUsername.setText(fieldValue);
        editTextFieldEmail.setText(fieldValue);
        editTextFieldNome.setText(fieldValue);
        editTextFieldTelemovel.setText(fieldValue);
        editTextFieldRua.setText(fieldValue);
        editTextFieldLocalidade.setText(fieldValue);
        editTextFieldCodPostal.setText(fieldValue);

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtenha os valores inseridos pelo usuário
                String newFieldValueUsername = editTextFieldUsername.getText().toString();
                String newFieldValueEmail = editTextFieldEmail.getText().toString();
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
                textView.setText(fieldLabel + ": " + newFieldValueUsername);
                textView.setText(fieldLabel + ": " + newFieldValueEmail);
                textView.setText(fieldLabel + ": " + newFieldValueNome);
                textView.setText(fieldLabel + ": " + newFieldValueTelemovel);
                textView.setText(fieldLabel + ": " + newFieldValueRua);
                textView.setText(fieldLabel + ": " + newFieldValueLocalidade);
                textView.setText(fieldLabel + ": " + newFieldValueCodPostal);
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
