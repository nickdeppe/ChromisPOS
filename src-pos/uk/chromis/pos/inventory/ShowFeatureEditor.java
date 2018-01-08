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
import javax.swing.JSpinner;
import uk.chromis.basic.BasicException;
import uk.chromis.data.gui.ComboBoxValModel;
import uk.chromis.data.loader.SentenceList;
import uk.chromis.data.user.DirtyManager;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.DataLogicSales;

/**
 *
 * @author adrian
 */
public class ShowFeatureEditor extends javax.swing.JPanel implements EditorRecord {
    
    private Object showfeatureid;
    private final SentenceList featureSentence;
    private ComboBoxValModel featureModel;
    private Object featureKey;
    private Object showKey;
    private final ShowFeatureFilter showFeatureFilter;
    private final DataLogicSales m_dlSales;

    /** Creates new form AttributesValuesEditor
     * @param dlSales
     * @param dirty
     * @param filter */
    public ShowFeatureEditor(DataLogicSales dlSales, DirtyManager dirty, ShowFeatureFilter filter) {
        
        initComponents();
        
        m_dlSales = dlSales;

        m_jFeature.addActionListener(dirty);
        m_jSequence.addChangeListener(dirty);
        m_jStartTime.addChangeListener(dirty);
        

//        featureSentence = new StaticSentence(
//                app.getSession(), 
//                "SELECT ID, NAME, IMAGE, RUNTIME, RATINGID, ACTIVE FROM FEATURES WHERE ACTIVE = TRUE ORDER BY NAME", 
//                null, 
//                new SerializerRead() {
//                    @Override
//                    public Object readValues(DataRead dr) throws BasicException {
//                        return new FeatureInfo(dr.getString(1), dr.getString(2), ImageUtils.readImage(dr.getBytes(3)), dr.getInt(4), dr.getString(5), dr.getBoolean(6));
//                    }
//                }
//        );        
        
        
        featureSentence = dlSales.getFeaturesList();

        String appFormatTime = AppConfig.getInstance().getProperty("format.time");
        String timeFormat = ( appFormatTime == null || appFormatTime.equals("") ) ? "hh:mm a" : appFormatTime ;
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(m_jStartTime, timeFormat );        
        m_jStartTime.setEditor(timeEditor);
        
        this.showFeatureFilter = filter;
        
    }

    /**
     *
     * @param insertid
     */
    public void setInsertId(String insertid) {
        this.showfeatureid = insertid;
    }

    /**
     *
     */
    @Override
    public void refresh() {
    }


    public void activate() throws BasicException {

        featureModel = new ComboBoxValModel(featureSentence.list());
        m_jFeature.setModel(featureModel);

    }


    /**
     *
     */
    @Override
    public void writeValueEOF() {

        showfeatureid = null;
        showKey = this.showFeatureFilter.getShowKey();
        featureModel.setSelectedKey(null);
        m_jSequence.setValue(1);
        m_jStartTime.setValue(new Date());

        m_jFeature.setEnabled(false);
        m_jSequence.setEnabled(false);
        m_jStartTime.setEnabled(false);

    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {

        showfeatureid = UUID.randomUUID().toString();
        showKey = this.showFeatureFilter.getShowKey();
        featureModel.setSelectedKey(featureKey);
        try {
            m_jSequence.setValue(m_dlSales.getNextShowFeatureSequence(showKey.toString()));
        } catch (BasicException ex) {
            m_jSequence.setValue(1);
        }
        m_jStartTime.setValue(new Date());

        m_jFeature.setEnabled(true);
        m_jSequence.setEnabled(true);
        m_jStartTime.setEnabled(true);

    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {

        Object[] obj = (Object[]) value;

        showfeatureid = obj[0];
        showKey = obj[1];
        featureModel.setSelectedKey(obj[2]);
        m_jSequence.setValue(obj[3]);
        try {
            Date getDate = (Date)Formats.TIMEHOURMINAMPM.parseValue(obj[4].toString());
            m_jStartTime.setValue(getDate);
        } catch (BasicException ex) {
            
        }

        m_jFeature.setEnabled(true);
        m_jSequence.setEnabled(true);
        m_jStartTime.setEnabled(true);
        
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        Object[] obj = (Object[]) value;

        showfeatureid = obj[0];
        showKey = obj[1];
        featureModel.setSelectedKey(obj[2]);
        m_jSequence.setValue(obj[3]);
        try {
            Date getDate = (Date)Formats.TIMEHOURMINAMPM.parseValue(obj[4].toString());
            m_jStartTime.setValue(getDate);
        } catch (BasicException ex) {
            
        }
        
        m_jFeature.setEnabled(false);
        m_jSequence.setEnabled(false);
        m_jStartTime.setEnabled(false);
    }


    public void setFeatureKey( Object key ) {
        featureKey = key;
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
            showfeatureid,
            showKey,
            Formats.STRING.formatValue(featureModel.getSelectedKey()),
            Formats.INT.parseValue(m_jSequence.getValue().toString()),
            Formats.TIMEHOURMINAMPM.formatValue(m_jStartTime.getValue()),
            Formats.STRING.formatValue(featureModel.getSelectedText())
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
        m_jFeature = new javax.swing.JComboBox<>();
        m_jSequence = new javax.swing.JSpinner();
        m_jStartTime = new javax.swing.JSpinner( new javax.swing.SpinnerDateModel() );

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.feature")); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(AppLocal.getIntString("label.sequence")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(AppLocal.getIntString("label.starttime")); // NOI18N

        m_jFeature.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        m_jSequence.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        m_jSequence.setModel(new javax.swing.SpinnerNumberModel(1, 1, 2147483647, 1));

        m_jStartTime.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        m_jStartTime.setModel(new javax.swing.SpinnerDateModel());
        m_jStartTime.setMinimumSize(new java.awt.Dimension(211, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_jStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jFeature, 0, 386, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jFeature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_jSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jStartTime, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 148, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> m_jFeature;
    private javax.swing.JSpinner m_jSequence;
    private javax.swing.JSpinner m_jStartTime;
    // End of variables declaration//GEN-END:variables


}
