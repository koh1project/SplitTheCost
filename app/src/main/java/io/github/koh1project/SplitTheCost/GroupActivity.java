package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.github.koh1project.SplitTheCost.dao.TypesDao;

public class GroupActivity extends Activity {

    private TypesDao typesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        typesDao = new TypesDao(this);
    }

    public void onClickGroupListButton(View view) {
        Intent i = new Intent(this, GroupListActivity.class);
        startActivity(i);
    }

    public void onClickPositionListButton(View view) {
        Intent i = new Intent(this, PositionListActivity.class);
        startActivity(i);
    }

}
