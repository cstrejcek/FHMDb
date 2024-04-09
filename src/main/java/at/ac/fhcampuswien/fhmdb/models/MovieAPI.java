package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.io.IOException;
import java.util.List;

public class MovieAPI {
    private static final String BASE_URL = "https://prog2.fh-campuswien.ac.at/movies";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String USER_AGENT_VALUE = "http.agent";
    private static final OkHttpClient client;
    private static final Gson gson;

    static {
        //client = new OkHttpClient();
        // Create a TrustManager that trusts all certificates
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[] {};
                    }
                }
        };

        // Create SSLContext with the TrustManager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        // Create OkHttpClient with the custom SSLContext
        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true)
                .build();

        gson = new Gson();
    }

    public static List<Movie> getAllMovies() throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(USER_AGENT_HEADER, USER_AGENT_VALUE)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, new TypeToken<List<Movie>>() {}.getType());
        }
    }

    public static List<Movie> filterMovies(String query, String genre,String releaseYear,String rating) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("query", query == null? "":query);
        urlBuilder.addQueryParameter("genre", genre == null? "":genre);
        urlBuilder.addQueryParameter("releaseYear", releaseYear == null? "":releaseYear);
        urlBuilder.addQueryParameter("ratingFrom", rating == null? "":rating);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(USER_AGENT_HEADER, USER_AGENT_VALUE)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, new TypeToken<List<Movie>>() {}.getType());
        }
    }
    public static Movie getMovie(String iD) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/" + iD).newBuilder();
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(USER_AGENT_HEADER, USER_AGENT_VALUE)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, new TypeToken<Movie>() {}.getType());
        }
    }
}
