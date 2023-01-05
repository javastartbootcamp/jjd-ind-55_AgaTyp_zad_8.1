package pl.javastart.task;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.TreeSet;

public class UniversityApp {

    private Lecturer[] lecturers = new Lecturer[10];
    private Group[] groups = new Group[10];

    private int lecturersSize = 0;
    private int groupSize = 0;

    /**
     * Tworzy prowadzącego zajęcia.
     * W przypadku gdy prowadzący z zadanym id już istnieje, wyświetlany jest komunikat:
     * "Prowadzący z id [id_prowadzacego] już istnieje"
     *
     * @param id        - unikalny identyfikator prowadzącego
     * @param degree    - stopień naukowy prowadzącego
     * @param firstName - imię prowadzącego
     * @param lastName  - nazwisko prowadzącego
     */

    public void createLecturer(int id, String degree, String firstName, String lastName) {

        if (lecturersSize >= lecturers.length) {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }

        Lecturer lecturer = findLecturer(id);

        if (lecturer == null) {
            lecturers[lecturersSize] = new Lecturer(id, degree, firstName, lastName);
            lecturersSize++;
        } else {
            System.out.printf("Prowadzący z id %d już istnieje\n", id);
        }

    }

    private Lecturer findLecturer(int id) {

        for (int i = 0; i < lecturersSize; i++) {
            if (lecturers[i].getId() == id) {
                return lecturers[i];
            }
        }
        return null;
    }

    /**
     * Tworzy grupę zajęciową.
     * W przypadku gdy grupa z zadanym kodem już istnieje, wyświetla się komunikat:
     * "Grupa [kod grupy] już istnieje"
     * W przypadku gdy prowadzący ze wskazanym id nie istnieje wyświetla się komunikat:
     * "Prowadzący o id [id prowadzacego] nie istnieje"
     *
     * @param code       - unikalny kod grupy
     * @param name       - nazwa przedmiotu (np. "Podstawy programowania")
     * @param lecturerId - identyfikator prowadzącego. Musi zostać wcześniej utworzony za pomocą metody {@link #createLecturer(int, String, String, String)}
     */

    public void createGroup(String code, String name, int lecturerId) {
        if (groupSize >= groups.length) {
            groups = Arrays.copyOf(groups, groups.length * 2);
        }

        Group group = findGroup(code);
        Lecturer lecturer = findLecturer(lecturerId);

        if (lecturer == null) {
            System.out.printf("Prowadzący o id %d nie istnieje\n", lecturerId);
        } else if (group == null) {
            groups[groupSize] = new Group(code, name, lecturerId);
            groupSize++;
        } else {
            System.out.printf("Grupa %s już istnieje\n", code);
        }

    }

    private Group findGroup(String code) {
        for (int i = 0; i < groupSize; i++) {
            if (groups[i].getCode() == code) {
                return groups[i];
            }
        }
        return null;
    }

    /**
     * Dodaje studenta do grupy zajęciowej.
     * W przypadku gdy grupa zajęciowa nie istnieje wyświetlany jest komunikat:
     * "Grupa [kod grupy] nie istnieje
     *
     * @param index     - unikalny numer indeksu studenta
     * @param groupCode - kod grupy utworzonej wcześniej za pomocą {@link #createGroup(String, String, int)}
     * @param firstName - imię studenta
     * @param lastName  - nazwisko studenta
     */

    public void addStudentToGroup(int index, String groupCode, String firstName, String lastName) {

        Group group = findGroup(groupCode);

        if (group == null) {
            System.out.printf("Grupa %s nie istnieje\n", groupCode);
        } else if (Objects.equals(group.getCode(), groupCode) && group.indexExist(index)) {
            System.out.printf("Student o indeksie %d jest już w grupie %s\n", index, groupCode);
        } else if (Objects.equals(group.getCode(), groupCode) && !group.indexExist(index)) {
            group.add(new Student(index, firstName, lastName));
        }

    }

    /**
     * Wyświetla informacje o grupie w zadanym formacie.
     * Oczekiwany format:
     * Kod: [kod_grupy]
     * Nazwa: [nazwa przedmiotu]
     * Prowadzący: [stopień naukowy] [imię] [nazwisko]
     * Uczestnicy:
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * [nr indeksu] [imie] [nazwisko]
     * W przypadku gdy grupa nie istnieje, wyświetlany jest komunikat w postaci: "Grupa [kod] nie znaleziona"
     *
     * @param groupCode - kod grupy, dla której wyświetlić informacje
     */
    public void printGroupInfo(String groupCode) {

        Group group = findGroup(groupCode);
        if (group == null) {
            System.out.printf("Grupa %s nie znaleziona\n", groupCode);
        } else if (Objects.equals(group.getCode(), groupCode)) {
            Lecturer lecturer = findLecturer(group.getLecturerId());
            group.showGroupInfo();
            if (lecturer != null) {
                lecturer.showInfo();
            }
            group.showGroupStudents();
        }

    }

    /**
     * Dodaje ocenę końcową dla wskazanego studenta i grupy.
     * Student musi być wcześniej zapisany do grupy za pomocą {@link #addStudentToGroup(int, String, String, String)}
     * W przypadku, gdy grupa o wskazanym kodzie nie istnieje, wyświetlany jest komunikat postaci:
     * "Grupa pp-2022 nie istnieje"
     * W przypadku gdy student nie jest zapisany do grupy, wyświetlany jest komunikat w
     * postaci: "Student o indeksie 179128 nie jest zapisany do grupy pp-2022"
     * W przypadku gdy ocena końcowa już istnieje, wyświetlany jest komunikat w postaci:
     * "Student o indeksie 179128 ma już wystawioną ocenę dla grupy pp-2022"
     *
     * @param studentIndex - numer indeksu studenta
     * @param groupCode    - kod grupy
     * @param grade        - ocena
     */
    public void addGrade(int studentIndex, String groupCode, double grade) {

        Group group = findGroup(groupCode);
        if (group == null) {
            System.out.printf("Grupa %s nie istnieje\n", groupCode);
        } else if (Objects.equals(group.getCode(), groupCode)) {
            group.addGrade(studentIndex, grade);
        }

    }

    /**
     * Wyświetla wszystkie oceny studenta.
     * Przykładowy wydruk:
     * Podstawy programowania: 5.0
     * Programowanie obiektowe: 5.5
     *
     * @param index - numer indesku studenta dla którego wyświetlić oceny
     */
    public void printGradesForStudent(int index) {
        double studentGrade;

        for (int i = 0; i < groupSize; i++) {
            if ((studentGrade = groups[i].getGrade(index)) != 0.0) {
                System.out.printf(new Locale("US"), "%s: %.1f\n", groups[i].getName(), studentGrade);
            }
        }
    }

    /**
     * Wyświetla oceny studentów dla wskazanej grupy.
     * Przykładowy wydruk:
     * 179128 Marcin Abacki: 5.0
     * 179234 Dawid Donald: 4.5
     * 189521 Anna Kowalska: 5.5
     *
     * @param groupCode - kod grupy, dla której wyświetlić oceny
     */
    public void printGradesForGroup(String groupCode) {
        Group group = findGroup(groupCode);
        if (group == null) {
            System.out.printf("Grupa %s nie istnieje\n", groupCode);
        } else if (Objects.equals(group.getCode(), groupCode)) {
            group.showGroupGrades();
        }

    }

    /**
     * Wyświetla wszystkich studentów. Każdy student powinien zostać wyświetlony tylko raz.
     * Każdy student drukowany jest w nowej linii w formacie [nr_indesku] [imie] [nazwisko]
     * Przykładowy wydruk:
     * 179128 Marcin Abacki
     * 179234 Dawid Donald
     * 189521 Anna Kowalska
     */
    public void printAllStudents() {
        TreeSet<Integer> set = new TreeSet<>();
        int indexNo;

        for (int i = 0; i < groupSize; i++) {
            for (int j = 0; j < groups[i].getStudentsNo(); j++) {
                indexNo = groups[i].getStudents()[j].getIndex();
                if (!set.contains(indexNo)) {
                    System.out.println(groups[i].getStudents()[j].showInfo());
                    set.add(indexNo);
                }
            }
        }
    }
}
