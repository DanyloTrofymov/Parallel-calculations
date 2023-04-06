package Task_3;

import Task_3.teacher.AbstractTeacher;

import java.util.concurrent.locks.ReentrantLock;

public class GradeJournal {
    private int[][][] grades;
    private Group[] groups;
    private final ReentrantLock lock = new ReentrantLock();

    public GradeJournal(Group[] groups, int weeks, int teachersCount) {
        this.groups = groups;
        grades = new int[studentsCount()][weeks][teachersCount];
    }

    public void addGrades(int[] gradesToAdd, int week, AbstractTeacher teacher) {
        lock.lock(); // заблокувати доступ до журналу
        try {
            int numStudents = studentsCount();
            for(int i = 0; i < numStudents; i++) {
                grades[i][week][teacher.getTeacherId()] = gradesToAdd[i];
            }
        } finally {
            lock.unlock(); // розблокувати доступ до журналу
        }
    }

    public int studentsCount(){
        int students = 0;
        for (Group group : groups) {
            students += group.getStudents().length;
        }
        return students;
    }
    public void printGrades() {
        lock.lock(); // заблокувати доступ до журналу
        try {
            int numStudents = studentsCount();
            int numWeeks = grades[0].length;
            int numTeachers = grades[0][0].length;
            for (int i = 0; i < numStudents; i++) {
                System.out.print("Student " + i + ": ");
                for (int j = 0; j < numWeeks; j++) {
                    System.out.print("Week " + (j + 1) + ": ");
                    for (int k = 0; k < numTeachers; k++) {
                        System.out.print(grades[i][j][k] + " ");
                    }
                    System.out.print(" | ");
                }
                System.out.println();
            }
        } finally {
            lock.unlock(); // розблокувати доступ до журналу
        }
    }
}
