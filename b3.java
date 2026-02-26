import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        if (price <= 0) throw new IllegalArgumentException("Giá phải > 0");
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return id + " - " + name + " - " + price;
    }
}

class Order {
    private String orderId;
    private List<Product> products = new ArrayList<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public double total() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public String getOrderId() { return orderId; }

    @Override
    public String toString() {
        return "Order " + orderId + " | Total = " + total();
    }
}

public class Main {

    static List<Product> productList = new ArrayList<>();
    static Map<String, Order> orderMap = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n1. Thêm sản phẩm");
            System.out.println("2. Hiển thị sản phẩm");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Thêm SP vào đơn");
            System.out.println("5. Xem tổng tiền đơn");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");

            int c = Integer.parseInt(sc.nextLine());

            try {
                switch (c) {
                    case 1 -> addProduct();
                    case 2 -> productList.forEach(System.out::println);
                    case 3 -> createOrder();
                    case 4 -> addProductToOrder();
                    case 5 -> showTotal();
                    case 6 -> { return; }
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    static void addProduct() {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());

        productList.add(new Product(id, name, price));
    }

    static void createOrder() {
        System.out.print("Order ID: ");
        String id = sc.nextLine();
        orderMap.put(id, new Order(id));
    }

    static Product findProduct(int id) {
        return productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    }

    static Order findOrder(String id) {
        Order o = orderMap.get(id);
        if (o == null) throw new RuntimeException("Không tìm thấy đơn hàng");
        return o;
    }

    static void addProductToOrder() {
        System.out.print("Order ID: ");
        Order order = findOrder(sc.nextLine());
        System.out.print("Product ID: ");
        Product p = findProduct(Integer.parseInt(sc.nextLine()));
        order.addProduct(p);
    }

    static void showTotal() {
        System.out.print("Order ID: ");
        Order o = findOrder(sc.nextLine());
        System.out.println("Tổng tiền: " + o.total());
    }
}
