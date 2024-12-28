package main.java.io;

import main.java.model.Student;
import main.java.model.StudentGraph;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class StudentGraphBuilder {
    private final CSVReader csvReader;

    public StudentGraphBuilder() {
        this.csvReader = new CSVReader();
    }

    public StudentGraph buildFromCSV(Path filePath) throws IOException {
        List<String[]> records = csvReader.readCSV(filePath);
        StudentGraph graph = new StudentGraph();

        // First pass: Create all students
        for (String[] record : records) {
            try {
                Student student = createStudent(record);
                graph.addStudent(student);
            } catch (Exception e) {
                System.err.println("Error processing record: " + String.join(", ", record));
                throw e;
            }
        }

        // Second pass: Connect friends
        for (String[] record : records) {
            connectFriends(record, graph);
        }

        return graph;
    }

    private Student createStudent(String[] values) {
        try {
            validateRecord(values);
            String name = values[0];
            Student.Gender gender = Student.Gender.fromDutchString(values[1]);
            Student.CognitiveLevel cognitiveLevel = Student.CognitiveLevel.fromString(values[2]);
            int behavior = Integer.parseInt(values[3]);
            
            Student student = new Student(name, gender, cognitiveLevel, behavior);

            // Add friend names (starting from index 4)
            for (int i = 4; i < values.length; i++) {
                if (values[i] != null && !values[i].trim().isEmpty()) {
                    student.addFriendName(values[i].trim());
                }
            }

            return student;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating student from values: " + 
                String.join(", ", values), e);
        }
    }

    private void validateRecord(String[] values) {
        if (values == null || values.length < 4) {
            throw new IllegalArgumentException("Record must have at least 4 values (name, gender, cognitive level, behavior)");
        }
    }

    private void connectFriends(String[] values, StudentGraph graph) {
        Student student = graph.getStudent(values[0]);
        if (student == null) {
            throw new IllegalStateException("Student not found: " + values[0]);
        }
        
        for (String friendName : student.getFriendNames()) {
            Student friend = graph.getStudent(friendName);
            if (friend != null) {
                student.addFriend(friend); // Only add friend to the student who listed them
            } else {
                System.err.println("Warning: Friend not found: " + friendName + " for student: " + student.getName());
            }
        }
    }
}