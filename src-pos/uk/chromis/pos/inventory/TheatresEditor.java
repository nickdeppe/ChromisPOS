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

import javax.swing.*;
import java.awt.Component;
import java.util.UUID;
import uk.chromis.basic.BasicException;
import uk.chromis.data.gui.ComboBoxValModel;
import uk.chromis.data.user.DirtyManager;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppLocal;

/**
 *
 * @author  adrian
 */
public final class TheatresEditor extends javax.swing.JPanel implements EditorRecord {

    private Object id;

	 private ComboBoxValModel capacityModeModel;
        
    /** Creates new form AttributesEditor
     * @param dirty */
    public TheatresEditor(DirtyManager dirty) {
        initComponents();
        
        m_jName.getDocument().addDocumentListener(dirty);
        m_jCapacity.addChangeListener(dirty);
        try {
            JFormattedTextField tf = ((JSpinner.DefaultEditor)m_jCapacity.getEditor()).getTextField();
//            tf.addActionListener(dirty);
            tf.getDocument().addDocumentListener(dirty);
        } catch (Exception e) {
            
        }
        
        m_jCapacityMode.addActionListener(dirty);
        m_jHardLimit.addActionListener(dirty);
        m_jActive.addActionListener(dirty);

        capacityModeModel = new ComboBoxValModel();
        capacityModeModel.add(CapacityType.BY_TICKETS);
        capacityModeModel.add(CapacityType.BY_TRANSACTIONS);

        m_jCapacityMode.setModel(capacityModeModel);
        
        writeValueEOF();
    }

    /**
     *
     */
    @Override
    public void writeValueEOF() {
        
        id = null;
        
        m_jName.setText(null);
        m_jCapacity.setValue(0);
		  capacityModeModel.setSelectedKey(CapacityType.BY_TICKETS);
        m_jHardLimit.setSelected(false);
        m_jActive.setSelected(false);
        
        m_jName.setEnabled(false);
        m_jCapacityMode.setEnabled(false);
        m_jCapacity.setEnabled(false);
        m_jHardLimit.setEnabled(false);
        m_jActive.setEnabled(false);
    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {
        id = UUID.randomUUID().toString();
        m_jName.setText(null);
        m_jCapacity.setValue(0);
		  capacityModeModel.setSelectedKey(CapacityType.BY_TICKETS);
        m_jHardLimit.setSelected(false);
        m_jActive.setSelected(true);
        
        m_jName.setEnabled(true);
        m_jCapacityMode.setEnabled(true);
        m_jCapacity.setEnabled(true);
        m_jHardLimit.setEnabled(true);
        m_jActive.setEnabled(true);
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        Object[] theatre = (Object[]) value;
        id = theatre[0];
        m_jName.setText(Formats.STRING.formatValue(theatre[1]));
		  capacityModeModel.setSelectedKey(theatre[2]);
        m_jCapacity.setValue(Formats.INT.formatValue(theatre[3]));
        m_jHardLimit.setSelected( (Boolean) theatre[4] );
        m_jActive.setSelected( (Boolean) theatre[5] );
        
        m_jName.setEnabled(false);
        m_jCapacityMode.setEnabled(false);
        m_jCapacity.setEnabled(false);
        m_jHardLimit.setEnabled(false);
        m_jActive.setEnabled(false);
    }    

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {

        Object[] theatre = (Object[]) value;
        id = theatre[0];
        m_jName.setText(Formats.STRING.formatValue(theatre[1]));
		  capacityModeModel.setSelectedKey(theatre[2]);
        m_jCapacity.setValue(theatre[3]);
        m_jHardLimit.setSelected( (Boolean) theatre[4] );
        m_jActive.setSelected( (Boolean) theatre[5] );
        
        m_jName.setEnabled(true);
        m_jCapacityMode.setEnabled(true);
        m_jCapacity.setEnabled(true);
        m_jHardLimit.setEnabled(true);
        m_jActive.setEnabled(true);
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        
        Object[] theatre = new Object[6];

        theatre[0] = id;
        theatre[1] = m_jName.getText();
        theatre[2] = capacityModeModel.getSelectedKey();
        theatre[3] = m_jCapacity.getValue();
        theatre[4] = m_jHardLimit.isSelected();
        theatre[5] = m_jActive.isSelected();

        return theatre;
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
     */
    @Override
    public void refresh() {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      jLabel2 = new javax.swing.JLabel();
      m_jName = new javax.swing.JTextField();
      jLabel3 = new javax.swing.JLabel();
      m_jCapacity = new javax.swing.JSpinner();
      jLabel4 = new javax.swing.JLabel();
      m_jCapacityMode = new javax.swing.JComboBox<>();
      m_jHardLimit = new eu.hansolo.custom.SteelCheckBox();
      m_jActive = new eu.hansolo.custom.SteelCheckBox();

      jLabel1.setText("jLabel1");

      jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
      jLabel2.setText(AppLocal.getIntString("Label.Name")); // NOI18N

      m_jName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

      jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
      jLabel3.setText(AppLocal.getIntString("Label.Capacity")); // NOI18N

      jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
      jLabel4.setText(AppLocal.getIntString("Label.CapacityMode")); // NOI18N

      m_jCapacityMode.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

      m_jHardLimit.setText(AppLocal.getIntString("Label.HardLimit")); // NOI18N

      m_jActive.setText(AppLocal.getIntString("Label.Active")); // NOI18N

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabel2)
               .addComponent(jLabel4)
               .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(m_jHardLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addGroup(layout.createSequentialGroup()
                        .addComponent(m_jCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134))
                     .addComponent(m_jCapacityMode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addComponent(m_jActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(m_jName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(m_jCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(m_jCapacityMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(m_jHardLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(m_jActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(100, Short.MAX_VALUE))
      );
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private eu.hansolo.custom.SteelCheckBox m_jActive;
   private javax.swing.JSpinner m_jCapacity;
   private javax.swing.JComboBox<String> m_jCapacityMode;
   private eu.hansolo.custom.SteelCheckBox m_jHardLimit;
   private javax.swing.JTextField m_jName;
   // End of variables declaration//GEN-END:variables

}
