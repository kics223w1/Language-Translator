package myapptranslate1.my.Class;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "russian")
public class RussianBookLang {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String usernameL;
    private String usernameR;

    public RussianBookLang(String usernameL , String usernameR) {
        this.usernameL = usernameL;
        this.usernameR = usernameR;
    }

    public int getId() {
        return id;
    }

    public String getUsernameL() {
        return usernameL;
    }

    public String getUsernameR() {
        return usernameR;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsernameL(String usernameL) {
        this.usernameL = usernameL;
    }

    public void setUsernameR(String usernameR) {
        this.usernameR = usernameR;
    }
}
