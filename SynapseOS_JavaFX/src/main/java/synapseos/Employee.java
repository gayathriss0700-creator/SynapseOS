package synapseos;
public class Employee extends User {

    private int id;
    private String name;
    private String department;
    private String email;

    public Employee(int id, String name, String department, String email) {
        super(name, "Employee");

        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayEmployee() {

        System.out.println("Employee ID   : " + id);
        System.out.println("Name          : " + name);
        System.out.println("Department    : " + department);
        System.out.println("Email         : " + email);
    }
    @Override
    public void showPermissions() {
        System.out.println("Employee Permissions:");
        System.out.println("- View own information");
        System.out.println("- View assigned tasks");
        System.out.println("- Submit requests");
    }
}