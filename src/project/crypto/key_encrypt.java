/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;

/**
 *
 * @author lenovo
 */
public class key_encrypt extends javax.swing.JFrame {

    /**
     * Creates new form key_encrypt
     */
    public key_encrypt() {
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

        jButton1 = new javax.swing.JButton();
        key = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        output = new javax.swing.JLabel();
        close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Key encryption");

        jButton1.setText("Encrypt Key");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Key");

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
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(key))
                        .addGap(117, 117, 117))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(key, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser file_upload = new JFileChooser();
        int res = file_upload.showOpenDialog(null);
        try {
            if (res == JFileChooser.APPROVE_OPTION) {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                File publicKeyFile = new File(file_upload.getSelectedFile().getAbsoluteFile() + "");
                byte[] publicKeyEncoded = Files.readAllBytes(publicKeyFile.toPath());
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                String password = key.getText();
                  if("".equals(password)){
                   output.setText("Key must be provided");
                   return;
                }
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] ciphertext = cipher.doFinal(password.getBytes());
                File ciphertextKeyFile = new File("Path" + appendDate("ciphertextKey") + ".txt");
                FileOutputStream ciphertextFileout = new FileOutputStream(ciphertextKeyFile);
                ciphertextFileout.write(ciphertext);
                ciphertextFileout.close();

                sendemail(ciphertextKeyFile.getAbsolutePath());
                 output.setText("Email Sent successfully");
            }
   }
   catch(Exception e){
        output.setText("Something went wrong");
   }
    }//GEN-LAST:event_jButton1ActionPerformed

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
        public void sendemail(String filepath) {
try{
	    Properties properties=new Properties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	    String myAccountEmail="email@gmail.com";
	    String password="pass";
	    Session session=Session.getInstance(properties, new  javax.mail.Authenticator() {
                @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(myAccountEmail, password);
	        }
	    });
	    
	    Message message=prepareMessage(session,myAccountEmail,"email@gmail.com",filepath);
	    Transport.send(message);
            output.setText("Email Sent successfully");
}
catch(Exception e){
    System.out.println(e);
}
	}

	private  Message prepareMessage(Session session,String from,String to,String filepath) {
	    Message message = new MimeMessage(session);
	    try {
	        
	        message.setFrom(new InternetAddress(from));
	        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        message.setSubject("Encrypted key");
                message.setText("Find attach encrypted key");
                Multipart multipart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText("Find attach encrypted key");
			multipart.addBodyPart(bodyPart);
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.attachFile(new File(filepath));
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);


	        return message;
	    } catch (AddressException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
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
            java.util.logging.Logger.getLogger(key_encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(key_encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(key_encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(key_encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new key_encrypt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton close;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField key;
    private javax.swing.JLabel output;
    // End of variables declaration//GEN-END:variables
}
