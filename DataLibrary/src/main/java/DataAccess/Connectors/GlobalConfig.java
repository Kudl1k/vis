package DataAccess.Connectors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GlobalConfig {
    public static String dbName = "db.connectionString";
    public static IDataConnection connection = new TextConnector();
    public static String separator = ",";
    public static String configFilePath = "/Users/stepankudlacek/Developer/IdeaProjects/vis/DataLibrary/src/config.properties";
    public static String dataFilePathName = "dataFilePath";


    public static String CnnString(String name)
    {
        FileInputStream propsInput;
        try {
            propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            try {
                prop.load(propsInput);
                String connectionString = prop.getProperty(dbName);
                return connectionString;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("tady IO");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("tady FILe");
            e.printStackTrace();
        }

        return null;
    }

    public static String dataFilesPath()
    {
        FileInputStream propsInput;
        try {
            propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            try {
                prop.load(propsInput);
                String dataFilePath = prop.getProperty(dataFilePathName);
                return dataFilePath;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
