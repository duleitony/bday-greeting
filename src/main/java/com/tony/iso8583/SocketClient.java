package com.tony.iso8583;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
public class SocketClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of a machine that is\n" +
                        "running the date service on port 9090:");
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
    }

    /**
     * 将request里的数据包转成字符串
     *
     * @param request
     * @return String
     */
    public static String getRequestBodyTxt(HttpServletRequest request) {
        // 接收手机传过来的参数
        BufferedInputStream bufferedInputStream = null;
        // 此类实现了一个输出流，其中的数据被写入一个字节数组
        ByteArrayOutputStream bytesOutputStream = null;
        String body = null;
        try {
            // BufferedInputStream 输入流
            bufferedInputStream = new BufferedInputStream(request.getInputStream());
            bytesOutputStream = new ByteArrayOutputStream();
            // 写入数据
            int ch;
            while ((ch = bufferedInputStream.read()) != -1) {
                bytesOutputStream.write(ch);
            }
            // 转换为String类型
            body = new String(bytesOutputStream.toByteArray(), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭此输入流并释放与该流关联的所有系统资源。
            try {
                bytesOutputStream.flush();
                bytesOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * 将request里的数据包转成字节数组
     *
     * @param request
     * @return
     */
    public static byte[] getRequestBodyByte(HttpServletRequest request) {
        // 接收手机传过来的参数
        BufferedInputStream bufferedInputStream = null;
        // 此类实现了一个输出流，其中的数据被写入一个字节数组
        ByteArrayOutputStream bytesOutputStream = null;
        byte[] body = null;
        try {
            // BufferedInputStream 输入流
            bufferedInputStream = new BufferedInputStream(request.getInputStream());
            bytesOutputStream = new ByteArrayOutputStream();
            // 写入数据
            int ch;
            while ((ch = bufferedInputStream.read()) != -1) {
                bytesOutputStream.write(ch);
            }
            // 转换为String类型
            body = bytesOutputStream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭此输入流并释放与该流关联的所有系统资源。
            try {
                bytesOutputStream.flush();
                bytesOutputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * 向服务器 发送8583报文
     *
     * @param send8583Str 发送给服务器的报文
     *
     * @param host 主机地址IP
     *
     * @param port 端口号
     *
     * @return 返回的数据
     * */
    public static String send8583(String send8583Str,String host,int port) throws Exception{
        //客户端请求与本机在20011端口建立TCP连接
        Socket client = new Socket(host, port);
        client.setSoTimeout(70000);
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        InputStream buf =  client.getInputStream();
        String str = "mpos-"+send8583Str;
        //发送数据到服务端
        out.println(str);
        try{
            byte[] b = new byte[1024];
            int rc=0;
            int c = 0;
            while( (rc = buf.read(b, c, 1024) )>=0){
                c = buf.read(b, 0, rc);
            }
            String returnStr = Iso8583Util.byte2hex(b);
            String string = returnStr;
            String str16 = string.substring(0, 4);
            int leng = Integer.parseInt(str16,16);
            String result = string.substring(0, leng*2 + 4);
            if (client!=null) {
                client.close();
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Time out, No response");
        }
        if (client!=null) {
            client.close();
        }
        return null;
    }
}
