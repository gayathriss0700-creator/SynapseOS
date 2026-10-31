package synapseos;

public class Request {
    private int id;
    private String employeeName;
    private String description;
    private String status;
    public Request(int id, String employeeName, String description) {
        this.id = id;
        this.employeeName = employeeName;
        this.description = description;
        this.status = "Pending";
    }

    public int getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayRequest() {
        System.out.println("Request ID    : " + id);
        System.out.println("Employee      : " + employeeName);
        System.out.println("Description   : " + description);
        System.out.println("Status        : " + status);
    }
}