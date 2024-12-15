package stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Employee implements  Cloneable {
    private int id;
    private String name;
    private double salary;
    private int age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public Employee(int id, String name, double salary, int age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }




    public static void main(String[] args) {
        Employee emp1  = new Employee(1,"Ashish",1000,20);
        Employee emp2  = new Employee(2,"Rakesh",1020,23);
        Employee emp3  = new Employee(3,"Bhupendra",500,24);
        Employee emp4  = new Employee(4,"Rajesh",19102,23);
        Employee emp5  = new Employee(5,"Akash",403,22);

        Map<Integer,Employee> map = new HashMap<>();

        map.put(1,emp1);
        map.put(2,emp2);
        map.put(3,emp3);
        map.put(4,emp4);
        map.put(5,emp5);

        Employee maxSalaryEmp = map.values().stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);

        Employee maxEmployee1 = map.entrySet().stream().max((entry1, entry2) -> (int) entry1.getValue().getSalary() - (int) entry2.getValue().getSalary()).stream().findFirst().get().getValue();

        System.out.print("Employee with maximum salary: "+ maxSalaryEmp.getName());
        System.out.print("Employee with maximum salary: "+ maxEmployee1.getName());
        System.out.println("***********************");

        /********************/
        List<String> names = Arrays.asList("John", "Rakesh", "Bob", "Jonathan");

        Map<String, Long>  map1 = new HashMap<>();
        map1.put("John",4L);
        map1.put("Rakesh",6L);


//        names.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).forEach((k,v) -> System.out.println(k + " : " + v));

        // Convert list to map using Streams
        names.stream()
                .collect(Collectors.toMap(name -> name, String::length)).forEach((k,v) -> System.out.println(k + " : " + v));
    }
}
