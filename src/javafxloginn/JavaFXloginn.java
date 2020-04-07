/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.Conversation;
import entities.Message;
import entities.User;
import java.util.Calendar;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.MessageService;
import utilitez.MyConnection;


/**
 *
 * @author Rafaa
 */
public class JavaFXloginn extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root, 618, 500);
        stage.setTitle("Login TGT");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        User u = new User(23);
        Conversation c = new Conversation(47);
        
        Calendar d = Calendar.getInstance();
        Date dd = new Date();
        d.set(2019,10,12);
        dd = d.getTime();
        
        System.out.println(dd + "" +c + u );
            //   Message m1 = new Message(44,"helo again","D:\\aaa\\150.jpg" ,dd ,c ,u);
               MessageService ms = new MessageService();
            //   ms.ajoutermsg(m1);

        launch(args);
    }
    
    
}
