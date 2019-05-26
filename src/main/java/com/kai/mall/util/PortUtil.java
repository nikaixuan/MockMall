package com.kai.mall.util;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;
/**
 * Created by nikaixuan on 25/5/19.
 */

public class PortUtil {

    public static boolean testPort(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            ss.close();
            return false;
        } catch (java.net.BindException e) {
            return true;
        } catch (IOException e) {
            return true;
        }
    }

    public static void checkPort(int port, String server, boolean shutdown) {
        if(!testPort(port)) {
            if(shutdown) {
                String message =String.format("At port %d not found %s run%n",port,server);
                JOptionPane.showMessageDialog(null, message);
                System.exit(1);
            }
            else {
                String message =String.format("At port %d not found %s run%n,continue?",port,server);
                if(JOptionPane.OK_OPTION !=     JOptionPane.showConfirmDialog(null, message))
                    System.exit(1);
            }
        }
    }

}
