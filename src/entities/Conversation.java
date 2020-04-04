/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *
 * @author Rafaa
 */
public class Conversation implements Serializable {
    
      private final SimpleIntegerProperty id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty dateCreation;

  

    public Conversation(int id, String nom, String dateCreation) {
        super();
         this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);

        this.dateCreation = new SimpleStringProperty(dateCreation) ;
    }

    public int getId() {
        return id.get();
    }

   

    public String getNom() {
        return nom.get();
    }

    public String getDateCreation() {
        return dateCreation.get();
    }

  

    @Override
    public String toString() {
        return "Conversation{" + "id=" + id + ", nom=" + nom + ", dateCreation=" + dateCreation + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conversation other = (Conversation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.dateCreation, other.dateCreation)) {
            return false;
        }
        return true;
    }
    
    
    
}
