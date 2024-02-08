package pt.ipleiria.estg.dei.lusitaniatravel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
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
    FragmentManager fragmentManager;
    private User user;

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

            // Passar o objeto User para o método showEditProfileDialog
            ImageButton editarButton = view.findViewById(R.id.imageButtonEdit);
            editarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ao clicar no botão de edição, mostrar uma caixa de diálogo com o layout personalizado
                    showEditProfileDialog(user);
                }
            });
        }
    }

    private void showEditProfileDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_editar_perfil, null);

        // Criar um ScrollView programaticamente
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollView.addView(dialogView);

        builder.setView(scrollView);

        // Referenciar os EditTexts do layout
        EditText editTextUsername = dialogView.findViewById(R.id.editTextFieldUsername);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextFieldEmail);
        EditText editTextNome = dialogView.findViewById(R.id.editTextFieldNome);
        EditText editTextTelemovel = dialogView.findViewById(R.id.editTextFieldTelemovel);
        EditText editTextRua = dialogView.findViewById(R.id.editTextFieldRua);
        EditText editTextLocalidade = dialogView.findViewById(R.id.editTextFieldLocalidade);
        EditText editTextCodPostal = dialogView.findViewById(R.id.editTextFieldCodPostal);

        // Preencher os EditTexts com os dados do usuário
        editTextUsername.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());
        if (user.getProfile() != null) {
            Profile profile = user.getProfile();
            editTextNome.setText(profile.getName());
            editTextTelemovel.setText(profile.getMobile());
            editTextRua.setText(profile.getStreet());
            editTextLocalidade.setText(profile.getLocale());
            editTextCodPostal.setText(profile.getPostalCode());
        }

        // Configurar os botões positivo e negativo da caixa de diálogo
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obter os novos valores dos EditTexts
                String novoUsername = editTextUsername.getText().toString();
                String novoEmail = editTextEmail.getText().toString();
                String novoNome = editTextNome.getText().toString();
                String novoTelemovel = editTextTelemovel.getText().toString();
                String novaRua = editTextRua.getText().toString();
                String novaLocalidade = editTextLocalidade.getText().toString();
                String novoCodPostal = editTextCodPostal.getText().toString();

                // Chamar a função do Singleton para alterar o user com os novos valores
                SingletonGestorLusitaniaTravel.getInstance(getContext()).alterarUserAPI(novoUsername, novoEmail, novoNome, novoTelemovel, novaRua, novaLocalidade, novoCodPostal,getContext());

                // Fechar a caixa de diálogo
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Fechar a caixa de diálogo sem fazer alterações
                dialog.dismiss();
            }
        });

        // Definir a mensagem do diálogo como "Editar Perfil"
        builder.setMessage("Editar Perfil");

        // Configurar a mensagem para estar centralizada
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                TextView messageTextView = alertDialog.findViewById(android.R.id.message);
                if (messageTextView != null) {
                    messageTextView.setGravity(Gravity.CENTER);
                }
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.blue)); // Altere a cor conforme necessário
                    negativeButton.setTextColor(getResources().getColor(R.color.blue)); // Altere a cor conforme necessário
                }
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                AlertDialog alertDialog = (AlertDialog) dialogInterface;
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (positiveButton != null && negativeButton != null) {
                    positiveButton.setTextColor(getResources().getColor(R.color.blue)); // Altere a cor conforme necessário
                    negativeButton.setTextColor(getResources().getColor(R.color.blue)); // Altere a cor conforme necessário
                }
            }
        });

        // Mostrar a caixa de diálogo
        dialog.show();
    }

    // Implement the missing method from UserListener
    @Override
    public void onRefreshDetalhes(User user) {
        View view = getView();
        if (view != null && user != null) {
            updateUserDetails(user, view);
        } else {
            // Additional handling, e.g., show an error message to the user
            Toast.makeText(requireContext(), "Erro ao carregar o User", Toast.LENGTH_SHORT).show();
        }

        atualizarUser();
    }

    private void atualizarUser() {
        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(getContext());
        singleton.getUserDefinicoesAPI(getContext());
    }


}
