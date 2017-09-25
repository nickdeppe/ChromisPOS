/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.chromis.pos.inventory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import uk.chromis.basic.BasicException;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;

/**
 *
 * @author nick
 */
public class ShowScheduleAddTime extends javax.swing.JDialog {

    private DefaultListModel<JCheckBox> listModel;
    private Boolean ok = false;

    /**
     * Creates new form ShowScheduleAddTime2
     */
    public ShowScheduleAddTime(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    
    public ShowScheduleAddTime(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }
    
    
    public Boolean isOK() {
        return ok;
    }
    
    private void init() {
        initComponents();
        listModel = new DefaultListModel<>();
        m_jDateList.setModel(listModel);
        m_jDateList.setCellRenderer(new CheckboxListRenderer());
        m_jDateList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JList list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                if ( index != -1 ) {
                    JCheckBox item = (JCheckBox) list.getModel().getElementAt(index);
                    item.setSelected(!item.isSelected());
                    repaint();
                }
            }
        });
        m_jDateList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                JList list = (JList) e.getSource();
                int index = list.getSelectedIndex();
                if (index != -1 && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    boolean newVal = !((JCheckBox) (list.getModel().getElementAt(index))).isSelected();
                    for (int i : list.getSelectedIndices()) {
                        JCheckBox checkbox = (JCheckBox) list.getModel().getElementAt(i);
                        checkbox.setSelected(newVal);
                        repaint();
                    }
                }
            }
        });

        
        String appFormatTime = AppConfig.getInstance().getProperty("format.time");
        String timeFormat = ( appFormatTime == null || appFormatTime.equals("") ) ? "hh:mm a" : appFormatTime ;
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(m_jTimeSpinner, timeFormat );
        m_jTimeSpinner.setEditor(timeEditor);

    }



    public ArrayList<Date> getDates() {

        Date date;
        JCheckBox item;
        ArrayList<Date> dates = new ArrayList<>();

        for (int i = 0; i < listModel.getSize(); i++ ) {
            item = listModel.getElementAt(i);
            try {
                date = (Date) Formats.DATE.parseValue(item.getText());
            } catch(BasicException e) {
                continue;
            }
            dates.add(date);
        }

        return dates;

    }


    public Date getTime() {
        Date time;
        time = (Date) m_jTimeSpinner.getModel().getValue();
        return time;
    }
    
    
    
    public void showDialog(Date startDate, Date endDate) {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        for (cal.setTime(startDate); !cal.getTime().after(endDate); cal.add(Calendar.DATE, 1)) {
            JCheckBox newCheck = new JCheckBox(Formats.DATE.formatValue(cal.getTime()));
            newCheck.setSelected(true);
            listModel.addElement(newCheck);
        }

        m_jTimeSpinner.setValue(new Date());
        
        this.setVisible(true);
        
    }


    private boolean validateDialog() {

        JCheckBox item;
        Date date, time;
        ArrayList<String> errors = new ArrayList<>();
        StringBuilder errorMessage = new StringBuilder();
        int totalSelected = 0;


        // Make sure at least one date is selected
        for (int i = 0; i < listModel.getSize(); i++ ) {
            item = listModel.getElementAt(i);
            try {
                date = (Date) Formats.DATE.parseValue(item.getText());
            } catch(BasicException e) {
                errors.add("Could not parse '" + item.getText() + "' as Date");
            }
            if ( item.isSelected() ) {
                ++totalSelected;
            }
        }

        if ( totalSelected < 1 ) {
            errors.add("Please select at least one date");
        }

        try {
            time = (Date) m_jTimeSpinner.getModel().getValue();
        } catch (Exception e) {
            errors.add("Could not parse '" + m_jTimeSpinner.getValue().toString() + "' as Time");
        }

        if ( errors.isEmpty() ) {
            return true;
        } else {
            errorMessage.append("Please correct the following: \n");
            for ( int i = 0; i < errors.size(); i++ ) {
                errorMessage.append("- ");
                errorMessage.append(errors.get(i));
                errorMessage.append("\n");
            }
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }


    
    public static ShowScheduleAddTime getShowScheduleAddTime(Component parent) {
        
        Window window = SwingUtilities.getWindowAncestor(parent);

        ShowScheduleAddTime myTime;
        if (window instanceof Frame) {
            myTime = new ShowScheduleAddTime((Frame) window, true);
        } else {
            myTime = new ShowScheduleAddTime((Dialog) window, true);
        }

        myTime.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        myTime.setUndecorated(true);

        myTime.init();
        myTime.applyComponentOrientation(parent.getComponentOrientation());

        return myTime;
        
    }



    protected class CheckboxListRenderer extends JCheckBox implements ListCellRenderer<JCheckBox> {

       @Override
       public Component getListCellRendererComponent(JList<? extends JCheckBox> list, JCheckBox value, int index, boolean isSelected, boolean cellHasFocus) {
          setEnabled(list.isEnabled());
          setSelected(value.isSelected());
          setFont(list.getFont());
          setBackground(list.getBackground());
          setForeground(list.getForeground());
          setText(value.getText());
          return this;
       }

    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jButtonCancel = new javax.swing.JButton();
        m_jButtonOK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_jDateList = new javax.swing.JList<>();
        m_jTimeSpinner = new javax.swing.JSpinner( new javax.swing.SpinnerDateModel() );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("ShowScheduleAddTime"); // NOI18N
        setSize(new java.awt.Dimension(384, 285));

        m_jButtonCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/cancel.png"))); // NOI18N
        m_jButtonCancel.setText(AppLocal.getIntString("Button.Cancel")); // NOI18N
        m_jButtonCancel.setFocusPainted(false);
        m_jButtonCancel.setFocusable(false);
        m_jButtonCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonCancel.setRequestFocusEnabled(false);
        m_jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonCancelActionPerformed(evt);
            }
        });

        m_jButtonOK.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/ok.png"))); // NOI18N
        m_jButtonOK.setText(AppLocal.getIntString("Button.OK")); // NOI18N
        m_jButtonOK.setFocusPainted(false);
        m_jButtonOK.setFocusable(false);
        m_jButtonOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonOK.setRequestFocusEnabled(false);
        m_jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonOKActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("Label.Time")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("Label.AddTimeText")); // NOI18N

        m_jDateList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jScrollPane1.setViewportView(m_jDateList);

        m_jTimeSpinner.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        m_jTimeSpinner.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(m_jButtonOK)
                        .addGap(5, 5, 5)
                        .addComponent(m_jButtonCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(8, 8, 8)
                        .addComponent(m_jTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_jButtonOK)
                    .addComponent(m_jButtonCancel))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(407, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonCancelActionPerformed

        ok = false;

        dispose();
    }//GEN-LAST:event_m_jButtonCancelActionPerformed

    private void m_jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonOKActionPerformed

        if ( validateDialog() ) {
            ok = true;
            dispose();
        }
    }//GEN-LAST:event_m_jButtonOKActionPerformed

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
            java.util.logging.Logger.getLogger(ShowScheduleAddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowScheduleAddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowScheduleAddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowScheduleAddTime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShowScheduleAddTime dialog = new ShowScheduleAddTime(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton m_jButtonCancel;
    private javax.swing.JButton m_jButtonOK;
    private javax.swing.JList<JCheckBox> m_jDateList;
    private javax.swing.JSpinner m_jTimeSpinner;
    // End of variables declaration//GEN-END:variables
}


