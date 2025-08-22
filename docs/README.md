# YormBot User Guide

![YormBot UI](./UI.png)

> "Yorm" ~YormBot

YormBot frees your mind of having to remember things you need to do. It's,

- text-based
- easy to learn
- ~FAST~ _SUPER_ FAST to use

All you need to do is,

1. download it from [the latest release](https://github.com/TheMythologist/ip/releases/latest).
2. double-click it.
3. add your tasks.
4. let it manage your tasks for you 😉

And it is **FREE**!

Features:

- [x] Managing tasks
- [x] Managing deadlines
- [ ] Reminders (coming soon)

---

If you are a Java programmer, you can use it to practice Java too. Here's the `main` method:

```java
public class Main {
    public static void main(String[] args) {
        Application.launch(Yorm.class, args);
    }
}
```

## Adding todos

Use YormBot to keep track of your todos!

Usage: `todo <description>`

Example: `todo read book`

## Adding deadlines

Use YormBot to keep track of your deadlines!

Usage: `deadline <description> /by <deadline_date>`

Example: `deadline return book /by 2026-06-06`

## Adding afters

Use YormBot to keep track of your afters!

Usage: `after <description> /after <after_date>`

Example: `after party /after 2026-06-06`

## Adding events

Use YormBot to keep track of your event!

Usage: `event <description> /from <event_start_date> /to <event_end_date>`

Example: `event project meeting /from 2026-07-01 /to 2026-07-02`

## Listing tasks

Use YormBot to list your existing tasks!

Usage: `list`

```txt
1.[T][X] read book
2.[D][ ] return book (by: Jun 6 2026)
3.[E][ ] project meeting (from: Aug 6 2026 to: Aug 7 2026)
4.[T][X] join sports club
5.[T][ ] borrow book
```

## Marking tasks as completed/incomplete

Usage: `mark/unmark <task_index>`

Example: `mark 1`

Example: `unmark 2`

## Find tasks

Use YormBot to find your tasks!

Usage: `find <keyword>`

Example: `find book`

## Deleting tasks

Use YormBot to delete your tasks!

Usage: `delete <task_index>`

Example: `delete 1`

## Exiting

Once you're done with YormBot, feel free to exit!

Usage: `bye`
