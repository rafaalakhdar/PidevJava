// 
// Decompiled by Procyon v0.5.36
// 

package utilitez;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class ServiceSysdate
{
    static MyConnection ds;
    
    public Date selectDate() {
        Date datee = null;
        try {
            final Statement ste = ServiceSysdate.ds.getCnx().createStatement();
            final String req = "select sysdate()";
            final ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                final Date datenow = datee = rs.getDate(1);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ServiceSysdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datee;
    }
    
    static {
        ServiceSysdate.ds = MyConnection.getInstance();
    }
}
