package polymorphism;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class Flight {

    private Long id;

    public BigInteger flightNo = BigInteger.valueOf(9);

    public List<Integer> arr = new ArrayList<>(10);


    private String carrierName;

    private String flightModel;

    private int seatCapacity;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public BigInteger getFlightNo() {
        return flightNo;
    }
    public void setFlightNo(BigInteger flightNo) {
        this.flightNo = flightNo;
    }
    public String getCarrierName() {
        return carrierName;
    }
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }
    public String getFlightModel() {
        return flightModel;
    }
    public void setFlightModel(String flightModel) {
        this.flightModel = flightModel;
    }
    public int getSeatCapacity() {
        return seatCapacity;
    }
    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public static void main(String[] args) {
        FlightChild flightChild = new FlightChild();
        Flight flight = new Flight();
        System.out.println(flight.flightNo);
        System.out.println(flightChild.getFlightNo());
    }

}
