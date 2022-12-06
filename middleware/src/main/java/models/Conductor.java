package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Conductor {
    ArrayList<HashMap<String, String>> associatedTriples = new ArrayList<>();
    String URI;

    public ArrayList<HashMap<String, String>> getAssociatedTriples() {
        return associatedTriples;
    }

    public void setAssociatedTriples(ArrayList<HashMap<String, String>> associatedTriples) {
        this.associatedTriples = associatedTriples;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}