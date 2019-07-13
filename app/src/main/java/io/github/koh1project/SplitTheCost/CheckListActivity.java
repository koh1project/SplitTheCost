package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import io.github.koh1project.SplitTheCost.calculate.SpecialCalculate;
import io.github.koh1project.SplitTheCost.dao.CheckListDao;

public class CheckListActivity extends Activity { //集計のためのチェックリスト表示

    private CheckListDao checkListDao;
    private SpecialCalculate spCal;
    private Integer total;
    private Integer discount;
    private Button hideButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("START","CheckListActivity STARt--------------------------------------------");
        setContentView(R.layout.activity_check_list);
        checkListDao = new CheckListDao(this);
        spCal = new SpecialCalculate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        if (i.getBooleanExtra("newCalculation", true)) {
            checkListDao.resetPaidCheck();

            String[] waySpecials = i.getStringArrayExtra("ways");
            String[] numSpecials = i.getStringArrayExtra("nums");
            String[] priceSpecials = i.getStringArrayExtra("prices");

            spCal.setWay1(waySpecials[0]);
            spCal.setWay2(waySpecials[1]);
            spCal.setWay3(waySpecials[2]);

            spCal.setSpNum1(Integer.parseInt(numSpecials[0]));
            spCal.setSpNum2(Integer.parseInt(numSpecials[1]));
            spCal.setSpNum3(Integer.parseInt(numSpecials[2]));

            spCal.setSpPrice1(Integer.parseInt(priceSpecials[0]));
            spCal.setSpPrice2(Integer.parseInt(priceSpecials[1]));
            spCal.setSpPrice3(Integer.parseInt(priceSpecials[2]));


            total = i.getIntExtra("total", 0);
            discount = i.getIntExtra("discount", 0);
            spCal.setTotal(total);
            spCal.setDiscount(discount);
            spCal.setRoundingValue(Integer.parseInt(i.getStringExtra("roundingValue").replace("円",
                                                                                              "")));
            spCal.setMethod(i.getStringExtra("roundingMethod"));
            spCal.setNumMember(Integer.parseInt(i.getStringExtra("numMember")));

            spCal.calculateActPrices();
            checkListDao.insertPriceTable(spCal.getActPrice1(), spCal.getActPrice2(), spCal
                    .getActPrice3(), spCal.getPerMember());

        }

        List<Map<String, String>> checks = checkListDao.getMembersPrices();
        ListView list = findViewById(R.id.checkList);

        CheckListAdapter adapter = new CheckListAdapter(this, checks, R.layout.check_list_item);
        list.setAdapter(adapter);

        TextView total = findViewById(R.id.collectTotal);
        NumberFormat formatter = new DecimalFormat("#,###");

        String tmp_p = checkListDao.getTotalPrice();
        int p ;
        if(tmp_p == null){
            p = 0 ;
        }else{
            p = Integer.parseInt(tmp_p);
        }
        String price = formatter.format(p);

        total.setText(price + "円");
        hideButton = findViewById(R.id.hideButton);
    }

    public void onClickTempButton(View view) {
        finish();
    }

    public void onClickHideButton(View view) {
        if (hideButton.getAlpha() == 1) {
            hideButton.setAlpha(0);
        } else {
            hideButton.setAlpha(1);
        }
    }
}
