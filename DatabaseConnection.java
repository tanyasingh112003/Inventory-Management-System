import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String USER = "root";
    private static final String PASSWORD = "9068";
    private Connection connection;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try {
            Statement statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL," +
                    "description TEXT," +
                    "price DOUBLE NOT NULL," +
                    "quantity INT NOT NULL," +
                    "category VARCHAR(50)," +
                    "supplier VARCHAR(100)," +
                    "location VARCHAR(50)" +
                    ")";
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, quantity, category, supplier, location) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setString(5, product.getCategory());
            statement.setString(6, product.getSupplier());
            statement.setString(7, product.getLocation());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Product product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("category"),
                    resultSet.getString("supplier"),
                    resultSet.getString("location")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, quantity=?, " +
                    "category=?, supplier=?, location=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setString(5, product.getCategory());
            statement.setString(6, product.getSupplier());
            statement.setString(7, product.getLocation());
            statement.setInt(8, product.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("category"),
                    resultSet.getString("supplier"),
                    resultSet.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 