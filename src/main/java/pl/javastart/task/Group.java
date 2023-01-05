package pl.javastart.task;

import java.util.Arrays;

public class Group {
    private String code;
    private String name;
    private int lecturerId;

    private Student[] students = new Student[10];
    private int studentsNo = 0;

    private double[] grades = new double[10];

    public Group(String code, String name, int lecturerId) {
        this.code = code;
        this.name = name;
        this.lecturerId = lecturerId;
    }

    void add(Student student) {
        if (studentsNo >= students.length) {
            students = Arrays.copyOf(students, students.length * 2);
            grades = Arrays.copyOf(grades, grades.length * 2);
        }

        if (studentsNo < students.length) {
            students[studentsNo] = student;
            studentsNo++;
        }
    }

    boolean indexExist(int index) {
        boolean exists = false;
        for (int i = 0; i < this.students.length; i++) {
            if (this.students[i] != null && this.students[i].getIndex() == index) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    void showGroupInfo() {
        System.out.printf("Kod: %s\n", this.code);
        System.out.printf("Nazwa: %s\n", this.name);
    }

    void showGroupStudents() {
        System.out.print("Uczestnicy:\n");
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                break;
            } else {
                System.out.println(students[i].showInfo());
            }
        }
    }

    void addGrade(int index, double grade) {
        for (int i = 0; i < this.students.length; i++) {
            if (!indexExist(index)) {
                System.out.printf("Student o indeksie %d nie jest zapisany do grupy %s\n", index, this.code);
                break;
            } else if (this.students[i].getIndex() == index && this.grades[i] == 0) {
                this.grades[i] = grade;
                break;
            } else if (this.students[i].getIndex() == index && this.grades[i] != 0) {
                System.out.printf("Student o indeksie %d ma już wystawioną ocenę dla grupy %s\n", index, this.code);
                break;
            }
        }
    }

    double getGrade(int index) {
        double studentGrade = 0;

        for (int i = 0; i < this.students.length; i++) {
            if (indexExist(index)) {
                studentGrade = this.grades[i];
                break;
            }
        }
        return studentGrade;
    }

    void showGroupGrades() {
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                break;
            } else {
                System.out.println(students[i].showInfo() + ": " + this.grades[i]);
            }
        }
    }

    public int getStudentsNo() {
        return studentsNo;
    }

    public void setStudentsNo(int studentsNo) {
        this.studentsNo = studentsNo;
    }

    public Student[] getStudents() {
        return students;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }
}
