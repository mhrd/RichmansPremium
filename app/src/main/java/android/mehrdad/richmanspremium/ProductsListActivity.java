package android.mehrdad.richmanspremium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import android.mehrdad.richmanspremium.adapter.GproductCustomListAdapter;
import android.mehrdad.richmanspremium.app.AppController;
import android.mehrdad.richmanspremium.model.Gproduct;
import android.mehrdad.richmanspremium.model.Mproduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductsListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recProducts;

    private static final String TAG = ProductsListActivity.class.getSimpleName();

    // Gproducts json url
    String url = "http://89.163.249.183:3000/getproduct/";
    private ProgressDialog pDialog;
    private List<Gproduct> productList = new ArrayList<Gproduct>();
    private ListView listView;
    private GproductCustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        init();
        listView = (ListView) findViewById(R.id.list);
        adapter = new GproductCustomListAdapter(this, productList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("لطفا صبر کنید");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest productReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Gproduct product = new Gproduct();
                                product.setCode(obj.getString("_id"));
                                product.setName(obj.optString("name"));
                                product.setPrice(obj.getString("price"));
                                product.setDesc(obj.optString("comment"));
                                JSONArray pic = obj.getJSONArray("pictures");
                                product.setThumbnailUrl("http://" + pic.getString(0));
                                product.setThumbnailUrl2("http://" + pic.getString(1));
                                product.setThumbnailUrl3("http://" + pic.getString(2));
                                productList.add(product);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(productReq);

        ////////////////////////////////////// click item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ProductsListActivity.this, ProductDetailActivity.class);
                Gproduct mproduct = (Gproduct) parent.getAdapter().getItem(position);
                i.putExtra("product", new String[]{
                        mproduct.getName(),
                        mproduct.getPrice(),
                        mproduct.getDesc(),
                        mproduct.getThumbnailUrl(),
                        mproduct.getThumbnailUrl2(),
                        mproduct.getThumbnailUrl3(),
                        mproduct.getCode()
                });
                startActivity(i);
            }
        });
    }

    void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        url += WhichCat(getIntent().getStringExtra("URI"));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    void tt(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    String WhichCat(String str) {
        switch (str) {
            case "موبایل":
                return "0";
            case "تبلت و کتابخوان":
                return "1";
            case "لپ تاپ":
                return "2";
            case "دوربین":
                return "3";
            case "کامپیوتر و تجهیزات جانبی":
                return "4";
            case "ماشین های اداری":
                return "5";
            case "لوازم جانبی کالای دیجیتال":
                return "6";
            case "مردانه":
                return "7";
            case "زنانه":
                return "8";
            case "بچگانه":
                return "9";
            case "ورزشی":
                return "10";
            case "عطر":
                return "11";
            case "ساعت":
                return "12";
            case "اکسسوری لوازم شخصی":
                return "13";
            case "صوتی و تصویری":
                return "14";
            case "لوازم خانگی برقی":
                return "15";
            case "آشپزخانه":
                return "16";
            case "سرو و پذیرایی":
                return "17";
            case "دکوراتیو":
                return "18";
            case "خواب حمام":
                return "19";
            case "شستشو و نظافت":
                return "20";
            case "ابزار غیر برقی":
                return "21";
            case "ابزار برقی":
                return "22";
            case "باغبانی":
                return "23";
            case "نور و روشنایی":
                return "24";
            case "خوراکی و آشامیدنی":
                return "25";
            case "لوازم آرایشی":
                return "26";
            case "لوازم بهداشتی":
                return "27";
            case "لوازم شخصی برقی":
                return "28";
            case "عینک آفتابی":
                return "29";
            case "زیورآلات":
                return "30";
            case "ابزار سلامت":
                return "31";
            case "کتاب و مجلات":
                return "32";
            case "لوازم التحریر":
                return "33";
            case "صنایع دستی":
                return "34";
            case "فرش":
                return "35";
            case "آلات موسیسقی":
                return "36";
            case "موسیقی":
                return "37";
            case "فیلم":
                return "38";
            case "نرم افزار و بازی":
                return "39";
            case "محتوای آموزشی":
                return "40";
            case "پوشاک ورزشی":
                return "41";
            case "کفش ورزشی":
                return "42";
            case "لوازم ورزشی":
                return "43";
            case "دوچرخه و لوازم جانبی":
                return "44";
            case "تجهیزات سفر":
                return "45";
            case "اسباب بازی":
                return "46";
            case "حیوانات خانگی":
                return "47";
            case "ایمنی و مراقبت":
                return "48";
            case "غذاخوری":
                return "49";
            case "لوازم شخصی":
                return "50";
            case "بهداشت و حمام":
                return "51";
            case "گردش و سفر":
                return "52";
            case "سرگرمی و آموزشی":
                return "53";
            case "خواب کودک":
                return "54";
            case "خودرو":
                return "55";
            case "لوازم جانبی خودرو":
                return "56";
            case "لوازم مصرفی خودرو":
                return "57";
            case "موتور سیکلت":
                return "58";
            case "لوازم جانبی موتور سیکلت":
                return "59";
            case "لوازم مصرفی موتور سیکلت":
                return "60";
            case "دندانپزشکی":
                return "61";
            case "باشگاه":
                return "62";
            case "کلاس های آزاد":
                return "63";
            case "هتل":
                return "64";
            case "تور مسافرتی":
                return "65";
            case "کنسرت":
                return "66";
        }
        return "0";
    }
}
