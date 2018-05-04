
import javax.swing.JFrame;


public class mozzy1 {

    
    public static void main(String[] args) {
      client obj;
      obj= new client("127.0.0.1");// this is the ip adress for my own (any) computer
      obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      obj.startrunning ();// SINCE ITS THE METHOD THAT refers to all the other methods
    }
    
}
