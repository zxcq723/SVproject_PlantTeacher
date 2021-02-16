package com.example.plantteacher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class CommentTable {
    private static final String Tag = "comment";
    private static SQLiteDatabase database;
    private static String outline = "create table if not exists ";
    private static String comment = "" +
            "(" +
            "_id integer PRIMARY KEY autoincrement, " +
            "id text, "+
            "comment text"+
            ")";
    private static String name;

    public static void openDatabase(Context context, String databaseName)
    {
        println("openDatabase 호출됨");

            name = databaseName;
            CommentTable.commentHelper helper = new CommentTable.commentHelper(context, databaseName, null, 1);
            database = helper.getWritableDatabase();

    }

    public static void createTable(String tableName)
    {
        println("createTable 호출 됨 : " +tableName);
        if(database !=null)
        {
                String table = outline + tableName + comment;
                database.execSQL(table);
                println("댓글 "+ tableName+" 테이블 생성 요청됨");
        }
        else
            println("데이터베이스를 먼저 오픈하세요");
    }

    public static void insert(String tableName, String id, String comment)
    {
        println("insert 호출됨");
        if(database != null){
            String sql = "insert into " + tableName + "(id, comment)" +
                    " values(?, ?)";
            Object[] params = {id, comment};
            database.execSQL(sql,params);
            println("comment 데이터 추가");
        }else
            println("먼저 데이터 베이스를 오픈하세요");
    }


    public static Cursor selectData(String tableName){
        println("selectData 호출됨");
        Cursor cursor = null;
        if(database!=null){
            String sql ="select id, comment from " + tableName;
            cursor = database.rawQuery(sql,null);
            println("조회된 데이터 개수 : " + cursor.getCount());
        }
        return cursor;
    }

    public static void println(String data){
        Log.d(Tag,data);
    }

    static class commentHelper extends SQLiteOpenHelper {
        public commentHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            println("onCreate() 호출됨.");

            String sql = "create table if not exists " + name + "(_id integer PRIMARY KEY autoincrement, " +
                    "id text, "+
                    "comment text)";
            db.execSQL(sql);
            println("테이블 생성됨.");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("onUpgrade 호출됨 : "+oldVersion + " "+newVersion);
            if(newVersion > 1){
                String tableName = name;
                db.execSQL("drop table if exists " + tableName);
                println("테이블 삭제함");
                String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, " +
                        "id text, "+
                        "comment text)";
                db.execSQL(sql);

                println("테이블 새로 생성됨.");
            }


        }
    }
}
