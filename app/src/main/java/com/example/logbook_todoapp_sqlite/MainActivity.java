package com.example.logbook_todoapp_sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        FloatingActionButton addTaskFab = findViewById(R.id.addTaskFab);

        dbHelper = new DatabaseHelper(this);
        tasks = dbHelper.getAllTasks(); // Load tasks from the database
        taskAdapter = new TaskAdapter(tasks, this::editTask, this::deleteTask, dbHelper);

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);

        addTaskFab.setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_task, null);
        builder.setView(dialogView);

        EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        AlertDialog dialog = builder.create();

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        saveButton.setOnClickListener(v -> {
            String taskName = taskNameEditText.getText().toString().trim();
            if (!taskName.isEmpty()) {
                addTask(taskName);
                dialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void addTask(String taskName) {
        Task task = new Task(taskName);
        long id = dbHelper.addTask(task); // Add to the database
        task.setId((int) id); // Set the ID from the database
        tasks.add(task);
        taskAdapter.notifyItemInserted(tasks.size() - 1);
    }

    private void editTask(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_task, null);
        builder.setView(dialogView);

        EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        taskNameEditText.setText(task.getName());

        AlertDialog dialog = builder.create();

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        saveButton.setOnClickListener(v -> {
            String newTaskName = taskNameEditText.getText().toString().trim();
            if (!newTaskName.isEmpty()) {
                task.setName(newTaskName);
                dbHelper.updateTask(task); // Update in the database
                taskAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void deleteTask(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            dbHelper.deleteTask(task); // Delete from the database
            int position = tasks.indexOf(task);
            tasks.remove(task);
            taskAdapter.notifyItemRemoved(position);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}