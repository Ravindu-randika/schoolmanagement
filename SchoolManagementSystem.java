import java.io.*;
import java.util.*;

/*
 * ADVANCED SCHOOL MANAGEMENT SYSTEM
 * Single Java File | Console Based | File Storage
 */

public class AdvancedSchoolManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Teacher> teachers = new ArrayList<>();

    static final String STUDENT_FILE = "students.dat";
    static final String TEACHER_FILE = "teachers.dat";

    // ================= MAIN =================
    public static void main(String[] args) {
        loadData();
        login();
        saveData();
    }

    // ================= LOGIN =================
    static void login() {
        System.out.println("===== SCHOOL MANAGEMENT LOGIN =====");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (user.equals("admin") && pass.equals("admin123")) {
            adminMenu();
        } else if (user.equals("teacher") && pass.equals("teach123")) {
            teacherMenu();
        } else {
            System.out.println("Invalid login!");
        }
    }

    // ================= ADMIN MENU =================
    static void adminMenu() {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Teacher");
            System.out.println("4. View Teachers");
            System.out.println("5. Remove Student");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> addTeacher();
                case 4 -> viewTeachers();
                case 5 -> removeStudent();
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ================= TEACHER MENU =================
    static void teacherMenu() {
        while (true) {
            System.out.println("\n===== TEACHER MENU =====");
            System.out.println("1. Add Marks");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Student Report");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addMarks();
                case 2 -> markAttendance();
                case 3 -> studentReport();
                case 0 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ================= STUDENT =================
    static void addStudent() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Class: ");
        String grade = sc.nextLine();

        students.add(new Student(id, name, grade));
        System.out.println("Student Added!");
    }

    static void viewStudents() {
        students.forEach(System.out::println);
    }

    static void removeStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        students.removeIf(s -> s.id == id);
        System.out.println("Student Removed!");
    }

    // ================= TEACHER =================
    static void addTeacher() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Subject: ");
        String subject = sc.nextLine();

        teachers.add(new Teacher(id, name, subject));
        System.out.println("Teacher Added!");
    }

    static void viewTeachers() {
        teachers.forEach(System.out::println);
    }

    // ================= ACADEMIC =================
    static void addMarks() {
        Student s = findStudent();
        if (s == null) return;

        System.out.print("Subject: ");
        String subject = sc.nextLine();
        System.out.print("Marks: ");
        int marks = sc.nextInt();

        s.marks.put(subject, marks);
        System.out.println("Marks Saved!");
    }

    static void markAttendance() {
        Student s = findStudent();
        if (s == null) return;

        System.out.print("Present? (true/false): ");
        boolean present = sc.nextBoolean();
        s.attendance.add(present);

        System.out.println("Attendance Updated!");
    }

    static void studentReport() {
        Student s = findStudent();
        if (s == null) return;

        System.out.println("\n===== REPORT =====");
        System.out.println(s);

        System.out.println("Marks:");
        s.marks.forEach((k, v) -> System.out.println(k + " : " + v));

        long present = s.attendance.stream().filter(a -> a).count();
        System.out.println("Attendance: " + present + "/" + s.attendance.size());
    }

    static Student findStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students)
            if (s.id == id) return s;

        System.out.println("Student Not Found!");
        return null;
    }

    // ================= FILE HANDLING =================
    static void saveData() {
        try {
            ObjectOutputStream o1 = new ObjectOutputStream(new FileOutputStream(STUDENT_FILE));
            o1.writeObject(students);
            o1.close();

            ObjectOutputStream o2 = new ObjectOutputStream(new FileOutputStream(TEACHER_FILE));
            o2.writeObject(teachers);
            o2.close();
        } catch (Exception ignored) {}
    }

    static void loadData() {
        try {
            ObjectInputStream i1 = new ObjectInputStream(new FileInputStream(STUDENT_FILE));
            students = (ArrayList<Student>) i1.readObject();
            i1.close();

            ObjectInputStream i2 = new ObjectInputStream(new FileInputStream(TEACHER_FILE));
            teachers = (ArrayList<Teacher>) i2.readObject();
            i2.close();
        } catch (Exception ignored) {}
    }

    // ================= CLASSES =================
    static class Student implements Serializable {
        int id;
        String name, grade;
        HashMap<String, Integer> marks = new HashMap<>();
        ArrayList<Boolean> attendance = new ArrayList<>();

        Student(int id, String name, String grade) {
            this.id = id;
            this.name = name;
            this.grade = grade;
        }

        public String toString() {
            return id + " | " + name + " | Class " + grade;
        }
    }

    static class Teacher implements Serializable {
        int id;
        String name, subject;

        Teacher(int id, String name, String subject) {
            this.id = id;
            this.name = name;
            this.subject = subject;
        }

        public String toString() {
            return id + " | " + name + " | " + subject;
        }
    }
}
