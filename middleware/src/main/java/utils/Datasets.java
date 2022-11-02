package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Datasets {
    private static String host = "";
    private final HashMap<String, Dataset> datasets;
    private final String defaultDataset;

    public static class Dataset {
        private final String name;
        private final String description;
        private final List<String> files;

        Dataset(String name, String description, List<String> files) {
            this.name = name;
            this.description = description;
            this.files = files;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getFiles() {
            return files;
        }
    };

    public Datasets() {
        this.datasets = new HashMap<>();
        this.datasets.put("default", new Dataset("default",
                "The default dataset.",
                Arrays.asList(
                        "datasets/classical.ttl"
                )));

        this.defaultDataset = "default";
    }

    public HashMap<String, Dataset> getDatasets() {
        return datasets;
    }

    public static void setHost(String host) {
        Datasets.host = host;
    }

    public static String getHost() {
        return host;
    }

    public String getDefaultDatasetHostname() throws Exception {
        return this.getDatasetHostname(this.defaultDataset);
    }

    public String getDatasetHostname(String dataset) throws Exception {
        if (!datasets.containsKey(dataset)) throw new Exception("Unknown dataset! (" + dataset + ")");
        if (this.getHost().equals("")) throw new Exception("Unavailable host!");
        return this.getHost() + dataset + "/";
    }

    public String[] getDatasetFiles(String dataset) throws Exception {
        if (!datasets.containsKey(dataset)) throw new Exception("Unknown dataset! (" + dataset + ")");
        return this.datasets.get(dataset).files.toArray(new String[0]);
    }

    public Boolean existsDataset(String dataset) {
        return datasets.containsKey(dataset);
    }
}
