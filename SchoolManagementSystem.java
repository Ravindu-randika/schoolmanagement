import java.util.*;

/*
 * ONE FILE School Management System
 * Console-based Java application
 */

public class SchoolManagementSystem {

    static Scanner sc = new Scanner(System.in);

    // ===== DATA STORAGE =====
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Teacher> teachers = new ArrayList<>();

    // ===== MAIN METHOD =====
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== SCHOOL MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Teacher");
            System.out.println("4. View Teachers");
            System.out.println("5. Add Marks to Student");
            System.out.println("6. View Student Report");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> addTeacher();
                case 4 -> viewTeachers();
                case 5 -> addMarks();
                case 6 -> viewStudentReport();
                case 0 -> {
                    System.out.println("Exiting System...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ===== STUDENT METHODS =====
    static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Grade/Class: ");
        String grade = sc.nextLine();

        students.add(new Student(id, name, grade));
        System.out.println("Student added successfully!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // ===== TEACHER METHODS =====
    static void addTeacher() {
        System.out.print("Enter Teacher ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Teacher Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();

        teachers.add(new Teacher(id, name, subject));
        System.out.println("Teacher added successfully!");
    }

    static void viewTeachers() {
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }

        System.out.println("\n--- Teacher List ---");
        for (Teacher t : teachers) {
            System.out.println(t);
        }
    }

    // ===== MARKS =====
    static void addMarks() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter Subject: ");
        sc.nextLine();
        String subject = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        student.marks.put(subject, marks);
        System.out.println("Marks added successfully!");
    }

    static void viewStudentReport() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\n===== STUDENT REPORT =====");
        System.out.println("ID: " + student.id);
        System.out.println("Name: " + student.name);
        System.out.println("Class: " + student.grade);

        if (student.marks.isEmpty()) {
            System.out.println("No marks available.");
            return;
        }

        int total = 0;
        for (Map.Entry<String, Integer> e : student.marks.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
            total += e.getValue();
        }

        double avg = total / (double) student.marks.size();
        System.out.println("Average Marks: " + avg);
    }

    static Student findStudentById(int id) {
        for (Student s : students) {
            if (s.id == id) return s;
        }
        return null;
    }

    // ===== INNER CLASSES =====
    static class Student {
        int id;
        String name;
        String grade;
        HashMap<String, Integer> marks = new HashMap<>();

        Student(int id, String name, String grade) {
            this.id = id;
            this.name = name;
            this.grade = grade;
        }

        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Class: " + grade;
        }
    }

    static class Teacher {
        int id;
        String name;
        String subject;

        Teacher(int id, String name, String subject) {
            this.id = id;
            this.name = name;
            this.subject = subject;
        }

        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Subject: " + subject;
        }
    }
}
