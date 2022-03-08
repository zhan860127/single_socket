public class ClientCode {
    public static void main(String[] argv)
    {
        new ClientThreadCode("127.0.0.1", 8000).start();//建立物件，傳入IP和Port並執行等待接受連線的動作
        //由於此範例都在自己電腦上執行，所以IP設定為127.0.0.1
    }
}
