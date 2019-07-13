package io.github.koh1project.SplitTheCost;


import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;
import io.github.koh1project.SplitTheCost.domain.Member;

public class MemberListAdapter extends BaseAdapter {

    private Context context ;
    private List<Member> data ;
    private int resource ;


    public MemberListAdapter(Context context, List<Member> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
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
        Member member = (Member) this.getItem(position);
        ConstraintLayout layout = (ConstraintLayout) activity.getLayoutInflater().inflate
                (resource, null);

        ImageView image = layout.findViewById(R.id.icon);

        TextView name = layout.findViewById(R.id.memberName);
        name.setText(member.getName());
        TextView group = layout.findViewById(R.id.memberGroup);
        group.setText(member.getGroupName());
        TextView pos = layout.findViewById(R.id.memberPosition);
        pos.setText(member.getPositionName());

        TextView id = layout.findViewById(R.id.id);
        id.setText(member.getId().toString());

        return layout;
    }
}
