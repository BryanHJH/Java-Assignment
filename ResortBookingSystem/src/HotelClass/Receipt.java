package HotelClass;

public class Receipt {
    private String custIC, custName, custAddress;
    private int custFamily, durationOfStay;

    // Constructors
    public Receipt(String custIC, String custName, String custAddress, int custFamily, int durationOfStay) {
        if (custIC == null || custIC.isBlank() || custName == null || custName.isBlank() || custAddress == null || custAddress.isBlank() || custFamily < 0 || durationOfStay < 0) {
            throw new IllegalArgumentException("Fields contain invalid values");
        }

        this.custIC = custIC;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custFamily = custFamily;
        this.durationOfStay = durationOfStay;
    }

    public Receipt(Receipt source) {
        this.custIC = source.custIC;
        this.custName = source.custName;
        this.custAddress = source.custAddress;
        this.custFamily = source.custFamily;
        this.durationOfStay = source.durationOfStay;
    }

    // Getters
    public String getCustName() {
        return custName;
    }

    public String getCustIC() {
        return custIC;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public int getCustFamily() {
        return custFamily;
    }

    public int getDurationOfStay() {
        return durationOfStay;
    }

    // Setters
    
}
