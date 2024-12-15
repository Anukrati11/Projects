package sorting;

public class Employee implements Comparable<Employee> {
    String name;
    int id;
    double salary;

    public Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee employee) {
//        return this.id - employee.id;
        if (this.id != employee.id) {
            return this.id - employee.id;
        } else {
            return Double.compare(this.salary, employee.salary);
        }
    }
}
