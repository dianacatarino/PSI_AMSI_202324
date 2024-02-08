package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.lusitaniatravel.CarrinhoFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.VerificarDisponibilidadeFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Carrinho;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fornecedor;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class ListaCarrinhoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Carrinho> carrinhos;
    private String fornecedorNome;
    private CarrinhoFragment fragmento;

    public ListaCarrinhoAdaptador(Context context, ArrayList<Carrinho> carrinhos) {
        this.context = context;
        this.carrinhos = carrinhos;
    }

    public void atualizarListaCarrinho(ArrayList<Carrinho> listaCarrinho) {
        this.carrinhos = listaCarrinho;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return carrinhos.size();
    }

    @Override
    public Object getItem(int position) {
        return carrinhos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carrinhos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_carrinho, null);

        // Otimização
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView, position);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(carrinhos.get(position), context);

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvQuantidade, tvPreco, tvSubtotal, tvReservaId, tvEstado;
        private ImageButton btnRemoverCarrinho;
        private Button btnVerificarDisponibilidade;

        public ViewHolderLista(View view, final int position) {
            tvQuantidade = view.findViewById(R.id.tvQuantidade);
            tvPreco = view.findViewById(R.id.tvPreco);
            tvSubtotal = view.findViewById(R.id.tvSubtotal);
            tvReservaId = view.findViewById(R.id.tvReserva);
            tvEstado = view.findViewById(R.id.tvEstado);
            btnRemoverCarrinho = view.findViewById(R.id.btnRemoverCarrinho);
            btnVerificarDisponibilidade = view.findViewById(R.id.btnVerificarDisponibilidade);

            btnRemoverCarrinho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (carrinhos != null && carrinhos.size() > position) {
                        Carrinho carrinhoClicado = carrinhos.get(position);
                        fornecedorNome = carrinhoClicado.getNomeFornecedor();


                        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(context);

                        singleton.setFornecedoresListener(new FornecedoresListener() {
                            @Override
                            public void onRefreshListaFornecedores(ArrayList<Fornecedor> listaFornecedores) {
                                // Find the fornecedor with the matching name
                                Fornecedor fornecedor = findFornecedorByName(listaFornecedores, fornecedorNome);

                                if (fornecedor != null) {
                                    // Get the fornecedor ID
                                    int fornecedorId = fornecedor.getId();

                                    // Use the fornecedorId in your removerCarrinhoAPI method
                                    SingletonGestorLusitaniaTravel.getInstance(context).removerCarrinhoAPI(fornecedorId, context);

                                    Toast.makeText(context, "Item removido ao carrinho", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Fornecedor não encontrado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        // Call getAllFornecedoresAPI to trigger the listener
                        singleton.getAllFornecedoresAPI(context);
                    }
                }
            });
            btnVerificarDisponibilidade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (carrinhos != null && carrinhos.size() > position) {
                        Carrinho carrinhoClicado = carrinhos.get(position);
                        int reservaId = carrinhoClicado.getReservaId();

                        SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(context);

                        singleton.setCarrinhosListener(new CarrinhosListener() {
                            @Override
                            public void onRefreshListaCarrinho(ArrayList<Carrinho> listaCarrinhos) {
                                Carrinho selectedCarrinho = null;
                                for (Carrinho carrinho : carrinhos) {
                                    if (carrinho.getReservaId() == reservaId) {
                                        selectedCarrinho = carrinho;
                                        break;
                                    }
                                }

                                if (selectedCarrinho != null) {
                                    // Create a Bundle to pass data to the fragment
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("reservaId", reservaId);

                                    // Create an instance of VerificarDisponibilidadeFragment
                                    VerificarDisponibilidadeFragment verificarDisponibilidadeFragment = new VerificarDisponibilidadeFragment();

                                    // Configuração do carrinhoId
                                    verificarDisponibilidadeFragment.setReservaId(reservaId);

                                    FragmentActivity activity = (FragmentActivity) context;
                                    if (activity != null) {
                                        activity.setTitle("Verificar");
                                    }

                                    // Inicia a transação para substituir o Fragment
                                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.fragmentContainer, verificarDisponibilidadeFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            }
                        });

                        singleton.getAllCarrinhoAPI(context);
                    }
                }
            });
        }


        private Fornecedor findFornecedorByName(List<Fornecedor> fornecedores, String fornecedorNome) {
            for (Fornecedor fornecedor : fornecedores) {
                if (fornecedor.getNomeAlojamento().equals(fornecedorNome)) {
                    return fornecedor;
                }
            }
            return null;
        }


        public void update(Carrinho carrinho, Context context) {
            tvQuantidade.setText("" + carrinho.getQuantidade());

            // Formatando o preço e subtotal
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String precoFormatado = decimalFormat.format(carrinho.getPreco());
            String subtotalFormatado = decimalFormat.format(carrinho.getSubtotal());

            tvPreco.setText("" + precoFormatado + "€");
            tvSubtotal.setText("" + subtotalFormatado + "€");

            tvReservaId.setText("" + carrinho.getReservaId());
            tvEstado.setText("" + carrinho.getEstado());
        }
    }
}
