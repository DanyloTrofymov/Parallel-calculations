package Task_3;

public class Group {
    private Student[] students;
    private String groupName;

    public Group(String groupName, Student[] students) {
        this.students = students;
        this.groupName = groupName;
    }
    public Student[] getStudents() {
        return students;
    }

    public String getGroupName() {
        return groupName;
    }
}
