import com.demo.mall1.beans.User;
import com.demo.mall1.utils.GetQueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<User> query = GetQueryRunner.getQueryRunner().query("select * from users", new BeanListHandler<>(User.class));
        for (User user : query) {
            System.out.println(user.getUsername() + " " + user.getPassword() + " " + user.getEmail());

        }
    }
}
