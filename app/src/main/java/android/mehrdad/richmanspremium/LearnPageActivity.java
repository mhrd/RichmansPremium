package android.mehrdad.richmanspremium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import android.mehrdad.richmanspremium.app.AppController;
import android.mehrdad.richmanspremium.model.Mproduct;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LearnPageActivity extends AppCompatActivity {

    RecyclerView recTutorials;
    List<Tutorial> tutorials;
    TextView ttemp;
    Button buytt;

    String pl;
    private static final String TAG = LearnPageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_page);

        pl = getIntent().getStringExtra("plan");
        ttemp = (TextView) findViewById(R.id.ttemp);
        buytt = (Button) findViewById(R.id.buytt);
        buytt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tr();
            }
        });
        if (pl.equals("p")) {
            ttemp.setVisibility(View.VISIBLE);
            buytt.setVisibility(View.VISIBLE);
        }
        recTutorials = (RecyclerView) findViewById(R.id.rec_tutorials);
        tutorials = new ArrayList<>();

        getList();

//        //sample init
//
//        Tutorial tutorial1 = new Tutorial();
//        tutorial1.id = "tt1";
//        tutorial1.name = "بازی که بازی نیست";
//        tutorial1.explain = getString(getResources().getIdentifier("tt1", "string", getPackageName()));
//        tutorial1.type = 1;
//        tutorial1.image = R.drawable.tutolial_defult;
//        tutorials.add(tutorial1);
//
//
//        Tutorial tutorial2 = new Tutorial();
//        tutorial2.id = "tt2";
//        tutorial2.name = "قواعد بازی";
//        tutorial2.explain = getString(getResources().getIdentifier("tt2", "string", getPackageName()));
//        tutorial2.type = 1;
//        tutorials.add(tutorial2);
//
//        Tutorial tutorialVideo = new Tutorial();
//        tutorialVideo.id = "tv1";
//        tutorialVideo.name = "یه فیلمی";
//        tutorialVideo.explain = "یه فیلمی دیگه توضیح نمیخواد :)";
//        tutorialVideo.type = 2;
//        tutorials.add(tutorialVideo);
//
//        Tutorial tutorialSound = new Tutorial();
//        tutorialSound.id = "ts1";
//        tutorialSound.name = "یه صدایی";
//        tutorialSound.explain = "یه صدایی دیگه نمونست";
//        tutorialSound.type = 3;
//        tutorials.add(tutorialSound);


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recTutorials.setLayoutManager(linearLayoutManager);
//        recTutorials.setHasFixedSize(true);
//        recTutorials.setAdapter(new TutorialRecyclerAdapter(getApplicationContext(), tutorials));
//        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recTutorials.addItemDecoration(itemDecor);
    }

    void tr() {
        Intent i = new Intent(this, PurchaseActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private ProgressDialog pDialog;

    void getList() {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("لطفا صبر کنید");
        pDialog.show();

        String url = "http://89.163.249.183:3000/getfilmlist";
        // Creating volley request obj
        JsonArrayRequest Req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidePDialog();
                        for (int i = response.length() - 1; i >= 0; i--) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Tutorial t = new Tutorial();
                                t.id = obj.getString("_id");
                                t.name = obj.getString("name");
                                t.plan = obj.getString("plan");
                                t.explain = obj.getString("comment");
                                t.type = Integer.parseInt(obj.getString("type"));
                                switch (t.type) {
                                    case 1:
                                        //film
                                        t.video = "http://" + obj.getString("path");
                                        t.image = R.drawable.video;
                                        break;
                                    case 2:
                                        //voice
                                        t.sound = "http://" + obj.getString("path");
                                        t.image = R.drawable.voice;
                                        break;
                                    case 3:
                                        //text
                                        t.text = obj.getString("matn");
                                        t.image = R.drawable.text;
                                        break;
                                }
                                if (pl.equals(t.plan)) {
                                    tutorials.add(t);
                                } else {
                                    //
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recTutorials.setLayoutManager(linearLayoutManager);
                        recTutorials.setHasFixedSize(true);
                        recTutorials.setAdapter(new TutorialRecyclerAdapter(getApplicationContext(), tutorials));
                        DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                        recTutorials.addItemDecoration(itemDecor);
//                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(Req);

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

}
