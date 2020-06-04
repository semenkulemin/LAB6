package Server.Collection;

import java.io.Serializable;

public class ObjectsOfOrganizations implements Serializable {
    private String message;
    private Organization organization;

    public void setMessage(String message){
        this.message = message;
    }

    public void setOrganization(Organization organization){
        this.organization = organization;
    }

    public Organization getOrganization(){
        return organization;
    }

    public String getMessage(){
        return message;
    }
}
