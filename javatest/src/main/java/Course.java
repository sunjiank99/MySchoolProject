/**
 * Created by Administrator on 2017/10/20.
 */


public class Course {
    private  String Name;
    private  int Time;
    private  String Teacher;
    private  String classId;

    public  Course()
    {
        Name="AG";
        Time=64;
        Teacher="zzh";
        classId="A319";
    }
    public  Course(String name,int time,String teacher,String classId)
    {
        this.Name=name;
        this.Time=time;
        this.Teacher=teacher;
        this.classId=classId;
    }
    public void show()
    {
        System.out.println(Name+Time+Teacher+classId);

    }
}
