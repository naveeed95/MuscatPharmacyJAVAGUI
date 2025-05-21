import java.awt.*;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MedicineManagementUI extends JFrame {
    private final Inventory inventory;
    private final String medicineType; // "Oral" or "External"

    // UI Components
    private final JTextField nameField, codeField, expiryDateField, quantityField, rateField;
    private final JTextField specificField; // For Dosage or Weight
    private final JLabel specificLabel;     // Label for Dosage or Weight

    private final JTextField searchCodeField, deleteCodeField;
    private final JList<Medicine> medicineList;
    private final DefaultListModel<Medicine> listModel;

    private final JButton addButton, searchButton, deleteButton, exitButton;

   
    public MedicineManagementUI(Inventory inventory, String medicineType) {
        this.inventory = inventory;
        this.medicineType = medicineType;

        setTitle("Muscat Pharmacy Inventory System - " + medicineType + " Medicine Management");
        setSize(850, 600); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close, don't exit app
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Main layout

        // --- Input Panel (Left Side) ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10,10,10,10), // Outer margin
            new TitledBorder("Medicine Details") // Inner titled border
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Common Fields
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Name (Item):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(20); inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Code:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        codeField = new JTextField(20); inputPanel.add(codeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Expiration Date (MM/YYYY):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        expiryDateField = new JTextField(20); inputPanel.add(expiryDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        quantityField = new JTextField(20); inputPanel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(new JLabel("Rate:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        rateField = new JTextField(20); inputPanel.add(rateField, gbc);

        // Specific Field (Dosage or Weight)
        specificLabel = new JLabel(medicineType.equals("Oral") ? "Dosage:" : "Weight:");
        gbc.gridx = 0; gbc.gridy = 5; inputPanel.add(specificLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        specificField = new JTextField(20); inputPanel.add(specificField, gbc);

        gbc.fill = GridBagConstraints.NONE; // Reset fill for buttons

        // Add Button
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        addButton = new JButton("Add Medicine");
        inputPanel.add(addButton, gbc);
        gbc.gridwidth = 1; // Reset gridwidth
        gbc.anchor = GridBagConstraints.WEST; // Reset anchor

        // Search Panel
        JPanel searchActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchActionPanel.add(new JLabel("Enter Code:"));
        searchCodeField = new JTextField(10);
        searchActionPanel.add(searchCodeField);
        searchButton = new JButton("Search");
        searchActionPanel.add(searchButton);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(searchActionPanel, gbc);

        // Delete Panel
        JPanel deleteActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deleteActionPanel.add(new JLabel("Enter Code:"));
        deleteCodeField = new JTextField(10);
        deleteActionPanel.add(deleteCodeField);
        deleteButton = new JButton("Delete");
        deleteActionPanel.add(deleteButton);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(deleteActionPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; gbc.weighty = 1.0; // Push everything up
        inputPanel.add(new JLabel(""), gbc); // Filler


        // --- List Panel (Right Side) ---
        JPanel listPanel = new JPanel(new BorderLayout(5,5));
        listPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10,0,10,10), // Outer margin (no left margin)
            new TitledBorder("List of " + medicineType + " Medicines")
        ));
        listModel = new DefaultListModel<>();
        medicineList = new JList<>(listModel);
        medicineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(medicineList);
        listPanel.add(scrollPane, BorderLayout.CENTER);


        // --- Bottom Panel for Exit Button ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitButton = new JButton("Exit");
        bottomPanel.add(exitButton);
        bottomPanel.setBorder(new EmptyBorder(0,10,10,10));


        // Add panels to frame
        add(inputPanel, BorderLayout.WEST);
        add(listPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load initial data
        refreshMedicineList();

        // --- Action Listeners ---
        addButton.addActionListener(e -> addMedicine());
        searchButton.addActionListener(e -> searchMedicine());
        deleteButton.addActionListener(e -> deleteMedicine());
        exitButton.addActionListener(e -> dispose()); // Close this window

        // Listener for JList selection to populate fields (optional, but good for UX)
        medicineList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Medicine selected = medicineList.getSelectedValue();
                if (selected != null) {
                    populateFields(selected);
                }
            }
        });
    }

    
    private void populateFields(Medicine med) {
        nameField.setText(med.getName());
        codeField.setText(med.getCode());
        codeField.setEditable(false); // Don't allow editing code of existing item via these fields
        expiryDateField.setText(med.getExpiryDate());
        quantityField.setText(String.valueOf(med.getQuantity()));
        rateField.setText(String.valueOf(med.getRate()));
        if (med instanceof OralMedicine oralMed) { // Java 16+ pattern matching for instanceof
            specificField.setText(oralMed.getDosage());
            specificLabel.setText("Dosage:"); // Ensure label matches type
        } else if (med instanceof ExternalMedicine externalMed) { // Java 16+ pattern matching for instanceof
            specificField.setText(externalMed.getWeight());
            specificLabel.setText("Weight:"); // Ensure label matches type
        }
    }

    /**
     * Handles the action of adding a new medicine to the inventory.
     * It retrieves data from input fields, validates it, creates a new medicine object,
     * adds it to the inventory, and then refreshes the UI.
     * Displays error messages for invalid input or other issues.
     */
    private void addMedicine() {
        try {
            String name = nameField.getText().trim();
            String code = codeField.getText().trim();
            String expiry = expiryDateField.getText().trim();
            String qtyStr = quantityField.getText().trim();
            String rateStr = rateField.getText().trim();
            String specific = specificField.getText().trim();

            // Basic Validation
            if (name.isEmpty() || code.isEmpty() || expiry.isEmpty() || qtyStr.isEmpty() || rateStr.isEmpty() || specific.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity = Integer.parseInt(qtyStr);
            double rate = Double.parseDouble(rateStr);

            if (quantity < 0 || rate < 0) {
                 JOptionPane.showMessageDialog(this, "Quantity and Rate cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (medicineType.equals("Oral")) {
                OralMedicine om = new OralMedicine(code, name, expiry, quantity, rate, specific);
                inventory.addOralMedicine(om);
            } else {
                ExternalMedicine em = new ExternalMedicine(code, name, expiry, quantity, rate, specific);
                inventory.addExternalMedicine(em);
            }
            refreshMedicineList();
            clearInputFields();
            JOptionPane.showMessageDialog(this, medicineType + " medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Quantity or Rate.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the action of searching for a medicine in the inventory by its code.
     * Retrieves the code from the search input field, queries the inventory,
     * and if found, populates the input fields with the medicine's details and selects it in the list.
     * Displays messages for success or if the medicine is not found.
     */
    private void searchMedicine() {
        String code = searchCodeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a code to search.", "Search Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Optional<? extends Medicine> foundMedicine;
        if (medicineType.equals("Oral")) {
            foundMedicine = inventory.findOralMedicineByCode(code);
        } else {
            foundMedicine = inventory.findExternalMedicineByCode(code);
        }

        if (foundMedicine.isPresent()) {
            Medicine med = foundMedicine.get();
            populateFields(med); // Populate main fields
            // Also select in list
            medicineList.setSelectedValue(med, true);
            // medicineList.ensureIndexIsVisible(listModel.indexOf(med)); // Can be redundant if setSelectedValue(..., true) works as expected

            JOptionPane.showMessageDialog(this,
                "Medicine Found:\nName: " + med.getName() + "\nCode: " + med.getCode() +
                "\nExpiry: " + med.getExpiryDate() + "\nQuantity: " + med.getQuantity() +
                "\nRate: " + med.getRate() + "\n" + med.getSpecificDetail(),
                "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Medicine with code '" + code + "' not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
        }
        searchCodeField.setText("");
    }

    /**
     * Handles the action of deleting a medicine from the inventory.
     * Retrieves the code from the delete input field, asks for user confirmation,
     * removes the medicine from the inventory if confirmed, and then refreshes the UI.
     * Displays messages for success or if the medicine is not found.
     */
    private void deleteMedicine() {
        String code = deleteCodeField.getText().trim();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a code to delete.", "Delete Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete medicine with code '" + code + "'?",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean removed;
            if (medicineType.equals("Oral")) {
                removed = inventory.removeOralMedicine(code);
            } else {
                removed = inventory.removeExternalMedicine(code);
            }

            if (removed) {
                refreshMedicineList();
                clearInputFields(); // Clear fields if the deleted item was displayed
                JOptionPane.showMessageDialog(this, "Medicine with code '" + code + "' deleted successfully.", "Deletion Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Medicine with code '" + code + "' not found for deletion.", "Deletion Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        deleteCodeField.setText("");
    }

    /**
     * Refreshes the list of medicines displayed on the UI.
     * Clears the current list model and repopulates it with the medicines
     * of the current {@code medicineType} from the inventory.
     */
    private void refreshMedicineList() {
        listModel.clear();
        List<? extends Medicine> medicines;
        if (medicineType.equals("Oral")) {
            medicines = inventory.getAllOralMedicines();
        } else {
            medicines = inventory.getAllExternalMedicines();
        }
        for (Medicine med : medicines) {
            listModel.addElement(med);
        }
    }

    /**
     * Clears all the medicine detail input fields on the UI.
     * Also makes the code field editable again (as it's set to non-editable when displaying an existing item)
     * and clears any selection in the medicine list.
     */
    private void clearInputFields() {
        nameField.setText("");
        codeField.setText("");
        codeField.setEditable(true);
        expiryDateField.setText("");
        quantityField.setText("");
        rateField.setText("");
        specificField.setText("");
        // searchCodeField.setText(""); // Commented out to keep user convenience
        // deleteCodeField.setText(""); // Commented out to keep user convenience
        medicineList.clearSelection();
    }
}