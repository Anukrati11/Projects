package sorting;

import java.util.Comparator;

public class Emp  {
    String name;

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

    int id;
    double salary;

    public Emp(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }



}
