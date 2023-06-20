document.addEventListener("DOMContentLoaded", function() {
    var todoInput = document.getElementById("todo-input");
    var addBtn = document.getElementById("add-btn");
    var todoList = document.getElementById("todo-list");
  
    addBtn.addEventListener("click", function() {
      var task = todoInput.value;
      if (task !== "") {
        // Καλούμε την υπηρεσία ToDoListService για να προσθέσουμε μια εργασία
        addToDoTask(task);
      }
    });
  
    // Φορτώνουμε αρχικά όλες τις εργασίες από τη βάση δεδομένων
    loadTasks();
  
    function addToDoTask(task) {
      fetch("http://localhost:8080/addTask", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: task,
          description: "",
          status: "Εκκρεμής",
          userId: 1,
        }),
      })
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            var listItem = document.createElement("li");
            listItem.textContent = task;
            todoList.appendChild(listItem);
            todoInput.value = "";
          } else {
            console.log(data.message);
          }
        })
        .catch(error => {
          console.error("Error:", error);
        });
    }
  
    function loadTasks() {
      fetch("http://localhost:8080/getAllTasks")
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            data.tasks.forEach(task => {
              var listItem = document.createElement("li");
              listItem.textContent = task.title;
              todoList.appendChild(listItem);
            });
          } else {
            console.log(data.message);
          }
        })
        .catch(error => {
          console.error("Error:", error);
        });
    }
  });
  