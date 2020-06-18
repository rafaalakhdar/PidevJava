// 
// Decompiled by Procyon v0.5.36
// 

package services;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.Node;
import org.controlsfx.control.Notifications;

public class ServiceNotification
{
    public static void showNotif(final String text, final String text2) {
        final Notifications notificationBuilder = Notifications.create().title(text).text(text2).graphic((Node)null).hideAfter(Duration.seconds(5.0)).position(Pos.TOP_CENTER).onAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
            }
        });
        notificationBuilder.darkStyle();
        notificationBuilder.showConfirm();
    }
}
