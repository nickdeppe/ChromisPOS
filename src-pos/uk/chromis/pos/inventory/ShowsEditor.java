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
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class ShowsEditor extends javax.swing.JPanel implements EditorRecord {
    
    private Object showid;
    
    private final SentenceList theatreSentence;

    private ComboBoxValModel theatreModel;

    private enum RecordStatus {
        EOF,
        UPDATE,
        INSERT,
        DELETE
    }

    private ShowsFilter showsFilter;

    private RecordStatus recStatus;
    
    
    /** Creates new form AttributesValuesEditor
    * @param dlSales
     * @param dirty
     * @throws uk.chromis.basic.BasicException */
    public ShowsEditor(DataLogicSales dlSales, DirtyManager dirty, ShowsFilter filter) throws BasicException {
        
        initComponents();

        this.showsFilter = filter;
        
        m_jTheatre.addActionListener(dirty);
        m_jStartDate.getDocument().addDocumentListener(dirty);
        m_jEndDate.getDocument().addDocumentListener(dirty);
        m_jReportStartDate.getDocument().addDocumentListener(dirty);
        m_jReportEndDate.getDocument().addDocumentListener(dirty);
        
        theatreSentence = dlSales.getTheatresList();

    }
    
    
    /**
     *
     * @param insertid
     */
    public void setInsertId(String insertid) {

        this.showid = insertid;
    }

    /**
     *
     */
    @Override
    public void refresh() {
    }


    public void activate() throws BasicException {

        theatreModel = new ComboBoxValModel(theatreSentence.list());
        m_jTheatre.setModel(theatreModel);

    }


    /**
     *
     */
    @Override
    public void writeValueEOF() {

        showid = null;
        theatreModel.setSelectedKey(this.showsFilter.getSelectedThreatre());
        m_jStartDate.setText(null);
        m_jEndDate.setText(null);
        m_jReportStartDate.setText(null);
        m_jReportEndDate.setText(null);

        m_jTheatre.setEnabled(false);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        m_jReportStartDate.setEnabled(false);
        m_jReportEndDate.setEnabled(false);

        this.recStatus = RecordStatus.EOF;
        
    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {

        showid = UUID.randomUUID().toString();
        theatreModel.setSelectedKey(this.showsFilter.getSelectedThreatre());
        m_jStartDate.setText(null);
        m_jEndDate.setText(null);
        m_jReportStartDate.setText(null);
        m_jReportEndDate.setText(null);

        m_jTheatre.setEnabled(true);
        m_jStartDate.setEnabled(true);
        m_jEndDate.setEnabled(true);
        m_jReportStartDate.setEnabled(true);
        m_jReportEndDate.setEnabled(true);

        this.recStatus = RecordStatus.INSERT;
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {

        Object[] obj = (Object[]) value;

        showid = obj[0];
        theatreModel.setSelectedKey(obj[1]);
        m_jStartDate.setText(Formats.DATE.formatValue(obj[2]));
        m_jEndDate.setText(Formats.DATE.formatValue(obj[3]));
        m_jReportStartDate.setText(Formats.DATE.formatValue(obj[4]));
        m_jReportEndDate.setText(Formats.DATE.formatValue(obj[5]));

        m_jTheatre.setEnabled(true);
        m_jStartDate.setEnabled(true);
        m_jEndDate.setEnabled(true);
        m_jReportStartDate.setEnabled(true);
        m_jReportEndDate.setEnabled(true);
        
        this.recStatus = RecordStatus.UPDATE;

    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        Object[] obj = (Object[]) value;

        showid = obj[0];
        theatreModel.setSelectedKey(obj[1]);
        m_jStartDate.setText(Formats.DATE.formatValue(obj[2]));
        m_jEndDate.setText(Formats.DATE.formatValue(obj[3]));
        m_jReportStartDate.setText(Formats.DATE.formatValue(obj[4]));
        m_jReportEndDate.setText(Formats.DATE.formatValue(obj[5]));

        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        m_jReportStartDate.setEnabled(false);
        m_jReportEndDate.setEnabled(false);
        
        this.recStatus = RecordStatus.DELETE;

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
            showid,
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

        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        m_jReportEndDate = new javax.swing.JTextField();
        m_jReportStartDate = new javax.swing.JTextField();
        m_jEndDate = new javax.swing.JTextField();
        m_jStartDate = new javax.swing.JTextField();
        m_jTheatre = new javax.swing.JComboBox<>();
        m_jbtnStartDate = new javax.swing.JButton();
        m_jbtnEndDate = new javax.swing.JButton();
        m_jbtnReportStartDate = new javax.swing.JButton();
        m_jbtn_ReportEndDate = new javax.swing.JButton();

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.theatre")); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(AppLocal.getIntString("label.productshowstartdate")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(AppLocal.getIntString("label.productshowenddate")); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText(AppLocal.getIntString("label.productshowreportstartdate")); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText(AppLocal.getIntString("label.productshowreportenddate")); // NOI18N

        m_jReportEndDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jReportStartDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jEndDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jStartDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jTheatre.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jTheatre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(m_jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(m_jbtnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(m_jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(m_jReportStartDate)
                                                .addGap(7, 7, 7)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(m_jbtnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(m_jbtnReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jbtn_ReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 82, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jTheatre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtnReportStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(m_jReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jbtn_ReportEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 100, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void m_jbtn_ReportEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtn_ReportEndDateActionPerformed
        // TODO add your handling code here:
        Date date, startDate;
        try {
            date = (Date) Formats.DATE.parseValue(m_jReportEndDate.getText());
        } catch (BasicException e) {
            date = null;
        }
        try {
            startDate = (Date) Formats.DATE.parseValue(m_jReportStartDate.getText());
        } catch (BasicException e) {
            startDate = null;
        }
        JCalendarDialog.setMinDate(startDate);
        JCalendarDialog.setMaxDate(null);                
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jReportEndDate.setText(Formats.DATE.formatValue(date));
        }
    }//GEN-LAST:event_m_jbtn_ReportEndDateActionPerformed

    private void m_jbtnReportStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnReportStartDateActionPerformed
        Date date, endDate;
        try {
            date = (Date) Formats.DATE.parseValue(m_jReportStartDate.getText());
        } catch (BasicException e) {
            date = null;
        }
        try {
            endDate = (Date) Formats.DATE.parseValue(m_jReportEndDate.getText());
        } catch (BasicException e) {
            endDate = null;
        }
        JCalendarDialog.setMinDate(null);
        JCalendarDialog.setMaxDate(endDate);
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jReportStartDate.setText(Formats.DATE.formatValue(date));
        }
    }//GEN-LAST:event_m_jbtnReportStartDateActionPerformed

    private void m_jbtnEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnEndDateActionPerformed
        Date date, startDate;
        try {
            date = (Date) Formats.DATE.parseValue(m_jEndDate.getText());
        } catch (BasicException e) {
            date = null;
        }
        try {
            startDate = (Date) Formats.DATE.parseValue(m_jStartDate.getText());
        } catch (BasicException e) {
            startDate = null;
        }
        JCalendarDialog.setMinDate(startDate);
        JCalendarDialog.setMaxDate(null);        
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jEndDate.setText(Formats.DATE.formatValue(date));
            if (m_jReportEndDate.getText().equals("")) {
                m_jReportEndDate.setText(Formats.DATE.formatValue(date));
            }
        }
    }//GEN-LAST:event_m_jbtnEndDateActionPerformed

    private void m_jbtnStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnStartDateActionPerformed
        Date date, endDate;
        try {
            date = (Date) Formats.DATE.parseValue(m_jStartDate.getText());
        } catch (BasicException e) {
            date = null;
        }
        try {
            endDate = (Date) Formats.DATE.parseValue(m_jEndDate.getText());
        } catch (BasicException e) {
            endDate = null;
        }
        JCalendarDialog.setMinDate(null);
        JCalendarDialog.setMaxDate(endDate);
        date = JCalendarDialog.showCalendar(this, date);
        if (date != null) {
            m_jStartDate.setText(Formats.DATE.formatValue(date));
            if (m_jReportStartDate.getText().equals("")) {
                m_jReportStartDate.setText(Formats.DATE.formatValue(date));
            }
        }
    }//GEN-LAST:event_m_jbtnStartDateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField m_jEndDate;
    private javax.swing.JTextField m_jReportEndDate;
    private javax.swing.JTextField m_jReportStartDate;
    private javax.swing.JTextField m_jStartDate;
    private javax.swing.JComboBox<String> m_jTheatre;
    private javax.swing.JButton m_jbtnEndDate;
    private javax.swing.JButton m_jbtnReportStartDate;
    private javax.swing.JButton m_jbtnStartDate;
    private javax.swing.JButton m_jbtn_ReportEndDate;
    // End of variables declaration//GEN-END:variables


}
