import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;

    public Inventory() {
        products = new HashMap<>();
    }

    public class Product {
        private String name;
        private double price;
        private int quantity;
    
        public Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    
        // Геттеры и сеттеры
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        
        public double getPrice() {return price;}
        public void setPrice(double price) {this.price = price;}

        public int getQuantity() {return quantity;}
        public void setQuantity(int quantity) {this.quantity = quantity;}
        
        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + ", quantity=" + quantity + "}";
        }
    }
    

    // Вставка продукта
    public void addProduct(String barcode, Product product) {
        if (products.containsKey(barcode)) {
            System.out.println("Продукт с таким штрихкодом уже существует. Обновление информации.");
        }
        products.put(barcode, product);
        System.out.println("Продукт добавлен/обновлен: " + product);
    }
    
    // Удаление продукта по штрихкоду
        public void removeProduct(String barcode) {
            Product removedProduct = products.remove(barcode);
            if (removedProduct != null) {
                System.out.println("Удален продукт: " + removedProduct);
            } else {
                System.out.println("Продукт с штрихкодом " + barcode + " не найден.");
            }
        }

    // Поиск продукта по штрихкоду
    public Product getProduct(String barcode) {
        return products.get(barcode);
    }

    public void searchBarcode(String barcode){
        Product foundProduct = getProduct(barcode);
        if (foundProduct != null) {
            System.out.println("Найден продукт по штрихкоду " + barcode + ": " + foundProduct);
        } else {
            System.out.println("Продукт с штрихкодом " + barcode + " не найден.");
        }
    }

    

    // Отображение всех продуктов
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Склад пуст.");
            return;
        }
        System.out.println("\n Список всех продуктов на складе:");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            System.out.println("Штрихкод: " + entry.getKey() + ", " + entry.getValue());
        }
        System.out.println("");
    }
    

    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        // Добавление продуктов
        Product product1 = inventory.new Product("Молоко", 1.50, 100);
        Product product2 = inventory.new Product("Хлеб", 0.80, 200);
        Product product3 = inventory.new Product("Яблоки", 2.00, 150);

        inventory.addProduct("1234567890123", product1);
        inventory.addProduct("9876543210987", product2);
        inventory.addProduct("5555555555555", product3);

        // Отображение всех продуктов
        inventory.displayAllProducts();

        // Поиск продукта
        inventory.searchBarcode("9876543210987");

        // Удаление продукта
        inventory.removeProduct("1234567890123");
        

        // Отображение всех продуктов после удаления
        inventory.displayAllProducts();
    }
}
