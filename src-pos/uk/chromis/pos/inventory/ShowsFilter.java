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
import java.util.List;
import uk.chromis.basic.BasicException;
import uk.chromis.data.gui.ComboBoxValModel;
import uk.chromis.data.loader.QBFCompareEnum;
import uk.chromis.data.loader.SentenceList;
import uk.chromis.data.loader.SerializerWrite;
import uk.chromis.data.loader.SerializerWriteString;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.AppView;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.reports.ReportEditorCreator;

/**
 *
 * @author adrianromero
 */
public class ShowsFilter extends javax.swing.JPanel implements ReportEditorCreator {

    private SentenceList theatreSentence;
    private ComboBoxValModel theatreModel;

    /** Creates new form AttributeUseFilter */
    public ShowsFilter() {
        initComponents();
    }

    /**
     *
     * @param app
     */
    @Override
    public void init(AppView app) {

        DataLogicSales dlSales = (DataLogicSales) app.getBean("uk.chromis.pos.forms.DataLogicSales");

        theatreSentence = dlSales.getTheatresList();

    }

    
    public Object getSelectedThreatre() {
        return theatreModel.getSelectedKey();
    }
    
    
    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {

        List b = theatreSentence.list();
        theatreModel = new ComboBoxValModel(b);
        theatreModel.setSelectedFirst();
        m_jTheatre.setModel(theatreModel);
        
        m_jShowDates.setSelected(true);
        
    }

    /**
     *
     * @return
     */
    @Override
    public SerializerWrite getSerializerWrite() {
        return SerializerWriteString.INSTANCE;
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
     * @param l
     */
    public void addActionListener(ActionListener l) {
        m_jTheatre.addActionListener(l);
        m_jShowDates.addActionListener(l);
    }

    /**
     *
     * @param l
     */
    public void removeActionListener(ActionListener l) {
        m_jTheatre.removeActionListener(l);
        m_jShowDates.removeActionListener(l);
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        
        QBFCompareEnum theatreMode;
        QBFCompareEnum dateMode;
        
        java.util.Date today = new java.util.Date();
        java.sql.Date myDate = new java.sql.Date(today.getTime());
        
        Object theatreKey = theatreModel.getSelectedKey();
        Object dateKey = myDate;
        
        theatreMode = ( theatreKey == null ) ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS;
        dateMode = ( m_jShowDates.isSelected() ) ? QBFCompareEnum.COMP_GREATEROREQUALS : QBFCompareEnum.COMP_NONE;
        
        return new Object[]{
            theatreMode, theatreKey,
            dateMode, dateKey
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

        jLabel8 = new javax.swing.JLabel();
        m_jTheatre = new javax.swing.JComboBox();
        m_jShowDates = new eu.hansolo.custom.SteelCheckBox();

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("label.theatre")); // NOI18N

        m_jTheatre.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        m_jShowDates.setText("Only show current or future dates");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_jShowDates, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(m_jTheatre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(m_jTheatre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(m_jShowDates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel8.getAccessibleContext().setAccessibleName(AppLocal.getIntString("label.theatre")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private eu.hansolo.custom.SteelCheckBox m_jShowDates;
    private javax.swing.JComboBox m_jTheatre;
    // End of variables declaration//GEN-END:variables

}

