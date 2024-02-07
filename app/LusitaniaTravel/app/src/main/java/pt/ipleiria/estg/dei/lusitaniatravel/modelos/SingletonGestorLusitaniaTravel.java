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
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ComentarioListener;
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.ComentariosListener;
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
import pt.ipleiria.estg.dei.lusitaniatravel.listeners.VerificarListener;
import pt.ipleiria.estg.dei.lusitaniatravel.utils.ComentarioJsonParser;
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
    private ArrayList<Comentario> comentarios;
    private static SingletonGestorLusitaniaTravel instance = null;
    private LusitaniaTravelBDHelper lusitaniaTravelBDHelper = null;
    private String username;
    private String password;
    private EditText editTextSearch;
    private User user;
    private static RequestQueue volleyQueue = null;
    private static final String BASE_URL = "http://172.22.21.204/LusitaniaTravelAPI/backend/web/api";
    private static final String mUrlAPILogin = BASE_URL + "/user/login/%s/%s";
    private static final String mUrlAPIRegister = BASE_URL + "/user/register";
    private static final String mUrlAPIFornecedores = BASE_URL + "/fornecedor/alojamentos";
    private static final String mUrlAPIFornecedor = BASE_URL + "/fornecedor/alojamento/";
    private static final String mUrlAPILocalizacao = BASE_URL + "/fornecedor/localizacao/%s";
    private static final String mUrlAPIDefinicoes = BASE_URL + "/user/mostrar/%s";
    private static final String mUrlAPIAlterarUser = BASE_URL + "/user/atualizar";
    private static final String mUrlAPIReservas = BASE_URL + "/reserva/mostrar/%s";
    private static final String mUrlAPIReserva = BASE_URL + "/reserva/detalhes/";
    private static final String mUrlAPIFaturas = BASE_URL + "/fatura/ver/%s";
    private static final String mUrlAPIFatura = BASE_URL + "/fatura/detalhes/";
    private static final String mUrlAPIFavoritos = BASE_URL + "/fornecedor/favoritos";
    private static final String mUrlAPIAdicionarFavorito = BASE_URL + "/fornecedor/adicionarfavorito/";
    private static final String mUrlAPIRemoverFavorito = BASE_URL + "/fornecedor/removerfavorito/";
    private static final String mUrlAPIDetalhesFavorito = BASE_URL + "/fornecedor/detalhesfavorito/";
    private static final String mUrlAPICarrinho = BASE_URL + "/carrinho/mostrar";
    private static final String mUrlAPIAdicionarCarrinho = BASE_URL + "/carrinho/adicionarcarrinho/";
    private static final String mUrlAPIRemoverCarrinho = BASE_URL + "/carrinho/removercarrinho/";
    private static final String mUrlAPIVerificarReserva = BASE_URL + "/reserva/verificar/";
    private static final String mUrlAPIFinalizarCarrinho = BASE_URL + "/carrinho/finalizarcarrinho/";
    private static final String mUrlAPIComentarios = BASE_URL + "/fornecedor/comentarios";
    private static final String mUrlAPIComentario = BASE_URL + "/fornecedor/comentario/";
    private static final String mUrlAPIComentariosAlojamento = BASE_URL + "/fornecedor/comentariosalojamento/";
    private static final String mUrlAPIAdicionarComentario = BASE_URL + "/fornecedor/adicionarcomentario/";
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
    private VerificarListener verificarListener;
    private ComentariosListener comentariosListener;
    private ComentarioListener comentarioListener;

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

    public void setVerificarListener(VerificarListener verificarListener) {
        this.verificarListener = verificarListener;
    }

    public void setComentariosListener(ComentariosListener comentariosListener) {
        this.comentariosListener = comentariosListener;
    }

    public void setComentarioListener(ComentarioListener comentarioListener) {
        this.comentarioListener = comentarioListener;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
            JSONObject userObject = new JSONObject();
            JSONObject profileObject = new JSONObject();
            JSONObject jsonBody = new JSONObject();
            try {
                // User object
                userObject.put("username", username);
                userObject.put("email", email);
                userObject.put("password", password);

                // Profile object
                profileObject.put("name", name);
                profileObject.put("mobile", mobile);
                profileObject.put("street", street);
                profileObject.put("locale", locale);
                profileObject.put("postalCode", postalCode);

                // Combine user and profile objects into main jsonBody
                jsonBody.put("user", userObject);
                jsonBody.put("profile", profileObject);

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


    public void getAllFornecedoresAPI(final Context context) {
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
                            if (fornecedoresListener != null)
                                fornecedoresListener.onRefreshListaFornecedores(fornecedores);
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

    public void getFornecedorAPI(int id, final Context context) {
        if (!FornecedorJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIFornecedor + id;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Fornecedor fornecedor = FornecedorJsonParser.parserJsonFornecedor(response);
                                    if (fornecedorListener != null)
                                        fornecedorListener.onRefreshDetalhes(fornecedor);
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void getAllReservasAPI(final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();

            ArrayList<Reserva> reservasBD = getReservasBD();

            if (reservasListener != null) {
                reservasListener.onRefreshListaReservas(reservasBD);
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

                                // Verificar se não há reservas
                                if (reservasArray.length() == 0) {
                                    // Exibir a mensagem do servidor em um toast
                                    String mensagem = response.getString("message");
                                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Se houver reservas, processar normalmente
                                    reservas = ReservaJsonParser.parserJsonReservas(reservasArray);

                                    // Notificar o listener de reservas
                                    if (reservasListener != null) {
                                        reservasListener.onRefreshListaReservas(reservas);
                                    }

                                    // Adicionar reservas à BD local
                                    adicionarAllReservasBD(reservas);
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

    public void getReservaAPI(int id, final Context context) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();

            Reserva reservaBD = getReserva(id);

            if (reservaListener != null) {
                reservaListener.onRefreshDetalhes(reservaBD);
            }
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIReserva + id;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Reserva reserva = ReservaJsonParser.parserJsonReserva(response);
                                    if (reservaListener != null) {
                                        reservaListener.onRefreshDetalhes(reserva);
                                    }

                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void getAllFaturasAPI(final Context context) {
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

                                // Verificar se não há faturas
                                if (faturasArray.length() == 0) {
                                    // Exibir a mensagem do servidor em um Toast
                                    String mensagem = response.getString("message");
                                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Se houver faturas, processar normalmente
                                    ArrayList<Fatura> faturas = FaturaJsonParser.parserJsonFaturas(faturasArray);

                                    // Notificar o listener de faturas
                                    if (faturasListener != null) {
                                        faturasListener.onRefreshListaFaturas(faturas);
                                    }
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

    public void getFaturaAPI(int id, final Context context) {
        if (!FaturaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIFatura + id;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Fatura fatura = FaturaJsonParser.parserJsonFatura(response);
                                    if (faturaListener != null)
                                        faturaListener.onRefreshDetalhes(fatura);
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void getUserDefinicoesAPI(final Context context) {
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

                            // Parse do JSON para obter as definições do user
                            User user = UserJsonParser.parserJsonUser(jsonResponse);

                            // Definir o usuário atual no singleton
                            SingletonGestorLusitaniaTravel singleton = SingletonGestorLusitaniaTravel.getInstance(context);
                            singleton.setUser(user);

                            // Executar no thread principal
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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

    public void alterarUserAPI(final User user, final Context context) {
        if (!LoginJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            // Construir o objeto JSON com os dados do usuário e do perfil
            JSONObject requestBody = new JSONObject();
            JSONObject userData = new JSONObject();
            JSONObject profileData = new JSONObject();
            try {
                userData.put("username", user.getUsername());
                userData.put("email", user.getEmail());

                profileData.put("name", user.getProfile().getName());
                profileData.put("mobile", user.getProfile().getMobile());
                profileData.put("street", user.getProfile().getStreet());
                profileData.put("locale", user.getProfile().getLocale());
                profileData.put("postalCode", user.getProfile().getPostalCode());

                requestBody.put("User", userData);
                requestBody.put("Profile", profileData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, mUrlAPIAlterarUser, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Processar a resposta, se necessário
                            Toast.makeText(context, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Erro ao alterar usuário: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    String credentials = getUsername() + ":" + getPassword();
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            volleyQueue.add(req);
        }
    }


    public void getLocalizacaoFornecedoresAPI(final Context context, String localizacao) {
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
                            if (fornecedoresListener != null)
                                fornecedoresListener.onRefreshListaFornecedores(fornecedores);
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

    public void getAllFavoritosAPI(final Context context) {
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
                                // Verificar se a resposta contém dados de favoritos
                                if (!response.isNull("favoritos")) {
                                    // Se houver dados de favoritos, processar normalmente
                                    JSONArray favoritosArray = response.getJSONArray("favoritos");
                                    fornecedores = FavoritoJsonParser.parserJsonFavoritos(favoritosArray);
                                    if (favoritosListener != null) {
                                        favoritosListener.onRefreshListaFornecedores(fornecedores);
                                    }
                                } else {
                                    // Se não houver dados de favoritos, mostrar mensagem do servidor
                                    String mensagem = response.getString("message");
                                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
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

    public void adicionarFavoritoAPI(final int fornecedorId, final Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIAdicionarFavorito + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (favoritoListener != null)
                        favoritoListener.onRefreshDetalhes(MainActivity.ADD);
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

    public void removerFavoritoAPI(final int fornecedorId, final Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIRemoverFavorito + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (favoritoListener != null)
                        favoritoListener.onRefreshDetalhes(MainActivity.DELETE);
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

    public void getFavoritoAPI(int fornecedorId, Context context) {
        if (!FavoritoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIDetalhesFavorito + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Fornecedor fornecedor = FavoritoJsonParser.parserJsonFavorito(response.optJSONObject("fornecedor"));
                                    if (fornecedorListener != null)
                                        fornecedorListener.onRefreshDetalhes(fornecedor);
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void getAllCarrinhoAPI(Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlAPICarrinho, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Verificar se a resposta contém dados de carrinho
                                if (!response.isNull("carrinho")) {
                                    // Se houver dados de carrinho, processar normalmente
                                    List<Carrinho> carrinhos = CarrinhoJsonParser.parserJsonCarrinhos(response);
                                    if (carrinhosListener != null) {
                                        carrinhosListener.onRefreshListaCarrinho(new ArrayList<>(carrinhos));
                                    }
                                } else {
                                    // Se não houver dados de carrinho, mostrar mensagem do servidor
                                    String mensagem = response.getString("message");
                                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

    public void adicionarCarrinhoAPI(final int fornecedorId, final Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIAdicionarCarrinho + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (carrinhoListener != null)
                        carrinhoListener.onRefreshDetalhes(MainActivity.ADD);
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

    public void removerCarrinhoAPI(final int fornecedorId, final Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIRemoverCarrinho + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (carrinhoListener != null)
                        carrinhoListener.onRefreshDetalhes(MainActivity.DELETE);
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

    public void verificarReservaAPI(String checkin, String checkout, int numeroClientes, int numeroQuartos,
                                    String tipoQuarto, int numeroCamas, Context context, int reservaId) {
        if (!ReservaJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIVerificarReserva + reservaId;

            // Construir o corpo da solicitação POST
            JSONObject jsonBody = new JSONObject();
            try {
                // Adicionar os campos ao jsonBody
                jsonBody.put("checkin", checkin);
                jsonBody.put("checkout", checkout);
                jsonBody.put("numeroclientes", numeroClientes);
                jsonBody.put("numeroquartos", numeroQuartos);

                // Construir o JSONArray para linhasreservas
                JSONArray linhasReservasArray = new JSONArray();
                JSONObject linhaReserva = new JSONObject();
                linhaReserva.put("tipoquarto", tipoQuarto);
                linhaReserva.put("numerocamas", numeroCamas);
                linhasReservasArray.put(linhaReserva);

                // Adicionar linhasreservas ao jsonBody
                jsonBody.put("linhasreservas", linhasReservasArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlCompleta,jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Analisar a resposta usando o parser de reserva específico
                                    Reserva reserva = ReservaJsonParser.parserJsonVerificar(response);

                                    // Notificar o listener com o resultado
                                    if (verificarListener != null) {
                                        verificarListener.onRefreshDetalhes(reserva);
                                    }
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Erro na solicitação HTTP", Toast.LENGTH_SHORT).show();
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

    public void finalizarCarrinhoAPI(int reservaId, Context context) {
        if (!CarrinhoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIFinalizarCarrinho + reservaId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (carrinhoListener != null)
                        carrinhoListener.onRefreshDetalhes(CarrinhoFragment.ADD);
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

    public void getAllComentariosAPI(final Context context) {
        if (!ComentarioJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlAPIComentarios, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Verificar se a resposta contém a mensagem de ausência de comentários
                                if (!response.isNull("message")) {
                                    String mensagem = response.getString("message");
                                    // Exibir a mensagem do servidor em um Toast
                                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Se houver comentários, processar normalmente
                                    JSONArray comentariosArray = response.getJSONArray("comentarios_avaliacoes");
                                    comentarios = ComentarioJsonParser.parserJsonComentarios(comentariosArray);

                                    // Notificar o listener de comentários
                                    if (comentariosListener != null) {
                                        comentariosListener.onRefreshListaComentarios(comentarios);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

    public void getComentarioAPI(int id, final Context context) {
        if (!ComentarioJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIComentario + id;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Comentario comentario = ComentarioJsonParser.parserJsonComentario(response);
                                    if (comentarioListener != null)
                                        comentarioListener.onRefreshDetalhes(comentario);
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void getAllComentariosAlojamentoAPI(int fornecedorId, final Context context) {
        if (!ComentarioJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIComentariosAlojamento + fornecedorId;

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, urlCompleta, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray comentariosArray = response.getJSONArray("comentarios_avaliacoes");
                                comentarios = ComentarioJsonParser.parserJsonComentarios(comentariosArray);

                                if (comentariosListener != null)
                                    comentariosListener.onRefreshListaComentarios(comentarios);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.getMessage() != null) {
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Erro desconhecido", Toast.LENGTH_SHORT).show();
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

    public void adicionarComentarioAPI(String titulo, String descricao, int classificacao, Context context, int fornecedorId) {
        if (!ComentarioJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, "Não tem ligação à Internet", Toast.LENGTH_SHORT).show();
        } else {
            String username = SingletonGestorLusitaniaTravel.getInstance(context).getUsername();
            String password = SingletonGestorLusitaniaTravel.getInstance(context).getPassword();

            String urlCompleta = mUrlAPIAdicionarComentario + fornecedorId;

            // Construir o corpo da solicitação POST
            JSONObject jsonBody = new JSONObject();
            try {
                // Adicionar os campos ao jsonBody
                JSONObject comentarioObject = new JSONObject();
                comentarioObject.put("titulo", titulo);
                comentarioObject.put("descricao", descricao);

                JSONObject avaliacaoObject = new JSONObject();
                avaliacaoObject.put("classificacao", classificacao);

                jsonBody.put("Comentario", comentarioObject);
                jsonBody.put("Avaliacao", avaliacaoObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlCompleta, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String successMessage = response.getString("success");
                                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Erro na solicitação HTTP", Toast.LENGTH_SHORT).show();
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


    //endregion
}
