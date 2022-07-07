package com.example.project.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project.Modals.User;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Cleaner.db"; // data base name

    public DBHelper( Context context ) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        // create users table
        MyDB.execSQL("create table users(" +
                "email TEXT primary key," +
                "name TEXT," +
                "password TEXT," +
                "userType TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop table if exists users");
    }

    /*============ start user CRUD operations ============**/


    /**
     * Insert user to data base function
     * @param user
     * @return Boolean
     */
    public boolean saveUser(User user){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",user.getEmail());
        contentValues.put("name",user.getName());
        contentValues.put("userType",user.getType());
        contentValues.put("password",user.getPassword());

        long results = MyDB.insert("users",null,contentValues);

        return results != -1;
    }

    /**
     * Sign in user by checking data base values
     * @param email
     * @param password
     * @return Boolean
     */
    public boolean checkUserEmailAndPassword(String email,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(
                "Select * from users where email = ? and password = ?",
                new String[] {email,password}
        );

        return cursor.getCount() > 0;
    }

    /**
     * Check user email address if already used in data base
     * @param email
     * @return Boolean
     */
    public boolean checkUserEmail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(
                "Select * from users where email = ?",
                new String[] {email}
        );

        return cursor.getCount() > 0;
    }

    /**
     * Select user details by user email address
     * @param email
     * @return user
     */
    public User getUserDetails(String email){
        User user = new User();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(
                "Select * from users where email = ?",
                new String[] {email}
        );

        if (!(cursor.getCount() > 0)){
            user.setEmail(cursor.getString(0));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setType(cursor.getString(3));
        }

        return user;
    }

    /**
     * Delete user details by email address
     * @param email
     * @return Boolean
     */
    public boolean deleteUserByEmail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(
                "Delete from users where email = ?",
                new String[] {email}
        );

        return cursor.getCount() > 0;
    }

    /**
     * Update user details by email address
     * @param user
     * @return Boolean
     */
    public boolean updateUser(User user){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery(
                "Update users SET " +
                        "email = ?," +
                        "name = ?," +
                        "password = ?," +
                        "type = ?" +
                        "where email = ?",
                new String[] {user.getEmail(),
                        user.getName(),
                        user.getPassword(),
                        user.getType(),
                        user.getEmail()
                }
        );

        return cursor.getCount() > 0;
    }

    /*============ end user CRUD operations ============**/

}
