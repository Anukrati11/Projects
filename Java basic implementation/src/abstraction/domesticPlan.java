package abstraction;

public class domesticPlan implements abstractRatePlanClass, abstractClassMultipleInheritance {
//    @Override
public void getRate() {
//        rate = 3.50;
        System.out.println("Rate is 3.50");
    }

    @Override
    public void count() {
        System.out.println("Counting");
    }
}
