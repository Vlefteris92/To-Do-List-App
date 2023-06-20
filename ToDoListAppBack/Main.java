package ToDoListAppBack;

public class Main {
    public static void main(String[] args) {
        // Δημιουργία μιας σύνδεσης με τη βάση δεδομένων
        DatabaseConnector.connect();

        // Δημιουργία μιας εργασίας
        Task task1 = new Task("Εργασία 1", "Περιγραφή εργασίας 1", "Εκκρεμής", 1);
        TodoListService todoListService = new TodoListService();
        todoListService.addTask(task1);

        // Ενημέρωση της εργασίας
        task1.setStatus("Ολοκληρωμένη");
        todoListService.updateTask(task1);

        // Διαγραφή της εργασίας
        todoListService.deleteTask(task1.getId());

//        // Ανάκτηση όλων των εργασιών
//        List<Task> tasks = todoListService.getAllTasks();
//
//        // Εκτύπωση των εργασιών
//        for (Task task : tasks) {
//            System.out.println(task);
//        }

        // Αποσύνδεση από τη βάση δεδομένων
        DatabaseConnector.disconnect();
    }
}