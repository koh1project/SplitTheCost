package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    private SharedPreferences prefs;
    private String method;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //設定情報が設定されているかチェック
        method = prefs.getString(getString(R.string.i_param_rounding_method), null);
        value = prefs.getString(getString(R.string.i_param_rounding_value), null);

        //端数設定ない
        if( method == null || value == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle(getString(R.string.setting_request_title));
            StringBuilder sb = new StringBuilder(getString(R.string.setting_request_from) + "\n\n");

            if(value == null){
                sb.append(getString(R.string.fractional_value)+"\n");
            }
            if(method == null){
                sb.append(getString(R.string.method)+"\n");
            }
            sb.append("\n"+getString(R.string.setting_request_end));

            builder.setMessage(sb);
            builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(MainActivity.this ,
                            /*端数計算設定画面*/CalPrefsActivity.class);
                    startActivity(i);
                }
            });
            builder.show();
        }// <--端数設定がない-->
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


    public void onClickGoSimpleButton(View view) {
        Intent i = new Intent(this, SimpleCalculationActivity.class);

        i.putExtra(getString(R.string.i_param_rounding_method), method);
        i.putExtra(getString(R.string.i_param_rounding_value), Integer.parseInt(
                value.replace(getString(R.string.currency), "")));
        startActivity(i);
    }

    public void OnClickManageButton(View view){
        Intent i = new Intent(this, ManageListActivity.class);
        startActivity(i);
    }

    public void onClickGoSpecialButton(View view){
        Intent i = new Intent(this, SpecialCalculationActivity.class);
        startActivity(i);
    }

}
