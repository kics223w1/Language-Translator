package myapptranslate1.my.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import myapptranslate1.my.Class.BelarusBookLang;
import myapptranslate1.my.Class.BookSelect;
import myapptranslate1.my.Class.FranceBookLang;
import myapptranslate1.my.Class.GermanyBookLang;
import myapptranslate1.my.Class.PolandBookLang;
import myapptranslate1.my.Class.RussianBookLang;
import myapptranslate1.my.Class.TransferLang;
import myapptranslate1.my.Class.UserLang;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertTransferLang(TransferLang transferLang);

    @Insert
    void insertUserEnglish(UserLang userLang);

    @Insert
    void insertUserPoland(PolandBookLang polandBookLang);

    @Insert
    void insertUserBelarus(BelarusBookLang belarusBookLang);

    @Insert
    void insertUserFrance(FranceBookLang franceBookLang);

    @Insert
    void insertUserRussian(RussianBookLang russianBookLang);

    @Insert
    void insertUserGermanty(GermanyBookLang germanyBookLang);

    @Insert
    void insertBookSelect(BookSelect bookSelect);

    @Query("SELECT bookstore FROM transferlang")
    String getBookstorename();

    @Query("SELECT COUNT(usernameL) FROM user")
    int getSizeEnglishBook();

    @Query("SELECT COUNT(usernameL) FROM belarus")
    int getSizeBelarusBook();

    @Query("SELECT COUNT(usernameL) FROM france")
    int getSizeFranceBook();

    @Query("SELECT COUNT(usernameL) FROM germany")
    int getSizeGermanyBook();

    @Query("SELECT COUNT(usernameL) FROM russian")
    int getSizeRussianBook();

    @Query("SELECT COUNT(usernameL) FROM poland")
    int getSizePolandBook();

    @Query("SELECT name FROM bookselect WHERE id = :id")
    String setBookSelect(int id);

    @Query("SELECT * FROM transferlang")
    List<TransferLang> getListTransferlang();

    @Query("SELECT * FROM bookselect")
    List<BookSelect> getListBookSelect();

    @Query("SELECT * FROM user")
    List<UserLang> getListEnglish();

    @Query("SELECT * FROM poland")
    List<UserLang> getListPoland();

    @Query("SELECT * FROM germany")
    List<UserLang> getListGermany();

    @Query("SELECT * FROM france")
    List<UserLang> getListFrance();

    @Query("SELECT * FROM russian")
    List<UserLang> getListRussian();

    @Query("SELECT * FROM belarus")
    List<UserLang> getListBelarus();

    @Query("DELETE FROM user WHERE usernameL = :nameL")
    void DeleteUserEnglish(String nameL);

    @Query("DELETE FROM belarus WHERE usernameL = :nameL")
    void DeleteUserBelarus(String nameL);

    @Query("DELETE FROM france WHERE usernameL = :nameL")
    void DeleteUserFrance(String nameL);

    @Query("DELETE FROM poland WHERE usernameL = :nameL")
    void DeleteUserPoland(String nameL);

    @Query("DELETE FROM russian WHERE usernameL = :nameL")
    void DeleteUserRussian(String nameL);

    @Query("DELETE FROM germany WHERE usernameL = :nameL")
    void DeleteUserGermany(String nameL);

    @Query("SELECT * FROM user WHERE usernameL= :nameL")
    List<UserLang> checkEnglishLangExist(String nameL);

    @Query("SELECT * FROM belarus WHERE usernameL= :nameL")
    List<UserLang> checkBelarusLangExist(String nameL);

    @Query("SELECT * FROM germany WHERE usernameL= :nameL")
    List<UserLang> checkGermanyLangExist(String nameL);

    @Query("SELECT * FROM poland WHERE usernameL= :nameL")
    List<UserLang> checkPolandLangExist(String nameL);

    @Query("SELECT * FROM russian WHERE usernameL= :nameL")
    List<UserLang> checkRussianLangExist(String nameL);

    @Query("SELECT * FROM france WHERE usernameL= :nameL")
    List<UserLang> checkFranceLangExist(String nameL);

    @Query("UPDATE transferlang SET bookstore = :name")
    void updateBookstore(String name);

    @Query("SELECT * FROM bookselect WHERE name = :namebook")
    BookSelect setIdbookSelect(String namebook);

    @Query("SELECT * FROM bookselect WHERE id = :id")
    BookSelect setNameBookSelect(int id);

    @Query("UPDATE bookselect SET name = :namebook WHERE id = :idbook")
    void updateNameBook(String namebook , int idbook);

    @Query("SELECT * FROM bookselect WHERE name = :namebook")
    List<BookSelect> isCheckNameBookEsixt(String namebook);

    @Update
    void updateTransferLang(TransferLang transferLang);

    @Query("UPDATE user SET usernameR = :nameR WHERE usernameL = :nameL")
    void update_nameL_UserLang(String nameR , String nameL);

    @Query("UPDATE germany SET usernameR = :nameR WHERE usernameL = :nameL")
    void update_nameL_GermanyLang(String nameR , String nameL);

    @Query("UPDATE belarus SET usernameR = :nameR WHERE usernameL = :nameL")
    void update_nameL_BelarusLang(String nameR , String nameL);

    @Query("UPDATE poland SET usernameR = :nameR WHERE usernameL = :nameL")
    void update_nameL_PolandLang(String nameR , String nameL);

    @Query("UPDATE russian SET usernameR = :nameR WHERE usernameL = :nameL")
    void update_nameL_RussianLang(String nameR , String nameL);

    @Query("UPDATE france SET usernameR = :nameR WHERE usernameL = :nameL")
    void update_nameL_FranceLang(String nameR , String nameL);

}