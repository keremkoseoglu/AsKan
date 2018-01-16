/*
 * frmSDFull.java
 *
 * Created on 01 Kas�m 2007 Per�embe, 15:29
 */

package askan;

import askan.configuration.*;
import askan.systems.*;
import askan.steelyard.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author  Kerem
 */
public class frmSDFull extends javax.swing.JFrame {
    
    private SalesProcess salesProcess;
    private ArrayList
            wareHouses;
    
    private boolean itemSaved;
    
    /** Creates new form frmSDFull */
    public frmSDFull() {
        
        ActionMap am;
        InputMap im;
        KeyStroke ks;
        
        initComponents();
        
        Action plateActionListener = new AbstractAction() {
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
            public void actionPerformed(ActionEvent actionEvent) {
              btnWeighActionPerformed(actionEvent);
            }
          };        
        am = btnWeigh.getActionMap();
        im = btnWeigh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0);
        im.put(ks, "weigh");
        am.put("weigh", weighActionListener);
        
        Action printActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              btnPrintActionPerformed(actionEvent);
            }
          };        
        am = btnPrint.getActionMap();
        im = btnPrint.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ks = KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0);
        im.put(ks, "print");
        am.put("print", printActionListener);          
        
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
        
        setStatus("L�tfen plakay� girip SORGULA d��mesine bas�n");
        txtWeightFull.setEditable(Main.config.intParam.manualWeight);
        txtVrkmeFull.setEditable(Main.config.intParam.manualWeight);        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtPlate = new javax.swing.JTextField();
        btnPlate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCustomer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbWarehouse = new javax.swing.JComboBox();
        lblStatus = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtQuantityRequested = new javax.swing.JTextField();
        txtWeightEmpty = new javax.swing.JTextField();
        txtWeightFull = new javax.swing.JTextField();
        lblTheo = new javax.swing.JLabel();
        txtWeightCalc = new javax.swing.JTextField();
        txtWeightTheoretical = new javax.swing.JTextField();
        txtVrkmeRequested = new javax.swing.JTextField();
        txtVrkmeEmpty = new javax.swing.JTextField();
        txtVrkmeFull = new javax.swing.JTextField();
        txtVrkmeCalc = new javax.swing.JTextField();
        txtVrkmeTheoretical = new javax.swing.JTextField();
        btnWeigh = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        chkInspect = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dolu Tart�m");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel1.setText("Plaka");

        txtPlate.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnPlate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/add_16.gif"))); // NOI18N
        btnPlate.setText("F1 - Se�");
        btnPlate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlateActionPerformed(evt);
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

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel11.setText("Depo");

        cmbWarehouse.setFont(new java.awt.Font("Tahoma", 0, 14));

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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel6.setText("�stenen Miktar");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel3.setText("Bo� A��rl�k");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel7.setText("Dolu A��rl�k");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel12.setText("Tart�lan A��rl�k");

        txtQuantityRequested.setEditable(false);
        txtQuantityRequested.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtWeightEmpty.setEditable(false);
        txtWeightEmpty.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtWeightFull.setEditable(false);
        txtWeightFull.setFont(new java.awt.Font("Tahoma", 0, 14));

        lblTheo.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblTheo.setText("Teorik A��rl�k");

        txtWeightCalc.setEditable(false);
        txtWeightCalc.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtWeightTheoretical.setEditable(false);
        txtWeightTheoretical.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtVrkmeRequested.setEditable(false);
        txtVrkmeRequested.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtVrkmeEmpty.setEditable(false);
        txtVrkmeEmpty.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtVrkmeFull.setEditable(false);
        txtVrkmeFull.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtVrkmeCalc.setEditable(false);
        txtVrkmeCalc.setFont(new java.awt.Font("Tahoma", 0, 14));

        txtVrkmeTheoretical.setEditable(false);
        txtVrkmeTheoretical.setFont(new java.awt.Font("Tahoma", 0, 14));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtWeightTheoretical, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWeightCalc, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWeightEmpty, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWeightFull, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantityRequested, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTheo)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtVrkmeRequested, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeFull, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeCalc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeTheoretical, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtVrkmeRequested, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantityRequested, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtWeightEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtWeightFull, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeFull, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtWeightCalc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeCalc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTheo)
                    .addComponent(txtWeightTheoretical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVrkmeTheoretical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnWeigh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/applications_16.gif"))); // NOI18N
        btnWeigh.setText("F7 - Tart");
        btnWeigh.setEnabled(false);
        btnWeigh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWeighActionPerformed(evt);
                jButton1ActionPerformed(evt);
            }
        });

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/delete_16.gif"))); // NOI18N
        btnClose.setText("F12 - Kapat");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel8.setText("Notlar");

        txtNote.setEditable(false);
        txtNote.setFont(new java.awt.Font("Tahoma", 0, 14));

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/askan/binaries/S_B_PRNT.gif"))); // NOI18N
        btnPrint.setText("F10");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnWeigh, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addContainerGap())
                            .addComponent(btnPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addGap(62, 62, 62)
                        .addComponent(cmbWarehouse, 0, 384, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtNote, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkInspect)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPlate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWeigh, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cmbWarehouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(chkInspect)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnClose)
                    .addComponent(btnPrint))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus))
        );

        btnWeigh.getAccessibleContext().setAccessibleName("btnWeigh");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        boolean canDispose = true;
        
        if (!itemSaved) canDispose = askan.systems.Util.confirmStep(this, "Giri� hen�z kaydedilmedi! \nKapatmak istiyor musunuz?"); 
        if (canDispose) this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnWeighActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWeighActionPerformed
        btnWeigh.setEnabled(false);
        
        TwoWaySerialComm.truckStatus = TwoWaySerialComm.TRUCK_STATUS.FULL;
        
        if (Main.config.intParam.manualWeight)
        {
            try
            {
                salesProcess.fullWeight = new Weight();
                salesProcess.fullWeight.setWeight(txtWeightFull.getText(), txtVrkmeFull.getText());            
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
            salesProcess.fullWeight = Main.steelYard.getLastWeight();
        }
        
        if (salesProcess.fullWeight.getWeight() == 0) {
            setStatus("Tart�m hatas�");
            btnWeigh.setEnabled(true);
            return;
        } else {
            truckWeighted();
        }
    }//GEN-LAST:event_btnWeighActionPerformed

    private void transferFormToObjectPreSave()
    {
        salesProcess.fullNote = txtNote.getText();
        salesProcess.inspect = chkInspect.isSelected();

        if (cmbWarehouse.getSelectedIndex() > 0)
        {
            salesProcess.wareHouse = (WareHouse) wareHouses.get(cmbWarehouse.getSelectedIndex() - 1);
        }
        else
        {
            salesProcess.wareHouse = null;
        }       
        
        if (salesProcess.dateFull == null) salesProcess.dateFull = Calendar.getInstance();
    }
    
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        btnSave.setEnabled(false);
        
        if (!itemSaved)
        {
            saveEnable(false);
            transferFormToObjectPreSave();

            try
            {
                salesProcess.saveFullWeight();
                setStatus("Dolu a��rl�k kaydedildi, kamyon tesisten ��kabilir");
                itemSaved = true;
                btnSave.setEnabled(false);
                btnPrint.setEnabled(true);
                chkInspect.setEnabled(false);
            }
            catch(Exception ex)
            {
                setStatus("Dolu a��rl�k kaydedilirken bir hata olu�tu");
                Main.appendLog(salesProcess.trmtyp + " arac�na ait dolu a��rl�k kaydedilirken bir hata olu�tu: " + ex.toString());
                saveEnable(true);
                return;
            }
        }
        

    }//GEN-LAST:event_btnSaveActionPerformed

    public void saveEnable(boolean Enable)
    {
        btnPrint.setEnabled(Enable);
        btnSave.setEnabled(Enable);
        cmbWarehouse.setEnabled(Enable);
        txtNote.setEditable(Enable);
    }
    
    public void setStatus(String Status)
    {
        lblStatus.setText(Status);
    }     
    
    private void truckWeighted()
    {
        btnWeigh.setEnabled(true);
        salesProcess.calculateCalcWeight();
        
        // Devam
        txtWeightFull.setText(salesProcess.fullWeight.toString(false));
        txtVrkmeFull.setText(Material.getUOM(salesProcess.fullWeight.uom));
        
        txtWeightCalc.setText(salesProcess.calcWeight.toString(false));
        txtVrkmeCalc.setText(Material.getUOM(salesProcess.calcWeight.uom));
        
        // Negatif a��rl�k kontrol�
        if (salesProcess.calcWeight.getWeight() <= 0)
        {
            setStatus("Tart�m hatas�: Negatif a��rl�k!");
            return;
        }        
        
        // Tolerans kontrol�
        try
        {
            boolean toler = salesProcess.checkSDTolerance();
            if (!toler) 
            {
                boolean stopTruck = false;
                if (Main.config.steelyardParam.atole)
                {
                    String tol = String.valueOf(salesProcess.material.division.tolerance);
                    String exc = String.valueOf(Math.ceil(salesProcess.getDifferenceRate()));
                    if (!askan.systems.Util.confirmStep(this, "�stenen miktar ile y�klenen miktar aras�ndaki fark (%" + exc + ")\n tolerans s�n�r� (%" + tol + ") d���nda! \nDevam etmek istiyor musunuz?"))
                    {
                        stopTruck = true;
                    }
                }
                else
                {
                    stopTruck = true;
                }
                
                if (stopTruck)
                {
                    setStatus("Tolerans limiti a��ld�, kamyon ��kamaz");
                    return;
                }
            }
        }
        catch(Exception ex)
        {
            Main.appendLog(String.valueOf(salesProcess.id) + " tolerans kontrol� hatas�: " + ex.toString());
        }
        
        // Devam
        setStatus("A��rl�k hesapland�, di�er bilgileri tamamlay�p kaydedebilirsiniz");
        txtNote.setEditable(true);
        
        wareHouses = new ArrayList();
        try
        {
            cmbWarehouse.removeAllItems();
            cmbWarehouse.addItem("-- L�tfen Depo Se�in--");
            wareHouses = WareHouse.getAll(WareHouse.USAGE_GOAL.SALE);
            for (int n = 0; n < wareHouses.size(); n++)
            {
                WareHouse w = (WareHouse) wareHouses.get(n);
                cmbWarehouse.addItem(w.getDisplayText());
            }
        }
        catch(Exception ex)
        {
            Main.appendLog("Depo yerleri �ekilemedi: " + ex.toString());
        }
        
        btnSave.setEnabled(true);
        btnPrint.setEnabled(true);
    }    
    
    private void btnPlateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlateActionPerformed
        connectToSql();
        
        // Ekran� kapat
        setStatus("Ara� sorgulan�yor...");
        txtPlate.setEditable(false);
        btnPlate.setEnabled(false);
        
        // Process'i bul
        try
        {
            salesProcess = new SalesProcess();
            boolean b = salesProcess.fillFromPlate(txtPlate.getText());
            if (!b)
            {
                setStatus(txtPlate.getText() + " plakal� ara� hen�z sevkiyat giri�ini yapmam��");
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
        
        if (!salesProcess.passedEmpty) 
        {
            setStatus(txtPlate.getText() + " plakal� ara� hen�z bo� kantardan ge�memi�");
            enableAfterPlateQuery();
            return;
        }
        
        if (salesProcess.passedFull)
        {
            setStatus(txtPlate.getText() + " plakal� ara� dolu kantarda zaten tart�lm��");
            enableAfterPlateQuery();
            return;
        }
        
        // Aktar�m
        txtCustomer.setText(salesProcess.getCustomerDisplayText());
        txtMaterial.setText(salesProcess.material.getDisplayText());
        txtQuantityRequested.setText(salesProcess.lfimg.toString(false));
        txtVrkmeRequested.setText(salesProcess.getVrkmeDisplayText());
        
        txtWeightEmpty.setText(salesProcess.emptyWeight.toString(false));
        txtVrkmeEmpty.setText(Material.getUOM(salesProcess.emptyWeight.uom));
        if (salesProcess.inspect)
        {
            chkInspect.setEnabled(false);
            chkInspect.setSelected(true);
        }        
        
        try
        {
            salesProcess.calculateTheoWeight();
            txtWeightTheoretical.setText(salesProcess.theoWeight.toString(false));
            txtVrkmeTheoretical.setText(Material.getUOM(salesProcess.theoWeight.uom));
        }
        catch(Exception ex)
        {
            setStatus("Malzemeye ait a��rl�k algoritmas� tespit edilemedi");
            Main.appendLog("Malzemeye ait a��rl�k algoritmas� belirlenirken bir hata olu�tu: " + ex.toString());
            enableAfterPlateQuery();
            return;
        }
        
        btnWeigh.setEnabled(true);
        setStatus("L�tfen dolu arac� tart�n");
    }//GEN-LAST:event_btnPlateActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        saveEnable(false);
        
        if (!itemSaved) transferFormToObjectPreSave();
        
        this.setStatus("Kantar fi�i yazd�r�l�yor, l�tfen bekleyin");
        salesProcess.printFull2();
        setStatus("Kantar fi�i yazd�r�ld�");
        
        this.setStatus("�rsaliye yazd�r�l�yor, l�tfen bekleyin");
        salesProcess.printFull();
        setStatus("�rsaliye yazd�r�ld�");               
        saveEnable(true);
}//GEN-LAST:event_btnPrintActionPerformed
    
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
    
    private void enableAfterPlateQuery()
    {
        txtPlate.setEditable(true);
        btnPlate.setEnabled(true);
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSDFull().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPlate;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnWeigh;
    private javax.swing.JCheckBox chkInspect;
    private javax.swing.JComboBox cmbWarehouse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTheo;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtPlate;
    private javax.swing.JTextField txtQuantityRequested;
    private javax.swing.JTextField txtVrkmeCalc;
    private javax.swing.JTextField txtVrkmeEmpty;
    private javax.swing.JTextField txtVrkmeFull;
    private javax.swing.JTextField txtVrkmeRequested;
    private javax.swing.JTextField txtVrkmeTheoretical;
    private javax.swing.JTextField txtWeightCalc;
    private javax.swing.JTextField txtWeightEmpty;
    private javax.swing.JTextField txtWeightFull;
    private javax.swing.JTextField txtWeightTheoretical;
    // End of variables declaration//GEN-END:variables
    
}
