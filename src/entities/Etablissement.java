// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import java.util.Objects;

public class Etablissement
{
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    
    public Double getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }
    
    public Etablissement() {
    }
    
    public Etablissement(final Double latitude, final Double longitude, final String name, final String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
    }
    
    public Etablissement(final String name, final String address) {
        this.name = name;
        this.address = address;
    }
    
    public Etablissement(final Double latitude, final Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    @Override
    public String toString() {
        return "Etablissement{latitude=" + this.latitude + ", longitude=" + this.longitude + ", name=" + this.name + ", address=" + this.address + '}';
    }
    
    @Override
    public int hashCode() {
        final int hash = 7;
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Etablissement other = (Etablissement)obj;
        return Objects.equals(this.name, other.name) && Objects.equals(this.address, other.address) && Objects.equals(this.latitude, other.latitude) && Objects.equals(this.longitude, other.longitude);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
}
