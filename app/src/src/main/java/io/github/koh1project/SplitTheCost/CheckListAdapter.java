package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import io.github.koh1project.SplitTheCost.dao.CheckListDao;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CheckListAdapter extends BaseAdapter {

    private Context context = null;
    private List<Map<String, String>> data = null;
    private int resource = 0;
    private CheckListDao checkListDao;

    public CheckListAdapter(Context context, List<Map<String, String>> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
        checkListDao = new CheckListDao(context);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(data.get(position).get("id"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        RelativeLayout layout = (RelativeLayout) activity.getLayoutInflater().inflate(resource,
                                                                                      null);
        final Map<String, String> check = (Map<String, String>) this.getItem(position);

        CheckBox checkBox = layout.findViewById(R.id.checkName);
        checkBox.setText(check.get("name"));

        if (check.get("paid") != null) {
            checkBox.setChecked(true);
        }

        TextView price = layout.findViewById(R.id.checkPrice);

        NumberFormat formatter = new DecimalFormat("#,###");
        int p = Integer.parseInt(check.get("price"));
        price.setText(formatter.format(p) + "円");

        final TextView id = layout.findViewById(R.id.checkId);
        id.setText(check.get("id"));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String checkId = id.getText().toString();

                if (isChecked) {
                    checkListDao.insertPaidCheck(checkId);
                    Log.d("CHECKリスナー", checkId + "が登録された");

                } else {
                    checkListDao.deletePaidCheck(checkId);
                    Log.d("CHECKリスナー", checkId + "が削除された");
                }
            }
        });
        return layout;
    }
}
