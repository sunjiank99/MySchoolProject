package com.example;
import java.io.*;
import java.io.InputStreamReader;
import java.lang.StringBuffer;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/10.
 */
public class test
{
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5469);//创建一个ServerSocket在端口5469监听客户请求
        Socket client = server.accept();//使用accept()阻塞等待客户请求
        BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter os = new PrintWriter(client.getOutputStream());
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = is.readLine();
            System.out.println("Client : " + str);//在标准输出上打印从Client读入的字符串

            os.println(sin.readLine());
            os.flush();//刷新输出流，使Client马上收到该字符串
            if (str.equals("end")) {
                break;
            }
        }
        is.close();
        os.close();
        client.close();
        server.close();

    }
    }
