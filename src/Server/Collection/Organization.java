package Server.Collection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Organization implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле может быть null

    public Organization(List<String> args){
            this.id = (long)(Math.random()*100000);
            this.name = args.get(0);
            this.coordinates = new Coordinates(new ArrayList<>(args.subList(1, 3)));
            this.creationDate = LocalDateTime.now();
            this.annualTurnover = Float.parseFloat(args.get(3));
            this.type = OrganizationType.valueOf(args.get(4).toUpperCase());
            this.postalAddress = new Address(new ArrayList<>(args.subList(5, 7)));
    }
    public Organization(List<String> args, Long id){
        this.id = id;
        this.name = args.get(0);
        this.coordinates = new Coordinates(new ArrayList<>(args.subList(1, 3)));
        this.creationDate = LocalDateTime.now();
        this.annualTurnover = Float.parseFloat(args.get(3));
        this.type = OrganizationType.valueOf(args.get(4).toUpperCase());
        this.postalAddress = new Address(new ArrayList<>(args.subList(5, 7)));
    }


    public Long getIdOfOrganization(){
        return id;
    }
    public String getNameOfOrganization(){
        return name;
    }
    public float getAnnualTurnover(){
        return annualTurnover;
    }
    public OrganizationType getType(){
        return type;
    }
    public Address getPostalAddress(){
        return postalAddress;
    }


    @Override
    public String toString(){
        return "Org:" + " Id: " + id + " Name: " + name + " " + coordinates.toString() + " Data: " + creationDate + " AnnTurn: " + annualTurnover  + " Orgtype: " + type  + " " + postalAddress.toString();
    }
}