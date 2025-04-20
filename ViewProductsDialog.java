import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewProductsDialog extends JDialog {
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private DatabaseConnection dbConnection;

    public ViewProductsDialog(JFrame parent, DatabaseConnection dbConnection) {
        super(parent, "View Products", true);
        this.dbConnection = dbConnection;
        initializeUI();
        loadProducts();
    }

    private void initializeUI() {
        setSize(800, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"ID", "Name", "Description", "Price", "Quantity", "Category", "Supplier", "Location"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productsTable = new JTable(tableModel);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadProducts());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Load products from database
        List<Product> products = dbConnection.getAllProducts();
        for (Product product : products) {
            Object[] rowData = {
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getSupplier(),
                product.getLocation()
            };
            tableModel.addRow(rowData);
        }
    }
} 