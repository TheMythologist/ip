package yorm.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import yorm.task.After;
import yorm.task.Deadline;
import yorm.task.Event;
import yorm.task.Todo;
import yorm.tasklist.TaskList;

public class StorageTest {
    @Test
    public void storage_saveAndLoad_sameTasklist() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("task 1"));
        tasks.add(new Deadline("task 2", LocalDate.of(2026, 6, 6)));
        tasks.add(new Event("task 3", LocalDate.of(2026, 8, 6), LocalDate.of(2026, 8, 7)));
        tasks.add(new After("task 4", LocalDate.of(2025, 1, 1)));

        File tempFile = assertDoesNotThrow(() -> File.createTempFile("yormTest", null));
        // Ensure cleanup of file after test
        tempFile.deleteOnExit();

        // Delete file as `createTempFile` will create it
        tempFile.delete();

        Storage storage = new Storage(tempFile.getPath());
        storage.save(tasks);
        assertTrue(tempFile::exists);

        TaskList loadedTasks = assertDoesNotThrow(storage::load);
        assertTrue(loadedTasks.equals(tasks));
    }
}
