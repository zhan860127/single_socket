
import java.io.IOException;



public class ServerCode
{   
    public static void main(String[] argv)throws IOException {
       
        new ServerThreadCode(8000).start();//建立socketserver，傳入Port並執行等待接受連線的動作
    }
}

