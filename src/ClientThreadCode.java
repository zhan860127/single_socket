import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;

import java.net.Socket;
import java.util.Scanner;  // Import the Scanner class

public class ClientThreadCode extends Thread
{
    private Socket m_socket;//和伺服器端進行連線
    
    public ClientThreadCode(String ip, int port)
    {
        try
        {
            m_socket = new Socket(ip, port);//建立連線。(ip為伺服器端的ip，port為伺服器端開啟的port)
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void run()
    {           System.out.println("client");


        try
        {  new Thread(new MyClientWriter(m_socket)).start();

            while (m_socket != null)//連線成功才繼續往下執行
            {

                //由於Server端使用 PrintStream 和 BufferedReader 來接收和寄送訊息，所以Client端也要相同
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
                System.out.println("Server:" + reader.readLine());
                
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            System.out.println("fatal");
        }
    }


}


class MyClientWriter implements Runnable{
    private Socket socket = null;
    private PrintWriter printWriter;
    private Scanner scanner;

    public MyClientWriter(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(System.in);
        printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }

    @Override
    public void run() {
        String msg;//要发送的信息
        try {
            while ((msg = scanner.nextLine()) != null) {
                System.out.println("isClosed="+socket.isClosed());
                if(socket.isClosed()) {
                    break;
                } else {
                    if(msg.equals("88")) {
                        break;
                    }
                }
                printWriter.println(msg);
            }
        } catch (Exception e) {
//            System.out.println("异常关闭客户端（可能是直接关闭退出窗口）");
        }
        System.out.println("写线程准备死亡");
    }

}
