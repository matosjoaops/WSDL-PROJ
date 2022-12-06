package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Work {

    ArrayList<HashMap<String, String>> associatedTriples = new ArrayList<>();
    String URI;

    public ArrayList<HashMap<String, String>> getAssociatedTriples() {
        return associatedTriples;
    }

    public void setAssociatedTriples(ArrayList<HashMap<String, String>> associatedTriples) {
        this.associatedTriples = associatedTriples;
    }

    public void addTriple(HashMap<String, String> triple) {
        this.associatedTriples.add(triple);
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
