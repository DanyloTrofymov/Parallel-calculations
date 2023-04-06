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
        Group[] groups = new Group[GROUPS];
        for (int i = 0; i < GROUPS; i++) {
            Student[] students = new Student[STUDENTS];
            for (int j = 0; j < STUDENTS; j++) {
                students[j] = new Student("Student", String.valueOf(j), j);
            }
            groups[i] = new Group("Group " + i, students);
        }
        GradeJournal gradeJournal = new GradeJournal(groups, WEEKS, ASSISTANTS + LECTURERS);
        AbstractTeacher lecturer1 = new Lecturer("Lecturer", "1", groups, gradeJournal);
        AbstractTeacher assistant1 = new Assistant("Assistant", "1", groups, gradeJournal);
        AbstractTeacher assistant2 = new Assistant("Assistant", "2", groups, gradeJournal);
        AbstractTeacher assistant3 = new Assistant("Assistant", "3", groups, gradeJournal);

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
