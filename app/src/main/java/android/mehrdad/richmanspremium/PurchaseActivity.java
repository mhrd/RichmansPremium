package android.mehrdad.richmanspremium;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

public class PurchaseActivity extends AppCompatActivity {

    Button exit;
    ProgressDialog pDialog;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        exit = (Button) findViewById(R.id.btn_exit);
        exit.setEnabled(false);

        Send1("https://pay.ir/payment/send");

        wv = (WebView) findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
//                tt();
            }
        });

    }

    void tt() {
        Toast.makeText(this, wv.getUrl() + "", Toast.LENGTH_SHORT).show();
    }

    void Send1(final String URL) {
        Log.d("req", "___send started");
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("لطفا صبر کنید");
        pDialog.show();
        ////////////////////////////////////////////////////////

        final Thread send = new Thread(new Runnable() {
            @Override
            public void run() {

                JSONObject obj = new JSONObject();

                try {
//                    obj.put("api", "997fb31002ac609f2ff5ca6a5a3e908b");
                    obj.put("api", "44ff86905313e61b94c0ba7c65897c6a");
//                    obj.put("api", "test");
                    obj.put("amount", 100000);
                    obj.put("redirect",
                            URLEncoder.encode("http://www.madresetavangari.ir/pay/verify"));
//                    obj.put("redirect", "https://www.madresetavangari.ir/pay/verify");
                    obj.put("factorNumber", HomePageActivity.phn);
//                    obj.put("factorNumber", "1");
                } catch (Exception e) {
                    //
                }
                postData(URL, obj);

            }
        });
        send.start();
    }

    String tr;

    public void postData(String url, JSONObject obj) {
        // Create a new HttpClient and Post Header
        HttpParams myParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(myParams, 10000);
        HttpConnectionParams.setSoTimeout(myParams, 10000);
        HttpClient httpclient = new DefaultHttpClient(myParams);

        try {
            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
//            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            se.setContentType("application/json");
            httppost.setEntity(se);


            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            if (temp.contains("transId")) {
                tr = temp.substring(temp.indexOf("transId") + 9, temp.length() - 1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        wv.loadUrl("http://www.madresetavangari.ir/pay/verify/");
                        wv.loadUrl("https://pay.ir/payment/gateway/" + tr);
                        hidePDialog();
                        exit.setEnabled(true);
                    }
                });
            }
        } catch (ClientProtocolException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //
                }
            });

        } catch (IOException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //
                }
            });
        }
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
