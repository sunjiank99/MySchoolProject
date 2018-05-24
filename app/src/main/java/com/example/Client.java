package com.example;

import com.example.administrator.redline.Main2Activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/20.
 */
public class Client extends Main2Activity {
   /* public static void main(String[] args) throws Exception {
        //向本机的5469端口发出客户请求
//        Socket socket=new Socket(InetAddress.getLocalHost(),5469);
        Socket socket=new Socket(InetAddress.getLocalHost(),5469);
        //由Socket对象得到输入流，并构造相应的BufferedReader对象
        BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //由Socket对象得到输出流，并构造PrintWriter对象
        PrintWriter os=new PrintWriter(socket.getOutputStream());
        //由系统标准输入设备构造BufferedReader对象
        BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String str=sin.readLine();//从系统标准输入读入一字符串
            os.println(str);
            os.flush(); //刷新输出流，使Server马上收到该字符串

            String s=is.readLine();
            System.out.println("Server : "+s);//在标准输出上打印从Server读入的字符串
            if(str.equals("end")){

                break;
            }
        }
        is.close();//关闭Socket输入流
        os.close();//关闭Socket输出流
        socket.close();//关闭Socket
    }*/

    public static String Fasong(String IP,String Context) throws Exception {
        String STR;
        //向本机的5469端口发出客户请求
//        Socket socket=new Socket(InetAddress.getLocalHost(),5469);
        Socket socket=new Socket(IP,5469);
        //由Socket对象得到输入流，并构造相应的BufferedReader对象
        BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //由Socket对象得到输出流，并构造PrintWriter对象
        PrintWriter os=new PrintWriter(socket.getOutputStream());
        //由系统标准输入设备构造BufferedReader对象
//        BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
        while(true){
//            String str=sin.readLine();//从系统标准输入读入一字符串
            String str=Context;
            os.println(str);
            os.flush(); //刷新输出流，使Server马上收到该字符串

            String s=is.readLine();
//            System.out.println("Server : "+s);//在标准输出上打印从Server读入的字符串
           STR="Server : "+s;
            if(str.equals("end")){

                break;
            }
        }
        is.close();//关闭Socket输入流
        os.close();//关闭Socket输出流
        socket.close();//关闭Socket
        return STR;

    }
}
