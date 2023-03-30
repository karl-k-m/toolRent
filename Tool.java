import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tool {
    private final int productId;    // Product ID
    private final String category;  // Tool category
    private final String model;     // Tool model name
    private final double price;     // Purchase price
    private boolean available;      // Availability

    public Tool(String category, String model, int productId, double price, boolean available) {
        this.category = category;
        this.model = model;
        this.productId = productId;
        this.price = price;
        this.available = available;
    }

    public Tool(String category, String model, int productId, double price, boolean available, String returnDate, String rentingClient) {
        this.category = category;
        this.model = model;
        this.productId = productId;
        this.price = price;
        this.available = available;
    }

    // Return the tool and update the entry in tools.txt
    public void returnTool() throws Exception {
        File toolsFile = new File("tools.txt");
        Scanner reader = new Scanner(toolsFile);
        List<String> lines = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(";");
            if (parts[0].equals(this.category) && parts[1].equals(this.model) && parts[2].equals(Integer.toString(this.productId))) {
                line = this.category + ";" + this.model + ";" + this.productId + ";" + this.price + ";" + "true";
            }
            lines.add(line);
        }
        reader.close();

        FileWriter writer = new FileWriter(toolsFile);
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
    }

    // Rent the tool and update the entry in tools.txt
    public void rentOut(Client c) throws Exception {
        // nationalId of client renting the tool
        String rentingClient = c.getNationalId();
        this.available = false;
        // Date to return tool
        String returnDate = LocalDate.now().plusDays(7).toString();

        File toolsFile = new File("tools.txt");
        Scanner reader = new Scanner(toolsFile);
        List<String> lines = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(";");
            if (parts[0].equals(this.category) && parts[1].equals(this.model) && parts[2].equals(Integer.toString(this.productId))) {
                line = this.category + ";" + this.model + ";" + this.productId + ";" + this.price + ";" + this.available + ";" + returnDate + ";" + rentingClient;
            }
            lines.add(line);
        }

        reader.close();
        FileWriter writer = new FileWriter(toolsFile);
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
        c.addToDepositBalance(calculateDeposit(this.price));

    }

    // Calculate the deposit for the tool
    public double calculateDeposit(double price) {
        return (double) Math.round((price * 0.3) * 100) / 100;
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

    // Get price of renting the tool for 7 days
    public double getRentalPrice() {
        return (double) Math.round((this.price * 0.1) * 100) / 100;
    }

    public boolean getAvailable() {
        return available;
    }

}