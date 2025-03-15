package ru.skillbox.jdbc.step4;

import ru.skillbox.jdbc.step4.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Код к шагу 4.2 (CRUD)
 */
public class ProductApplication {
    public static void main(String[] args) throws SQLException {
        ProductsCrudRepository repository = new ProductsCrudRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Создать новый продукт");
            System.out.println("2. Получить продукт по ID");
            System.out.println("3. Обновить продукт по ID");
            System.out.println("4. Удалить продукт по ID");
            System.out.println("5. Показать все продукты");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    createProduct(repository, scanner);
                    break;
                case "2":
                    getProductById(repository, scanner);
                    break;
                case "3":
                    updateProduct(repository, scanner);
                    break;
                case "4":
                    deleteProduct(repository, scanner);
                    break;
                case "5":
                    showAllProducts(repository);
                    break;
                case "6":
                    System.out.println("Выход из программы.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Некорректный ввод. Пожалуйста, выберите опцию от 1 до 6.");
            }
        }
    }

    // Метод для создания нового продукта
    private static void createProduct(ProductsCrudRepository repository, Scanner scanner) throws SQLException {
        System.out.print("Введите название продукта: ");
        String name = scanner.nextLine();

        System.out.print("Введите цену продукта: ");
        long price;
        try {
            price = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Цена должна быть числом.");
            return;
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        boolean created = repository.insertProduct(product);
        if (created) {
            System.out.println("Новый продукт создан с ID: " + product.getId());
        } else {
            System.out.println("Не удалось создать продукт.");
        }
    }

    // Метод для получения продукта по ID
    private static void getProductById(ProductsCrudRepository repository, Scanner scanner) throws SQLException {
        System.out.print("Введите ID продукта: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом.");
            return;
        }

        Product product = repository.getProductById(id);
        if (product != null) {
            System.out.println("Найден продукт: " + product);
        } else {
            System.out.println("Продукт с ID " + id + " не найден.");
        }
    }

    // Метод для обновления продукта по ID
    private static void updateProduct(ProductsCrudRepository repository, Scanner scanner) throws SQLException {
        System.out.print("Введите ID продукта для обновления: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID должно быть числом.");
            return;
        }

        Product existingProduct = repository.getProductById(id);
        if (existingProduct == null) {
            System.out.println("Продукт с ID " + id + " не найден.");
            return;
        }

        System.out.print("Введите новое название продукта (текущее значение: " + existingProduct.getName() + "): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = existingProduct.getName();
        }

        System.out.print("Введите новую цену продукта (текущее значение: " + existingProduct.getPrice() + "): ");
        String priceInput = scanner.nextLine();
        long price;
        if (priceInput.isEmpty()) {
            price = existingProduct.getPrice();
        } else {
            try {
                price = Long.parseLong(priceInput);
            } catch (NumberFormatException e) {
                System.out.println("Цена должна быть числом.");
                return;
            }
        }

        existingProduct.setName(name);
        existingProduct.setPrice(price);

        boolean updated = repository.updateProduct(existingProduct);
        if (updated) {
            System.out.println("Продукт обновлен.");
        } else {
            System.out.println("Не удалось обновить продукт.");
        }
    }

    // Метод для удаления продукта по ID
    private static void deleteProduct(ProductsCrudRepository repository, Scanner scanner) throws SQLException {
        System.out.print("Введите ID продукта для удаления: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID должно быть числом.");
            return;
        }

        boolean deleted = repository.deleteProduct(id);
        if (deleted) {
            System.out.println("Продукт удален.");
        } else {
            System.out.println("Продукт с ID " + id + " не найден или не удалось удалить.");
        }
    }

    // Метод для показа всех продуктов
    private static void showAllProducts(ProductsCrudRepository repository) throws SQLException {
        List<Product> products = repository.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Список продуктов пуст.");
        } else {
            System.out.println("Список всех продуктов:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
}
