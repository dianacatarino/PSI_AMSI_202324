package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedorListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.FornecedorJsonParser;

public class SingletonGestorLusitaniaTravel {
    private ArrayList<Fornecedor> fornecedores;
    private ArrayList<Reserva> reservas;
    private ArrayList<Profile> profiles;
    private static SingletonGestorLusitaniaTravel instance = null;
    private LusitaniaTravelBDHelper lusitaniaTravelBDHelper = null;
    private String username = "diana";
    private String password = "diana123";



    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPIFornecedores = "http://localhost/LusitaniaTravelAPI/backend/web/api/fornecedor";
    private static final String mUrlAPILogin = "http://localhost/LusitaniaTravelAPI/backend/web/api";
    private static final String TOKEN = "j8th_N_UlbaTSb2sCmagTNTP-lD28syt";
    private FornecedoresListener fornecedoresListener;
    private FornecedorListener fornecedorListener;

    // Método que garante apenas uma instância do Singleton
    public static synchronized SingletonGestorLusitaniaTravel getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorLusitaniaTravel(context);
        }
        return instance;
    }

    private SingletonGestorLusitaniaTravel(Context context) {
        lusitaniaTravelBDHelper = new LusitaniaTravelBDHelper(context);
    }

    public LusitaniaTravelBDHelper getLusitaniaTravelBDHelper() {
        return lusitaniaTravelBDHelper;
    }

    public void setFornecedoresListener(FornecedoresListener fornecedoresListener) {
        this.fornecedoresListener = fornecedoresListener;
    }

    public void setFornecedorListener(FornecedorListener fornecedorListener) {
        this.fornecedorListener = fornecedorListener;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //region PEDIDOS BDLOCAL

    public ArrayList<Reserva> getReservasBD() {
        reservas = lusitaniaTravelBDHelper.getAllReservasBD();
        return new ArrayList<>(reservas);
    }

    public void adicionarAllReservasBD(ArrayList<Reserva> reservas) {
        // Remover todas as reservas da BD -> LusitaniaTravelBDHelper e depois adicionar todas as reservas à BD
        lusitaniaTravelBDHelper.removerAllReservasBD();
        for (Reserva reserva : reservas)
            adicionarReservaBD(reserva);
    }

    public Reserva getReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id)
                return r;
        }
        return null;
    }

    public void adicionarReservaBD(Reserva reserva) {
        lusitaniaTravelBDHelper.adicionarReservaBD(reserva);
    }

    public void editarReservaBD(Reserva reserva) {
        if (reserva != null)
            lusitaniaTravelBDHelper.editarReservaBD(reserva);
    }

    public void removerReservaBD(int idReserva) {
        lusitaniaTravelBDHelper.removerReservaBD(idReserva);
    }

    public ArrayList<Profile> getProfilesBD() {
        profiles = lusitaniaTravelBDHelper.getAllProfilesBD();
        return new ArrayList<>(profiles);
    }

    public void adicionarAllProfilesBD(ArrayList<Profile> profiles) {
        // Remover todos os perfis da BD -> LusitaniaTravelBDHelper e depois adicionar todos os perfis à BD
        lusitaniaTravelBDHelper.removerAllProfilesBD();
        for (Profile profile : profiles)
            adicionarProfileBD(profile);
    }

    public Profile getProfile(int id) {
        for (Profile p : profiles) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public void adicionarProfileBD(Profile profile) {
        lusitaniaTravelBDHelper.adicionarProfileBD(profile);
    }

    public void editarProfileBD(Profile profile) {
        if (profile != null)
            lusitaniaTravelBDHelper.editarProfileBD(profile);
    }

    public void removerProfileBD(int idProfile) {
        lusitaniaTravelBDHelper.removerProfileBD(idProfile);
    }
    //endregion

    //region PEDIDOS API
    /*public void adicionarFornecedorAPI(final Fornecedor fornecedor, final Context context){
        if(!FornecedorJsonParser.isConnectionInternet(context)){
            Toast.makeText(context,"Não tem ligação à Internet",Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIFornecedores, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Adicionar ao BD local
                    adicionarFornecedorBD(FornecedorJsonParser.parserJsonFornecedor(response));
                    if(fornecedorListener != null)
                        fornecedorListener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("token", TOKEN);
                    params.put("responsavel", fornecedor.getResponsavel());
                    params.put("tipo", fornecedor.getTipo());
                    params.put("nome_alojamento", fornecedor.getNomeAlojamento());
                    params.put("localizacao_alojamento", fornecedor.getLocalizacaoAlojamento());
                    params.put("acomodacoes_alojamento", fornecedor.getAcomodacoesAlojamento());
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }*/

    public void getAllFornecedoresAPI(FornecedoresListener listener, Context context) {
        if (!FornecedorJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIFornecedores, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            fornecedores = FornecedorJsonParser.parserJsonFornecedores(response);
                            if (listener != null)
                                listener.onRefreshListaFornecedores(fornecedores);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = username + ":" + password;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }

    /*public void removerFornecedorAPI(final Fornecedor fornecedor, final Context context){
        if(!FornecedorJsonParser.isConnectionInternet(context)){
            Toast.makeText(context,"Não tem ligação à Internet",Toast.LENGTH_SHORT).show();
        }
        else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIFornecedores + "/" + fornecedor.getId(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Remover do BD local
                    removerFornecedorBD(fornecedor.getId());

                    if(fornecedorListener != null)
                        fornecedorListener.onRefreshDetalhes(MainActivity.DELETE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

    public void editarFornecedorAPI(final Fornecedor fornecedor, final Context context){
    if(!FornecedorJsonParser.isConnectionInternet(context)){
        Toast.makeText(context,"Não tem ligação à Internet",Toast.LENGTH_SHORT).show();
    }
    else{
        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIFornecedores + "/" + fornecedor.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Editar no BD local
                editarFornecedorBD(fornecedor);
                if(fornecedorListener != null)
                    fornecedorListener.onRefreshDetalhes(MainActivity.EDIT);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("token", TOKEN);
                params.put("responsavel", fornecedor.getResponsavel());
                params.put("tipo", fornecedor.getTipo());
                params.put("nome_alojamento", fornecedor.getNomeAlojamento());
                params.put("localizacao_alojamento", fornecedor.getLocalizacaoAlojamento());
                params.put("acomodacoes_alojamento", fornecedor.getAcomodacoesAlojamento());
                return params;
            }
        };
        volleyQueue.add(req);
    }*/
    //endregion
}
