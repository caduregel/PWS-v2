package main.java.model;

import java.util.*;

public class StudentGraph {
    private final Map<String, Student> students;

    public StudentGraph() {
        this.students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getName(), student);
    }

    public Student getStudent(String name) {
        return students.get(name);
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(students.values());
    }

    public int getSize() {
        return students.size();
    }
}