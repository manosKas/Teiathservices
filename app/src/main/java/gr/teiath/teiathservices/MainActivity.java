package gr.teiath.teiathservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Επιλογή υπηρεσίας");
    }

    // HANDLE USER CHOICE
    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, ServiceFunctions.class);
        switch (view.getId()){
            case R.id.carpooling:
                i.putExtra("text","carpooling");
                i.putExtra("logo",R.drawable.actions_carpooling);
                i.putExtra("title",R.string.carpooling_service_text);
                i.putExtra("info",R.string.carpooling_service_info);
                i.putExtra("link",R.string.carpooling_service_link);
                break;
            case R.id.swaps:
                i.putExtra("text","swaps");
                i.putExtra("logo",R.drawable.actions_swaps);
                i.putExtra("title",R.string.swaps_service_text);
                i.putExtra("info",R.string.swaps_service_info);
                i.putExtra("link",R.string.swaps_service_link);
                break;
            case R.id.educult:
                i.putExtra("text","educult");
                i.putExtra("logo",R.drawable.actions_educult);
                i.putExtra("title",R.string.educult_service_text);
                i.putExtra("info",R.string.educult_service_info);
                i.putExtra("link",R.string.educult_service_link);
                break;
            case R.id.roommates:
                i.putExtra("text","roommates");
                i.putExtra("logo",R.drawable.actions_roommates);
                i.putExtra("title",R.string.roommates_service_text);
                i.putExtra("info",R.string.roommates_service_info);
                i.putExtra("link",R.string.roommates_service_link);
                break;
            case R.id.openactions:
                i.putExtra("text","openactions");
                i.putExtra("logo",R.drawable.actions_openactions);
                i.putExtra("title",R.string.openactions_service_text);
                i.putExtra("info",R.string.openactions_service_info);
                i.putExtra("info",R.string.openactions_service_link);
                break;
            case R.id.offers:
                i.putExtra("text","offers");
                i.putExtra("logo",R.drawable.actions_offers);
                i.putExtra("title",R.string.offers_service_text);
                i.putExtra("info",R.string.offers_service_info);
                i.putExtra("link",R.string.offers_service_link);
                break;
        }

        // Start next activity
        startActivity(i);
    }
}
