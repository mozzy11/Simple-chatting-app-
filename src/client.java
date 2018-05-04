import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class client extends JFrame {
     private JTextField UserText;
      private JTextArea ChatWindow;//dispalys the chat
      private ObjectOutputStream OutPut;
      private ObjectInputStream Input;
     private String message = "";
     private String ServerIP;
      private Socket connection;
     
      public client (String host){
       super ("MOZZY MESSANGER CLIENT");
      
       ServerIP = host;
       UserText = new JTextField();
         UserText.setBackground(Color.blue);
        
       UserText.setEditable(false);
      UserText.addActionListener(
      new ActionListener (){
      public void actionPerformed(ActionEvent event){
      sendMessage(event.getActionCommand());// retursn the string in here
       UserText.setText(""); //empties the textarea after sending the message
         }
        }
       );
       add(UserText, BorderLayout.NORTH);
           ChatWindow = new JTextArea ();
            ChatWindow.setFont(new Font("forte",Font.PLAIN, 14));
           ChatWindow.setBackground(Color.red);
            ChatWindow.setForeground(Color.WHITE);
           add(new JScrollPane(ChatWindow),BorderLayout.CENTER);
           setSize(300,400);
           setVisible(true); 
      }
      //CONNECTING TO SERVER
      public void startrunning(){
      try{
      connectToServer();
      setUpStreams();
       whileChatting();
      }catch(EOFException eofExcepion){
      showMessage ("client terminated connection");}
      
      catch(IOException ioException){// U can catch different exceptions by just adding on the cathch parameters
        ioException.printStackTrace(); 
        } finally {
        closeCrap();// this closes the streams
   }
     
 }
             // CONNECTING TO SERVER
        private void   connectToServer() throws IOException {
         showMessage( "attempting connection.....");
        connection = new Socket(InetAddress.getByName( ServerIP),6789);//on computer i.p adress
          showMessage ("connected to :" + connection.getInetAddress().getHostName() )  ;
     }
        
        
        //SETup STREAMS
        private void setUpStreams()throws IOException{
       OutPut = new ObjectOutputStream(connection.getOutputStream());//creating a pathway to another comp
       OutPut.flush(); // cleans up the remainuing data in the strems
       Input = new ObjectInputStream (connection.getInputStream());
       showMessage("streams are now set up");
       }
            //WHILE CHATTING
            private void  whileChatting()throws IOException{
      String message = "you are niw connected ";
      sendMessage (message);
      ableToType (true);
       do{ 
           try{
       message = (String)Input.readObject();
       showMessage(   message);
       } catch(ClassNotFoundException  classNotFoundException)
       {
           showMessage ("\n wat ahell is that");
       }
       
       }while(!message.equals("SERVER - END"));//the chat continues as long as the user doesnt type "end"
     }  // CLOSING THE CONNECTION
        private void closeCrap(){
       showMessage ("\n closing connection");
      ableToType (false);
      try {
          OutPut.close();// closing the output stream
           Input.close();//closing the input stream
           connection.close();// closes the socket
      } catch (IOException ioException){
      ioException.printStackTrace();
       }
      }     
            // SENDING MESSAGE
        
        
         private void sendMessage(String message){
       try{
        OutPut.writeObject("CLIENT -  " + message);// the "writeobject" sends the message thru the streams
        OutPut.flush();
        showMessage ("\n CLIENT- " + message +"\n");// displays the messgae on the screen
       } catch(IOException ioException){
       ChatWindow.append("\n man i cant send that messo");// append means stick it to the chat area
        }
      
      }
         //DISPLAYING MESSAGE ON CHAT WINDOW
         private void  showMessage (final String text){
       SwingUtilities.invokeLater(    // this updates the GUI(PORTION) without making anew GUI everytime
       new Runnable (){// introducing athread
       public void run(){
        
           ChatWindow.append( text  );// this dds stuff to the chatwindow and updates the chat window with a new message
            }
           }
         );
}
         // being able to type
          private void ableToType (final boolean tof ){
      
      SwingUtilities.invokeLater(    // this updates the GUI(PORTION) without making anew GUI everytime
       new Runnable (){// introducing athread
       public void run(){
         UserText.setEditable(tof);
         
            }
           }
         );
      }
          
         
}