package io.github.koh1project.SplitTheCost.dao
        ;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import io.github.koh1project.SplitTheCost.MyDBHelper;

public class TypesDao extends BaseDao {

    public TypesDao(Context context) {
        this.context = context;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();

    }

    public List<String> getAllPositions() throws Exception {


        Cursor cs = db.rawQuery("SELECT position_name FROM positions", null);

        cs.moveToFirst();
        List<String> positions = new ArrayList<>();
        positions.add("");
        if (cs.moveToFirst()) {
            do {
                positions.add(cs.getString(0));
            } while (cs.moveToNext());
        }
        cs.close();
        return positions;
    }

    public List<String> getAllGroups() throws Exception {

        Cursor cs = db.rawQuery("SELECT group_name FROM groups", null);
        cs.moveToFirst();
        List<String> groups = new ArrayList<>();
        groups.add("");
        if (cs.moveToFirst()) {
            do {
                groups.add(cs.getString(0));
            } while (cs.moveToNext());
        }
        cs.close();
        return groups;
    }

    public void insertGroup(String groupName) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("group_name", groupName);
        db.insert("groups", null, cv);
    }

    public void insertPosition(String positionName) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put("position_name", positionName);
        db.insert("positions", null, cv);
    }

    public String getGroupId(String groupName) {
        Cursor cs = db.rawQuery("SELECT id FROM groups WHERE group_name = ? ", new
                String[]{groupName});
        cs.moveToFirst();

        try {
            return cs.getString(0);
        } catch (Exception e) {
            return null;
        }
    }

    public String getPositionId(String positionName) {
        Cursor cs = db.rawQuery("SELECT id FROM positions WHERE position_name = ? ", new
                String[]{positionName});
        cs.moveToFirst();
        try {
            return cs.getString(0);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateGroup(String beforeName, String afterName) {
        String[] after = {afterName};
        Cursor cs = db.rawQuery("SELECT * FROM groups WHERE group_name =?", after);
        cs.moveToFirst();

        if (cs.getCount() == 0) {
            String query = "UPDATE groups SET group_name=? WHERE group_name=?";
            SQLiteStatement stmt = db.compileStatement(query);
            stmt.bindString(1, afterName);
            stmt.bindString(2, beforeName);
            stmt.executeUpdateDelete();
            return true;
        }
        return false;
    }

    public void deleteGroup(String selected) {
        db.delete("groups", "group_name=?", new String[]{selected});
    }

    public boolean updatePosition(String beforeName, String afterName) {
        String[] after = {afterName};
        Cursor cs = db.rawQuery("SELECT * FROM positions WHERE position_name =?", after);
        cs.moveToFirst();

        if (cs.getCount() == 0) {
            String query = "UPDATE positions SET position_name=? WHERE position_name=?";
            SQLiteStatement stmt = db.compileStatement(query);
            stmt.bindString(1, afterName);
            stmt.bindString(2, beforeName);
            stmt.executeUpdateDelete();
            return true;
        }
        return false;

    }

    public void deletePosition(String selected) {
        db.delete("positions", "position_name=?", new String[]{selected});
    }

    public String getTypesNameByName(String name) {
        Cursor cs = db.rawQuery("SELECT positions.position_name FROM members LEFT JOIN positions ON " +
                                        "members.position_id = positions.id WHERE members" +
                                        ".name = ? ", new String[]{name});
        if (cs.moveToFirst()) {
            return cs.getString(0);

        }
        return " ";
    }
}

