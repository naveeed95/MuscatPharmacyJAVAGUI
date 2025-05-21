import java.awt.*;
import javax.swing.*;

public class MainAppWindow extends JFrame {
    private final Inventory inventory;

    public MainAppWindow(Inventory inventory) {
        this.inventory = inventory;
        setTitle("Muscat Pharmacy Inventory System - Main Menu");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout(10, 10)); // Add some padding

        // Title Label
        JLabel titleLabel = new JLabel("Muscat Pharmacy Inventory System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        // Using GridBagLayout to center buttons more effectively
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around buttons
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons fill available horizontal space
        gbc.ipadx = 50; // Add internal padding to make buttons wider
        gbc.ipady = 20; // Add internal padding to make buttons taller

        JButton oralMedicineButton = new JButton("Oral Medicine");
        oralMedicineButton.setFont(new Font("Arial", Font.PLAIN, 14));
        oralMedicineButton.addActionListener(e -> new MedicineManagementUI(inventory, "Oral").setVisible(true));

        JButton externalMedicineButton = new JButton("External Medicine");
        externalMedicineButton.setFont(new Font("Arial", Font.PLAIN, 14));
        externalMedicineButton.addActionListener(e -> new MedicineManagementUI(inventory, "External").setVisible(true));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(oralMedicineButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(externalMedicineButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        // Optional: Add a small status bar or footer
        JLabel footerLabel = new JLabel("Select a medicine category to manage.", SwingConstants.CENTER);
        add(footerLabel, BorderLayout.SOUTH);
    }
}