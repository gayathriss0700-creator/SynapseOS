package synapseos;
public class Admin extends User {

    public Admin(String username) {
        super(username, "Admin");
    }
    @Override
    public void showPermissions() {
        System.out.println("Admin Permissions:");
        System.out.println("- Manage employees");
        System.out.println("- Manage departments");
        System.out.println("- Manage tasks");
        System.out.println("- Manage requests");
        System.out.println("- View reports");
    }
}