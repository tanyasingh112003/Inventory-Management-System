import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryManagementSystem {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton addProductButton;
    private JButton viewProductsButton;
    private JButton updateProductButton;
    private JButton deleteProductButton;
    private DatabaseConnection dbConnection;

    public InventoryManagementSystem() {
        dbConnection = new DatabaseConnection();
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("Inventory Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create buttons
        addProductButton = new JButton("Add Product");
        viewProductsButton = new JButton("View Products");
        updateProductButton = new JButton("Update Product");
        deleteProductButton = new JButton("Delete Product");

        // Add action listeners
        addProductButton.addActionListener(e -> showAddProductDialog());
        viewProductsButton.addActionListener(e -> showViewProductsDialog());
        updateProductButton.addActionListener(e -> showUpdateProductDialog());
        deleteProductButton.addActionListener(e -> showDeleteProductDialog());

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(addProductButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(viewProductsButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(updateProductButton, gbc);

        gbc.gridy = 3;
        mainPanel.add(deleteProductButton, gbc);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void showAddProductDialog() {
        AddProductDialog dialog = new AddProductDialog(mainFrame, dbConnection);
        dialog.setVisible(true);
    }

    private void showViewProductsDialog() {
        ViewProductsDialog dialog = new ViewProductsDialog(mainFrame, dbConnection);
        dialog.setVisible(true);
    }

    private void showUpdateProductDialog() {
        UpdateProductDialog dialog = new UpdateProductDialog(mainFrame, dbConnection);
        dialog.setVisible(true);
    }

    private void showDeleteProductDialog() {
        DeleteProductDialog dialog = new DeleteProductDialog(mainFrame, dbConnection);
        dialog.setVisible(true);
    }

    private void showGenerateReportDialog() {
        // TODO: Implement report generation
        JOptionPane.showMessageDialog(mainFrame, "Report generation functionality will be implemented here");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementSystem());
    }
} 