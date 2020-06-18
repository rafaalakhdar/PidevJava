// 
// Decompiled by Procyon v0.5.36
// 

package utilitez;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class MyConnection
{
    String url;
    String login;
    String mdp;
    Connection cnx;
    public static MyConnection instance;
    
    private MyConnection() {
        this.url = "jdbc:mysql://localhost:3306/test";
        this.login = "root";
        this.mdp = "";
        try {
            this.cnx = DriverManager.getConnection(this.url, this.login, this.mdp);
            System.out.println("cnx etablie!");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static MyConnection getInstance() {
        if (MyConnection.instance == null) {
            MyConnection.instance = new MyConnection();
        }
        return MyConnection.instance;
    }
    
    public Connection getCnx() {
        return this.cnx;
    }
}
