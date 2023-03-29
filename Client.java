import java.util.List;

public class Client {
    private final int nationalId;
    private String firstName;
    private String lastName;
    private List<Tool> rentedTools;

    public Client(int nationalId, String firstName, String lastName) {
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addTool(Tool t) {
        rentedTools.add(t);
    }

}
