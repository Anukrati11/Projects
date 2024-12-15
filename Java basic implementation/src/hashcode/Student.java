package hashcode;

import java.util.Objects;

public class Student {
    private String name;
    private int age;
    private int id;

    public Student(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && id == student.id && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id);
    }

    public static void main(String[] args) {
        Student s1 = new Student("John", 101, 1001);
        Student s2 = new Student("John", 101, 1001);
        Student s3 = new Student("Johns", 101, 1001);

        System.out.println(s1.hashCode() + ",  "+s2.hashCode()+",  "+s3.hashCode() +"  "+ s1.equals(s2) + "  "+ s1.equals(s3));
    }
}
