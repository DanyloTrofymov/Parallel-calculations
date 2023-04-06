package Task_3;

import Task_3.teacher.AbstractTeacher;
import Task_3.teacher.Assistant;
import Task_3.teacher.Lecturer;

import java.util.List;

public class Main {
    private static final int ASSISTANTS = 3;
    public static final int WEEKS = 18;
    private static final int GROUPS = 3;
    private static final int LECTURERS = 1;
    private static final int STUDENTS = 30;

    public static void main(String[] args) {
        Student[] students = new Student[STUDENTS];
        for (int j = 0; j < STUDENTS; j++) {
            students[j] = new Student("Student", String.valueOf(j), j);
        }
        Group group = new Group("Group 1", students);
        GradeJournal gradeJournal = new GradeJournal(group, WEEKS, ASSISTANTS + LECTURERS);
        AbstractTeacher lecturer1 = new Lecturer("Lecturer", "0", gradeJournal);
        AbstractTeacher assistant1 = new Assistant("Assistant", "1", gradeJournal);
        AbstractTeacher assistant2 = new Assistant("Assistant", "2", gradeJournal);
        AbstractTeacher assistant3 = new Assistant("Assistant", "3", gradeJournal);

        lecturer1.start();
        assistant1.start();
        assistant2.start();
        assistant3.start();

        try {
            lecturer1.join();
            assistant1.join();
            assistant2.join();
            assistant3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gradeJournal.printGrades();
    }
}
