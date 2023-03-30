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
                if (parts.length == 7) {
                    Tool t = new Tool(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]), parts[5], parts[6]);
                    tools.add(t);
                } else {
                    Tool t = new Tool(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Boolean.parseBoolean(parts[4]));
                    tools.add(t);
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

    // Rent a tool
    public static void rentTool(Client client) throws Exception {
        System.out.println("Mis kategooria tööriista otsite?");
        System.out.println("    1. Elektritööriistad");
        System.out.println("    2. Elektroonikatööriistad");
        System.out.println("    3. Abivahendid");
        System.out.println("    4. Autotööriistad");
        System.out.println("    5. Kaitsevarustus");

        String searchCategoryNr = askForInput("Sisesta kategooria number: ");
        String searchCategory = "";
        switch (searchCategoryNr) {
            case "1" -> searchCategory = "Elektritööriistad";
            case "2" -> searchCategory = "Elektroonikatööriistad";
            case "3" -> searchCategory = "Abivahendid";
            case "4" -> searchCategory = "Autotööriistad";
            case "5" -> searchCategory = "Kaitsevarustus";
            default -> {
            }
        }

        List<Tool> tools = getToolsFromFile();
        List<Tool> toolsInCategory = new ArrayList<>();
        for (Tool t : tools) {
            if (t.getCategory().equals(searchCategory)) {
                toolsInCategory.add(t);
            }
        }
        if (toolsInCategory.size() > 0) {
            System.out.println("Kategoorias " + searchCategory.toLowerCase() + " on järgmised tööriistad:");
            for (Tool t : toolsInCategory) {
                if (t.getAvailable()) {
                    System.out.println("    " + t.getProductId() + ". " + t.getModel() + " - " + t.getRentalPrice() + "€/7 päeva");
                }
            }
        }
        else {
            System.out.println("Kategoorias " + searchCategory.toLowerCase() + " ei ole ühtegi tööriista saadaval.");
            return;
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

    // Return a tool
    public static void returnTool(Client c) throws Exception {
        if (c.getRentedTools().size() == 0) {
            System.out.println("Teil ei ole ühtegi renditud tööriista!");
            return;
        }

        System.out.println("Teie renditud tooted:");
        for (Tool t : c.getRentedTools()) {
            System.out.println("    " + t.getProductId() + ". " + t.getModel());
        }

        String searchProductId = askForInput("Sisesta tööriista tootenumber, mida soovid tagastada: ");
        if (searchProductId != null) {
            Tool returnedTool = getToolByProductId(Integer.parseInt(searchProductId));
            returnedTool.returnTool();
            c.addToDepositBalance(-returnedTool.calculateDeposit(returnedTool.getPrice()));
        }
        else {
            System.out.println("Sellist tootenumbrit ei leitud!");
        }
        System.out.println("Tööriist on edukalt tagastatud!");
        System.out.println("Teile on tagastatud tagatis.");
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
                    client = new Client(z.getNationalId(), z.getFirstName(), z.getLastName(), z.getDepositBalance());
                    System.out.println("Tere tulemast tagasi, " + client.getFirstName() + "!");
                    break;
                }
            }
        }
        else {
            String firstName = askForInput("Sisesta oma eesnimi: ");
            String lastName = askForInput("Sisesta oma perekonnanimi: ");
            String nationalId = askForInput("Sisesta oma isikukood: ");
            client = new Client(nationalId, firstName, lastName, 0);
            client.addClient();
            System.out.println("Tere tulemast, " + client.getFirstName() + "!");
        }

        while (true) {
            System.out.println("Mida soovite teha?");
            System.out.println("    1. Rentida tööriist");
            System.out.println("    2. Tagastada tööriist");
            System.out.println("    3. Lahkuda");
            String answer2 = askForInput("");
            switch (answer2) {
                case "1" -> rentTool(client);
                case "2" -> returnTool(client);
                case "3" -> {
                    System.out.println("Nägemist!");
                    return;
                }
                default -> {
                }
            }
        }
    }
}