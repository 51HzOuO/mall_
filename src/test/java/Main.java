import com.demo.mall1.utils.GetQueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {

        String key = "clion1";

        try {
            int query = (int) (long) GetQueryRunner.getQueryRunner().query(("select count(*) from furniture where `name` like ? "), new ScalarHandler<>(), "%" + key + "%");
            System.out.println(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
