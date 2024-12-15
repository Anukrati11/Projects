package polymorphism;

import java.util.HashMap;
import java.util.Map;

public class overloading {

//    public void m1(String s){
//        System.out.println("String method");
//    }
//     public void m1(Object o){
//        System.out.println("Object method");
//     }
//
//     public int m2(Integer k){
//        System.out.println("Integer method");
//        return k;
//     }
//    public int m2(int k){
//        System.out.println("int method");
//        return k;
//    }
        void m1(long x) {
            System.out.println("long method");
        }

        void m1(Integer x) {
            System.out.println("Integer method");
        }

        void m1(int... x) {
            System.out.println("varargs method");
        }
     public static void main(String[] args) {
        overloading o = new overloading();
         o.m1(10);
//        o.m1(null);
//        o.m1("abc");
//        o.m1(new Object());
//        o.m2(10);

         Integer a = 10;
         Integer b = 10;
         System.out.println(a.equals(b));
         System.out.println(a.hashCode());
         System.out.println(b.hashCode());

       addMap();
     }
    public static void  addMap(){
        Map<Flight, String> map = new HashMap<>();
        Flight flight1 = new Flight();
        Flight flight = new Flight();
        map.put(flight, flight.getCarrierName());
        map.put(flight1, "2nd Flight");
        map.put(flight1, "3rd Flight");
        for(Map.Entry<Flight, String> entry : map.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
