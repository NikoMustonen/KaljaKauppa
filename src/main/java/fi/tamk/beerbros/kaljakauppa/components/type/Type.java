package fi.tamk.beerbros.kaljakauppa.components.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity(name = "type")
@JsonInclude(Include.NON_NULL)
public class Type implements Serializable {

    @Id
    @Column(name = "name", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "main_type", referencedColumnName = "name", nullable = true)
    private Type mainType;
    
    @OneToMany(mappedBy = "mainType")
    private Collection<Type> subTypes;
    
    public Type(){}
    
    public Type(String name, Type mainType){
        this.name = name;
        this.mainType = mainType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainType() {
        if(mainType != null && !mainType.getName().trim().equals("")) {
            return mainType.getName();
        } else {
            return null;
        }
    }

    public void setMainType(String mainType) {
        if(mainType != null && !mainType.trim().equals(""))
        this.mainType = new Type(mainType, null);
    }

    public Collection<Type> getSubTypes() {
        if(mainType == null || mainType.getName().equals("")){
            return subTypes;
        } else {
            return null;
        }
    }

    public void setSubTypes(Collection<Type> subTypes) {
        this.subTypes = subTypes;
    }
}
