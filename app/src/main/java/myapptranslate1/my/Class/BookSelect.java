package myapptranslate1.my.Class;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookselect")
public class BookSelect {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public BookSelect(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
