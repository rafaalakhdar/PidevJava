// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import services.MessageService;
import java.util.Date;
import java.util.Calendar;
import services.UserService;
import entities.Conversation;
import entities.User;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.application.Application;

public class JavaFXloginn extends Application
{
    public void start(final Stage stage) throws Exception {
        final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("FXMLDocument.fxml"));
        final Scene scene = new Scene(root);
        stage.setTitle("Login TGT");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(final String[] args) {
        final User u = new User();
        final Conversation c = new Conversation(47);
        final UserService ps = new UserService();
        final Calendar d = Calendar.getInstance();
        Date dd = new Date();
        d.set(2019, 10, 12);
        dd = d.getTime();
        System.out.println(dd + "" + c + u);
        final MessageService ms = new MessageService();
        launch(args);
    }
}
