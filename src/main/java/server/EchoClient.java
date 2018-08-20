package server;

import java.io.*;
import java.net.*;

public class EchoClient {	//客户端
    private String host = "localhost";	//与本地服务端器通信
    private int port = 8888;	//指定通信端口
    private Socket socket;

    public EchoClient() throws IOException {	//与服务端建立连接，收到“问候语”表示连接成功
        socket = new Socket(host, port);	//与指定的服务器的指定端口连接
    }

    private PrintWriter getWriter(Socket socket) throws IOException {	//给服务端发送回话语句
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {	//接收服务端的会话语句
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void talk()throws IOException{ //与服务端回话程序
        try{
            BufferedReader br=getReader(socket);	//接收服务端的会话语句
            PrintWriter pw=getWriter(socket);		//给服务端发送回话语句
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));//本地控制台输入
            String msg=null;	//清空语句缓存
//            System.out.println("客户端ok");
            while((msg=localReader.readLine())!=null){	//读取到本地客户端控制台语句，不为空
                pw.println(msg);	//给服务端发送本地客户端控制台输入的会话语句
                System.out.println(br.readLine());	//将接收到的服务端发来的会话语句输出到本地客户端控制台
                if(msg.equals("bye"))break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally
        {
            try{
                socket.close();	//断开连接
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException{	//main
        new EchoClient().talk();
    }
}
