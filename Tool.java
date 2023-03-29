import java.time.LocalDate;

public class Tool {
    private String category;
    private String model;
    private int productId;
    private double price;
    private boolean available;
    private String returnDate;

    public Tool(String category, String model, int productId, double price, boolean available) {
        this.category = category;
        this.model = model;
        this.productId = productId;
        this.price = price;
        this.available = available;
    }

    public String getCategory() {
        return category;
    }

    public String getModel() {
        return model;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public boolean getAvailable() {
        return available;
    }

    public void changeAvailability(boolean available) {
        this.available = available;

        if (!available) {
            LocalDate currentDate = LocalDate.now();
            LocalDate returnDate = currentDate.plusDays(7);
            this.returnDate = returnDate.toString();
        }
    }

    public double calculateDeposit(double price) {
        return price * 0.3;
    }

}