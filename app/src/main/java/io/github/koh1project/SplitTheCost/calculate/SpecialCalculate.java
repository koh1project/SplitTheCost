package io.github.koh1project.SplitTheCost.calculate;

import android.util.Log;

public class SpecialCalculate extends SimpleCalculate {

    static final String SPECIFIED_COST = "円";
    static final String DISCOUNT_COST = "円引き";

    private int spPrice1 = 0;
    private String way1 = null;
    private int spNum1 = 0;
    private int actPrice1 = 0;
    private int spPrice2 = 0;
    private String way2 = null;
    private int spNum2 = 0;

    private int actPrice2 = 0;

    private int spPrice3 = 0;
    private String way3 = null;
    private int spNum3 = 0;
    private int actPrice3 = 0;

    public void calculateActPrices() {
        String[] ways = {way1, way2, way3};
        int[] spPrices = {spPrice1, spPrice2, spPrice3};
        int[] actPrices = {actPrice1, actPrice2, actPrice3};
        int[] spNumbers = {spNum1, spNum2, spNum3};

        Log.d("計算チェック１total", toString().valueOf(getTotal()));
        Log.d("計算チェック１Discount", toString().valueOf(getDiscount()));
        Log.d("計算チェック１NumMember", toString().valueOf(getNumMember()));


        //固定額は確定させ、全体から引く
        int discount = getDiscount();
        int spNumTotal = 0;
        for (int i = 0; i < ways.length; i++) {
            if (ways[i].equals(SPECIFIED_COST)) {
                Log.d("円指定:", toString().valueOf(spPrices[i]) + "円");
                Log.d("円指定:", toString().valueOf(spNumbers[i]) + "人");

                actPrices[i] = spPrices[i];
                discount = discount + (spPrices[i] * spNumbers[i]);
                spNumTotal = spNumTotal + spNumbers[i];

            }
        }
        setDiscount(discount);
        Log.d("計算チェック2Discount", toString().valueOf(discount));
        Log.d("計算チェック2spNumTotal", toString().valueOf(spNumTotal));
        Log.d("計算チェック2全体人数引く前  ", toString().valueOf(getNumMember()));

        setNumMember(getNumMember() - spNumTotal);
        Log.d("計算チェック2全体人数調整後  ", toString().valueOf(getNumMember()));
        int tempPer = 0;
        try {
            tempPer = getPerMember(getTotal(), getDiscount(), getNumMember(), getMethod(),
                                   getRoundingValue());

            if (tempPer % 10 != 0 && (tempPer % getNumMember()) != 0) {
                if (getRoundingValue() >= 100) {
                    tempPer = ((tempPer / 100) + 1) * 100;
                }
            }

            int totalSpecial = 0;


            for (int i = 0; i < ways.length; i++) {
                if (ways[i].equals(DISCOUNT_COST)) {
                    actPrices[i] = (tempPer - spPrices[i]);
                    totalSpecial = totalSpecial + (spPrices[i] * spNumbers[i]);
                    setNumMember(getNumMember() - spNumbers[i]);
                    Log.d("円引き対象", spPrices[i] + "円");
                    Log.d("円引き対象", spNumbers[i] + "人");

                }
            }
            Log.d("計算チェック3total", toString().valueOf(getTotal()));
            Log.d("計算チェック3tempPer", toString().valueOf(tempPer));
            Log.d("計算チェック3totalSpecial", toString().valueOf(totalSpecial));


            int addToNormal = 0;
            if (totalSpecial > 0) {
                addToNormal = (totalSpecial / getNumMember());
                Log.d("計算チェック4addToNormal", toString().valueOf(addToNormal));
                Log.d("計算チェック4負担人数", toString().valueOf(getNumMember()));

                if (addToNormal >= 100) {
                    if ((addToNormal % 10) != 0)
                        addToNormal = ((addToNormal / 100) + 1) * 100; //端数切り上げ
                } else {
                    addToNormal = 100;
                }
            }


            tempPer = tempPer + addToNormal;
            Log.d("計算チェック5tempPer", toString().valueOf(tempPer));
            Log.d("計算チェック5add", toString().valueOf(addToNormal));


            actPrice1 = actPrices[0];
            actPrice2 = actPrices[1];
            actPrice3 = actPrices[2];


            super.setPerMember(tempPer);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public String getWay1() {
        return way1;
    }
    public void setWay1(String way1) {
        this.way1 = way1;
    }
    public String getWay2() {
        return way2;
    }
    public void setWay2(String way2) {
        this.way2 = way2;
    }
    public String getWay3() {
        return way3;
    }
    public void setWay3(String way3) {
        this.way3 = way3;
    }
    public int getSpPrice1() {
        return spPrice1;
    }
    public void setSpPrice1(int spPrice1) {
        this.spPrice1 = spPrice1;
    }
    public int getSpNum1() {
        return spNum1;
    }
    public void setSpNum1(int spNum1) {
        this.spNum1 = spNum1;
    }
    public int getSpPrice2() {
        return spPrice2;
    }
    public void setSpPrice2(int spPrice2) {
        this.spPrice2 = spPrice2;
    }
    public int getSpNum2() {
        return spNum2;
    }
    public void setSpNum2(int spNum2) {
        this.spNum2 = spNum2;
    }
    public int getSpPrice3() {
        return spPrice3;
    }
    public void setSpPrice3(int spPrice3) {
        this.spPrice3 = spPrice3;
    }
    public int getSpNum3() {
        return spNum3;
    }
    public void setSpNum3(int spNum3) {
        this.spNum3 = spNum3;
    }
    public int getActPrice1() {
        return actPrice1;
    }
    public void setActPrice1(int actPrice1) {
        this.actPrice1 = actPrice1;
    }
    public int getActPrice2() {
        return actPrice2;
    }
    public void setActPrice2(int actPrice2) {
        this.actPrice2 = actPrice2;
    }
    public int getActPrice3() {
        return actPrice3;
    }
    public void setActPrice3(int actPrice3) {
        this.actPrice3 = actPrice3;
    }
}
