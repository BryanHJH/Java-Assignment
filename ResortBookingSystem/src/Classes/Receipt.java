package Classes;


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
    public void setCustName(String custName) {
        if (custName == null || custName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be blank or null!");
        }

        this.custName = custName;
    }

    public void setCustAddress(String custAddress) {
        if (custAddress == null || custAddress.isBlank()) {
            throw new IllegalArgumentException("Customer address cannot be blank or null!");
        }

        this.custAddress = custAddress;
    }
    
    public void setCustIC(String custIC) {
        if (custIC == null || custIC.isBlank()) {
            throw new IllegalArgumentException("Customer IC cannot be blank or null!");
        }

        this.custIC = custIC;
    }

    public void setCustFamily(int custFamily) {
        if (custFamily < 0) {
            throw new IllegalArgumentException("Customer's number of family members cannot be less than zero!");
        }

        this.custFamily = custFamily;
    }

    public void setDurationOfStay(int durationOfStay) {
        if (durationOfStay <= 0) {
            throw new IllegalArgumentException("Customer's duration of stay cannot be blank or null!");
        }

        this.durationOfStay = durationOfStay;
    }

}
