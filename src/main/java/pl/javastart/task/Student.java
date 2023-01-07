package pl.javastart.task;

public class Student extends Person {
    private int index;

    public Student(int index, String firstName, String lastName) {
        super(firstName, lastName);
        this.index = index;
    }

    public String showInfo() {
        return this.index + " " + getFirstName() + " " + getLastName();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
