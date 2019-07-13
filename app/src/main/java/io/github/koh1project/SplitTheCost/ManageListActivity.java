package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ManageListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);
    }


    public void onClickAddMemberButton(View view){
        Intent i = new Intent(this , AddMemberActivity.class);
        startActivity(i);
    }

    public void onClickMemberListButton(View view){
        Intent i = new Intent(this , MemberListActivity.class);
        startActivity(i);

    }
    public void onClickGroupButton(View view){
        Intent i = new Intent(this, GroupActivity.class);
        startActivity(i);
    }

}
