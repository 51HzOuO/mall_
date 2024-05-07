import com.demo.mall1.utils.GetQueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        int count;
        try {

            count = (int) ((long) GetQueryRunner.getQueryRunner().query("select count(*) from furniture", new ScalarHandler<>()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(count);
    }
}
