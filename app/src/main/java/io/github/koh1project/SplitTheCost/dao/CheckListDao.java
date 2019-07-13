package io.github.koh1project.SplitTheCost.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.koh1project.SplitTheCost.MyDBHelper;
import io.github.koh1project.SplitTheCost.domain.Member;


public class CheckListDao extends BaseDao {

    private MemberDao memberDao;

    public CheckListDao(Context context) {
        this.context = context;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
        memberDao = new MemberDao(context);
    }

    public void createCheckListTable() {
        helper.createCheckListTable(db);
    }

    public void dropCheckListTable() {
        helper.dropCheckListTable(db);
    }

    public Integer getTotalAmount() {
        try {
            Cursor cs = db.rawQuery("SELECT COUNT(id) FROM checklist", null);
            cs.moveToFirst();
            Integer num = Integer.parseInt(cs.getString(0));
            cs.close();
            return num;
        } catch (SQLiteException e) {
            return 0;
        }
    }

    public void insertMemberById(String id) {
        Member member = memberDao.findMemberById(id);
        ContentValues cv = new ContentValues();
        cv.put("name", member.getName());
        cv.put("member_id", member.getId());

        db.insert("checklist", null, cv);
    }

    public void deleteMemberById(String id) {
        db.delete("checklist", "member_id=?", new String[]{id});
    }

    public boolean isExist(String memberId) {
        Cursor cs = db.rawQuery("SELECT id FROM checklist WHERE member_id=?", new
                String[]{memberId});
        if (cs.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public String[] getMembers() {
        List<String> m = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT * FROM checklist LEFT JOIN members ON checklist.member_id" +
                                        " = members.id  WHERE special IS NULL ORDER BY members" +
                                        ".position_id ", null);

        if (cs.moveToFirst()) {
            do {
                m.add(cs.getString(1));
            } while (cs.moveToNext());
        }
        String[] members = new String[m.size()];
        for (int i = 0; i < m.size(); i++) {
            members[i] = m.get(i);
        }
        return members;
    }

    public String[] getMembers(String specialId) {
        List<String> m = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT * FROM checklist LEFT JOIN members ON checklist.member_id" +
                                        " = members.id  WHERE special IS NULL OR special =? ORDER" +
                                        " BY members" +
                                        ".position_id ", new String[]{specialId});

        if (cs.moveToFirst()) {
            do {
                m.add(cs.getString(1));

            } while (cs.moveToNext());
        }
        String[] members = new String[m.size()];
        for (int i = 0; i < m.size(); i++) {
            members[i] = m.get(i);
        }
       return members;
    }

    public List<Map<String, String>> getMembersAndSpecials(String specialId) {
        List<Map<String, String>> members = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT * FROM checklist LEFT JOIN members ON checklist.member_id" +
                                        " = members.id  WHERE special IS NULL OR special =? ORDER" +
                                        " BY members" +
                                        ".position_id ", new String[]{specialId});
        int i = 0;
        if (cs.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<>();
                map.put("name", cs.getString(1));
                map.put("special", cs.getString(3));
                i++;
                members.add(map);
            } while (cs.moveToNext());
        }
        return members;
    }


    public boolean[] getChecked(String specialId) {
        List<String> b = new ArrayList<>();

        Cursor cs = db.rawQuery("SELECT special FROM checklist LEFT JOIN members ON checklist" +
                                        ".member_id" +
                                        " = members.id  WHERE special IS NULL OR special =? ORDER" +
                                        " BY members" +
                                        ".position_id ", new String[]{specialId});

        if (cs.moveToFirst()) {
            b.add(cs.getString(0));
        }

        while (cs.moveToNext());

        boolean[] booleans = new boolean[b.size()];
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i) == null) {
                booleans[i] = false;
            } else {
                booleans[i] = true;
            }
        }
       return booleans;
    }


    public void updateSpecial(String name, String specialId) {
        ContentValues cv = new ContentValues();
        cv.put("special", specialId);
        db.update("checklist", cv, "name=?", new String[]{name});
    }

    public void removeSpecial(String name) {
        ContentValues cv = new ContentValues();
        cv.putNull("special");
        db.update("checklist", cv, "name=?", new String[]{name});
    }

    public String getCountSpecials(Integer specialId) {
        Cursor cs = db.rawQuery("SELECT COUNT(special) FROM checklist WHERE special = ?", new
                String[]{specialId.toString()});
        cs.moveToFirst();
        String num = cs.getString(0);
        cs.close();

        return num;
    }

    public void insertPriceTable(Integer sp1, Integer sp2, Integer sp3, Integer numPerMember) {
        ContentValues cv = new ContentValues();
        cv.put("price", sp1);
        db.update("checklist", cv, "special = ?", new String[]{"1"});
        cv.put("price", sp2);
        db.update("checklist", cv, "special=?", new String[]{"2"});
        cv.put("price", sp3);
        db.update("checklist", cv, "special=?", new String[]{"3"});
        cv.put("price", numPerMember);
        db.update("checklist", cv, "special IS NULL", null);
    }

    public List<Map<String, String>> getMembersPrices() {
        List<Map<String, String>> checks = new ArrayList<>();

        Cursor cs = db.rawQuery("SELECT name , price , id ,paid_check FROM checklist ", null);
        if (cs.moveToFirst()) {
            do {
                Map<String, String> map = new HashMap<>();
                map.put("name", cs.getString(0));
                map.put("price", cs.getString(1));
                map.put("id", cs.getString(2));
                map.put("paid", cs.getString(3));
                checks.add(map);
            } while (cs.moveToNext());
        }
        return checks;
    }

    public void insertPaidCheck(String id) {
        ContentValues cv = new ContentValues();
        cv.put("paid_check", "paid");
        db.update("checklist", cv, "id=?", new String[]{id});
    }

    public void deletePaidCheck(String id) {
        ContentValues cv = new ContentValues();
        cv.putNull("paid_check");
        db.update("checklist", cv, "id=?", new String[]{id});
    }

    public void resetPaidCheck() {
        ContentValues cv = new ContentValues();
        cv.putNull("paid_check");
        db.update("checklist", cv, null, null);
    }

    public void resetTable() {
        helper.dropCheckListTable(db);
        helper.createCheckListTable(db);
    }

    public String getTotalPrice() {
        Cursor cs = db.rawQuery("SELECT SUM(price) FROM checklist", null);
        String price = "";
        if (cs.moveToFirst()) {
            price = cs.getString(0);
        }
        cs.close();
        return price;
    }
}

