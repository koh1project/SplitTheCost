package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



import java.util.List;

import io.github.koh1project.SplitTheCost.dao.MemberDao;
import io.github.koh1project.SplitTheCost.dao.TypesDao;

public class MemberListActivity extends Activity {

    private MemberDao memberDao;
    private TypesDao typesDao;
    private Spinner spinner;
    private MemberListAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        memberDao = new MemberDao(this);
        typesDao = new TypesDao(this);

    }

    @Override
    protected void onResume() {
        spinner = findViewById(R.id.extraGroupSpinnerForList);
        super.onResume();
        try {
            List<String> groups = typesDao.getAllGroups();
            groups.add(getString(R.string.group_name_unset));

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_dropdown_item,
                            groups);
            spinner.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = findViewById(R.id.memberList);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position,
                                               long id) {
                        String groupName = ((TextView) view).getText()
                                .toString();
                        Log.d("グループ名", groupName);
                        Log.d("グループ名", toString().valueOf(groupName.isEmpty()));

                        if (groupName.isEmpty()) {
                            adapter = new MemberListAdapter(MemberListActivity.this,
                                    memberDao.getAllMembersOrdered(),
                                    R.layout.member_list_item);
                            list.setAdapter(adapter);
                        } else if (groupName.equals(getString(R.string.group_name_unset))) {
                            adapter = new MemberListAdapter
                                    (MemberListActivity.this, memberDao
                                            .getNoGroupMembers(), R
                                            .layout.member_list_item);
                            list.setAdapter(adapter);
                            spinner.setSelection(position);
                        } else {
                            adapter = new MemberListAdapter
                                    (MemberListActivity.this, memberDao
                                            .getGroupMembers(groupName), R
                                            .layout.member_list_item);
                            list.setAdapter(adapter);
                            spinner.setSelection(position);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        adapter = new MemberListAdapter(MemberListActivity.this,
                                memberDao.getAllMembersOrdered(),
                                R.layout.choice_member_list_item);
                        list.setAdapter(adapter);
                    }
                });


//        MemberListAdapter adapter = new MemberListAdapter(
//                this,
//                memberDao.getAllMembers(),
//                R.layout.member_list_item);
//        list.setAdapter(adapter);


        //クリックされたら
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idText = ((TextView) view.findViewById(R.id.id)).getText().toString();
                Intent i = new Intent(MemberListActivity.this, EditMemberActivity.class);
                i.putExtra(getString(R.string.id), idText);
                startActivity(i);
            }
        });
    }

    public void onClickAddMemberButton(View view) {
        Intent i = new Intent(this, AddMemberActivity.class);
        startActivity(i);
    }


}
