package DataAccess.Connectors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GlobalConfig {
    public static String dbName = "db.connectionString";
    public static IDataConnection connection = new SqlConnector();
    public static String separator = ",";
    public static String configFilePath = "/Users/stepankudlacek/Developer/IdeaProjects/vis/DataLibrary/src/config.properties";
    public static String dataFilePathName = "dataFilePath";


    public static String CnnString(String name) {
        FileInputStream propsInput;
        try {
            propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            try {
                prop.load(propsInput);
                String connectionString = prop.getProperty(name);
                return connectionString;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        return null;
    }

    public static String dataFilePath() {
        FileInputStream propsInput;
        try {
            propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            try {
                prop.load(propsInput);
                String dataFilePath = prop.getProperty(dataFilePathName);
                return dataFilePath;

            } catch (IOException e) {
                e.printStackTrace();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        return null;
    }

}
