package io.github.koh1project.SplitTheCost.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import io.github.koh1project.SplitTheCost.MyDBHelper;


public abstract class BaseDao {
    protected MyDBHelper helper;
    protected SQLiteDatabase db;
    protected Context context;
}
