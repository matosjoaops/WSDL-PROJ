package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Composer {
    ArrayList<HashMap<String, String>> associatedTriples = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getAssociatedTriples() {
        return associatedTriples;
    }

    public void setAssociatedTriples(ArrayList<HashMap<String, String>> associatedTriples) {
        this.associatedTriples = associatedTriples;
    }
}
