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

package uk.chromis.pos.inventory;

import java.awt.Component;
import java.util.UUID;
import java.util.Date;
import uk.chromis.basic.BasicException;
import uk.chromis.beans.JCalendarDialog;
import uk.chromis.data.gui.ComboBoxValModel;
import uk.chromis.data.loader.SentenceList;
import uk.chromis.data.user.DirtyManager;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.DataLogicSales;

/**
 *
 * @author adrian
 */
public class ShowScheduleEditor extends javax.swing.JPanel implements EditorRecord {
    
    private Object schedid;
    
    private final SentenceList showSentence;
    private final SentenceList theatreSentence;

    private final ComboBoxValModel showModel;
    private final ComboBoxValModel theatreModel;
    
    private Object showKey;
    private Object theatreKey;

    /** Creates new form AttributesValuesEditor
    * @param dlSales
     * @param dirty
     * @throws uk.chromis.basic.BasicException */
    public ShowScheduleEditor(DataLogicSales dlSales, DirtyManager dirty) throws BasicException {
        
        initComponents();

        m_jShow.addActionListener(dirty);
        m_jTheatre.addActionListener(dirty);
        m_jStartDate.getDocument().addDocumentListener(dirty);
        m_jEndDate.getDocument().addDocumentListener(dirty);
        m_jReportStartDate.getDocument().addDocumentListener(dirty);
        m_jReportEndDate.getDocument().addDocumentListener(dirty);
        
        showSentence = dlSales.getShowsList();
        showModel = new ComboBoxValModel(showSentence.list());
        m_jShow.setModel(showModel);
        
        theatreSentence = dlSales.getTheatresList();
        theatreModel = new ComboBoxValModel(theatreSentence.list());
        m_jTheatre.setModel(theatreModel);
        
    }

    /**
     *
     * @param insertid
     */
    public void setInsertId(String insertid) {

        this.schedid = insertid;
    }

    /**
     *
     */
    @Override
    public void refresh() {
    }


    public void activate() throws BasicException {

    }


    /**
     *
     */
    @Override
    public void writeValueEOF() {

        schedid = null;
        showModel.setSelectedKey(null);
        theatreModel.setSelectedKey(null);
        m_jStartDate.setText(null);
        m_jEndDate.setText(null);
        m_jReportStartDate.setText(null);
        m_jReportEndDate.setText(null);

        m_jShow.setEnabled(false);
        m_jTheatre.setEnabled(false);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        m_jReportStartDate.setEnabled(false);
        m_jReportEndDate.setEnabled(false);

    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {

        schedid = UUID.randomUUID().toString();
        showModel.setSelectedKey(showKey);
        theatreModel.setSelectedKey(theatreKey);
        m_jStartDate.setText(null);
        m_jEndDate.setText(null);
        m_jReportStartDate.setText(null);
        m_jReportEndDate.setText(null);

        
        m_jShow.setEnabled(true);
        m_jShow.setEditable(false);
        m_jTheatre.setEnabled(true);
        m_jTheatre.setEditable(false);
        m_jStartDate.setEnabled(true);
        m_jEndDate.setEnabled(true);
        m_jReportStartDate.setEnabled(true);
        m_jReportEndDate.setEnabled(true);

    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {

        Object[] obj = (Object[]) value;

        schedid = obj[0];
        showModel.setSelectedKey(obj[1]);
        theatreModel.setSelectedKey(obj[2]);
        m_jStartDate.setText(Formats.DATE.formatValue(obj[3]));
        m_jEndDate.setText(Formats.DATE.formatValue(obj[4]));
        m_jReportStartDate.setText(Formats.DATE.formatValue(obj[5]));
        m_jReportEndDate.setText(Formats.DATE.formatValue(obj[6]));

        m_jShow.setEnabled(true);
        m_jShow.setEditable(false);
        m_jTheatre.setEnabled(true);
        m_jTheatre.setEditable(false);
        m_jStartDate.setEnabled(true);
        m_jEndDate.setEnabled(true);
        m_jReportStartDate.setEnabled(true);
        m_jReportEndDate.setEnabled(true);
        
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        Object[] obj = (Object[]) value;

        schedid = obj[0];
        showModel.setSelectedKey(obj[1]);
        theatreModel.setSelectedKey(obj[2]);
        m_jStartDate.setText(Formats.DATE.formatValue(obj[3]));
        m_jEndDate.setText(Formats.DATE.formatValue(obj[4]));
        m_jReportStartDate.setText(Formats.DATE.formatValue(obj[5]));
        m_jReportEndDate.setText(Formats.DATE.formatValue(obj[6]));

        m_jShow.setEnabled(false);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        m_jReportStartDate.setEnabled(false);
        m_jReportEndDate.setEnabled(false);
    }

    public void setShowKey( Object key ) {
        showKey = key;
    }
    
    public void setTheatreKey( Object key ) {
        theatreKey = key;
    }
    
    
    /**
     *
     * @return
     */
    @Override
    public Component getComponent() {
        return this;
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        return new Object[] {
            schedid,
            Formats.STRING.formatValue(showModel.getSelectedKey()),
            Formats.STRING.formatValue(theatreModel.getSelectedKey()),
            Formats.DATE.parseValue(m_jStartDate.getText()),
            Formats.DATE.parseValue(m_jEndDate.getText()),
            Formats.DATE.parseValue(m_jReportStartDate.getText()),
            Formats.DATE.parseValue(m_jReportEndDate.getText())
        };
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        m_jStartDate = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        m_jShow = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        m_jReportStartDate = new javax.swing.JTextField();
        m_jReportEndDate = new javax.swing.JTextField();
        m_jEndDate = new javax.swing.JTextField();
        m_jbtnStartDate = new javax.swing.JButton();
        m_jbtnEndDate = new javax.swing.JButton();
        m_jbtnReportStartDate = new javax.swing.JButton();
        m_jbtn_ReportEndDate = new javax.swing.JButton();
        m_jTheatre = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText(AppLocal.getIntString("label.show")); // NOI18N

        m_jStartDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(AppLocal.getIntString("label.productshowstartdate")); // NOI18N

        m_jShow.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(AppLocal.getIntString("label.productshowenddate")); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText(AppLocal.getIntString("label.productshowreportenddate")); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText(AppLocal.getIntString("label.productshowreportstartdate")); // NOI18N

        m_jReportStartDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jReportEndDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jEndDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jbtnStartDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/date.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        m_jbtnStartDate.setToolTipText(bundle.getString("tiptext.opencalendar")); // NOI18N
        m_jbtnStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnStartDateActionPerformed(evt);
            }
        });

        m_jbtnEndDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/date.png"))); // NOI18N
        m_jbtnEndDate.setToolTipText(bundle.getString("tiptext.opencalendar")); // NOI18N
        m_jbtnEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnEndDateActionPerformed(evt);
            }
        });

        m_jbtnReportStartDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/date.png"))); // NOI18N
        m_jbtnReportStartDate.setToolTipText(bundle.getString("tiptext.opencalendar")); // NOI18N
        m_jbtnReportStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnReportStartDateActionPerformed(evt);
            }
        });

        m_jbtn_ReportEndDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/date.png"))); // NOI18N
        m_jbtn_ReportEndDate.setToolTipText(bundle.getString("tiptext.opencalendar")); // NOI18N
        m_jbtn_ReportEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtn_ReportEndDateActionPerformed(evt);
            }
        });

        m_jTheatre.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.theatre")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(m_jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(m_jbtnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(m_jReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(m_jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(m_jbtnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(m_jbtnReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(m_jbtn_ReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 195, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_jTheatre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(m_jShow, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jTheatre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtnReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtn_ReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Distribution", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Times", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void m_jbtnStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnStartDateActionPerformed
        Date date;
        try {
            date = (Date) Formats.DATE.parseValue(m_jStartDate.getText());
        } catch (BasicException e) {
            date = null;
        }        
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jStartDate.setText(Formats.DATE.formatValue(date));
            if (m_jReportStartDate.getText().equals("")) {
                m_jReportStartDate.setText(Formats.DATE.formatValue(date));
            }
        }
    }//GEN-LAST:event_m_jbtnStartDateActionPerformed

    private void m_jbtnEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnEndDateActionPerformed
        Date date;
        try {
            date = (Date) Formats.DATE.parseValue(m_jEndDate.getText());
        } catch (BasicException e) {
            date = null;
        }        
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jEndDate.setText(Formats.DATE.formatValue(date));
            if (m_jReportEndDate.getText().equals("")) {
                m_jReportEndDate.setText(Formats.DATE.formatValue(date));
            }
        }
    }//GEN-LAST:event_m_jbtnEndDateActionPerformed

    private void m_jbtnReportStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnReportStartDateActionPerformed
        Date date;
        try {
            date = (Date) Formats.DATE.parseValue(m_jReportStartDate.getText());
        } catch (BasicException e) {
            date = null;
        }        
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jReportStartDate.setText(Formats.DATE.formatValue(date));
        }
    }//GEN-LAST:event_m_jbtnReportStartDateActionPerformed

    private void m_jbtn_ReportEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtn_ReportEndDateActionPerformed
        // TODO add your handling code here:
        Date date;
        try {
            date = (Date) Formats.DATE.parseValue(m_jReportEndDate.getText());
        } catch (BasicException e) {
            date = null;
        }        
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jReportEndDate.setText(Formats.DATE.formatValue(date));
        }
    }//GEN-LAST:event_m_jbtn_ReportEndDateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField m_jEndDate;
    private javax.swing.JTextField m_jReportEndDate;
    private javax.swing.JTextField m_jReportStartDate;
    private javax.swing.JComboBox<String> m_jShow;
    private javax.swing.JTextField m_jStartDate;
    private javax.swing.JComboBox<String> m_jTheatre;
    private javax.swing.JButton m_jbtnEndDate;
    private javax.swing.JButton m_jbtnReportStartDate;
    private javax.swing.JButton m_jbtnStartDate;
    private javax.swing.JButton m_jbtn_ReportEndDate;
    // End of variables declaration//GEN-END:variables


}
