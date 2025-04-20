import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateProductDialog extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField categoryField;
    private JTextField supplierField;
    private JTextField locationField;
    private JButton searchButton;
    private JButton updateButton;
    private JButton cancelButton;
    private DatabaseConnection dbConnection;

    public UpdateProductDialog(JFrame parent, DatabaseConnection dbConnection) {
        super(parent, "Update Product", true);
        this.dbConnection = dbConnection;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 600);
        setLocationRelativeTo(getParent());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID field and search button
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(10);
        add(idField, gbc);
        gbc.gridx = 2;
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProduct());
        add(searchButton, gbc);

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        descriptionArea = new JTextArea(3, 20);
        add(new JScrollPane(descriptionArea), gbc);

        // Price field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        priceField = new JTextField(20);
        add(priceField, gbc);

        // Quantity field
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        quantityField = new JTextField(20);
        add(quantityField, gbc);

        // Category field
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        categoryField = new JTextField(20);
        add(categoryField, gbc);

        // Supplier field
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        supplierField = new JTextField(20);
        add(supplierField, gbc);

        // Location field
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        locationField = new JTextField(20);
        add(locationField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");

        updateButton.addActionListener(e -> updateProduct());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        add(buttonPanel, gbc);

        // Disable fields initially
        setFieldsEnabled(false);
    }

    private void setFieldsEnabled(boolean enabled) {
        nameField.setEnabled(enabled);
        descriptionArea.setEnabled(enabled);
        priceField.setEnabled(enabled);
        quantityField.setEnabled(enabled);
        categoryField.setEnabled(enabled);
        supplierField.setEnabled(enabled);
        locationField.setEnabled(enabled);
        updateButton.setEnabled(enabled);
    }

    private void searchProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            Product product = dbConnection.getProductById(id);
            if (product != null) {
                nameField.setText(product.getName());
                descriptionArea.setText(product.getDescription());
                priceField.setText(String.valueOf(product.getPrice()));
                quantityField.setText(String.valueOf(product.getQuantity()));
                categoryField.setText(product.getCategory());
                supplierField.setText(product.getSupplier());
                locationField.setText(product.getLocation());
                setFieldsEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
                setFieldsEnabled(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
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

            Product product = new Product(id, name, description, price, quantity, category, supplier, location);
            if (dbConnection.updateProduct(product)) {
                JOptionPane.showMessageDialog(this, "Product updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update product", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and quantity", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 