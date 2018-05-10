/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soapclient;

import java.util.Scanner;

/**
 *
 * @author danny
 */
public class main {
    
    public static void main(String[]args) {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter your username");
        String username = scanner.next();
        System.out.println("Enter your password");
        String password = scanner.next();
        
        scanner.close();
        
        SoapClient sc = new SoapClient(username, password);
        sc.callApi();
        
    }
}
