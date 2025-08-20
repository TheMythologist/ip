import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
