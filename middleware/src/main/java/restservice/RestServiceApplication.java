package restservice;

import operations.SPARQLOperations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import utils.Datasets;

@SpringBootApplication(scanBasePackages = {"controllers", "services"})
public class RestServiceApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(RestServiceApplication.class, args);
        System.out.println(applicationContext.getEnvironment().getProperty("config.fusekiHost"));

        Datasets.setHost(applicationContext.getEnvironment().getProperty("config.fusekiHost"));
        Datasets datasets = new Datasets();

        try {
            // DEFAULT HOST
            SPARQLOperations cn = new SPARQLOperations(datasets.getDatasetHostname("default"));

            cn.importOWL(datasets.getDatasetFiles("default"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("===========================IS THIS CHANGING?!==========================================");
    }

}