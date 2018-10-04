package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentCurrencyType = parent.getItemAtPosition(position).toString();
                String newCurrencyType = "";

                /*
                Use these statements to find currency type
                 */
                if(currentCurrencyType.equals("AUD"))
                    newCurrencyType = "A$";
                else if(currentCurrencyType.equals("BRL"))
                    newCurrencyType = "R$";
                else if(currentCurrencyType.equals("CAD"))
                    newCurrencyType = "Can$";
                else if(currentCurrencyType.equals("CNY"))
                    newCurrencyType = "¥";
                else if(currentCurrencyType.equals("EUR"))
                    newCurrencyType = "€";
                else if(currentCurrencyType.equals("GBP"))
                    newCurrencyType = "£";
                else if(currentCurrencyType.equals("HKD"))
                    newCurrencyType = "HK$";
                else if(currentCurrencyType.equals("JPY"))
                    newCurrencyType = "¥";
                else if(currentCurrencyType.equals("PLN"))
                    newCurrencyType = "zł";
                else if(currentCurrencyType.equals("RUB"))
                    newCurrencyType = "\u20BD";
                else if(currentCurrencyType.equals("SEK"))
                    newCurrencyType = "kr";
                else if(currentCurrencyType.equals("USD"))
                    newCurrencyType = "$";
                else
                    newCurrencyType = "R";//ZAR


                Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                String finalUrl = BASE_URL + parent.getItemAtPosition(position); //Second part gets Currency
                Log.d("Bitcoin", "Final Url is: " + finalUrl);
                letsDoSomeNetworking(finalUrl, newCurrencyType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");
            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url, final String currencySymbol) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());

                try{
                    final String price = response.getString("last");
                    mPriceTextView.setText(currencySymbol + " " +price);
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("ERROR", e.toString());
            }
        });


    }


}
