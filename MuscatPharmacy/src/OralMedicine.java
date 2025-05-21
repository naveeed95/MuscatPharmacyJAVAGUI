public class OralMedicine extends Medicine {
    private String dosage; // e.g., "1 tablet twice a day"

    public OralMedicine(String code, String name, String expiryDate, int quantity, double rate, String dosage) {
        super(code, name, expiryDate, quantity, rate);
        this.dosage = dosage;
    }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    @Override
    public String getSpecificDetail() {
        return "Dosage: " + dosage;
    }

    @Override
    public String toString() {
        return super.toString() + ", Dosage: " + dosage;
    }
}