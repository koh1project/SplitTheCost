package io.github.koh1project.SplitTheCost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.github.koh1project.SplitTheCost.dao.TypesDao;

public class GroupListActivity extends Activity {

    private TypesDao typesDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        typesDao = new TypesDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView list = findViewById(R.id.groupListView);
        try {
            List<String> data = typesDao.getAllGroups();
            ArrayAdapter adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    data);
            list.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View
                    view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog
                        .Builder(GroupListActivity.this);
                builder.setTitle(getString(R.string.name_edit));

                final EditText input = new EditText(GroupListActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                final String selected = ((TextView) view).getText()
                        .toString();

                input.setText(selected, null);
                builder.setView(input);


                builder.setPositiveButton(getString(R.string.save_button), new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String edited = input.getText().toString();
                        Log.d("確認edited ", edited);
                        if (typesDao.updateGroup(selected, edited)) {
                            Toast.makeText(GroupListActivity.this, getString(R.string.complete_modification),
                                           Toast.LENGTH_SHORT).show();
                            onResume();

                        } else {
                            Toast.makeText(GroupListActivity.this, edited +
                                    getString(R.string.already_added), Toast
                                                   .LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.delete_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder confirm = new AlertDialog
                                .Builder(GroupListActivity.this);
                        confirm.setTitle(getString(R.string.delete_confirm_title));
                        confirm.setMessage(getString(R.string.delete_confirm_real));
                        confirm.setPositiveButton(getString(R.string.delete_button), new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                typesDao.deleteGroup(selected);
                                Toast.makeText(GroupListActivity.this,
                                               selected + getString(R.string.deleted), Toast
                                                       .LENGTH_SHORT).show();
                                onResume();

                            }
                        });
                        confirm.setNegativeButton(getString(R.string.return_button), new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        confirm.show();
                    }
                });
                builder.show();
            }
        });
    }


    public void onClickAddNewGroupButton(View view){
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
                Toast.makeText(GroupListActivity.this, name + getString(R.string.added), Toast.LENGTH_SHORT).show();
                GroupListActivity.this.onResume();

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

}



