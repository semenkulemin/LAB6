package Server.Collection;

import java.io.Serializable;
import java.util.List;

public class Coordinates implements Serializable {
    private Long x;
    private Float y; //Значение поля должно быть больше -425
    private List<String> args;

    public Coordinates(List<String> args){
        this.args = args;
        x=Long.valueOf(args.get(0));
        y=Float.valueOf(args.get(1));
    }

    public Long getCoordinatesOfX(){
        return this.x;
    }

    public Float getCoordinatesOfY(){
        return this.y;
    }

    public String toString(){
        return "Coordinates: (" + x + "," + y + ")";
    }
}