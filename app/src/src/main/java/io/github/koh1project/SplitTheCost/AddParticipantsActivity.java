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

import io.github.koh1project.SplitTheCost.dao.MemberDao;
import io.github.koh1project.SplitTheCost.dao.TypesDao;


public class AddParticipantsActivity extends Activity {

    private MemberDao memberDao;
    private MemberListCheckableAdapter adapter;
    private ListView list;
    private Spinner spinner;
    private TypesDao typesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participants);
        memberDao = new MemberDao(this);
        typesDao = new TypesDao(this);
        spinner = findViewById(R.id.extraGroupSpinner);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this,
                                       android.R.layout.simple_spinner_dropdown_item,
                                       typesDao.getAllGroups());
            adapter.add(getString(R.string.group_name_unset));
            spinner.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = findViewById(R.id.participantsList);

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
                            /*
                            adapter = new MemberListCheckableAdapter(AddParticipantsActivity.this,
                                                                     memberDao.getAllMembersOrdered(),
                                                                     R.layout.choice_member_list_item);
                        */
                        adapter = new MemberListCheckableAdapter(AddParticipantsActivity.this,memberDao.getAllMembersOrdered(),R.layout.choice_member_list_item);



                            list.setAdapter(adapter);

                        } else if (groupName.equals(getString(R.string.group_name_unset))) {
                            adapter = new MemberListCheckableAdapter
                                    (AddParticipantsActivity.this, memberDao
                                            .getNoGroupMembers(), R
                                             .layout .choice_member_list_item);
                            list.setAdapter(adapter);
                            spinner.setSelection(position);

                        } else {

                            adapter = new MemberListCheckableAdapter
                                    (AddParticipantsActivity.this, memberDao
                                            .getGroupMembers(groupName), R
                                             .layout
                                             .choice_member_list_item);

                            list.setAdapter(adapter);
                            spinner.setSelection(position);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        adapter = new MemberListCheckableAdapter(AddParticipantsActivity.this,
                                                                 memberDao.getAllMembersOrdered(),
                                                                 R.layout.choice_member_list_item);
                        list.setAdapter(adapter);
                    }
                });
    }

    public void onClickAddMemberButton(View view) {
        Intent i = new Intent(this, AddMemberActivity.class);
        startActivity(i);
    }

}
