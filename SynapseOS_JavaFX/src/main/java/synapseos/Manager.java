package synapseos;
public class Manager extends User {

    public Manager(String username) {
        super(username, "Manager");
    }
    @Override
    public void showPermissions() {
        System.out.println("Manager Permissions:");
        System.out.println("- View employees");
        System.out.println("- View departments");
        System.out.println("- Manage tasks");
        System.out.println("- Approve or reject requests");
        System.out.println("- View reports");
    }
}