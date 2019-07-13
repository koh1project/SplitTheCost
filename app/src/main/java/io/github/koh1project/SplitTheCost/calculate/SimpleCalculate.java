package io.github.koh1project.SplitTheCost.calculate;

import android.util.Log;

public class SimpleCalculate {

    static final String METHOD_CEIL = "切り上げ";
    static final String METHOD_FLOOR = "切り捨て";
    static final String METHOD_ROUND = "四捨五入";


    private Integer total;
    private Integer discount;
    private Integer numMember;
    private Integer perMember;
    private String method;
    private Integer roundingValue;


    public Integer getPerMember(Integer total, Integer discount, Integer numMember, String method,
                                Integer roundingValue) {
        this.total = total;
        this.discount = discount;
        this.numMember = numMember;
        this.method = method;
        this.roundingValue = roundingValue;
        total = total - discount;
        if (roundingValue == 1) {
            return total / numMember;
        }

        try {
            if (roundingValue == 500) {
                int temp = total / numMember;
                switch (method) {

                    case METHOD_CEIL:
                        perMember = 500 * ((temp / 500) + 1);
                        break;

                    case METHOD_FLOOR:
                        perMember = 500 * (temp / 500);
                        break;
                    case METHOD_ROUND:
                        int e = (temp / 100) % 10; //百の位を抽出
                        if (e < 3 || (e >= 5 && e < 7)) {
                            perMember = 500 * (temp / 500);
                        } else {
                            perMember = 500 * ((temp / 500) + 1);
                        }
                        break;
                }

                return perMember;

            } else {

                double temp = total / numMember;
                int count = 0;

                count = (int) temp / roundingValue;
                int preRound = roundingValue / 10;
                int check = (int) temp / preRound; //端数の1桁前で割り、1の位で判定する
                check = check % 10; //1の位を取り出す
                if (check == 0) {
                    perMember = count * roundingValue;
                    return perMember;
                }

                Log.d("端数", toString().valueOf(roundingValue));
                Log.d("メソッド", method);
                Log.d("カウント", toString().valueOf(count));
                Log.d("preRound", toString().valueOf(preRound));
                Log.d("チェック", toString().valueOf(check));


                switch (method) {
                    case METHOD_CEIL:
                        count = count + 1;
                        perMember = count * roundingValue;
                        break;

                    case METHOD_FLOOR:
                        perMember = count * roundingValue;
                        break;
                    case METHOD_ROUND:
                        if (check <= 4) {
                            perMember = count * roundingValue;
                        } else {
                            count = count + 1;
                            perMember = count * roundingValue;
                        }
                }
                Log.d("perMember", perMember.toString());
                return perMember;
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
            return null;
        }
    }


    public Integer getPerMember() {
        return perMember;
    }
    public Integer getRoundingValue() {
        return roundingValue;
    }
    public void setRoundingValue(Integer roundingValue) {
        this.roundingValue = roundingValue;
    }
    public Integer getNumMember() {
        return numMember;
    }
    public void setNumMember(Integer numMember) {
        this.numMember = numMember;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getDiscount() {
        return discount;
    }
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
    public void setPerMember(Integer perMember) {
        this.perMember = perMember;
    }

}
