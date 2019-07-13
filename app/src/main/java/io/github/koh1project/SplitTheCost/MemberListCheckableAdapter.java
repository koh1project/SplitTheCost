package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import java.util.List;

import io.github.koh1project.SplitTheCost.dao.CheckListDao;
import io.github.koh1project.SplitTheCost.dao.MemberDao;
import io.github.koh1project.SplitTheCost.domain.Member;

public class MemberListCheckableAdapter extends BaseAdapter {
    private Context context = null;
    private List<Member> data = null;
    private int resource = 0;
    private CheckListDao checkListDao;
    private MemberDao memberDao;

    public MemberListCheckableAdapter(Context context, List<Member> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
        checkListDao = new CheckListDao(context);
        memberDao = new MemberDao(context);
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
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        final Member member = (Member) this.getItem(position);
        ConstraintLayout layout = (ConstraintLayout) activity.getLayoutInflater().inflate
                (resource, null);

        final CheckBox check = layout.findViewById(R.id.memberName);
        check.setText(member.getName());


        final TextView group = layout.findViewById(R.id.memberGroup);
        group.setText(member.getGroupName());

        TextView pos = layout.findViewById(R.id.memberPosition);
        pos.setText(member.getPositionName());

        final TextView id = layout.findViewById(R.id.id);
        id.setText(member.getId().toString());

        if (checkListDao.isExist(id.getText().toString())) {
            check.setChecked(true);
        }

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String memberId = id.getText().toString();

                if (isChecked) {
                    checkListDao.insertMemberById(memberId);
                    Log.d("CHECKリスナー", memberId + "が登録された");

                } else {
                    checkListDao.deleteMemberById(memberId);
                    Log.d("CHECKリスナー", memberId + "が削除");
                }
            }
        });

        return layout;
    }

}
