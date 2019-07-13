package io.github.koh1project.SplitTheCost.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

import io.github.koh1project.SplitTheCost.MyDBHelper;
import io.github.koh1project.SplitTheCost.domain.Member;

public class MemberDao extends BaseDao {

    private TypesDao typesDao;

    public MemberDao(Context context) {
        this.context = context;
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
        typesDao = new TypesDao(context);
    }


    public void insertMember(String name, String groupName, String positionName) {

        ContentValues cv = new ContentValues();

        cv.put("name", name);
        if (groupName != null) {
            cv.put("group_id", typesDao.getGroupId(groupName));
        }
        if (positionName != null) {
            cv.put("position_id", typesDao.getPositionId(positionName));
        }
        db.insert("members", null, cv);
    }

    public List<Member> getAllMembers() {
        Cursor cs = db.rawQuery("SELECT * FROM members LEFT JOIN groups ON members.group_id = " +
                                        "groups.id  LEFT JOIN positions ON members.position_id = " +
                                        "positions.id",
                                null);
        List<Member> members = new ArrayList<>();
        if (cs.moveToFirst())
            do {
                Member member = new Member();
                member.setId(Integer.parseInt(cs.getString(0)));
                member.setName(cs.getString(1));
                member.setGroupName(cs.getString(5));
                member.setPositionName(cs.getString(7));
                members.add(member);
            } while (cs.moveToNext());
        return members;
    }

    public List<Member> getAllMembersOrdered() {
        Cursor cs = db.rawQuery("SELECT * FROM members LEFT JOIN groups ON members.group_id = " +
                                        "groups.id  LEFT JOIN positions ON members.position_id = " +
                                        "positions.id LEFT JOIN checklist ON members.id = " +
                                        "checklist" +
                                        ".member_id ORDER BY checklist.member_id DESC",
                                null);
        List<Member> members = new ArrayList<>();
        if (cs.moveToFirst())
            do {
                Member member = new Member();
                member.setId(Integer.parseInt(cs.getString(0)));
                member.setName(cs.getString(1));
                member.setGroupName(cs.getString(5));
                member.setPositionName(cs.getString(7));
                members.add(member);
            } while (cs.moveToNext());
        return members;
    }

    public Member findMemberById(String id) {
        Member member = new Member();
        Cursor cs = db.rawQuery("SELECT * FROM members LEFT JOIN groups ON members.group_id = " +
                                        "groups.id  LEFT JOIN positions ON members.position_id = " +
                                        "positions.id WHERE members.id = ?", new String[]{id});

        if (cs.moveToFirst()) {
            member.setId(Integer.parseInt(cs.getString(0)));
            member.setName(cs.getString(1));
            if (cs.getString(5) != null) {
                member.setGroupName(cs.getString(5));
            }
            if (cs.getString(7) != null) {
                member.setPositionName(cs.getString(7));
            }
        }
        cs.close();
        return member;
    }

    public void updateMember(Member member) {
        ContentValues cv = new ContentValues();
        cv.put("name", member.getName());
        cv.put("group_id", typesDao.getGroupId(member.getGroupName()));
        cv.put("position_id", typesDao.getPositionId(member.getPositionName()));

        db.update("members", cv, "id=?", new String[]{member.getId().toString()});

    }

    public void deleteMember(Member member) {
        db.delete("members", "id=?", new String[]{member.getId().toString()});

    }

    public List<Member> getGroupMembers(String groupName) {
        Cursor cs = db.rawQuery("SELECT * FROM members LEFT JOIN groups ON members.group_id = " +
                                        "groups.id  LEFT JOIN positions ON members.position_id = " +
                                        "positions.id  LEFT JOIN checklist ON members.id = " +
                                        "checklist.member_id " +
                                        "WHERE groups.group_name=? ORDER BY checklist.member_id " +
                                        "DESC",
                                new String[]{groupName});
        List<Member> members = new ArrayList<>();
        if (cs.moveToFirst())
            do {
                Member member = new Member();
                member.setId(Integer.parseInt(cs.getString(0)));
                member.setName(cs.getString(1));
                member.setGroupName(cs.getString(5));
                member.setPositionName(cs.getString(7));
                members.add(member);
            } while (cs.moveToNext());
        return members;
    }

    public List<Member> getNoGroupMembers() {
        Cursor cs = db.rawQuery("SELECT * FROM members LEFT JOIN groups ON members.group_id = " +
                                        "groups.id  LEFT JOIN positions ON members.position_id = " +
                                        "positions.id  LEFT JOIN checklist ON members.id = " +
                                        "checklist.member_id " +
                                        "WHERE groups.group_name IS NULL ORDER BY checklist.member_id " +
                                        "DESC",
                                null);
        List<Member> members = new ArrayList<>();
        if (cs.moveToFirst())
            do {
                Member member = new Member();
                member.setId(Integer.parseInt(cs.getString(0)));
                member.setName(cs.getString(1));
                member.setGroupName(cs.getString(5));
                member.setPositionName(cs.getString(7));
                members.add(member);
            } while (cs.moveToNext());
        return members;
    }
}
