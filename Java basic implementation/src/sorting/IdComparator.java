package sorting;

import java.util.Comparator;

public class IdComparator implements Comparator<Emp> {

    @Override
    public int compare(Emp e1, Emp e2) {
        return e1.id - e2.id;
    }
}
