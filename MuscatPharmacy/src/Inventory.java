import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    private final List<OralMedicine> oralMedicines;
    private final List<ExternalMedicine> externalMedicines;

    // Constructs a new Inventory, initializing empty lists for medicines.
    public Inventory() {
        this.oralMedicines = new ArrayList<>();
        this.externalMedicines = new ArrayList<>();
        // Add some dummy data for testing
        oralMedicines.add(new OralMedicine("O001", "Panadol", "12/2025", 100, 2.5, "1 tab SOS"));
        externalMedicines.add(new ExternalMedicine("E001", "Voltaren Gel", "06/2024", 50, 5.0, "50g"));
    }

    // --- Oral Medicine Methods ---

    // Adds an oral medicine to the inventory, checking for duplicate codes.
    public void addOralMedicine(OralMedicine medicine) {
        if (findOralMedicineByCode(medicine.getCode()).isPresent()) {
            throw new IllegalArgumentException("Oral medicine with code " + medicine.getCode() + " already exists.");
        }
        oralMedicines.add(medicine);
    }

    // Finds an oral medicine by its code (case-insensitive).
    public Optional<OralMedicine> findOralMedicineByCode(String code) {
        return oralMedicines.stream().filter(m -> m.getCode().equalsIgnoreCase(code)).findFirst();
    }

    // Removes an oral medicine by its code (case-insensitive).
    public boolean removeOralMedicine(String code) {
        return oralMedicines.removeIf(m -> m.getCode().equalsIgnoreCase(code));
    }

    // Returns a copy of the list of all oral medicines.
    public List<OralMedicine> getAllOralMedicines() {
        return new ArrayList<>(oralMedicines); // Return a copy
    }

    // --- External Medicine Methods ---

    // Adds an external medicine to the inventory, checking for duplicate codes.
    public void addExternalMedicine(ExternalMedicine medicine) {
        if (findExternalMedicineByCode(medicine.getCode()).isPresent()) {
            throw new IllegalArgumentException("External medicine with code " + medicine.getCode() + " already exists.");
        }
        externalMedicines.add(medicine);
    }

    // Finds an external medicine by its code (case-insensitive).
    public Optional<ExternalMedicine> findExternalMedicineByCode(String code) {
        return externalMedicines.stream().filter(m -> m.getCode().equalsIgnoreCase(code)).findFirst();
    }

    // Removes an external medicine by its code (case-insensitive).
    public boolean removeExternalMedicine(String code) {
        return externalMedicines.removeIf(m -> m.getCode().equalsIgnoreCase(code));
    }

    // Returns a copy of the list of all external medicines.
    public List<ExternalMedicine> getAllExternalMedicines() {
        return new ArrayList<>(externalMedicines); // Return a copy
    }
}