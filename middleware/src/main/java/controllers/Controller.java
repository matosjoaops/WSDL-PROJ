package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import utils.Datasets;

public abstract class Controller {
    protected final Datasets datasets = new Datasets();
    public String getFusekiHost(String dataset){
        String fusekiHost;

        try {
            fusekiHost = datasets.getDefaultDatasetHostname();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }

        if (dataset != null) {
            try {
                fusekiHost = datasets.getDatasetHostname(dataset);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

        return fusekiHost;
    }
}
