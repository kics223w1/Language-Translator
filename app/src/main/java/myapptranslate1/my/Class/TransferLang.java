package myapptranslate1.my.Class;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transferlang")
public class TransferLang {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String selangnameL;
    private String selangnameR;
    private String bookstore;

    public TransferLang(String selangnameL , String selangnameR , String bookstore) {
        this.selangnameL = selangnameL;
        this.selangnameR = selangnameR;
        this.bookstore = bookstore;
    }


    public String getSelangnameR() {
        return selangnameR;
    }

    public void setSelangnameR(String selangnameR) {
        this.selangnameR = selangnameR;
    }

    public String getSelangnameL() {
        return selangnameL;
    }

    public void setSelangnameL(String selangnameL) {
        this.selangnameL = selangnameL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookstore() {
        return bookstore;
    }

    public void setBookstore(String bookstore) {
        this.bookstore = bookstore;
    }
}
