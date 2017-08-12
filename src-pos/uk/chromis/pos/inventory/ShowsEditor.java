//    Chromis POS  - The New Face of Open Source POS
//    Copyright (c) (c) 2015-2016
//    http://www.chromis.co.uk
//
//    This file is part of Chromis POS
//
//     Chromis POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Chromis POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Chromis POS.  If not, see <http://www.gnu.org/licenses/>.
//
package uk.chromis.pos.inventory;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.UUID;
import javax.swing.JPanel;
import uk.chromis.basic.BasicException;
import uk.chromis.data.user.DirtyManager;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppLocal;

/**
 *
 * @author adrianromero
 */
public final class ShowsEditor extends JPanel implements EditorRecord {

    private Object m_id;

    /**
     * Creates new form JEditProduct
     *
     * @param dirty
     */
    public ShowsEditor(DirtyManager dirty) {
        
        initComponents();

        m_jName.getDocument().addDocumentListener(dirty);
        m_jShowOrder.addChangeListener(dirty);
        m_jShowLength.addChangeListener(dirty);
        m_jActive.addActionListener(dirty);
        m_jTabImage.addPropertyChangeListener("image", dirty);
		
        writeValueEOF();
    }

    /**
     *
     * @throws BasicException
     */
    public void activate() throws BasicException {

    }

    public String getID() {
        return (String) m_id;
    }

    // Save the currently edited show.  
    public void saveProduct() {
    }

    /**
     *
     */
    @Override
    public void refresh() {
    }

    /**
     *
     */
    @Override
    public void writeValueEOF() {

        m_jTitle.setText(AppLocal.getIntString("label.recordeof"));
        m_id = null;
        m_jName.setText(null);
        m_jShowOrder.setValue(0);
        m_jShowLength.setValue(0);
        m_jActive.setSelected(false);
        m_jTabImage.setImage(null);

        m_jName.setEnabled(false);
        m_jShowOrder.setEnabled(false);
        m_jShowLength.setEnabled(false);
        m_jActive.setEnabled(false);
        m_jTabImage.setEnabled(false);
    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {
        
        m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        m_id = UUID.randomUUID().toString();
        m_jName.setText(null);
        m_jShowOrder.setValue(0);
        m_jShowLength.setValue(0);
        m_jActive.setSelected(true);
        m_jTabImage.setImage(null);

        // Los habilitados
        m_jName.setEnabled(true);
        m_jShowOrder.setEnabled(true);
        m_jShowLength.setEnabled(true);
        m_jActive.setEnabled(true);
        m_jTabImage.setEnabled(true);

    }


    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        m_jTitle.setText(AppLocal.getIntString("label.recorddeleted"));
        
        m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        m_id = UUID.randomUUID().toString();
        m_jName.setText(null);
        m_jShowOrder.setValue(0);
        m_jShowLength.setValue(0);
        m_jActive.setSelected(false);
        m_jTabImage.setImage(null);

        m_jName.setEnabled(false);
        m_jShowOrder.setEnabled(false);
        m_jShowLength.setEnabled(false);
        m_jActive.setEnabled(false);
        m_jTabImage.setEnabled(false);
        
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {

        Object[] myshow = (Object[]) value;
        
        m_id = myshow[0];
        m_jName.setText(Formats.STRING.formatValue(myshow[1]));
        m_jTabImage.setImage((BufferedImage) myshow[2]);
        m_jShowOrder.setValue(myshow[3]);
        m_jShowLength.setValue(myshow[4]);
        m_jActive.setSelected((Boolean) myshow[5]);
        
        m_jName.setEnabled(true);
        m_jShowOrder.setEnabled(true);
        m_jShowLength.setEnabled(true);
        m_jActive.setEnabled(true);
        m_jTabImage.setEnabled(true);
        
    }

    /**
     *
     * @return myprod
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        
        Object[] myshow = new Object[7];
        myshow[0] = m_id;
        myshow[1] = m_jName.getText();
        myshow[2] = m_jTabImage.getImage();
        myshow[3] = m_jShowOrder.getValue();
        myshow[4] = m_jShowLength.getValue();
        myshow[5] = m_jActive.isSelected();
        
        return myshow;

    }

    /**
     *
     * @return this
     */
    @Override
    public Component getComponent() {
        return this;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jScheduleMode = new javax.swing.ButtonGroup();
        m_jTitle = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        m_jTabGeneral = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        m_jActive = new eu.hansolo.custom.SteelCheckBox();
        m_jShowLength = new javax.swing.JSpinner();
        m_jShowOrder = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        m_jTabImage = new uk.chromis.data.gui.JImageEditor();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        m_jTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        m_jTitle.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        add(m_jTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 330, 30));

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jTabGeneral.setLayout(null);

        jLabel34.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel34.setText(AppLocal.getIntString("label.showlength")); // NOI18N
        m_jTabGeneral.add(jLabel34);
        jLabel34.setBounds(10, 70, 122, 15);

        m_jName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                m_jNameFocusLost(evt);
            }
        });
        m_jTabGeneral.add(m_jName);
        m_jName.setBounds(180, 10, 270, 25);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.showactive")); // NOI18N
        m_jTabGeneral.add(jLabel3);
        jLabel3.setBounds(10, 100, 37, 15);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("label.showorder")); // NOI18N
        m_jTabGeneral.add(jLabel2);
        jLabel2.setBounds(10, 40, 63, 15);

        m_jActive.setText(" ");
        m_jActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jActiveActionPerformed(evt);
            }
        });
        m_jTabGeneral.add(m_jActive);
        m_jActive.setBounds(180, 100, 30, 30);
        m_jTabGeneral.add(m_jShowLength);
        m_jShowLength.setBounds(180, 70, 80, 26);
        m_jTabGeneral.add(m_jShowOrder);
        m_jShowOrder.setBounds(180, 40, 80, 26);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.showname")); // NOI18N
        m_jTabGeneral.add(jLabel4);
        jLabel4.setBounds(10, 10, 36, 15);

        jTabbedPane1.addTab(AppLocal.getIntString("label.showgeneral"), m_jTabGeneral); // NOI18N
        jTabbedPane1.addTab(AppLocal.getIntString("label.showimage"), m_jTabImage); // NOI18N

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 600, 420));
    }// </editor-fold>//GEN-END:initComponents

    private void m_jActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jActiveActionPerformed
    }//GEN-LAST:event_m_jActiveActionPerformed

    private void m_jNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_m_jNameFocusLost
    }//GEN-LAST:event_m_jNameFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private eu.hansolo.custom.SteelCheckBox m_jActive;
    private javax.swing.JTextField m_jName;
    private javax.swing.ButtonGroup m_jScheduleMode;
    private javax.swing.JSpinner m_jShowLength;
    private javax.swing.JSpinner m_jShowOrder;
    private javax.swing.JPanel m_jTabGeneral;
    private uk.chromis.data.gui.JImageEditor m_jTabImage;
    private javax.swing.JLabel m_jTitle;
    // End of variables declaration//GEN-END:variables

}
