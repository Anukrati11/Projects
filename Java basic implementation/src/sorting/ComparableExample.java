package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableExample {
    public static void main(String[] args) {
        System.out.println("Comparable Example");
        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(new Employee("John", 104, 1000));
        employeeList.add(new Employee("Mike", 102, 2000));
        employeeList.add(new Employee("David", 103, 3000));
        employeeList.add(new Employee("John", 104, 4000));
        employeeList.add(new Employee("John", 101, 9000));
        employeeList.add(new Employee("John", 101, 6000));

        Collections.sort(employeeList);

        for(Employee employee : employeeList){
            System.out.println(employee.id + " : " + employee.name + " : " + employee.salary);
        }
    }
}
