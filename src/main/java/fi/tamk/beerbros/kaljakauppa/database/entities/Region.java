package fi.tamk.beerbros.kaljakauppa.database.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Region implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    public Region(){}
    
    public Region(int id, String name) {
        this.id = id;
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
