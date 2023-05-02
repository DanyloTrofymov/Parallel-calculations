package Task_3;

import Task_3.teacher.AbstractTeacher;

import java.util.concurrent.locks.ReentrantLock;

public class GradeJournal {
    private int[][] grades;
    private Group group;
    private final ReentrantLock lock = new ReentrantLock();

    public GradeJournal(Group group, int weeks) {
        this.group = group;
        grades = new int[studentsCount()][weeks];
    }

    public void addGrades(int[] gradesToAdd, int week) {
            int numStudents = studentsCount();
            for(int i = 0; i < numStudents; i++) {
                synchronized (grades[i]) {
                    grades[i][week] = gradesToAdd[i];
                }
            }
    }

    public int studentsCount(){
        int students = group.getStudents().length;
        return students;
    }
    public void printGrades() {
        lock.lock(); // заблокувати доступ до журналу
        try {
            int numStudents = studentsCount();
            int numWeeks = grades[0].length;
            for (int i = 0; i < numStudents; i++) {
                System.out.print("Student " + i + ":\t");
                for (int j = 0; j < numWeeks; j++) {
                    System.out.print("Week " + (j + 1) + ": ");
                        System.out.print(grades[i][j] + " \t");
                }
                System.out.println();
            }
        } finally {
            lock.unlock(); // розблокувати доступ до журналу
        }
    }
}
