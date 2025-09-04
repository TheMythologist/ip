package yorm.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import yorm.exception.YormException;
import yorm.tasklist.TaskList;

/**
 * Handles the loading and storing of tasks to the disk/filesystem.
 */
public class Storage {
    /** The save file location */
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the tasks from the filepath.
     *
     * @return The tasks loaded from the save file.
     * @throws YormException If an error occurs during loading of the save file.
     */
    public TaskList load() throws YormException {
        try (FileInputStream streamIn = new FileInputStream(this.filePath)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(streamIn)) {
                TaskList tasks = (TaskList) objectInputStream.readObject();
                return tasks;
            }
        } catch (Exception e) {
            return new TaskList();
        }
    }

    /**
     * Save the tasks into the filepath.
     */
    public void save(TaskList tasks) {
        try {
            // Ensure directory exists
            File file = new File(this.filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            assert parentDir.exists();

            try (FileOutputStream fout = new FileOutputStream(this.filePath)) {
                try (ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                    oos.writeObject(tasks);
                }
            }
            assert file.exists();
        } catch (IOException e) {
            System.out.println("Error saving to file");
        }
    }
}
