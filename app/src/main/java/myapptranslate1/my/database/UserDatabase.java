package myapptranslate1.my.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import myapptranslate1.my.Class.BelarusBookLang;
import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.Class.FranceBookLang;
import myapptranslate1.my.Class.GermanyBookLang;
import myapptranslate1.my.Class.PolandBookLang;
import myapptranslate1.my.Class.RussianBookLang;
import myapptranslate1.my.Class.TransferLang;
import myapptranslate1.my.Class.UserLang;
import myapptranslate1.my.Class.UserVip;
import myapptranslate1.my.Class.UserVipVip;


@Database(entities = {UserLang.class ,
        PolandBookLang.class , FranceBookLang.class ,
        BelarusBookLang.class , RussianBookLang.class ,
        GermanyBookLang.class , BookSelect.class  ,
        TransferLang.class , UserVip.class , UserVipVip.class} , version = 1)
public abstract class UserDatabase extends RoomDatabase {


    private static final  String DATABASE_NAME = "user.db";
    private static UserDatabase instance;


    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , UserDatabase.class , DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


    public abstract UserDAO userDao();

}
