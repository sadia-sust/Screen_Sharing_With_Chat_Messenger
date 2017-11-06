/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sadia
 */
public class ClientGui extends javax.swing.JFrame {

    /**
     * Creates new form ClientMessenger
     */
    
   
    private DataOutputStream output;
    private DataInputStream input;
     private String message  = "";
    public  Socket connection;  
    
    public ClientGui(Socket c,DataInputStream dis) {
        connection = c;
        input = dis;
        initComponents();
        setVisible(true);
        startRunning();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        ClientMessengerPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatWindow = new javax.swing.JTextArea();
        userText = new javax.swing.JTextField();
        endClient = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chatWindow.setColumns(20);
        chatWindow.setRows(5);
        jScrollPane1.setViewportView(chatWindow);

        userText.setText("Type something");

        endClient.setText("END");
        endClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ClientMessengerPanelLayout = new javax.swing.GroupLayout(ClientMessengerPanel);
        ClientMessengerPanel.setLayout(ClientMessengerPanelLayout);
        ClientMessengerPanelLayout.setHorizontalGroup(
            ClientMessengerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientMessengerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClientMessengerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(ClientMessengerPanelLayout.createSequentialGroup()
                        .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(endClient, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ClientMessengerPanelLayout.setVerticalGroup(
            ClientMessengerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClientMessengerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ClientMessengerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endClient))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ClientMessengerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientMessengerPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        userText.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );
    }// </editor-fold>                        

    private void endClientActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        
        
        closeConnection();
    }                                         
    
    private void closeConnection(){
        showMessage("\nconnection closed\n");
        //ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
            
        
        }catch(IOException e){
            e.printStackTrace();
        
        }
    
    
    }
    
    public void startRunning()
    {
        try{
           // connectToServer();
            setupStreams();
            whileChatting();
        }
        catch(EOFException ioException ){
            showMessage("\nClient terminated\n");
        }catch(IOException ioException ){
        ioException.printStackTrace();
        }
   
    
    
    }
    
   
    
    private void setupStreams() throws IOException{
    
    try{Thread.sleep(1000);}
        catch(Exception e){}
        output = new DataOutputStream(connection.getOutputStream());
        output.flush();
 
        showMessage("Streams are now setup\n");
    }
    
    private void whileChatting() throws IOException{
        //ableToType(true);
       do{
           message = (String)input.readUTF();
           showMessage("\n" + message);
            

        }while(!message.equals("SERVER - END"));

    
    }
    
      private void sendMessage(String message) {
        try {
            if(message.equals("ABC")){JOptionPane.showMessageDialog(this, "Client Ternimated the Connection");closeConnection();}
            
            System.out.println(message + " "+ "ABC");
                 
           
            output.writeUTF("CLIENT - " + message);
            output.flush();
            showMessage("\nCLIENT - " + message);
        } catch (IOException e) {
            chatWindow.append("\nerror \n");
        }

    }

    //updates window

    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append(text);
                    }
                }
        );

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
      
    }

    // Variables declaration - do not modify                     
    public static javax.swing.JPanel ClientMessengerPanel;
    private javax.swing.JTextArea chatWindow;
    private javax.swing.JButton endClient;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField userText;
    // End of variables declaration                   
}

