package yorm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import yorm.exception.YormException;
import yorm.task.Task;

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
    public ArrayList<Task> load() throws YormException {
        File file = new File(this.filePath);

        try (Scanner reader = new Scanner(file)) {
            ArrayList<Task> tasks = new ArrayList<>();
            while (reader.hasNextLine()) {
                String taskString = reader.nextLine();
                tasks.add(Task.fromSaveString(taskString));
            }
            return tasks;
        } catch (FileNotFoundException _) {
            return new ArrayList<>();
        }
    }

    /**
     * Save the tasks into the filepath
     */
    public void save(ArrayList<Task> tasks) {
        try {
            File file = new File(this.filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(this.filePath);
            for (Task task : tasks) {
                fileWriter.write(String.format("%s\n", task.toSaveString()));
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving to file");
        }
    }
}
