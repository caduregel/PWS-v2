package main.java;

import main.java.io.StudentGraphBuilder;
import main.java.model.Student;
import main.java.model.StudentGraph;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Main {

    // print de lijst van leerlingen
    public static void main(String[] args) {
        try {
            Path csvPath = Paths.get("data", "input", "students.csv");
            StudentGraphBuilder builder = new StudentGraphBuilder();
            StudentGraph graph = builder.buildFromCSV(csvPath);

            System.out.println("Total number of students: " + graph.getSize());
            System.out.println("\nStudent Information:");
            System.out.println("===================");

            for (Student student : graph.getAllStudents()) {
                // Create a string of friend names, joined by commas
                String friendsList = student.getFriends().stream()
                        .map(Student::getName)
                        .collect(Collectors.joining(", "));

                System.out.printf("""
                        
                        Name: %s
                        Gender: %s
                        Cognitive Level: %s
                        Behavior Level: %d
                        Friends: %s
                        ------------------
                        """,
                        student.getName(),
                        student.getGender().getDutchName(),
                        student.getCognitiveLevel().getLabel(),
                        student.getBehaviorLevel(),
                        friendsList.isEmpty() ? "None" : friendsList
                );
            }
        } catch (Exception e) {
            System.err.println("Error processing CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
