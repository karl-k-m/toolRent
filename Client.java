import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final String nationalId;   // Estonian national ID
    private String firstName;       // First name
    private String lastName;        // Last name
    private double depositBalance;     // Deposit balance

    public Client(String nationalId, String firstName, String lastName) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Client(String nationalId, String firstName, String lastName, double depositBalance) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.depositBalance = (double) depositBalance;
    }

    public void addTool(Tool t) {
        int x;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void addToDepositBalance(double depositBalance) throws Exception {
        this.depositBalance += depositBalance;
        updateEntry();
    }

    public void addClient() throws Exception{
        File clientsFile = new File("clients.txt");
        FileWriter writer = new FileWriter(clientsFile, true);
        writer.write(this.nationalId + ";" + this.firstName + ";" + this.lastName + ";" + this.depositBalance + System.lineSeparator());
        writer.close();
    }

    // Update client entry in clients.txt for changing deposit balance
    public void updateEntry() throws Exception{
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

    public String getLastName() {
        return lastName;
    }

    public double getDepositBalance() {
        return depositBalance;
    }
}
