package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import io.github.koh1project.SplitTheCost.dao.MemberDao;
import io.github.koh1project.SplitTheCost.dao.TypesDao;
import io.github.koh1project.SplitTheCost.domain.Member;

public class EditMemberActivity extends Activity {

    private MemberDao memberDao;
    private TypesDao typesDao;
    private String id;
    private Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        memberDao = new MemberDao(this);
        typesDao = new TypesDao(this);

        Intent i = getIntent();
        id = i.getStringExtra(getString(R.string.id));
    }

    @Override
    protected void onResume() {

        super.onResume();

        try {
            member = memberDao.findMemberById(id);
            EditText nameEdit = findViewById(R.id.nameEdit2);
            nameEdit.setText(member.getName());

            ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_dropdown_item
                    , typesDao.getAllGroups());
            Spinner groupSpinner = findViewById(R.id.groupSpinner2);

            groupSpinner.setAdapter(groupAdapter);

            if (member.getGroupName() != null) {
                groupSpinner.setSelection(
                        Integer.parseInt(findSpinnersId(groupSpinner, member.getGroupName())),
                        false);
            }


            ArrayAdapter<String> positionAdapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_dropdown_item
                    , typesDao.getAllPositions());
            Spinner positionSpinner = findViewById(R.id.positionSpinner2);
            positionSpinner.setAdapter(positionAdapter);

            if (member.getPositionName() != null) {
                positionSpinner.setSelection(Integer.parseInt(findSpinnersId(positionSpinner, member
                        .getPositionName())), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickSaveButton(View view) {
        EditText name = findViewById(R.id.nameEdit2);
        member.setName(name.getText().toString());

        Spinner group = findViewById(R.id.groupSpinner2);
        if (group.getSelectedItem() != null) {
            member.setGroupName(group.getSelectedItem().toString());
        }
        Spinner position = findViewById(R.id.positionSpinner2);
        if (position.getSelectedItem() != null) {
            member.setPositionName(position.getSelectedItem().toString());
        }
        memberDao.updateMember(member);
        Toast.makeText(this, member.getName() + getString(R.string.modified), Toast.LENGTH_SHORT).show();
        finish();

    }

    public void onClickDeleteButton(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.delete_confirm_title));
        builder.setMessage(member.getName() +getString(R.string.delete_confirm));
        builder.setPositiveButton(getString(R.string.delete_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                memberDao.deleteMember(member);
                Toast.makeText(EditMemberActivity.this, member.getName() + getString(R.string.deleted), Toast
                        .LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        builder.show();
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
                Toast.makeText(EditMemberActivity.this, name + getString(R.string.added), Toast.LENGTH_SHORT).show();
                EditMemberActivity.this.onResume();

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
                Toast.makeText(EditMemberActivity.this, name + getString(R.string.added), Toast.LENGTH_SHORT).show();
                EditMemberActivity.this.onResume();
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

    public String findSpinnersId(Spinner spinner, String keyWord) {
        Log.d("Keyword", keyWord);
        for (int i = 0; i < spinner.getCount(); i++) {
            if (keyWord.equals(spinner.getItemAtPosition(i))) {
                Log.d("検索", toString().valueOf(i));
                return toString().valueOf(i);
            }
        }
        return null;
    }


}
