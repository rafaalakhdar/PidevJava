// 
// Decompiled by Procyon v0.5.36
// 

package Test;

import java.sql.SQLException;
import entities.keyValuePair;
import java.util.List;
import entities.Categorie;
import entities.Audition;
import services.CategorieService;
import services.AuditionService;
import utilitez.MyConnection;

public class MainClass
{
    public static void main(final String[] args) throws SQLException {
        final MyConnection mc = MyConnection.getInstance();
        System.out.println(mc.hashCode());
        final AuditionService ps = new AuditionService();
        final CategorieService pc = new CategorieService();
        final Audition p = new Audition();
        final Categorie c = new Categorie();
        p.getCategorieFk();
        p.setCategorieFk(c);
        System.out.println(p.getCategorieFk());
        final List<keyValuePair> pair = pc.getUserNomId();
        System.out.println(pair);
        System.out.println(pc.getCategorieNames());
    }
}
