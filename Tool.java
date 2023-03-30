import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tool {
    private String category;                    // Tool category
    private String model;                       // Tool model name
    private final int productId;                // Product ID
    private double price;                       // Purchase price
    private boolean available;                  // Availability
    private String returnDate;                  // Date to return tool
    private String rentingClient;               // nationalId of client renting the tool

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
    public double getRentalPrice() { return price  * 0.1; }

    public boolean getAvailable() {
        return available;
    }

    // Update the entry for this tool in tools.txt
    public void returnTool() throws Exception{
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

    // Update the entry for this tool in tools.txt and rent it out.
    public void rentOut(Client c) throws Exception {
        this.rentingClient = c.getNationalId();
        this.available = false;
        this.returnDate = LocalDate.now().plusDays(7).toString();

        File toolsFile = new File("tools.txt");
        Scanner reader = new Scanner(toolsFile);
        List<String> lines = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(";");
            if (parts[0].equals(this.category) && parts[1].equals(this.model) && parts[2].equals(Integer.toString(this.productId))) {
                line = this.category + ";" + this.model + ";" + this.productId + ";" + this.price + ";" + this.available + ";" + this.returnDate + ";" + this.rentingClient;
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