package Task_3.teacher;

import Task_3.GradeJournal;
import Task_3.Group;

import java.util.List;

import static Task_3.Main.WEEKS;

public abstract class AbstractTeacher extends Thread{
    private String name;
    private static int nextId = 0;
    private int id;
    private String surname;
    private GradeJournal gradeJournal;
    private int week;

    private static final int MAX_GRADE = 100;

    public AbstractTeacher(String name, String surname, GradeJournal gradeJournal) {
        this.name = name;
        this.surname = surname;
        this.gradeJournal = gradeJournal;
        this.id = nextId++;
        week = 1;
    }

    public void run() {
        while (week <= WEEKS) {
            int[] grades = new int[gradeJournal.studentsCount()];
            for (int i = 0; i < gradeJournal.studentsCount(); i++) {
                int grade = (int) (Math.random() * (MAX_GRADE-10)) + 10;
                grades[i] = grade;
            }
            gradeJournal.addGrades(grades, week - 1);
            System.out.println(name + " "  + surname + " has graded week " + week);
            week++;
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public int getTeacherId() {
        return id;
    }
}
