package Server.Collection;

import java.util.Comparator;

public class ComparatorByName implements Comparator<Organization> {
    @Override
    public int compare(Organization o1, Organization o2) {
        return o1.getNameOfOrganization().compareTo(o2.getNameOfOrganization());
    }
}
