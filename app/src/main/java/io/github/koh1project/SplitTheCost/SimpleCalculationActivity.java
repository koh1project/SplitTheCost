package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.koh1project.SplitTheCost.calculate.SimpleCalculate;

public class SimpleCalculationActivity extends Activity {

    static final String SPECIFIED_COST = "円";
    static final String DISCOUNT_COST = "円引き";

    private EditText totalEdit;
    private EditText discountEdit;
    private EditText numMemberEdit;
    private TextView perMember;
    private String roundingMethod;
    private Integer roundingValue;
    private SimpleCalculate cal;
    private Integer total;
    private Integer discount;
    private Integer numMember;
    private SharedPreferences prefs;
    private EditText specialPrice1;
    private EditText specialPrice2;
    private EditText specialPrice3;
    private EditText numSpecial1;
    private EditText numSpecial2;
    private EditText numSpecial3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculation);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        totalEdit = findViewById(R.id.totalEdit1);
        discountEdit = findViewById(R.id.discountEdit1);
        numMemberEdit = findViewById(R.id.numMemberEdit1);
        perMember = findViewById(R.id.perMemberView1);
        specialPrice1 = findViewById(R.id.specialPrice1);
        specialPrice2 = findViewById(R.id.specialPrice2);
        specialPrice3 = findViewById(R.id.specialPrice3);
        numSpecial1 = findViewById(R.id.numSpecial1);
        numSpecial2 = findViewById(R.id.numSpecial2);
        numSpecial3 = findViewById(R.id.numSpecial3);

        cal = new SimpleCalculate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, CalPrefsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickCalcButton1(View view) {

        roundingMethod = prefs.getString(getString(R.string.i_param_rounding_method), "");
        roundingValue = Integer.parseInt(prefs.getString(getString(R.string.i_param_rounding_value),
                "").replace(getString(R.string.currency), ""));

        if (totalEdit.getText().toString().isEmpty()) {
            totalEdit.setText("0");
        }
        if (numMemberEdit.getText().toString().isEmpty()) {
            numMemberEdit.setText("0");
        }
        if (discountEdit.getText().toString().isEmpty()) {
            discountEdit.setText("0");
        }
        if (specialPrice1.getText().toString().isEmpty()) {
            specialPrice1.setText("0");
        }
        if (specialPrice2.getText().toString().isEmpty()) {
            specialPrice2.setText("0");
        }
        if (specialPrice3.getText().toString().isEmpty()) {
            specialPrice3.setText("0");
        }
        if (numSpecial1.getText().toString().isEmpty()) {
            numSpecial1.setText("0");
        }
        if (numSpecial2.getText().toString().isEmpty()) {
            numSpecial2.setText("0");
        }
        if (numSpecial3.getText().toString().isEmpty()) {
            numSpecial3.setText("0");
        }

        total = Integer.parseInt(totalEdit.getText().toString());
        numMember = Integer.parseInt(numMemberEdit.getText().toString());
        discount = Integer.parseInt(discountEdit.getText().toString()
                .replace(getString(R.string.currency), ""));

        Integer sp1 = Integer.parseInt(specialPrice1.getText().toString());
        Integer sp2 = Integer.parseInt(specialPrice2.getText().toString());
        Integer sp3 = Integer.parseInt(specialPrice3.getText().toString());
        List<Integer> specialPrices = new ArrayList<>();
        specialPrices.add(sp1);
        specialPrices.add(sp2);
        specialPrices.add(sp3);

        Integer ns1 = Integer.parseInt(numSpecial1.getText().toString());
        Integer ns2 = Integer.parseInt(numSpecial2.getText().toString());
        Integer ns3 = Integer.parseInt(numSpecial3.getText().toString());
        List<Integer> numSpecials = new ArrayList<>();
        numSpecials.add(ns1);
        numSpecials.add(ns2);
        numSpecials.add(ns3);

        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);
        List<Spinner> spinners = new ArrayList<>();
        spinners.add(spinner1);
        spinners.add(spinner2);
        spinners.add(spinner3);

        if (total < roundingValue) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.error_fraction_bigger));
            builder.setPositiveButton(getString(R.string.positive_button), null);
            builder.show();
            return;
        }

        if (numMember <= 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.error_numMember));
            builder.setPositiveButton(getString(R.string.positive_button), null);
            builder.show();
            return;
        }

        for (int i = 0; i < specialPrices.size(); i++) {
            if (numSpecials.get(i) > 0 || specialPrices.get(i) > 0) {
                Log.d("特別価格", "処理を開始　分岐前");
                switch (spinners.get(i).getSelectedItem().toString()) {
                    case SPECIFIED_COST:
                        Log.d("特別価格", "処理を開始　円");
                        total = total - (specialPrices.get(i) * numSpecials.get(i));
                        numMember = numMember - numSpecials.get(i);
                        break;

                    case DISCOUNT_COST:
                        Log.d("特別価格", "処理を開始　円引き");
                        int tempPerMember = cal.getPerMember(total, discount, numMember,
                                roundingMethod,
                                roundingValue);
                        int specialPrice = tempPerMember - specialPrices.get(i);
                        Log.d("特別価格", toString().valueOf(specialPrice) + "円");
                        Log.d("引く前", toString().valueOf(total) + "円");

                        total = total - (specialPrice * numSpecials.get(i)); //暫定1人当たりから指定額を差し引く

                        Log.d("引く後", toString().valueOf(total) + "円");

                        numMember = numMember - numSpecials.get(i);
                        break;
                }
            }
        }

        int p = cal.getPerMember(total, discount, numMember, roundingMethod,
                roundingValue);
        NumberFormat formatter = new DecimalFormat("#,###");
        String price = formatter.format(p);
        perMember.setText(price + getString(R.string.currency));

    }
}
