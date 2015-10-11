package com.rut0.kurisumessenger.chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winston on 9/17/2015.
 */
public class ChatDatabase {

    public final String TABLE_NAME;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MESSAGE = "message";
    public SQLiteDatabase database;
    private Helper _helper;

    public static final String DATABASE_NAME = "chat.db";
    public static final int DATABASE_VERSION = 1;

    public ChatDatabase(Context ctx, String name) {
        TABLE_NAME = name;
        _helper = new Helper(ctx, TABLE_NAME);
    }

    public Cursor getMessages() {
        return getMessages(-1);
    }

    public Cursor getMessages(int limit) {
        Cursor c = null;
        if (database == null)
            database = _helper.open();
        c = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_MESSAGE}, null, null, null, null, null, (limit > 0 ? limit + "" : null));
        return c;
    }

    public List<MessageData> getMessagesL() {
        Cursor c = getMessages();
        List<MessageData> msgs = new ArrayList<MessageData>();
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex(COLUMN_NAME));
            String message = c.getString(c.getColumnIndex(COLUMN_MESSAGE));
            MessageData data = new MessageData(name, message);
            msgs.add(data);
        }
        return msgs;
    }

    public boolean addMessage(String name, String message) {
        ContentValues vals = new ContentValues();
        vals.put(COLUMN_NAME, name);
        vals.put(COLUMN_MESSAGE, message);
        return database.insert(TABLE_NAME, null, vals) != -1;
    }

    private static class Helper extends SQLiteOpenHelper {
        private String TABLE_NAME;

        private String CREATE_TABLE;

        public Helper(Context ctx, String name) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
            TABLE_NAME = name;
            CREATE_TABLE = "CREATE TABLE " +
                    TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_MESSAGE + " TEXT);";
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public SQLiteDatabase open() {
            return getWritableDatabase();
        }
    }
}
