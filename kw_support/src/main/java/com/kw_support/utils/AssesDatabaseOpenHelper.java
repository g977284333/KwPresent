package com.kw_support.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by G-chen on 2015-5-20.
 */
public class AssesDatabaseOpenHelper {
    private Context mContext;
    private String mDatabaseName;

    public AssesDatabaseOpenHelper(Context context, String databaseName) {
        this.mContext = context;
        this.mDatabaseName = mDatabaseName;
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        File dbFile = mContext.getDatabasePath(mDatabaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized SQLiteDatabase getReadableDatabase() {
        File dbFile = mContext.getDatabasePath(mDatabaseName);
        if(dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    public String getDatabaseName() {
        return mDatabaseName;
    }

    private void copyDatabase(File dbFile) throws IOException{
        InputStream stream = mContext.getAssets().open(mDatabaseName);
        FileUtil.writeFile(dbFile, stream);
        stream.close();
    }
}
