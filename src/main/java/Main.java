import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String xmlFile = "data.xml";
        List<Employee> list = parseXML(xmlFile);
        writeString(listToJson(list), "employee.json");
    }

    protected static List<Employee> parseXML(String xmlFileName) {
        List<Employee> employee = new ArrayList<Employee>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFileName));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList nodeList1 = nodeList.item(i).getChildNodes();
                if (Node.ELEMENT_NODE == nodeList.item(i).getNodeType()) {
                    Map<String, String> map = new HashMap<>();
                    for (int s = 0; s < nodeList1.getLength(); s++) {
                        if (nodeList1.item(s).hasChildNodes()) {
                            map.put(nodeList1.item(s).getNodeName(), nodeList1.item(s).getTextContent());
                        }
                    }
                    employee.add(new Employee(Long.parseLong(map.get("id")), map.get("firstName"), map.get("lastName"), map.get("country"), Integer.parseInt(map.get("age"))));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            ex.printStackTrace();
        }
        return employee;
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
