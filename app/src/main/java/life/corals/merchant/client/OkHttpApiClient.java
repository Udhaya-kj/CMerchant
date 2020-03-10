package life.corals.merchant.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import life.corals.merchant.R;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public  class OkHttpApiClient {

    public ApiClient getApiClient() {
        return apiClient;
    }

    private ApiClient apiClient;

    @SuppressLint("AllowAllHostnameVerifier")
    public OkHttpApiClient(Context mCtx) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setAuthenticator(getAuthenticator());

        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        okHttpClient.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClient.setSslSocketFactory(getSslContext().getSocketFactory());
        apiClient = new ApiClient();
        apiClient.setHttpClient(okHttpClient);
        apiClient.setVerifyingSsl(false);
        apiClient.setSslCaCert(mCtx.getResources().openRawResource(R.raw.dev_server8443));

    }
    private SSLContext getSslContext() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sc;
    }

    private Authenticator getAuthenticator() {
        return new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                String credential = Credentials.basic("corals", "password");
                return response.request().newBuilder().header("Authorization", credential).build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        };
    }

}

