/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crypto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;

/**
 *
 * @author lenovo
 */
public class file_decryption extends javax.swing.JFrame {

    /**
     * Creates new form file_encrption
     */
    public file_decryption() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        key1 = new javax.swing.JLabel();
        key = new javax.swing.JTextField();
        file_dec = new javax.swing.JButton();
        output = new javax.swing.JLabel();
        close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("File Decryption");

        key1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        key1.setText("Key");

        file_dec.setText("Upload And Decrypt File");
        file_dec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                file_decActionPerformed(evt);
            }
        });

        output.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        output.setForeground(new java.awt.Color(255, 0, 0));

        close.setText("Close");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(key1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(file_dec, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(key))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(key, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(key1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(file_dec, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void file_decActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file_decActionPerformed
        JFileChooser file_upload = new JFileChooser();//create instance of file upload
        int res = file_upload.showOpenDialog(null);//open file upload dialog
        try {
            if (res == JFileChooser.APPROVE_OPTION) {//if file chosen and approve enter the if
                File inputFile = new File("" + file_upload.getSelectedFile().getAbsoluteFile());//create instance of file upload
                String password = key.getText();//read the key from the input
                if("".equals(password)){
                   output.setText("Key must be provided");
                   return;
                }
                String fileName=file_upload.getSelectedFile().getName();
                File encryptedFile = new File("C:\\Users\\lenovo\\Desktop\\project\\Decrypted_Files\\"+appendDate(fileName.split("\\.")[0])+"."+fileName.split("\\.")[1]);
                FileOutputStream outputStream = new FileOutputStream(encryptedFile);
		    byte[] salt = new byte[8], data = new byte[1024],tmp; 
		    int keylen = 32, ivlen = 16, cnt;
		 InputStream is = new FileInputStream(inputFile);
		        if( is.read(salt) != 8 || !Arrays.equals(salt, "Salted__".getBytes() )
		                || is.read(salt) != 8 ) output.setText("Something went wrong");    
		        byte[] keyandIV = SecretKeyFactory.getInstance("PBKDF2withHmacSHA256") 
		                .generateSecret( new PBEKeySpec(password.toCharArray(), salt, 10000, (keylen+ivlen)*8) 
		                ).getEncoded();
		        Cipher ciph = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		        ciph.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyandIV,0,keylen,"AES"), 
		                new IvParameterSpec(keyandIV,keylen,ivlen));
		        while( (cnt = is.read(data)) > 0 ){
		            if( (tmp = ciph.doFinal(data, 0, cnt)) != null ) {
		            	  outputStream.write(tmp);
		            }
		        }
                outputStream.close();
                is.close();
                output.setText("File is decrypted");       
            }

        } catch (Exception e) {
            System.out.print(e);
                  output.setText("Something went wrong");       
        }
    }//GEN-LAST:event_file_decActionPerformed

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
      this.dispose();
    }//GEN-LAST:event_closeActionPerformed
	
	public  String appendDate(String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(fileName);
                sb.append("-");
		Date d = new Date();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
		String date = formatter.format(d);
		sb.append(date.split("-")[0]);
		sb.append(date.split("-")[1]);
		sb.append(date.split("-")[2].split(" ")[0]);
		sb.append(date.split(" ")[1].split(":")[0]);
		sb.append(date.split(" ")[1].split(":")[1]);
		sb.append(date.split(" ")[1].split(":")[2]);
		return sb.toString();
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
            java.util.logging.Logger.getLogger(file_decryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(file_decryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(file_decryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(file_decryption.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new file_decryption().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton close;
    private javax.swing.JButton file_dec;
    private javax.swing.JTextField key;
    private javax.swing.JLabel key1;
    private javax.swing.JLabel output;
    // End of variables declaration//GEN-END:variables

   
}
