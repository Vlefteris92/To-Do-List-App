package ToDoListAppBack;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoListService {
    public void addTask(Task task) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (title, description, status, user_id) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus());
            statement.setInt(4, task.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE tasks SET title = ?, description = ?, status = ?, user_id = ?, updated_at = ? WHERE id = ?")) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus());
            statement.setInt(4, task.getUserId());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(6, task.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
            statement.setInt(1, taskId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setStatus(resultSet.getString("status"));
        task.setUserId(resultSet.getInt("user_id"));
        task.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        task.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

        return task;
    }

    public List<Task> getTasksByStatus(String status) {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE status = ?")) {
            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = mapResultSetToTask(resultSet);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
