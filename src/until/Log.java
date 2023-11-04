/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package until;

import org.apache.log4j.*;

/**
 *
 * @author USER
 */
public class Log {

    public static final Logger logger = Logger.getLogger(Log.class);
    public static void main(String[] args) {
        PropertyConfigurator.configure("E:\\DuAnMau\\src\\Log\\log4j.properties");
        logger.info("dd");
    }
    
}
