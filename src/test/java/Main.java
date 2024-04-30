import com.demo.mall1.beans.Info;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //testCheckPassword();
        String password = "123456";
        String md5Str = "e10adc3949ba59abbe56e057f20f883e";
        boolean b = checkPassword(password, md5Str);
        System.out.println(b);
    }

    private static boolean verifyLogin(Info info) {
        String regU = "^\\w{4,16}$";
        String regP = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]+$";
        String regE = "^[A-Za-z0-9一-龥]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";
        return info.getUsername().matches(regU) && info.getPassword().matches(regP) && info.getEmail().matches(regE) && info.getCode().matches("^[0-9]{4}$");
    }

    private static boolean checkPassword(String password, String md5Str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().equals(md5Str);
    }
}
