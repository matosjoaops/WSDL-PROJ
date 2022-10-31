package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Composer {
    String uuid;
    HashMap<String, ArrayList<HashMap<String, String>>> DOC_IDENTITY = new HashMap<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> getDOC_IDENTITY() {
        return DOC_IDENTITY;
    }

    public void setDOC_IDENTITY(HashMap<String, ArrayList<HashMap<String, String>>> DOC_IDENTITY) {
        this.DOC_IDENTITY = DOC_IDENTITY;
    }
}
