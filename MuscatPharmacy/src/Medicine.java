import java.io.Serializable; // Used to allow objects of this class to be serialized (e.g., saved to a file)

/**
 * Abstract base class representing a generic medicine item in the pharmacy inventory.
 * It contains common attributes and functionalities for all types of medicines.
 * This class implements {@link Serializable} to support object persistence.
 */
public abstract class Medicine implements Serializable {
    // A unique version identifier for serialization.
    // Helps ensure compatibility if the class structure changes in the future.
    private static final long serialVersionUID = 1L;

    protected String code;        
    protected String name;        
    protected String expiryDate;  
    protected int quantity;       
    protected double rate;        

    
    public Medicine(String code, String name, String expiryDate, int quantity, double rate) {
        this.code = code;
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.rate = rate;
    }

    // Getter and Setter methods for the medicine attributes.

    public String getCode() { return code; }

    
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    
    public String getExpiryDate() { return expiryDate; }

    
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

   
    public int getQuantity() { return quantity; }

    
    public void setQuantity(int quantity) { this.quantity = quantity; }

    
    public double getRate() { return rate; }

    
    public void setRate(double rate) { this.rate = rate; }

    
    // Returns a string representation of the medicine object.
    // This method is useful for displaying medicine details in a user-friendly format.
     
    @Override
    public String toString() {
        return "Code: " + code + ", Name: " + name + ", Qty: " + quantity;
    }

    
     //An abstract method to be implemented by subclasses (e.g., OralMedicine, ExternalMedicine).
     //This method should return a string containing details specific to that type of medicine
     
   
    public abstract String getSpecificDetail();
}