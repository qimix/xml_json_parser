import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

public class ParseXMLTest {
    @org.junit.jupiter.api.Test
    public void equalsOutput() {
        // given: Подготовительные данные
        String testXmlFile = "src/test/resources/data.xml";
        List<Employee> expected = new ArrayList<Employee>();
             expected.add(new Employee(1,"John","Smith","USA",25));
             expected.add(new Employee(2,"Inav","Petrov","RU",23));

        // when: Вызываемый целевой метод
        List<Employee> actual = Main.parseXML(testXmlFile);

        // then: Сравнение результата
        Assertions.assertEquals(expected.get(0).toString(), actual.get(0).toString());
        Assertions.assertEquals(expected.get(1).toString(), actual.get(1).toString());
    }
}
