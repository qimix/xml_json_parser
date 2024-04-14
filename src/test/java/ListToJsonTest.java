import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class ListToJsonTest {
    @org.junit.jupiter.api.Test
    public void equalsOutput() {
        List<Employee> list = new ArrayList<Employee>();
        list.add(new Employee(1,"John","Smith","USA",25));
        list.add(new Employee(2,"Inav","Petrov","RU",23));

        String actual = Main.listToJson(list);
        String expected = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"USA\",\"age\":25},{\"id\":2,\"firstName\":\"Inav\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":23}]";
        Assertions.assertArrayEquals(expected.toCharArray(), actual.toCharArray());
    }
}
