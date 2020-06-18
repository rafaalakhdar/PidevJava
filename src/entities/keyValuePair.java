// 
// Decompiled by Procyon v0.5.36
// 

package entities;

public class keyValuePair
{
    private final int key;
    private final String value;
    
    public keyValuePair(final int key, final String value) {
        this.key = key;
        this.value = value;
    }
    
    public keyValuePair() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int getKey() {
        return this.key;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
