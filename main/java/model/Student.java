package main.java.model;
import java.util.*;

public class Student {
    private final String name;
    private final Gender gender;
    private final CognitiveLevel cognitiveLevel;
    private final int behaviorLevel;
    private final Set<String> friendNames;
    private final Set<Student> friends;

    public enum Gender {
        MALE("Jongen"),
        FEMALE("Meisje");

        private final String dutchName;

        Gender(String dutchName) {
            this.dutchName = dutchName;
        }

        public static Gender fromDutchString(String dutch) {
            if (dutch == null) {
                throw new IllegalArgumentException("Gender cannot be null");
            }
            if (dutch.toLowerCase().startsWith("j")) {
                return MALE;
            } else if (dutch.toLowerCase().startsWith("m")) {
                return FEMALE;
            }
            throw new IllegalArgumentException("Invalid gender: " + dutch);
        }

        public String getDutchName() {
            return dutchName;
        }
    }

    public enum CognitiveLevel {
        INTENSIEF("intensief", 1),
        BASIS("basis", 2),
        BASIS_UITDAGEND("basis-uitdagend", 3),
        UITDAGEND("uitdagend", 4),
        HB("HB", 5);

        private final String label;
        private final int value;

        CognitiveLevel(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public static CognitiveLevel fromString(String text) {
            if (text == null) {
                throw new IllegalArgumentException("Cognitive level cannot be null");
            }
            String normalizedText = text.toLowerCase().trim();
            for (CognitiveLevel level : CognitiveLevel.values()) {
                if (normalizedText.equals(level.label.toLowerCase())) {
                    return level;
                }
            }
            throw new IllegalArgumentException("Invalid cognitive level: " + text);
        }
    }

    public Student(String name, Gender gender, CognitiveLevel cognitiveLevel, int behaviorLevel) {
        validateInput(name, behaviorLevel);
        this.name = name;
        this.gender = gender;
        this.cognitiveLevel = cognitiveLevel;
        this.behaviorLevel = behaviorLevel;
        this.friendNames = new HashSet<>();
        this.friends = new HashSet<>();
    }

    private void validateInput(String name, int behaviorLevel) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (behaviorLevel < 1 || behaviorLevel > 5) {
            throw new IllegalArgumentException("Behavior level must be between 1 and 5");
        }
    }

    // Getters
    public String getName() { return name; }
    public Gender getGender() { return gender; }
    public CognitiveLevel getCognitiveLevel() { return cognitiveLevel; }
    public int getBehaviorLevel() { return behaviorLevel; }
    public Set<String> getFriendNames() { return Collections.unmodifiableSet(friendNames); }
    public Set<Student> getFriends() { return Collections.unmodifiableSet(friends); }

    // Friend management
    public void addFriendName(String friendName) {
        if (friendName != null && !friendName.trim().isEmpty()) {
            friendNames.add(friendName.trim());
        }
    }

    public void addFriend(Student friend) {
        if (friend != null && !friend.equals(this)) {
            friends.add(friend);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", cognitiveLevel=" + cognitiveLevel +
                ", behaviorLevel=" + behaviorLevel +
                ", friendCount=" + friends.size() +
                '}';
    }
}