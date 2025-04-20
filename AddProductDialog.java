import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddProductDialog extends JDialog {
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField categoryField;
    private JTextField supplierField;
    private JTextField locationField;
    private JButton saveButton;
    private JButton cancelButton;
    private DatabaseConnection dbConnection;

    public AddProductDialog(JFrame parent, DatabaseConnection dbConnection) {
        super(parent, "Add New Product", true);
        this.dbConnection = dbConnection;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 500);
        setLocationRelativeTo(getParent());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(3, 20);
        add(new JScrollPane(descriptionArea), gbc);

        // Price field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(20);
        add(priceField, gbc);

        // Quantity field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(20);
        add(quantityField, gbc);

        // Category field
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryField = new JTextField(20);
        add(categoryField, gbc);

        // Supplier field
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 1;
        supplierField = new JTextField(20);
        add(supplierField, gbc);

        // Location field
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField(20);
        add(locationField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveProduct());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void saveProduct() {
        try {
            String name = nameField.getText();
            String description = descriptionArea.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            String category = categoryField.getText();
            String supplier = supplierField.getText();
            String location = locationField.getText();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = new Product(0, name, description, price, quantity, category, supplier, location);
            if (dbConnection.addProduct(product)) {
                JOptionPane.showMessageDialog(this, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and quantity", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 