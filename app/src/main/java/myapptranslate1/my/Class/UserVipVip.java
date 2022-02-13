package myapptranslate1.my.Class;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "uservipvip")
public class UserVipVip {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;


    public UserVipVip(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
