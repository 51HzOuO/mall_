import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        BigInteger a = new BigInteger("1234567890");
        int b = Integer.parseInt(a.toString());
        System.out.println(b);

    }
}
