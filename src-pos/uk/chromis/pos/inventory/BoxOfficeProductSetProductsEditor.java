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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSpinner;
import uk.chromis.basic.BasicException;
import uk.chromis.data.gui.ComboBoxValModel;
import uk.chromis.data.user.DirtyManager;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.ticket.ProductInfoExt;

/**
 *
 * @author adrian
 */
public class BoxOfficeProductSetProductsEditor extends javax.swing.JPanel implements EditorRecord {
    
    private Object boxofficeproductsetid;
    private Object boxofficeproductsetproductid;
    private ComboBoxValModel<ProductInfoExt> productModel;
    private Object productKey;
    private Object boxOfficeProductSetKey;
    private final BoxOfficeProductSetProductsFilter boxOfficeProductSetProductsFilter;
    private final DataLogicSales m_dlSales;

    /** Creates new form AttributesValuesEditor
     * @param dlSales
     * @param dirty
     * @param filter */
    public BoxOfficeProductSetProductsEditor(DataLogicSales dlSales, DirtyManager dirty, BoxOfficeProductSetProductsFilter filter) throws BasicException {
        
        initComponents();
        
        m_dlSales = dlSales;

        m_jProduct.addActionListener(dirty);
        m_jSequence.addChangeListener(dirty);
        
        this.boxOfficeProductSetProductsFilter = filter;
        
    }

    /**
     *
     * @param insertid
     */
    public void setInsertId(String insertid) {
        this.boxofficeproductsetid = insertid;
    }

    /**
     *
     */
    @Override
    public void refresh() {
    }


    public void activate() throws BasicException {

        productModel = new ComboBoxValModel<ProductInfoExt>(m_dlSales.getAllBoxOfficeProducts());
        m_jProduct.setModel(productModel);

    }


    /**
     *
     */
    @Override
    public void writeValueEOF() {

        boxofficeproductsetproductid = null;
        boxOfficeProductSetKey = this.boxOfficeProductSetProductsFilter.getBoxOfficeProductSetKey();
        productModel.setSelectedKey(null);
        m_jSequence.setValue(1);

        m_jProduct.setEnabled(false);
        m_jSequence.setEnabled(false);

    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {

        boxofficeproductsetproductid = UUID.randomUUID().toString();
        boxOfficeProductSetKey = this.boxOfficeProductSetProductsFilter.getBoxOfficeProductSetKey();
        productModel.setSelectedKey(productKey);
        try {
            m_jSequence.setValue(m_dlSales.getNextShowFeatureSequence(boxOfficeProductSetKey.toString()));
        } catch (BasicException ex) {
            m_jSequence.setValue(1);
        }
 
        m_jProduct.setEnabled(true);
        m_jSequence.setEnabled(true);
 
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {

        Object[] obj = (Object[]) value;

        boxofficeproductsetproductid = obj[0];
        boxOfficeProductSetKey = obj[1];
        productModel.setSelectedKey(obj[2]);
        m_jSequence.setValue(obj[3]);

        m_jProduct.setEnabled(true);
        m_jSequence.setEnabled(true);
        
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        Object[] obj = (Object[]) value;

        boxofficeproductsetproductid = obj[0];
        boxOfficeProductSetKey = obj[1];
        productModel.setSelectedKey(obj[2]);
        m_jSequence.setValue(obj[3]);
        
        m_jProduct.setEnabled(false);
        m_jSequence.setEnabled(false);

    }


    public void setProductKey( Object key ) {
        productKey = key;
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
        Object productKey = productModel.getSelectedKey();
        String productID = Formats.STRING.formatValue(productKey);
        return new Object[] {
            boxofficeproductsetproductid,
            boxOfficeProductSetKey,
            Formats.STRING.formatValue(productModel.getSelectedKey()),
            Formats.INT.parseValue(m_jSequence.getValue().toString()),
            Formats.STRING.formatValue(productModel.getSelectedText())
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
        m_jProduct = new javax.swing.JComboBox<>();
        m_jSequence = new javax.swing.JSpinner();

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.product")); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(AppLocal.getIntString("label.sequence")); // NOI18N

        m_jProduct.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        m_jSequence.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        m_jSequence.setModel(new javax.swing.SpinnerNumberModel(1, 1, 2147483647, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jSequence, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 276, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jProduct, 0, 386, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(m_jSequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addContainerGap(196, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<ProductInfoExt> m_jProduct;
    private javax.swing.JSpinner m_jSequence;
    // End of variables declaration//GEN-END:variables


}
