import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
       // String fileName = "data.csv";
       // String jsonFileName = "data.json";
       // List<Employee> list = parseCSV(columnMapping, fileName);
       // String json = listToJson(list);
       // writeString(json, jsonFileName);

        String xmlFile = "data.xml";
        List<Employee> list = parseXML(xmlFile);
    }

    protected static List<Employee> parseXML(String xmlFileName) {
        List<Employee> staff = new ArrayList<Employee>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(xmlFileName));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            System.out.println( "Корневой элемент: " + root.getNodeName());
            read(root);

        } catch (ParserConfigurationException | IOException | SAXException ex){
            ex.printStackTrace();
        }

        return staff;
    }

    private static void read(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                System. out.println( "Текущий узел: " + node_.getNodeName());
                Element element = (Element) node_;
                NamedNodeMap map = element.getAttributes();
                for (int a = 0; a < map.getLength(); a++) {
                    String attrName = map.item(a).getNodeName();
                    String attrValue = map.item(a).getNodeValue();
                    System. out.println( "Атрибут: " + attrName + "; значение: " + attrValue);
                }
                read(node_);
            }
        }
    }


    protected static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> staff = new ArrayList<Employee>();

        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);

        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            staff = csv.parse();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return staff;
    }

    protected static String listToJson(List<Employee> staff) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(staff, listType);
        return json;
    }

    protected static void writeString(String json, String jsonFileName) {
        try (Writer writer = new FileWriter(jsonFileName)) {
            writer.write(json.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
