import javax.swing.SwingUtilities; // Import the SwingUtilities class for thread management

/**
 * Main application class for the Muscat Pharmacy Inventory System.
 * This class contains the main method to launch the application.
 */
public class MuscatPharmacyApp {

   
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> { // Lambda expression for a Runnable
            // Create a single instance of the Inventory to be shared across the application
            Inventory inventory = new Inventory();
            // passing the shared inventory instance to it.
            new MainAppWindow(inventory).setVisible(true);
        });
    }
}