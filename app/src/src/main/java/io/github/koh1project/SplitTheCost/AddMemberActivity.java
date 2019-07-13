package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import io.github.koh1project.SplitTheCost.dao.MemberDao;
import io.github.koh1project.SplitTheCost.dao.TypesDao;


public class AddMemberActivity extends Activity {

    private TypesDao typesDao;
    private MemberDao memberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        memberDao = new MemberDao(this);
        typesDao = new TypesDao(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            EditText name = findViewById(R.id.nameEdit1);
            name.setText("");

            ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_dropdown_item
                    , typesDao.getAllGroups());
            Spinner groupSpinner = findViewById(R.id.groupSpinner1);
            groupSpinner.setAdapter(groupAdapter);

            ArrayAdapter<String> positionAdapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_dropdown_item
                    , typesDao.getAllPositions());
            Spinner positionSpinner = findViewById(R.id.positionSpinner1);
            positionSpinner.setAdapter(positionAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClickAddGroupButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.add_group_title));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                try {
                    typesDao.insertGroup(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(AddMemberActivity.this, name + getString(R.string.added), Toast.LENGTH_SHORT).show();
                AddMemberActivity.this.onResume();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    public void onClickAddPositionButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.add_position_title));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                try {
                    typesDao.insertPosition(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(AddMemberActivity.this, name + getString(R.string.added), Toast.LENGTH_SHORT).show();
                AddMemberActivity.this.onResume();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void onClickRegisterButton(View view) {
        EditText nameEdit = findViewById(R.id.nameEdit1);
        Spinner groupSpinner = findViewById(R.id.groupSpinner1);
        Spinner positionSpinner = findViewById(R.id.positionSpinner1);

        String name = nameEdit.getText().toString();
        String groupName = groupSpinner.getSelectedItem().toString();
        String positionName = positionSpinner.getSelectedItem().toString();
        memberDao.insertMember(name, groupName, positionName);
        Toast.makeText(this, name + getString(R.string.added), Toast.LENGTH_SHORT).show();
        onResume();


    }


}
