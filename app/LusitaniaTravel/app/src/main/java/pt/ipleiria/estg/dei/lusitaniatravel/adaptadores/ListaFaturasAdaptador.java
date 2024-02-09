package pt.ipleiria.estg.dei.lusitaniatravel.adaptadores;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesFaturaFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.DetalhesReservaFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.R;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Fatura;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.Reserva;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.SingletonGestorLusitaniaTravel;

public class ListaFaturasAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Fatura> faturas;

    public ListaFaturasAdaptador(Context context, ArrayList<Fatura> faturas) {
        this.context = context;
        this.faturas = faturas;
    }

    @Override
    public int getCount() {
        return faturas.size();
    }

    @Override
    public Object getItem(int position) {
        return faturas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return faturas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_lista_fatura, null);

        // Otimização
        ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderLista(convertView, position);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(faturas.get(position));

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvFaturaId, tvTotalFatura, tvReserva, tvData;
        private Button btnDetalhes;
        private ImageButton btnDownload;

        public ViewHolderLista(View view, final int position) {
            tvFaturaId = view.findViewById(R.id.tvFaturaId);
            tvTotalFatura = view.findViewById(R.id.tvTotalFatura);
            tvReserva = view.findViewById(R.id.tvReserva);
            tvData = view.findViewById(R.id.tvData);
            btnDetalhes = view.findViewById(R.id.btnDetalhes);
            btnDownload = view.findViewById(R.id.imageButtonDownload);

            btnDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (faturas != null && faturas.size() > position) {
                        Fatura faturaClicada = faturas.get(position);

                        int faturaId = faturaClicada.getId();

                        DetalhesFaturaFragment detalhesFaturaFragment = new DetalhesFaturaFragment();
                        detalhesFaturaFragment.setFaturaId(faturaId); // Define os argumentos do fragmento

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.fragmentContainer, detalhesFaturaFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });

            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (faturas != null && faturas.size() > position) {
                        Fatura faturaClicada = faturas.get(position);
                        int faturaId = faturaClicada.getId();

                        SingletonGestorLusitaniaTravel.getInstance(context).getDownloadAPI(faturaId, context);
                    }
                }
            });
        }

        public void update(Fatura fatura) {
            tvFaturaId.setText("" + fatura.getId());
            // Formatando os valores da fatura
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String totalFaturaFormatado = decimalFormat.format(fatura.getTotalF());

            tvTotalFatura.setText("" + totalFaturaFormatado + "€");
            tvReserva.setText("" + fatura.getReservaId());
            tvData.setText("" + fatura.getData());
        }
    }
}
