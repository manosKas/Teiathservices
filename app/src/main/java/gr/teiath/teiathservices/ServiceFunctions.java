package gr.teiath.teiathservices;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ServiceFunctions extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_functions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String service = intent.getStringExtra("text");
        int logo = intent.getIntExtra("logo",-1);
        int title = intent.getIntExtra("title",-1);
        int info = intent.getIntExtra("info",-1);
        int link = intent.getIntExtra("link",-1);

        setTitle(title);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hv = navigationView.getHeaderView(0);
        ImageView iv = (ImageView) hv.findViewById(R.id.service_logo);
        iv.setImageDrawable(ResourcesCompat.getDrawable(getResources(), logo, null));
        TextView tv = (TextView) hv.findViewById(R.id.service_title);
        tv.setText(info);
        tv = (TextView) hv.findViewById(R.id.service_link);
        tv.setText(link);

        navigationView.setNavigationItemSelectedListener(this);


        if(isNetworkAvailable()) {
            try {
                response = new CallAPI().execute(getString(R.string.api_carpooling_all)).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if (response.length() > 0) {

                ArrayList<HashMap<String,String>> data = new ArrayList<>();
                String fromArray[]={"name","category","price"};
                int to[]={R.id.name,R.id.category,R.id.price};

                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONObject("serviceProductList").getJSONArray("serviceProducts");

                    for (int i=0; i<ja.length(); i++){
                        //new product()
                        HashMap<String,String> m = new HashMap<>();//create a hashmap to store the data in key value pair
                        JSONObject c = ja.getJSONObject(i);
                        m.put("price",c.getString("price"));
                        m.put("name",c.getString("productName"));
                        m.put("category",c.getString("productCategoryName"));
                        data.add(m);//add the hashmap into arrayList
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ListView lv = (ListView) findViewById(R.id.mainlist);
                ListAdapter la = new SimpleAdapter(this,data,R.layout.listview_item,fromArray,to);
                lv.setAdapter(la);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Δεν υπάρχει σύνδεση στο διαδυκτιο");
            alertDialog.setMessage("Δεν υπάρχει σύνδεση στο διαδυκτιο. Βεβαιωθείτε οτι η συσκευη εχει ενεργή σύνδεση στο διαδύκτιο και δοκιμάστε ξανά");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class CallAPI extends AsyncTask<String, Void, String> {

        CallAPI() {
            //set context variables if required
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String resultToDisplay = "";

            URL url = null;
            try {
                url = new URL("https://swaps.teiath.gr/services/products");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json,text/html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }

            try {
                resultToDisplay = IOUtils.toString(in, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return resultToDisplay;
        }

        @Override
        protected void onPostExecute(final String r) {

            if (r.length() > 0) {
                //finish();
            } else {
                //TODO: notify request error
            }
        }

        @Override
        protected void onCancelled() {
            //TODO: cancel
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service_functions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search_option) {
            // Handle the camera action
        } else if (id == R.id.insert_option) {

        } else if (id == R.id.edit_option) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
