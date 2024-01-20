package pt.ipleiria.estg.dei.lusitaniatravel.modelos;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pt.ipleiria.estg.dei.lusitaniatravel.CarrinhoFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.MainActivity;
import pt.ipleiria.estg.dei.lusitaniatravel.VerificarDisponibilidadeFragment;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.CarrinhosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FaturaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FaturasListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FavoritoListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FavoritosListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedorListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.FornecedoresListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.LoginListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservaListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ReservasListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.SignUpListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.UserListener;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.FaturaJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.FavoritoJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.FornecedorJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.LoginJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.modelos.User;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.ReservaJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.SignUpJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.UserJsonParser;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.CarrinhoJsonParser;

public class SingletonGestorLusitaniaTravel {
    private ArrayList<Fornecedor> fornecedores;
    private ArrayList<Reserva> reservas;
    private ArrayList<Profile> profiles;
    private static SingletonGestorLusitaniaTravel instance = null;
    private LusitaniaTravelBDHelper lusitaniaTravelBDHelper = null;
    private String username;
    private String password;
    private EditText editTextSearch;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/user/login/%s/%s";
    private static final String mUrlAPIRegister = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/user/register";
    private static final String mUrlAPIFornecedores = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fornecedor/alojamentos";
    private static final String mUrlAPIFornecedor = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fornecedor/alojamento/%d";
    private static final String mUrlAPILocalizacao = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fornecedor/localizacao/%s";
    private static final String mUrlAPIDefinicoes = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/user/mostrar/%s";
    private static final String mUrlAPIReservas = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/reserva/mostrar/%s";
    private static final String mUrlAPIFaturas = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fatura/ver/%s";
    private static final String mUrlAPIFavoritos = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fornecedor/favoritos";
    private static final String mUrlAPIAdicionarFavorito = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fornecedor/adicionarfavorito/";
    private static final String mUrlAPIRemoverFavorito = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/fornecedor/removerfavorito/";
    private static final String mUrlAPICarrinho = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/carrinho/mostrar";
    private static final String mUrlAPIAdicionarCarrinho = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/carrinho/adicionarcarrinho/";
    private static final String mUrlAPIRemoverCarrinho = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/carrinho/removercarrinho/";
    private static final String mUrlAPIVerificarReserva = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/reserva/verificar/";
    private static final String mUrlAPIFinalizarCarrinho = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api/carrinho/finalizarcarrinho/";
    private FornecedoresListener fornecedoresListener;
    private FornecedorListener fornecedorListener;
    private ReservasListener reservasListener;
    private ReservaListener reservaListener;
    private FaturasListener faturasListener;
    private FaturaListener faturaListener;
    private LoginListener loginListener;
    private SignUpListener signUpListener;
    private UserListener userListener;
    private FavoritosListener favoritosListener;
    private FavoritoListener favoritoListener;
    private CarrinhosListener carrinhosListener;
    private CarrinhoListener carrinhoListener;

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

    //region GETS E SETTERS

    public void setFornecedoresListener(FornecedoresListener fornecedoresListener) {
        this.fornecedoresListener = fornecedoresListener;
    }

    public void setFornecedorListener(FornecedorListener fornecedorListener) {
        this.fornecedorListener = fornecedorListener;
    }

    public void setReservasListener(ReservasListener reservasListener) {
        this.reservasListener = reservasListener;
    }

    public void setReservaListener(ReservaListener reservaListener) {
        this.reservaListener = reservaListener;
    }


    public void setFaturasListener(FaturasListener faturasListener) {
        this.faturasListener = faturasListener;
    }

    public void setFaturaListener(FaturaListener faturaListener) {
        this.faturaListener = faturaListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setSignUpListener(SignUpListener signupListener) {
        this.signUpListener = signupListener;
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

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    public void setFavoritosListener(FavoritosListener favoritosListener) {
        this.favoritosListener = favoritosListener;
    }

    public void setFavoritoListener(FavoritoListener favoritoListener) {
        this.favoritoListener = favoritoListener;
    }

    public void setCarrinhosListener(CarrinhosListener carrinhosListener) {
        this.carrinhosListener = carrinhosListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener) {
        this.carrinhoListener = carrinhoListener;
    }

    //endregion

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

    public void registerAPI(String username, String password, String email,
                            String name, String mobile, String street, String locale, String postalCode,
                            Context context) {
        if (!SignUpJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            // Construir o corpo da solicitação POST
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", username);
                jsonBody.put("password", password);
                jsonBody.put("email", email);
                jsonBody.put("name", name);
                jsonBody.put("mobile", mobile);
                jsonBody.put("street", street);
                jsonBody.put("locale", locale);
                jsonBody.put("postalCode", postalCode);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, mUrlAPIRegister, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Executar no thread principal
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Analisar a resposta usando SignUpJsonParser
                                    User user = SignUpJsonParser.parserJsonRegister(response.toString());
                                    Log.d("SignUpResponse", "Raw Response: " + response);

                                    // Notificar o ouvinte de registro
                                    if (signUpListener != null) {
                                        signUpListener.onUpdateSignUp(user);
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
                                    Log.e("RegisterError", "Error: " + error.getMessage());
                                    Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    // Adicione as credenciais ao cabeçalho (username e password)
                    String auth = "Basic " + Base64.encodeToString("lusitaniatravel:admin123".getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            volleyQueue.add(req);
        }
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

    public void getFornecedorAPI(int id, FornecedorListener listener, Context context) {
        if (!FornecedorJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            // Ajuste da URL com o ID do fornecedor
            String url = String.format(Locale.getDefault(), mUrlAPIFornecedor, id);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            /*Fornecedor fornecedor = FornecedorJsonParser.parserJsonFornecedor(response);
                            if (listener != null)
                                listener.onRefreshDetalhes(fornecedor);*/
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

    public void getAllReservasAPI(ReservasListener listener, Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();

            ArrayList<Reserva> reservasBD = getReservasBD();

            if (listener != null) {
                listener.onRefreshListaReservas(reservasBD);
            }

        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String url = String.format(mUrlAPIReservas, username);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray reservasArray = response.getJSONArray("reservas");
                                reservas = ReservaJsonParser.parserJsonReservas(reservasArray);

                                // Notificar o listener de reservas
                                if (listener != null) {
                                    listener.onRefreshListaReservas(reservas);
                                }

                                // Adicionar reservas à BD local
                                adicionarAllReservasBD(reservas);

                            } catch (JSONException e) {
                                Toast.makeText(context, "Erro ao processar resposta do servidor", Toast.LENGTH_SHORT).show();
                            }
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

    public void getAllFaturasAPI(FaturasListener listener, Context context) {
        if (!FaturaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String url = String.format(mUrlAPIFaturas, username);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray faturasArray = response.getJSONArray("faturas");
                                ArrayList<Fatura> faturas = FaturaJsonParser.parserJsonFaturas(faturasArray);

                                // Log the parsed Faturas
                                for (Fatura fatura : faturas) {
                                    Log.d("FaturasAPI", "Parsed Fatura: " + fatura.toString());
                                }

                                // Notificar o listener de faturas
                                if (listener != null) {
                                    listener.onRefreshListaFaturas(faturas);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, "Erro ao processar resposta do servidor", Toast.LENGTH_SHORT).show();
                            }
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


    public void getUserDefinicoesAPI(Context context) {
        if (!LoginJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String url = String.format(mUrlAPIDefinicoes, getUsername());

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Convert JSONObject to String
                            String jsonResponse = response.toString();

                            // Executar no thread principal
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Parse do JSON para obter as definições do user
                                    User user = UserJsonParser.parserJsonUser(jsonResponse);

                                    // Chamada ao método onUpdateDefinicoes com o objeto UserDefinicoes
                                    if (userListener != null) {
                                        userListener.onRefreshDetalhes(user);
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
                                    Log.e("DefinicoesError", "Error: " + error.getMessage());
                                    Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = getUsername() + ":" + getPassword();
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    return headers;
                }
            };

            volleyQueue.add(req);
        }
    }


    public void getLocalizacaoFornecedoresAPI(FornecedoresListener listener, Context context, String localizacao) {
        if (!FornecedorJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            // Inclua o texto da EditText na URL
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
                            if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                                // Localização não existe, exibir Toast
                                Toast.makeText(context, "Localização não encontrada", Toast.LENGTH_SHORT).show();
                            } else {
                                // Outro erro, exibir mensagem padrão
                                Toast.makeText(context, "Erro na requisição", Toast.LENGTH_SHORT).show();
                            }
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

    public void getAllFavoritosAPI(FavoritosListener listener, Context context) {
        if (!FavoritoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlAPIFavoritos, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray favoritosArray = response.getJSONArray("favoritos");
                                fornecedores = FavoritoJsonParser.parserJsonFavoritos(favoritosArray);
                                if (listener != null) {
                                    listener.onRefreshListaFornecedores(fornecedores);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, "Erro ao processar resposta do servidor", Toast.LENGTH_SHORT).show();
                            }
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

    public void adicionarFavoritoAPI(final int fornecedorId, final Context context, final FavoritoListener listener) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIAdicionarFavorito + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (listener != null)
                        listener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
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

    public void removerFavoritoAPI(final int fornecedorId, final Context context, final FavoritoListener listener) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIRemoverFavorito + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (listener != null)
                        listener.onRefreshDetalhes(MainActivity.DELETE);
                }
            }, new Response.ErrorListener() {
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


    public void getAllCarrinhoAPI(CarrinhosListener listener, Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlAPICarrinho, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<Carrinho> carrinhos = CarrinhoJsonParser.parserJsonCarrinhos(response);
                            if (listener != null) {
                                listener.onRefreshListaCarrinho(new ArrayList<>(carrinhos));
                            }
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

    public void adicionarCarrinhoAPI(final Carrinho carrinho, final int fornecedorId, final Context context, final CarrinhoListener listener) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIAdicionarCarrinho + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (listener != null)
                        listener.onRefreshDetalhes(MainActivity.ADD);
                }
            }, new Response.ErrorListener() {
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

    public void removerCarrinhoAPI(final Carrinho carrinho, final int fornecedorId, final Context context, final CarrinhoListener listener) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIRemoverCarrinho + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (listener != null)
                        listener.onRefreshDetalhes(MainActivity.DELETE);
                }
            }, new Response.ErrorListener() {
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

    public void verificarReservaAPI(final String checkin, final String checkout, final int numeroClientes, final int numeroQuartos,
            final String tipoQuarto, final int numeroCamas, final Context context, final int carrinhoId, final ReservaListener listener) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIVerificarReserva + carrinhoId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Notificar o listener com o resultado
                            if (listener != null) {
                                listener.onRefreshDetalhes(VerificarDisponibilidadeFragment.ADD);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Erro na solicitação HTTP", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("checkin", checkin);
                    params.put("checkout", checkout);
                    params.put("numeroclientes", String.valueOf(numeroClientes));
                    params.put("numeroquartos", String.valueOf(numeroQuartos));
                    params.put("tipoquarto", tipoQuarto);
                    params.put("numerocamas", String.valueOf(numeroCamas));

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = SingletonGestorLusitaniaTravel.getInstance(context).getUsername() +
                            ":" + SingletonGestorLusitaniaTravel.getInstance(context).getPassword();
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
