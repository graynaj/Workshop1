package pl.coderslab;
import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadTasksFromFile("tasks.csv");
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equals("exit")) {
            displayOptions();
            input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    exitAndSave("tasks.csv");
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }

    public static void displayOptions() {
        System.out.println("\033[0;34m" + "Please select an option:" + "\033[0m"); // ConsoleColors.BLUE
        String[] options = {"add", "remove", "list", "exit"};
        for (String option : options) {
            System.out.println(option);
        }
    }

    public static String[][] loadTasksFromFile(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            String[][] tasks = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                tasks[i] = lines.get(i).split(",");
            }
            return tasks;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please add task description");
        String description = scanner.nextLine();

        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();

        System.out.println("Is your task is important: true/false");
        boolean isImportant = scanner.nextBoolean();

        String[] newTask = {description, dueDate, String.valueOf(isImportant)};

        tasks = java.util.Arrays.copyOf(tasks, tasks.length + 1);

        tasks[tasks.length - 1] = newTask;
    }


    public static void removeTask() {
        Scanner scanner = new Scanner(System.in);

        int index;
        while (true) {
            System.out.println("Please select number to remove");
            try {
                index = Integer.parseInt(scanner.nextLine());
                if (index < 0) {
                    throw new NumberFormatException();
                }
                tasks = ArrayUtils.remove(tasks, index);
                System.out.println("Value was successfully deleted.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect argument passed. Please give number less than " + tasks.length);
            }
        }
    }

    public static void listTasks() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + ": " + Arrays.toString(tasks[i]));
        }
    }

    public static void exitAndSave(String filename) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
            for (String[] task : tasks) {
                writer.write(String.join(",", task));
                writer.newLine();
            }
            writer.close();
            System.out.println("\033[0;31m" + "Bye, bye." + "\033[0m"); // ConsoleColors.RED
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
