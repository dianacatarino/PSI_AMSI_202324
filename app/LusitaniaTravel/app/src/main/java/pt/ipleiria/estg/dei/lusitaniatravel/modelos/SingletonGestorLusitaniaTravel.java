package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedorListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.LoginListener;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.FornecedorJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.LoginJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;

public class SingletonGestorLusitaniaTravel {
    private ArrayList<Fornecedor> fornecedores;
    private ArrayList<Reserva> reservas;
    private ArrayList<Profile> profiles;
    private static SingletonGestorLusitaniaTravel instance = null;
    private LusitaniaTravelBDHelper lusitaniaTravelBDHelper = null;
    private String username;
    private String password;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = "http://10.0.2.2/LusitaniaTravelAPI/backend/web/api/user/login/%s/%s";
    private static final String mUrlAPIFornecedores = "http://10.0.2.2/LusitaniaTravelAPI/backend/web/api/fornecedor/alojamentos";
    private static final String mUrlAPILocalizacao = "http://10.0.2.2/LusitaniaTravelAPI/backend/web/api/fornecedor/localizacao/%s";
    private FornecedoresListener fornecedoresListener;
    private FornecedorListener fornecedorListener;

    private LoginListener loginListener;

    // Método que garante apenas uma instância do Singleton
    public static synchronized SingletonGestorLusitaniaTravel getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorLusitaniaTravel(context);
        }
        if (volleyQueue == null) {
            volleyQueue = Volley.newRequestQueue(context);
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

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
    public void loginAPI(String username, String password, Context context) {
        if (!LoginJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String url = String.format(mUrlAPILogin, username, password);

            StringRequest req = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Executar no thread principal
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    User user = LoginJsonParser.parserJsonLogin(response);

                                    // Chamada ao método onUpdateLogin com o objeto User
                                    if (loginListener != null) {
                                        loginListener.onUpdateLogin(user);
                                    }
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Executar no thread principal
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("LoginError", "Error: " + error.getMessage());
                                    Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
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

    // Método auxiliar para executar no thread principal
    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }


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
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

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

    // Método para obter fornecedores com base na localização
    public void getLocalizacaoFornecedoresAPI(String localizacao, FornecedoresListener listener, Context context) {
        if (!FornecedorJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String url = String.format(mUrlAPILocalizacao, localizacao);
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
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
