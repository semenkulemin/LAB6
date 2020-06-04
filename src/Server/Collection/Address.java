package Server.Collection;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {
    private String street; //Поле может быть null
    private String zipCode; //Поле не может быть null
    private List<String> args;

    public Address(List<String> args){
        this.args = args;
        street = args.get(0);
        zipCode = args.get(1);
    }

    public String getZipCode(){
        return this.zipCode;
    }
    public String getStreet() {return this.street;}

    public String toString(){
        return "Address: {" + street + ", " + zipCode + "}";
    }
}