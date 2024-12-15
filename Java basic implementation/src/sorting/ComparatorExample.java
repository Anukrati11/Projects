package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorExample {

    public static void main(String[] args) {
        System.out.println("Comparator Example");
        List<Emp> employeeList = new ArrayList<>();

        employeeList.add(new Emp("John", 104, 1000));
        employeeList.add(new Emp("Mike", 102, 2000));
        employeeList.add(new Emp("David", 103, 3000));
        employeeList.add(new Emp("John", 104, 4000));
        employeeList.add(new Emp("John", 101, 9000));
        employeeList.add(new Emp("John", 101, 6000));
        employeeList.add(new Emp("Anu", 101, 6000));

        // comparing basis on id with IdComparator that we made
        employeeList.sort(new IdComparator());

        // or
        Collections.sort(employeeList, new IdComparator());

        // Comparison with lambda function to avoid making classes for different Comparators
        employeeList.sort((Emp e1, Emp e2) -> {
            return e1.name.compareTo(e2.name);
        });

        // Comparison with Comparator reversed
        employeeList.sort(Comparator.comparing(Emp :: getSalary).reversed());

        for(Emp employee : employeeList){
            System.out.println(employee.id + " : " + employee.name + " : " + employee.salary);
        }
    }
}
