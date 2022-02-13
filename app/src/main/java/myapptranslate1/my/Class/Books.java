package myapptranslate1.my.Class;

import java.io.Serializable;

public class Books implements Serializable {
    private String name;
    private int id;

    public Books(String name , int id) {
        this.name = name;
        this.id = id;
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
