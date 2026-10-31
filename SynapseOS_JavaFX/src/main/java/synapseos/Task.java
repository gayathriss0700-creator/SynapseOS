package synapseos;

public class Task {
    private int id;
    private String title;
    private String assignedTo;
    private String status;
    public Task(int id, String title, String assignedTo) {
        this.id = id;
        this.title = title;
        this.assignedTo = assignedTo;
        this.status = "Pending";
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getStatus() {
        return status;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayTask() {
        System.out.println("Task ID       : " + id);
        System.out.println("Task          : " + title);
        System.out.println("Assigned To   : " + assignedTo);
        System.out.println("Status        : " + status);
    }
}