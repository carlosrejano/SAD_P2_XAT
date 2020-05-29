import java.util.Map;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) {

        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        Map<String,MySocket> myMap = new ConcurrentHashMap<String,MySocket>();
        while(true){
            MySocket s = ss.accept();
            String nick = s.readLine();
            myMap.put(nick, s);

            new Thread(){
                public void run(){
                    String nickname = nick;
                    System.out.println(nickname);
                    String line;
                    while((line = myMap.get(nickname).readLine()) != null){
                        System.out.println(line);
                        for (String key : myMap.keySet()) {
                            if(key != nickname){
                                myMap.get(key).println(nickname + ": " + line);
                            }
                        }
                    }
                }
            }.start();
        }

    }
}
