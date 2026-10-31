package synapseos;
public class Department {
    private int id;
    private String name;
    private String manager;

    public Department(int id, String name, String manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void displayDepartment() {

        System.out.println("Department ID : " + id);
        System.out.println("Department    : " + name);
        System.out.println("Manager       : " + manager);
    }
}