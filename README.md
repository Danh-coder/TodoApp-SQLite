# Android To-Do App with Persistence and Completion Status

✅ A robust Android application for managing your tasks, featuring persistent storage using SQLite and the ability to mark tasks as completed.

This project expands upon a basic To-Do app by implementing database storage to save tasks across sessions and adding functionality to track task completion status.

## Features

*   **Persistent Storage:** Tasks are saved locally in an SQLite database, ensuring data is retained even after the app is closed.
*   **Add New Tasks:** Create tasks using a familiar dialog.
*   **Edit Existing Tasks:** Modify task names via the same add/edit dialog.
*   **Delete Tasks:** Remove tasks with a confirmation dialog.
*   **Mark as Completed:** Check or uncheck tasks directly in the list to mark them as done or not done.
*   **Visual Completion Status:** Completed tasks are visually indicated with a strike-through on the task name.
*   **Task List Display:** View all your tasks in a vertically scrolling list using `RecyclerView`.
*   **Floating Action Button (FAB):** Provides quick access to adding new tasks.
*   **Basic Input Validation:** Prevents saving tasks with empty names.

## Design

The application reuses and builds upon the design established in previous iterations:

*   **Main Screen:** Uses a `ConstraintLayout` to contain the `RecyclerView` and the bottom-right anchored `FloatingActionButton`.
*   **Task Item Layout:** Updated to include a `CheckBox` before the task name `TextView`. A horizontal `LinearLayout` aligns the checkbox, task name, and action buttons (`Edit`, `Delete`). The `TextView` uses `layout_weight` to occupy available space, and `gravity="center_vertical"` ensures vertical alignment.
*   **Add/Edit Dialog:** The layout (`dialog_add_edit_task.xml`) remains unchanged, providing a consistent input interface for adding and editing tasks.
*   **Visual Feedback:** Leverages the `CheckBox` and strike-through text (`Paint.STRIKE_THRU_TEXT_FLAG`) to clearly indicate task completion status. Uses dialogs for adding/editing and deleting, and toasts for input validation feedback.

## Installation and Setup

To build and run this project, you will need Android Studio.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Danh-coder/TodoApp-SQLite.git
    ```

2.  **Open in Android Studio:**
    *   Launch Android Studio.
    *   Select "Open an Existing Android Studio Project".
    *   Navigate to the cloned directory and select it.

3.  **Build and Run:**
    *   Android Studio will sync the project dependencies.
    *   Connect an Android device or start an AVD (Android Virtual Device) emulator.
    *   Click the "Run" button (green play icon) in the toolbar.
    *   Choose your connected device or emulator to deploy the app.

## Code Overview

The project structure has been updated to incorporate database persistence and the completed status:

*   **`layout/activity_main.xml`**:
    *   Defines the main screen layout. Largely unchanged, it contains the `RecyclerView` (`@+id/tasksRecyclerView`) and `FloatingActionButton` (`@+id/addTaskFab`).

*   **`layout/task_item.xml`**:
    *   **Updated:** Defines the layout for a single task item row. Now includes a `CheckBox` (`@+id/taskCompletedCheckBox`) at the beginning of the horizontal `LinearLayout` to manage task completion status.

*   **`layout/dialog_add_edit_task.xml`**:
    *   Defines the layout for the add/edit task dialog. This file remains unchanged from the previous version.

*   **`MainActivity.java`**:
    *   The central activity. **Updated** to use `DatabaseHelper` (`dbHelper`) instead of an in-memory `ArrayList` for task storage.
    *   Tasks are loaded from the database using `dbHelper.getAllTasks()` in `onCreate`.
    *   Methods like `addTask`, `editTask`, and `deleteTask` now interact with `dbHelper` to perform CRUD operations on the database.
    *   Notifies the `TaskAdapter` of data changes (`notifyItemInserted`, `notifyItemRemoved`, `notifyDataSetChanged`).
    *   Manages the display of add/edit and delete confirmation dialogs.

*   **`Task.java`**:
    *   The data model for a task. **Updated** to include `private int id` and `private boolean completed` fields.
    *   The `id` field is used for database primary key management.
    *   Includes constructors and getter/setter methods for `id` and `completed`.

*   **`TaskAdapter.java`**:
    *   Connects task data to the `RecyclerView`. **Updated** to handle the new `CheckBox`.
    *   In `onBindViewHolder`, it sets the checkbox state based on `task.isCompleted()` and applies/removes the strike-through flag (`Paint.STRIKE_THRU_TEXT_FLAG`) on the `taskNameTextView`.
    *   Adds an `OnCheckedChangeListener` to the `CheckBox` to update the task's completed status. This update is then saved persistently via `dbHelper.updateTask(currentTask)`.
    *   Still uses the ViewHolder pattern for efficiency and interface callbacks (`OnEditClickListener`, `OnDeleteClickListener`) to delegate edit/delete actions back to `MainActivity`.

*   **`DatabaseHelper.java`**:
    *   **New File:** Extends `SQLiteOpenHelper` to manage the SQLite database.
    *   Handles database creation (`onCreate`) with a `tasks` table containing `id` (AUTOINCREMENT), `name`, and `completed` (INTEGER: 0/1) columns.
    *   Handles database upgrades (`onUpgrade`).
    *   Provides CRUD methods: `addTask` (inserts a new task), `updateTask` (updates name and completed status), `deleteTask` (deletes by ID), and `getAllTasks` (retrieves all tasks from the database).
    *   Uses constant strings for database name, version, table, and column names for maintainability.

## Future Improvements

While persistence and completion tracking are significant additions, the application can still be enhanced:

*   **Empty State Message:** Display a placeholder message when the task list is empty.
*   **Button Styling:** Improve the visual distinction between 'Cancel' and action buttons in dialogs (e.g., outline vs. filled).
*   **FAB Contrast:** Enhance the visibility of the Floating Action Button, potentially by adjusting its color or the background behind it.
*   **More Task Details:** Add fields for description, due date, priority, etc.
*   **Sorting/Filtering:** Implement options to sort tasks (e.g., by creation date, completion status, due date) or filter them (e.g., show only incomplete).
*   **UI/UX Refinement:** Improve scrolling performance for large lists or add animations.

## Technologies Used

*   Java
*   Android SDK
*   XML
*   SQLite Database
*   Material Design Components

## Contributing

Contributions are welcome! If you find a bug or have a suggestion, please open an issue or submit a pull request.

## Author

*   Danh Phan
