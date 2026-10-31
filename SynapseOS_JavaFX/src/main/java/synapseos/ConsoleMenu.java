
package synapseos;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class ConsoleMenu {

    // Shared in-memory data for all roles in this running application.
    private static final ArrayList<Employee> employees = new ArrayList<>();
    private static final ArrayList<Department> departments = new ArrayList<>();
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final ArrayList<Request> requests = new ArrayList<>();

    private ConsoleMenu() {
    }

    // Returns when the current user logs out.
    public static void openRoleMenu(String role) {
        User user = createUserForRole(role);
        if (user == null) {
            showMessage("Invalid role", "The selected role is not valid.");
            return;
        }

        showMessage("Welcome", "Welcome, " + user.getUsername() + "!");
        user.showPermissions();

        boolean logout = false;
        while (!logout) {
            if (user instanceof Admin) {
                String choice = choose("Admin Main Menu",
                        "Employee Management", "Department Management",
                        "Task Management", "Request Management", "Reports",
                        "Logout");
                switch (choice) {
                    case "Employee Management": employeeManagement(true); break;
                    case "Department Management": departmentManagement(true); break;
                    case "Task Management": taskManagement(true); break;
                    case "Request Management": requestManagement(true, "Admin"); break;
                    case "Reports": reports(); break;
                    case "Logout": logout = true; break;
                    default: logout = true; break;
                }
            } else if (user instanceof Manager) {
                String choice = choose("Manager Main Menu",
                        "Employee Management", "Department Management",
                        "Task Management", "Request Management", "Reports",
                        "Logout");
                switch (choice) {
                    case "Employee Management": employeeManagement(false); break;
                    case "Department Management": departmentManagement(false); break;
                    case "Task Management": taskManagement(true); break;
                    case "Request Management": requestManagement(false, "manager"); break;
                    case "Reports": reports(); break;
                    case "Logout": logout = true; break;
                    default: logout = true; break;
                }
            } else {
                String choice = choose("Employee Main Menu",
                        "My Information", "My Tasks", "My Requests", "Logout");
                switch (choice) {
                    case "My Information":
                        ((Employee) user).displayEmployee();
                        break;
                    case "My Tasks":
                        viewMyTasks(user.getUsername());
                        break;
                    case "My Requests":
                        employeeRequests(user.getUsername());
                        break;
                    case "Logout":
                    default:
                        logout = true;
                        break;
                }
            }
        }
    }

    private static User createUserForRole(String role) {
        if (role == null) return null;
        switch (role.toLowerCase()) {
            case "admin": return new Admin("admin");
            case "manager": return new Manager("manager");
            case "employee":
                return findEmployee("employee") != null
                        ? findEmployee("employee")
                        : new Employee(1, "employee", "General",
                                "employee@synapseos.com");
            default: return null;
        }
    }

    private static String choose(String title, String... options) {
        ChoiceDialog<String> dialog =
                new ChoiceDialog<>(options[0], options);
        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText("Choose an option:");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("Logout");
    }

    private static String ask(String title, String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse(null);
    }

    private static Integer askInt(String title, String prompt) {
        while (true) {
            String value = ask(title, prompt);
            if (value == null) return null;
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException ex) {
                showMessage("Invalid number", "Please enter a whole number.");
            }
        }
    }

    private static void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("SynapseOS");
        alert.setHeaderText("Operation could not be completed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static Employee findEmployee(String username) {
        for (Employee e : employees) {
            if (e.getName().equalsIgnoreCase(username)) return e;
        }
        return null;
    }

    private static void employeeManagement(boolean admin) {
        boolean back = false;
        while (!back) {
            String choice = admin
                    ? choose("Employee Management", "Add Employee",
                            "View Employees", "Search Employee", "Back")
                    : choose("Employee Management", "View Employees",
                            "Search Employee", "Back");

            if (admin && "Add Employee".equals(choice)) {
                Integer id = askInt("Add Employee", "Enter ID:");
                if (id == null) continue;
                String name = ask("Add Employee", "Enter name:");
                String dept = ask("Add Employee", "Enter department:");
                String email = ask("Add Employee", "Enter email:");
                if (name == null || dept == null || email == null) continue;

                employees.add(new Employee(id, name.trim(), dept.trim(), email.trim()));
                showMessage("Success", "Employee added successfully.");
            } else if ("View Employees".equals(choice)) {
                if (employees.isEmpty()) {
                    showMessage("Employees", "No employees found.");
                } else {
                    for (Employee e : employees) {
                        showMessage("Employee", employeeText(e));
                    }
                }
            } else if ("Search Employee".equals(choice)) {
                Integer id = askInt("Search Employee", "Enter employee ID:");
                if (id == null) continue;
                Employee found = null;
                for (Employee e : employees) {
                    if (e.getId() == id) { found = e; break; }
                }
                showMessage("Search Result",
                        found == null ? "Employee not found." : employeeText(found));
            } else {
                back = true;
            }
        }
    }

    private static String employeeText(Employee e) {
        return "ID: " + e.getId() + "\nName: " + e.getName()
                + "\nDepartment: " + e.getDepartment()
                + "\nEmail: " + e.getEmail();
    }

    private static void departmentManagement(boolean admin) {
        boolean back = false;
        while (!back) {
            String choice = admin
                    ? choose("Department Management", "Add Department",
                            "View Departments", "Back")
                    : choose("Department Management", "View Departments", "Back");

            if (admin && "Add Department".equals(choice)) {
                Integer id = askInt("Add Department", "Enter department ID:");
                if (id == null) continue;
                String name = ask("Add Department", "Enter department name:");
                String manager = ask("Add Department", "Enter manager name:");
                if (name == null || manager == null) continue;

                departments.add(new Department(id, name.trim(), manager.trim()));
                showMessage("Success", "Department added successfully.");
            } else if ("View Departments".equals(choice)) {
                if (departments.isEmpty()) {
                    showMessage("Departments", "No departments found.");
                } else {
                    for (Department d : departments) d.displayDepartment();
                }
            } else {
                back = true;
            }
        }
    }

    private static void taskManagement(boolean manage) {
        boolean back = false;
        while (!back) {
            String choice = manage
                    ? choose("Task Management", "Add Task", "View Tasks",
                            "Update Task Status", "Back")
                    : choose("Task Management", "View Tasks", "Back");

            if (manage && "Add Task".equals(choice)) {
                Integer id = askInt("Add Task", "Enter task ID:");
                if (id == null) continue;
                String title = ask("Add Task", "Enter task title:");
                String assigned = ask("Add Task", "Assign to (username):");
                if (title == null || assigned == null) continue;

                tasks.add(new Task(id, title.trim(), assigned.trim()));
                showMessage("Success", "Task added successfully.");
            } else if ("View Tasks".equals(choice)) {
                if (tasks.isEmpty()) showMessage("Tasks", "No tasks found.");
                else for (Task t : tasks) t.displayTask();
            } else if (manage && "Update Task Status".equals(choice)) {
                updateTaskStatus();
            } else {
                back = true;
            }
        }
    }

    private static void updateTaskStatus() {
        Integer id = askInt("Update Task", "Enter task ID:");
        if (id == null) return;
        for (Task t : tasks) {
            if (t.getId() == id) {
                String status = ask("Update Task", "Enter new status:");
                if (status != null) {
                    t.setStatus(status.trim());
                    showMessage("Success", "Task status updated.");
                }
                return;
            }
        }
        showMessage("Tasks", "Task not found.");
    }

    private static void requestManagement(boolean admin, String requester) {
        boolean back = false;
        while (!back) {
            String choice = admin
                    ? choose("Request Management", "Submit Request", "View Requests",
                            "Approve Request", "Reject Request", "Back")
                    : choose("Request Management", "View Requests",
                            "Approve Request", "Reject Request", "Back");

            if (admin && "Submit Request".equals(choice)) {
                submitRequest(requester);
            } else if ("View Requests".equals(choice)) {
                if (requests.isEmpty()) showMessage("Requests", "No requests found.");
                else for (Request r : requests) r.displayRequest();
            } else if ("Approve Request".equals(choice)) {
                updateRequestStatus("Approved");
            } else if ("Reject Request".equals(choice)) {
                updateRequestStatus("Rejected");
            } else {
                back = true;
            }
        }
    }

    private static void submitRequest(String username) {
        Integer id = askInt("Submit Request", "Enter request ID:");
        if (id == null) return;
        String description = ask("Submit Request", "Enter description:");
        if (description == null) return;

        requests.add(new Request(id, username, description.trim()));
        showMessage("Success", "Request submitted successfully.");
    }

    private static void updateRequestStatus(String status) {
        Integer id = askInt("Update Request", "Enter request ID:");
        if (id == null) return;
        for (Request r : requests) {
            if (r.getId() == id) {
                r.setStatus(status);
                showMessage("Success", "Request " + status.toLowerCase() + ".");
                return;
            }
        }
        showMessage("Requests", "Request not found.");
    }

    private static void viewMyTasks(String username) {
        boolean found = false;
        for (Task t : tasks) {
            if (t.getAssignedTo().equalsIgnoreCase(username)) {
                t.displayTask();
                found = true;
            }
        }
        if (!found) showMessage("My Tasks", "No tasks assigned to you.");
    }

    private static void employeeRequests(String username) {
        boolean back = false;
        while (!back) {
            String choice = choose("My Requests",
                    "Submit Request", "View My Requests", "Back");
            if ("Submit Request".equals(choice)) {
                submitRequest(username);
            } else if ("View My Requests".equals(choice)) {
                boolean found = false;
                for (Request r : requests) {
                    if (r.getEmployeeName().equalsIgnoreCase(username)) {
                        r.displayRequest();
                        found = true;
                    }
                }
                if (!found) showMessage("My Requests", "No requests found.");
            } else {
                back = true;
            }
        }
    }

    private static void reports() {
        showMessage("Reports",
                "Total Employees: " + employees.size()
                + "\nTotal Departments: " + departments.size()
                + "\nTotal Tasks: " + tasks.size()
                + "\nTotal Requests: " + requests.size());
    }
}
