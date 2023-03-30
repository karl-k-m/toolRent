import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class rentalShop {
    public static String askForInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine();
    }

    // Gets all tools from tools.txt and returns them as a list
    public static List<Tool> getToolsFromFile() {
        List<Tool> tools = new ArrayList<>();
        try {
            File toolsFile = new File("tools.txt");
            Scanner reader = new Scanner(toolsFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(";");
                if (!Objects.equals(parts[4], "false")) {
                    if (parts.length == 6) {
                        Tool t = new Tool(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]), parts[5], parts[6]);
                        tools.add(t);
                    } else {
                        Tool t = new Tool(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]));
                        tools.add(t);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return tools;
    }

    // Get all clients from clients.txt and returns them as a list
    public static List<Client> getClientsFromFile() throws Exception{
        List<Client> clients = new ArrayList<>();
        File clientsFile = new File("clients.txt");
        Scanner reader = new Scanner(clientsFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(";");
            Client c = new Client(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
            clients.add(c);
        }
        reader.close();
        return clients;
    }

    // Gets the correct tool from tools.txt by productId
    public static Tool getToolByProductId(int productId) {
        List<Tool> tools = getToolsFromFile();
        for (Tool t : tools) {
            if (t.getProductId() == productId) {
                return t;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Client client = null;
        System.out.println("Teretulemast maailma kõige etemasse tööriistade rendipoodi!");
        System.out.println("Kas olete juba klient? (jah/ei)");
        String answer = askForInput("Sisesta vastus: ");
        if (answer.equals("jah")) {
            List<Client> clients = getClientsFromFile();
            String searchId = askForInput("Sisesta oma isikukood: ");
            for (Client z : clients) {
                if (Objects.equals(z.getNationalId(), searchId)){
                    Client c = new Client(z.getNationalId(), z.getFirstName(), z.getLastName(), z.getDepositBalance());
                    client = c;
                    System.out.println("Tere tulemast tagasi, " + client.getFirstName() + "!");
                    break;
                }
            }
        }
        else {
            String firstName = askForInput("Sisesta oma eesnimi: ");
            String lastName = askForInput("Sisesta oma perekonnanimi: ");
            String nationalId = askForInput("Sisesta oma isikukood: ");
            Client c = new Client(nationalId, firstName, lastName, 0);
            client = c;
            client.addClient();
            System.out.println("Tere tulemast, " + c.getFirstName() + "!");
        }

        System.out.println("Mis kategooria tööriista otsite?");
        System.out.println("    1. Elektritööriistad");
        System.out.println("    2. Elektroonikatööriistad");
        System.out.println("    3. Abivahendid");
        System.out.println("    4. Autotööriistad");
        System.out.println("    5. Kaitsevarustus");

        String searchCategoryNr = askForInput("Sisesta kategooria number: ");
        String searchCategory = "";
        switch (searchCategoryNr) {
            case "1":
                searchCategory = "Elektritööriistad";
                break;
            case "2":
                searchCategory = "Elektroonikatööriistad";
                break;
            case "3":
                searchCategory = "Abivahendid";
                break;
            case "4":
                searchCategory = "Autotööriistad";
                break;
            case "5":
                searchCategory = "Kaitsevarustus";
                break;
            default:
        }

        List<Tool> tools = getToolsFromFile();
        List<Tool> toolsInCategory = new ArrayList<>();
        for (Tool t : tools) {
            if (t.getCategory().equals(searchCategory)) {
                toolsInCategory.add(t);
            }
        }

        System.out.println("Kategoorias " + searchCategory.toLowerCase() + " on järgmised tööriistad:");
        for (Tool t : toolsInCategory) {
            System.out.println("    " + t.getProductId() + ". " + t.getModel() + " - " + t.getRentalPrice() + "€/7 päeva");
        }

        String searchProductId = askForInput("Sisesta tööriista tootenumber, mida soovid rentida: ");
        Tool rentedTool = getToolByProductId(Integer.parseInt(searchProductId));

        if (rentedTool != null) {
            System.out.println("Oled valinud tööriista " + rentedTool.getModel() + "!");
            System.out.println("Tööriista rendihind on " + rentedTool.getRentalPrice() + "€/7 päeva.");
            System.out.println("Tööriista tagatis on " + rentedTool.calculateDeposit(rentedTool.getPrice()) + "€.");
            System.out.println("Tööriista rendiperiood on 7 päeva.");
            System.out.println("Tööriista rendihind koos tagatisega on " + (rentedTool.getRentalPrice() + rentedTool.calculateDeposit(rentedTool.getPrice())) + "€.");
            String rentTool = askForInput("Kas soovid tööriista rentida? (jah/ei)");
            if (rentTool.equals("jah")) {
                System.out.println("Tööriista on edukalt renditud!");
                rentedTool.rentOut(client);
            }
            else {
                System.out.println("Tööriista ei ole renditud!");
            }
        }
        else {
            System.out.println("Sellist tootenumbrit ei leitud!");
        }

    }
}