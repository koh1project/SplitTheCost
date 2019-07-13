package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import io.github.koh1project.SplitTheCost.dao.CheckListDao;
import io.github.koh1project.SplitTheCost.dao.TypesDao;

public class SpecialCalculationActivity extends Activity {

    private EditText totalEdit;
    private EditText discountEdit;
    private TextView numMember;
    private Integer total;
    private Integer discount;
    private Integer roundingValue;
    private Spinner spSpinner1;
    private Spinner spSpinner2;
    private Spinner spSpinner3;
    private SharedPreferences prefs;
    private EditText specialPrice1;
    private EditText specialPrice2;
    private EditText specialPrice3;
    private TextView numSpecial1;
    private TextView numSpecial2;
    private TextView numSpecial3;
    private CheckListDao checkListDao;
    private TypesDao typesDao;
    private Button tmpButton;
    private boolean calculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_calculation);
        checkListDao = new CheckListDao(this);
        checkListDao.resetTable();
        typesDao = new TypesDao(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        totalEdit = findViewById(R.id.totalCostEdit);
        discountEdit = findViewById(R.id.discountEdit2);

        specialPrice1 = findViewById(R.id.specialEdit1);
        specialPrice2 = findViewById(R.id.specialEdit2);
        specialPrice3 = findViewById(R.id.specialEdit3);
        numMember = findViewById(R.id.numMember2);
        numSpecial1 = findViewById(R.id.nSpecial1);
        numSpecial2 = findViewById(R.id.nSpecial2);
        numSpecial3 = findViewById(R.id.nSpecial3);
        spSpinner1 = findViewById(R.id.spSpinner1);
        spSpinner2 = findViewById(R.id.spSpinner2);
        spSpinner3 = findViewById(R.id.spSpinner3);

        tmpButton = findViewById(R.id.callTempButton);
        tmpButton.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();

        numMember.setText(checkListDao.getTotalAmount().toString());
        numSpecial1.setText(checkListDao.getCountSpecials(1));
        numSpecial2.setText(checkListDao.getCountSpecials(2));
        numSpecial3.setText(checkListDao.getCountSpecials(3));



        //一度計算されていれば「一時保存情報を呼び出し」ボタンを表示する
        if(calculated){
            tmpButton.setVisibility(View.VISIBLE);
        }
        if(checkListDao.getMembers().length == 0){
            tmpButton.setVisibility(View.GONE);
        }

    }

    public void onClickAddParticipantButton(View view) {
        Intent i = new Intent(this, AddParticipantsActivity.class);
        startActivityForResult(i, 1);
    }


    public void onClickAddAppliedButton1(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.special_account));
        final String specialId = "1";
        final List<Map<String, String>> members = checkListDao.getMembersAndSpecials(specialId);
        final String[] memberNames = convertNames(members);
        boolean[] specials = convertSpecials(members);

        builder.setMultiChoiceItems(
                memberNames,
                specials,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        String n = memberNames[which];
                        String name;
                        if (n.contains(":")) {
                            int i = n.indexOf(":");
                            name = n.substring(0, i);
                        //    Log.d("置き換えたもの", name);
                        //    Log.d("空白がある", toString().valueOf(name.contains("")));
                        } else {
                            name = n;
                        //    Log.d("置き換えなかった", name);
                        }


                        if (isChecked) {
                            checkListDao.updateSpecial(name, specialId);
                        //    Log.d("ダイアログchecked", name);
                        } else {
                            checkListDao.removeSpecial(name);
                        //    Log.d("ダイアログELSE", name);
                        }
                    }
                });
        builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        builder.show();
    }

    public void onClickAddAppliedButton2(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.special_account));
        final String specialId = "2";
        final List<Map<String, String>> members = checkListDao.getMembersAndSpecials(specialId);
        final String[] memberNames = convertNames(members);
        boolean[] specials = convertSpecials(members);

        builder.setMultiChoiceItems(
                memberNames,
                specials,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {

                        String n = memberNames[which];
                        String name;
                        if (n.contains(":")) {
                            int i = n.indexOf(":");
                            name = n.substring(0, i);
                        //    Log.d("置き換えたもの", name);
                        //    Log.d("空白がある", toString().valueOf(name.contains("")));
                        } else {
                            name = n;
                        //    Log.d("置き換えなかった", name);
                        }

                        if (isChecked) {
                            checkListDao.updateSpecial(name, specialId);
                        //    Log.d("ダイアログchecked", name);
                        } else {
                            checkListDao.removeSpecial(name);
                        //    Log.d("ダイアログELSE", name);
                        }
                    }
                });
        builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        builder.show();


    }

    public void onClickAddAppliedButton3(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.special_account));
        final String specialId = "3";
        final List<Map<String, String>> members = checkListDao.getMembersAndSpecials(specialId);
        Log.d("ダイアログspecialId:", specialId);


        final String[] memberNames = convertNames(members);
        boolean[] specials = convertSpecials(members);

        builder.setMultiChoiceItems(
                memberNames,
                specials,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        String n = memberNames[which];
                        String name;
                        if (n.contains(":")) {
                            int i = n.indexOf(":");
                            name = n.substring(0, i);
                        //    Log.d("置き換えたもの", name);
                        //    Log.d("空白がある", toString().valueOf(name.contains("")));
                        } else {
                            name = n;
                        //    Log.d("置き換えなかった", name);
                        }

                        if (isChecked) {
                            checkListDao.updateSpecial(name, specialId);
                        //    Log.d("ダイアログchecked", name);
                        } else {
                            checkListDao.removeSpecial(name);
                        //    Log.d("ダイアログELSE", name);
                        }
                    }
                });
        builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        builder.show();
    }

    //選択された情報で計算を実行
    public void onClickCalculateButton(View view) {

        roundingValue = Integer.parseInt(prefs.getString(getString(R.string.i_param_rounding_value), "")
                .replace(getString(R.string.currency), ""));

        if (totalEdit.getText().toString().isEmpty()) {
            totalEdit.setText("0");
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

        total = Integer.parseInt(totalEdit.getText().toString());
        discount = Integer.parseInt(discountEdit.getText().toString());

        //エラー対策
        int count_member = Integer.parseInt(numMember.getText().toString());
        if(count_member == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle(getString(R.string.error_numMember));
            builder.setPositiveButton(getString(R.string.positive_button),null);
            builder.show();

        }else if (total < roundingValue) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.error_fraction_bigger));
            builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //設定画面に遷移させる場合には実行
                //    Intent i = new Intent(SpecialCalculationActivity.this, CalPrefsActivity.class);
                //    startActivity(i);
                }
            });
            builder.show();
            return;
        } else{

        total = Integer.parseInt(totalEdit.getText().toString());
        discount = Integer.parseInt(discountEdit.getText().toString());

        Intent i = new Intent(this,CheckListActivity.class);

        String[] waySpecials = {spSpinner1.getSelectedItem().toString()
                , spSpinner2.getSelectedItem().toString()
                , spSpinner3.getSelectedItem().toString()};
        String[] numSpecials = {numSpecial1.getText().toString(),
                numSpecial2.getText().toString(),
                numSpecial3.getText().toString()};
        String[] priceSpecials = {specialPrice1.getText().toString()
                , specialPrice2.getText().toString(),
                specialPrice3.getText().toString()};

        i.putExtra(getString(R.string.i_param_way), waySpecials);
        i.putExtra(getString(R.string.i_param_num_special), numSpecials);
        i.putExtra(getString(R.string.i_param_price_special), priceSpecials);
        i.putExtra(getString(R.string.i_param_total), total);
        i.putExtra(getString(R.string.i_param_discount), discount);
        i.putExtra(getString(R.string.i_param_num_member), numMember.getText().toString());
        i.putExtra(getString(R.string.i_param_rounding_method),
                prefs.getString(getString(R.string.i_param_rounding_method), ""));
        i.putExtra(getString(R.string.i_param_rounding_value),
                prefs.getString(getString(R.string.i_param_rounding_value), ""));

        i.putExtra(getString(R.string.i_param_new_calculation), true);

        calculated = true; //「一時保存---」ボタンを表示させる
        Log.d("Intent","INTENT:"+ i);
        startActivity(i);
        }
    }

    //一度計算した情報を再度読み込む
    public void onClickCallTempButton(View view) {
        Intent i = new Intent(this, CheckListActivity.class);
        i.putExtra(getString(R.string.i_param_new_calculation), false);
        startActivity(i);
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

    public boolean[] convertSpecials(List<Map<String, String>> members) {
        boolean[] specials = new boolean[members.size()];
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).get(getString(R.string.param_special)) == null) {
                specials[i] = false;
            } else {
                specials[i] = true;
            }
        }
        return specials;
    }


    public String[] convertNames(List<Map<String, String>> members) {
        String[] memberNames = new String[members.size()];
        for (int i = 0; i < members.size(); i++) {
            String name = members.get(i).get("name");
            String position = typesDao.getTypesNameByName(name);

            if (position == null) {
                memberNames[i] = (name);
            } else {
                memberNames[i] = (name + ":" + position);
            }
        }
        return memberNames;

    }


}
