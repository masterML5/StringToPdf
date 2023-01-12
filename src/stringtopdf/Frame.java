/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringtopdf;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author milosjelic
 */
public class Frame extends javax.swing.JFrame {

    private static File fdir;
    String pdfStringFile;

    /**
     * Creates new form Frame
     */
    public Frame() {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("String to PDF");

        jButton1.setText("Konvertuj");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton1MouseReleased(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Unesite string PDF-a"));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Poništi");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton2MouseReleased(evt);
            }
        });

        jButton3.setText("Paste");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton3MouseReleased(evt);
            }
        });

        jButton4.setText("Dodaj fajl");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(100, 100, 100)
                        .addComponent(jButton4)
                        .addGap(0, 189, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseReleased
        String str = jTextArea1.getText();

        try {
            skiniPdf(str);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1MouseReleased

    private void jButton2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseReleased
        jTextArea1.setText(null);
    }//GEN-LAST:event_jButton2MouseReleased

    private void jButton3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseReleased
        onPaste();
    }//GEN-LAST:event_jButton3MouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JFileChooser chooser = new JFileChooser();
        String FILENAME = null;
        // FileNameExtensionFilter filter = new FileNameExtensionFilter("xml");
        // chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            FILENAME = chooser.getSelectedFile().getAbsolutePath();
        }

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();

            doc.getDocumentElement().normalize();
            NodeList nListParentTag = doc.getElementsByTagName("env:DocumentHeader");
            for (int k = 0; k < nListParentTag.getLength(); k++) {
                Node nNode2 = nListParentTag.item(k);
                if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode2;
                    Node nodePdfElement = elem.getElementsByTagName("env:DocumentPdf").item(0);
                    pdfStringFile = nodePdfElement.getTextContent();
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pdfStringFile != null || !pdfStringFile.isEmpty()) {
            try {
                skiniPdf(pdfStringFile);
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    public void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void onPaste() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null) {
            return;
        }
        try {
            jTextArea1.setText((String) t.getTransferData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException e) {
            System.out.println(e);
        }//try
    }//onPaste

    void skiniPdf(String pdfString) throws IOException {
        String uspesnoSkidanjePdf;
        LocalDateTime myDateObj = LocalDateTime.now();

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyyHH-mm-ss");

        String datetime = myDateObj.format(myFormatObj);
        if (!Base64.isArrayByteBase64(pdfString.getBytes())) {
            JOptionPane.showMessageDialog(null, "Unesite Base64 String!");
            return;
        }
        byte[] decoded = java.util.Base64.getDecoder().decode(pdfString);

        Integer pdfPoruka;
        pdfPoruka = JOptionPane.showConfirmDialog(null, "Da li zelite da sacuvate PDF dokument fakture ?", "PDF faktura preuzimanje", JOptionPane.YES_NO_OPTION);
        //definisati gde ce se fakture smestati! PDF je cca 65KB
        if (pdfPoruka == 0) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(fdir);
            chooser.setDialogTitle("Preuzimanje PDF");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);
            //    
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            } else {
                JOptionPane.showMessageDialog(null, "Prekid");
            }

            String dirPDF = chooser.getSelectedFile().toString();
            dirPDF = dirPDF + "\\";
            String path = "eFakturePDF_" + datetime + ".pdf";
            try (FileOutputStream fosPdf = new FileOutputStream(dirPDF + path)) {
                fosPdf.write(decoded);
                fosPdf.flush();
                uspesnoSkidanjePdf = "Uspesno ste skinuli PDF dokument na lokaciju " + dirPDF + path;
                JOptionPane.showMessageDialog(null, uspesnoSkidanjePdf);
                openFile(dirPDF + path);
            }
        } else {
            uspesnoSkidanjePdf = "Prekid";
            JOptionPane.showMessageDialog(null, uspesnoSkidanjePdf);
        }

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
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
