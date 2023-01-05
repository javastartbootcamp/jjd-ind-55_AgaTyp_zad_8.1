package pl.javastart.task;

import java.util.Arrays;
import java.util.Locale;
import java.util.TreeSet;

public class UniversityApp {

    private Lecturer[] lecturers = new Lecturer[10];
    private Group[] groups = new Group[10];

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

        if ( id >= lecturers.length) {
            lecturers = Arrays.copyOf(lecturers, lecturers.length * 2);
        }

        if (lecturers[id - 1] == null) {
            lecturers[id - 1] = new Lecturer(id, degree, firstName, lastName);
        } else {
            System.out.printf("Prowadzący z id %d już istnieje\n", id);
        }
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
        if ( Group.getCounter() >= groups.length) {
            groups = Arrays.copyOf(groups, groups.length * 2);
        }

        boolean groupExists = false;
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                break;
            } else if (groups[i].getCode() == code) {
                groupExists = true;
                break;
            }
        }

        if (lecturers[lecturerId - 1] == null) {
            System.out.printf("Prowadzący o id %d nie istnieje\n", lecturerId);
        } else if (groupExists) {
            System.out.printf("Grupa %s już istnieje\n", code);
        } else {
            groups[Group.getCounter()] = new Group(code, name, lecturerId);
        }
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

        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                System.out.printf("Grupa %s nie istnieje\n", groupCode);
                break;
            } else if (groups[i].getCode() == groupCode && groups[i].indexExist(index)) {
                System.out.printf("Student o indeksie %d jest już w grupie %s\n", index, groupCode);
                break;
            } else if (groups[i].getCode() == groupCode && !groups[i].indexExist(index)) {
                groups[i].add(new Student(index, firstName, lastName));
                break;
            }
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
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                System.out.printf("Grupa %s nie znaleziona\n", groupCode);
                break;
            } else if (groups[i].getCode() == groupCode) {
                groups[i].showGroupInfo();
                int lecturerId = groups[i].getLecturerId();
                lecturers[lecturerId - 1].showInfo();
                groups[i].showGroupStudents();
                break;
            }
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
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                System.out.printf("Grupa %s nie istnieje\n", groupCode);
                break;
            } else if (groups[i].getCode() == groupCode) {
                groups[i].addGrade(studentIndex, grade);
                break;
            }
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
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                break;
            } else if ((studentGrade = groups[i].getGrade(index)) != 0.0) {
                System.out.printf(new Locale("US"),"%s: %.1f\n", groups[i].getName(), studentGrade);
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
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                System.out.printf("Grupa %s nie istnieje\n", groupCode);
                break;
            } else if (groups[i].getCode() == groupCode) {
                groups[i].showGroupGrades();
                break;
            }
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

        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == null) {
                break;
            } else {
                for (int j = 0; j < groups[i].getStudents().length; j++){
                    if (groups[i].getStudents()[j] == null) {
                        break;
                    } else {
                        indexNo = groups[i].getStudents()[j].getIndex();
                        if (!set.contains(indexNo)) {
                            System.out.println(groups[i].getStudents()[j].showInfo());
                            set.add(indexNo);
                        }
                    }
                }
            }
        }
    }
}
