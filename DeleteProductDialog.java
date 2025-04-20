import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteProductDialog extends JDialog {
    private JTextField idField;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JTextArea productInfoArea;
    private DatabaseConnection dbConnection;
    private Product currentProduct;

    public DeleteProductDialog(JFrame parent, DatabaseConnection dbConnection) {
        super(parent, "Delete Product", true);
        this.dbConnection = dbConnection;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
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

        // Product info area
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        productInfoArea = new JTextArea(8, 30);
        productInfoArea.setEditable(false);
        add(new JScrollPane(productInfoArea), gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        deleteButton.addActionListener(e -> deleteProduct());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(buttonPanel, gbc);

        // Disable delete button initially
        deleteButton.setEnabled(false);
    }

    private void searchProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            currentProduct = dbConnection.getProductById(id);
            if (currentProduct != null) {
                productInfoArea.setText(
                    "ID: " + currentProduct.getId() + "\n" +
                    "Name: " + currentProduct.getName() + "\n" +
                    "Description: " + currentProduct.getDescription() + "\n" +
                    "Price: " + currentProduct.getPrice() + "\n" +
                    "Quantity: " + currentProduct.getQuantity() + "\n" +
                    "Category: " + currentProduct.getCategory() + "\n" +
                    "Supplier: " + currentProduct.getSupplier() + "\n" +
                    "Location: " + currentProduct.getLocation()
                );
                deleteButton.setEnabled(true);
            } else {
                productInfoArea.setText("Product not found");
                deleteButton.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        if (currentProduct != null) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (dbConnection.deleteProduct(currentProduct.getId())) {
                    JOptionPane.showMessageDialog(this, "Product deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete product", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
} 