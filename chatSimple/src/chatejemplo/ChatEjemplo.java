/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatejemplo;

import sd_activeMQ.Receiver;

/**
 *
 * @author User
 */
public class ChatEjemplo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
         new Chat("CHAT").setVisible(true);
         Receiver r = new Receiver("Angely");
    }
    
}
