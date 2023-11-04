/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package until;

import com.mysql.cj.protocol.Message;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.security.MessageDigest;

/**
 *
 * @author USER
 */
public class MaHoa {
    public static String toSHA1(String str){
        String salt = "sadazxcjjfdhukaks@sad";
        String result = null;
        str =  str + salt;
        try {
            byte[] dataBytes = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.encode(md.digest(dataBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(toSHA1("123"));
    }
}
