/**
 * Created by Administrator on 2017/10/20.
 */

public class MyString {
   private char []str=new char[20];
    public  MyString()
    {
        str="iloveyou".toCharArray();
    }
    public  MyString(String a)
    {
        if(a.length()<20) {
            str = a.toCharArray();
        }
        else
        {
            System.out.print("array out of bouds");

        }
    }

    public char Get(int id)
    {
        return str[id];
    }

    public void print()
    {
        System.out.print(str);
    }
}
