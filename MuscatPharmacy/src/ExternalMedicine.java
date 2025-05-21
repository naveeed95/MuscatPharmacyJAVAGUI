public class ExternalMedicine extends Medicine {
    private String weight; // e.g., "50g", "100ml"

    public ExternalMedicine(String code, String name, String expiryDate, int quantity, double rate, String weight) {
        super(code, name, expiryDate, quantity, rate);
        this.weight = weight;
    }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    @Override
    public String getSpecificDetail() {
        return "Weight: " + weight;
    }

    @Override
    public String toString() {
        return super.toString() + ", Weight: " + weight;
    }
}