import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final String nationalId;    // Estonian national ID
    private final String firstName;     // First name
    private final String lastName;      // Last name
    private double depositBalance;      // Deposit balance

    public Client(String nationalId, String firstName, String lastName) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(String nationalId, String firstName, String lastName, double depositBalance) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.depositBalance = depositBalance;
    }

    // Add deposit to client's deposit balance
    public void addToDepositBalance(double depositBalance) throws Exception {
        this.depositBalance += depositBalance;
        updateEntry();
    }

    // Add new client to clients.txt
    public void addClient() throws Exception {
        File clientsFile = new File("clients.txt");
        FileWriter writer = new FileWriter(clientsFile, true);
        writer.write(this.nationalId + ";" + this.firstName + ";" + this.lastName + ";" + this.depositBalance + System.lineSeparator());
        writer.close();
    }

    // Update client entry in clients.txt for changing deposit balance
    public void updateEntry() throws Exception {
        File clientsFile = new File("clients.txt");
        Scanner reader = new Scanner(clientsFile);
        List<String> lines = new ArrayList<>();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(";");
            if (parts[0].equals(this.nationalId)) {
                line = this.nationalId + ";" + this.firstName + ";" + this.lastName + ";" + this.depositBalance;
            }
            lines.add(line);
        }
        reader.close();

        FileWriter writer = new FileWriter(clientsFile);
        for (String line : lines) {
            writer.write(line + System.lineSeparator());
        }
        writer.close();
    }

    // Get list of tools rented by client
    public List<Tool> getRentedTools() throws Exception {
        // get tools rented by user with nationalId from tools.txt
        List<Tool> rentedTools = new ArrayList<>();
        File toolsFile = new File("tools.txt");
        Scanner reader = new Scanner(toolsFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 5) {
                if (parts[6].equals(this.nationalId)) {
                    Tool t = new Tool(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]), parts[5], parts[6]);
                    rentedTools.add(t);
                }
            }
        }
        reader.close();
        return rentedTools;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getLastName() {
        return lastName;
    }

    public double getDepositBalance() {
        return depositBalance;
    }
}
