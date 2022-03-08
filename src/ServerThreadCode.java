import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;  // Import the Scanner class

public class ServerThreadCode extends Thread
{
    
    private ServerSocket m_serverSocket;//伺服器端的Socket，接收Client端的連線
    private Socket m_socket;//Server和Client之間的連線通道
    
    public ServerThreadCode(int port)
    {
        try
        {
            m_serverSocket = new ServerSocket(port);//建立伺服器端的Socket，並且設定Port
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());//出現例外時，捕捉並顯示例外訊息
        }
    }

    @Override
    public void run()//覆寫Thread內的run()方法
    {
        try
        {
            System.out.println("等待連線......");
            m_socket = m_serverSocket.accept();//等待伺服器端的連線，若未連線則程式一直停在這裡
            System.out.println("連線成功！");
           // m_serverSocket.close();//一旦連線建立成功，且不需要再接收其他連線，則可關閉ServerSocket
            //送出端的編寫必須和接收端的接收Class相同
            //使用Socket的getInputStream()和getOutputStream()進行接收和發送資料
            //想要寫入字串可以用 PrintStream；想要把各種基本資料型態，如 int, double...等的 "值" 輸出，可以用 DataOutputStream；想要把整個物件 Serialize，則可以用 ObjectOutputStream。
            PrintStream writer;//在此我使用PrintStream將字串進行編寫和送出
            System.out.println("Server");

            writer = new PrintStream(m_socket.getOutputStream());//由於是將資料編寫並送出，所以是Output
            Scanner myObj = new Scanner(System.in);
            String key;
            new Thread(new Myserverreader(m_socket)).start();
            while(true){
                try {
                    key=myObj.next();              
                    writer.println(key);
                    writer.flush();//清空緩衝區並送出資料
                   while (key.equals("exit"))
                   {   
                       m_socket.close();//關閉連線  
                       m_serverSocket.close();
                       myObj.close();
                       break;
                       
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());//出現例外時，捕捉並顯示例外訊息(連線成功不會出現例外)
        }
    }
 
}

class Myserverreader implements Runnable{
    private Socket socket = null;
    public Myserverreader(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
    
   
    try {
        while (socket != null)//連線成功才繼續往下執行
        {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Client:" + reader.readLine()); 
     } 
    }catch(Exception e){
        System.out.println("結束聊天");
    }

    }
}