# Inventory Management System

A Java-based Inventory Management System with a graphical user interface (GUI) for managing product inventory.

## Features

- Add new products to inventory
- View all products in a table format
- Update existing product information
- Delete products from inventory
- Generate reports (to be implemented)

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- MySQL Connector/J (JDBC driver)

## Setup Instructions

1. Install MySQL Server if not already installed
2. Create a new database named `inventory_db`:
   ```sql
   CREATE DATABASE inventory_db;
   ```

3. Update the database connection details in `DatabaseConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

4. Add the MySQL Connector/J to your project's classpath:
   - Download the MySQL Connector/J JAR file
   - Add it to your project's build path

## Running the Application

1. Compile all Java files:
   ```bash
   javac *.java
   ```

2. Run the application:
   ```bash
   java InventoryManagementSystem
   ```

## Usage

1. **Add Product**
   - Click the "Add Product" button
   - Fill in the product details
   - Click "Save" to add the product

2. **View Products**
   - Click the "View Products" button
   - View all products in a table format
   - Click "Refresh" to update the view

3. **Update Product**
   - Click the "Update Product" button
   - Enter the product ID and click "Search"
   - Modify the product details
   - Click "Update" to save changes

4. **Delete Product**
   - Click the "Delete Product" button
   - Enter the product ID and click "Search"
   - Click "Delete" to remove the product

## Project Structure

- `InventoryManagementSystem.java` - Main application class
- `Product.java` - Product model class
- `DatabaseConnection.java` - Database connection and operations
- `AddProductDialog.java` - Dialog for adding products
- `ViewProductsDialog.java` - Dialog for viewing products
- `UpdateProductDialog.java` - Dialog for updating products
- `DeleteProductDialog.java` - Dialog for deleting products

## License

This project is open source and available under the MIT License. 