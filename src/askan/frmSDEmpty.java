/*
 * frmSDEmpty.java
 *
 * Created on 27 Ekim 2007 Cumartesi, 17:27
 */

package askan;

import askan.systems.*;
import askan.steelyard.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author  Kerem
 */
public class frmSDEmpty extends javax.swing.JFrame {
    
    public SalesProcess salesProcess;
    private boolean isSaved;
    
    /** Creates new form frmSDEmpty */
    public frmSDEmpty() {
        initComponents();
        
        ActionMap am;
        InputMap im;
        KeyStroke ks;
        
        Action plateActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              btnPlateActionPerformed(actionEvent);
            }
          };        
        am = btnPlate.getActionMap();
        im = btnPlate.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        im.put(ks, "plate");
        am.put("plate", plateActionListener);       
        
        Action weighActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              btnWeighActionPerformed(actionEvent);
            }
          };        
        am = btnWeigh.getActionMap();
        im = btnWeigh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        im.put(ks, "weigh");
        am.put("weigh", weighActionListener);     
        
        Action saveActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              btnSaveActionPerformed(actionEvent);
            }
          };        
        am = btnSave.getActionMap();
        im = btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0);
        im.put(ks, "save");
        am.put("save", saveActionListener);       
        
        Action closeActionListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
              btnCloseActionPerformed(actionEvent);
            }
          };        
        am = btnClose.getActionMap();
        im = btnClose.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0);
        im.put(ks, "close");
        am.put("close", closeActionListener);        
        
        Action printActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              btnPrint1ActionPerformed(actionEvent);
            }
          };        
        am = btnPrint1.getActionMap();
        im = btnPrint1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0);
        im.put(ks, "print");
        am.put("print", printActionListener);          
        
        setStatus("L�tfen �ekici / dorse plakas�n� girip SORGULA d��mesine bas�n");
        txtWeightEmpty.setEditable(Main.config.intParam.manualWeight);
        txtVrkme.setEditable(Main.config.intParam.manualWeight);  
        isSaved = false;
    }
    
    public void setStatus(String Status)
    {
        lblStatus.setText(Status);
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPlate1 = new javax.swing.JTextField();
        btnPlate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtPlate2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtWeightEmpty = new javax.swing.JTextField();
        txtVrkme = new javax.swing.JTextField();
        btnWeigh = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCustomer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnPrint1 = new javax.swing.JButton();
        chkInspect = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bo� Tart�m");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText("�ekici Plakas�");

        txtPlate1.setFont(new java.awt.Font("Tahoma", 0, 14));
        txtPlate1.setNextFocusableComponent(txtPlate2);

        btnPlate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/add_16.gif"))); // NOI18N
        btnPlate.setText("F1 - Se�");
        btnPlate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlateActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel2.setText("Dorse Plakas�");

        txtPlate2.setFont(new java.awt.Font("Tahoma", 0, 14));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel3.setText("Bo� A��rl�k");

        txtWeightEmpty.setEditable(false);
        txtWeightEmpty.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtVrkme.setEditable(false);
        txtVrkme.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnWeigh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/applications_16.gif"))); // NOI18N
        btnWeigh.setText("F5 - Tart");
        btnWeigh.setEnabled(false);
        btnWeigh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWeighActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel4.setText("M��teri");

        txtCustomer.setEditable(false);
        txtCustomer.setFont(new java.awt.Font("Tahoma", 0, 14));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel5.setText("Malzeme");

        txtMaterial.setEditable(false);
        txtMaterial.setFont(new java.awt.Font("Tahoma", 0, 14));

        lblStatus.setText("...");
        lblStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/save_16.gif"))); // NOI18N
        btnSave.setText("F11 - Kaydet");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/delete_16.gif"))); // NOI18N
        btnClose.setText("F12 - Kapat");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel6.setText("Notlar");

        txtNote.setEditable(false);
        txtNote.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/S_B_PRNT.gif"))); // NOI18N
        btnPrint1.setText("F10");
        btnPrint1.setEnabled(false);
        btnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrint1ActionPerformed(evt);
            }
        });

        chkInspect.setText("�ncelensin");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPlate2)
                                    .addComponent(txtPlate1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPlate, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                                    .addComponent(txtMaterial, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)))))
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtWeightEmpty, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVrkme, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnWeigh, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrint1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtNote, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkInspect)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPlate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPlate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnWeigh)
                    .addComponent(txtVrkme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWeightEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(chkInspect)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnSave)
                    .addComponent(btnPrint1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatus))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        
        boolean canDispose = true;
        
        if (!isSaved) canDispose = askan.systems.Util.confirmStep(this, "Giri� hen�z kaydedilmedi! \nKapatmak istiyor musunuz?"); 
        if (canDispose) this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnWeighActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWeighActionPerformed
        TwoWaySerialComm.truckStatus = TwoWaySerialComm.TRUCK_STATUS.EMPTY;
        
        if (Main.config.intParam.manualWeight)
        {
            try
            {
                salesProcess.emptyWeight = new Weight();
                salesProcess.emptyWeight.setWeight(txtWeightEmpty.getText(), txtVrkme.getText());
            }
            catch(Exception ex)
            {
                Main.appendLog("A��rl�k hatas�: " + ex.toString());
                setStatus("L�tfen a��rl�klar� kontrol edin");
                return;
            }            
        }
        else
        {
            salesProcess.emptyWeight = Main.steelYard.getLastWeight();
        }
        
        if (salesProcess.emptyWeight.getWeight() == 0)
        {
            setStatus("Tart�m hatas�");
        }
        else
        {
            truckWeighted();
        }
    }//GEN-LAST:event_btnWeighActionPerformed

    private void transferFormToObjectPreSave()
    {
            salesProcess.trmtyp2 = Util.formatPlate(txtPlate2.getText());
            salesProcess.emptyNote = txtNote.getText();    
            salesProcess.inspect = chkInspect.isSelected();
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        btnSave.setEnabled(false);
        
        try
        {
            transferFormToObjectPreSave();
            salesProcess.saveEmptyWeight();
            setStatus("Bo� a��rl�k kaydedildi, kamyon mal almaya gidebilir");
            btnWeigh.setEnabled(false);
            txtNote.setEditable(false);
            chkInspect.setEnabled(false);
            isSaved = true;
        }
        catch(Exception ex)
        {
            setStatus("Bo� a��rl�k kaydedilirken bir hata olu�tu");
            Main.appendLog(salesProcess.trmtyp + " arac�na ait bo� a��rl�k kaydedilirken bir hata olu�tu: " + ex.toString());
            btnSave.setEnabled(true);
        }        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnPlateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlateActionPerformed
        
        connectToSql();
        
        // Ekran� kapat
        setStatus("Ara� sorgulan�yor...");
        txtPlate1.setEditable(false);
        txtPlate2.setEditable(false);
        btnPlate.setEnabled(false);
        
        // Process'i bul
        try
        {
            salesProcess = new SalesProcess();
            boolean b = salesProcess.fillFromPlate(txtPlate1.getText());
            if (!b)
            {
                setStatus(txtPlate1.getText() + " plakal� ara� hen�z sevkiyat giri�ini yapmam��");
                enableAfterPlateQuery();
                return;                
            }
        }
        catch(Exception ex)
        {
            Main.appendLog("Plaka sorgusu s�ras�nda hata: " + ex.toString());
            enableAfterPlateQuery();
            return;
        }
        
        if (salesProcess.passedEmpty)
        {
            if (askan.systems.Util.confirmStep(this, "Bu ara� bo� kantarda zaten tart�lm��! \nTart�m� tekrarlamak istiyor musunuz?")) 
            {
                try
                {
                    salesProcess.cancelEmptyWeight();
                }
                catch(Exception ex)
                {
                    Main.appendLog("Bo� tart�m iptali s�ras�nda hata: " + ex.toString());
                    setStatus("Bo� tart�m iptali s�ras�nda bir hata olu�tu");
                    enableAfterPlateQuery();
                    return;
                }
            }
            else
            {
                setStatus(txtPlate1.getText() + " plakal� ara� bo� kantarda zaten tart�lm��");
                enableAfterPlateQuery();
                return;
            }
        }
        
        // Aktar�m
        txtCustomer.setText(salesProcess.getCustomerDisplayText());
        txtMaterial.setText(salesProcess.material.getDisplayText());
        txtNote.setEditable(true);
        if (salesProcess.inspect)
        {
            chkInspect.setEnabled(false);
            chkInspect.setSelected(true);
        }
        
        // Bizim ara� ise a��rl�k?
        boolean mustWeight = false;
        if (txtPlate2.getText().length() > 0)
        {
            try
            {
                Weight w1 = Vehicle.getWeight(txtPlate1.getText());
                Weight w2 = Vehicle.getWeight(txtPlate2.getText());
                mustWeight = (w1.getWeight() == 0 || w2.getWeight() == 0);
                if (!mustWeight)
                {
                    salesProcess.emptyWeight.setWeight(Material.toKG(w1) + Material.toKG(w2), Material.UNIT_OF_MEASURE.KG);
                    truckWeighted();
                    btnWeigh.setEnabled(true);
                    txtNote.setEditable(true);
                }
            }
            catch(Exception ex)
            {
                mustWeight = true;
            }
        }
        else
        {
            mustWeight = true;
        }
        
        if (mustWeight)
        {
            setStatus("Ara� a��rl��� tespit edilemedi, l�tfen kantarda tart�n");
            btnWeigh.setEnabled(true);
            return;
        }
    }//GEN-LAST:event_btnPlateActionPerformed

    private void btnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrint1ActionPerformed
        btnPrint1.setEnabled(false);
        if (!isSaved) transferFormToObjectPreSave();
        this.setStatus("Kantar fi�i yazd�r�l�yor, l�tfen bekleyin");
        salesProcess.printSevk();
        setStatus("Kantar fi�i yazd�r�ld�");
        btnPrint1.setEnabled(true);
    }//GEN-LAST:event_btnPrint1ActionPerformed
    
    private void truckWeighted()
    {
        txtWeightEmpty.setText(salesProcess.emptyWeight.toString(false));
        txtVrkme.setText(salesProcess.emptyWeight.getUOM());
        setStatus("Bo� a��rl�k tespit edildi, yazd�r�p kaydedebilirsiniz. Gerekiyorsa tekrar tartabilirsiniz.");
        txtNote.setEditable(true);
        btnSave.setEnabled(true);
        btnPrint1.setEnabled(true);
    }
            
    private void enableAfterPlateQuery()
    {
        txtPlate1.setEditable(true);
        txtPlate2.setEditable(true);
        btnPlate.setEnabled(true);    
    }
    
    private boolean connectToSql()
    {
        try
        {
            Main.sql.connect();
            return true;
        }
        catch(Exception ex)
        {
            Main.appendLog("SQL ba�lant� hatas�: " + ex.toString());
            setStatus("SQL ba�lant� hatas�");
            return false;
        }        
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSDEmpty().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPlate;
    private javax.swing.JButton btnPrint1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnWeigh;
    private javax.swing.JCheckBox chkInspect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtPlate1;
    private javax.swing.JTextField txtPlate2;
    private javax.swing.JTextField txtVrkme;
    private javax.swing.JTextField txtWeightEmpty;
    // End of variables declaration//GEN-END:variables
    
}
