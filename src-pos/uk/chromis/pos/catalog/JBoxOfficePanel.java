/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.chromis.pos.catalog;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uk.chromis.basic.BasicException;
import uk.chromis.beans.JDateSelectorPanel;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.ticket.ShowSalesInfo;
import uk.chromis.pos.util.ThumbNailBuilder;


/**
 *
 * @author nick
 */
public class JBoxOfficePanel extends JPanel implements ListSelectionListener {

    private Date m_selectedDate;
    private final DataLogicSales m_dlSales;
    private final ThumbNailBuilder m_thumbBuilder;
    private ShowSalesInfo m_currentShow;
    private ShowListModel m_showListModel;
    
    
    public JBoxOfficePanel(DataLogicSales dlSales) {
        this(dlSales, 50);
    }
    
    
    public JBoxOfficePanel(DataLogicSales dlSales, int imageSize) {
        
        initComponents();
        
        m_dlSales = dlSales;
        
        m_thumbBuilder = new ThumbNailBuilder(imageSize, imageSize, "uk/chromis/images/package.png");
        
        jShowList.setCellRenderer(new ShowListCellRenderer());
        
        jShowList.addListSelectionListener(this);
        
        initializeDates();        
        
    }
    
    
    // This is here so the panel renders correctly in Netbeans.
    public JBoxOfficePanel() {
        
        initComponents();
        
        m_dlSales = new DataLogicSales();
        
        m_thumbBuilder = new ThumbNailBuilder(50, 50, "uk/chromis/images/package.png");
        
        jShowList.setCellRenderer(new ShowListCellRenderer());
        
    }
    
    
    private void initializeDates() {
        
        this.jDateSelectorPanel1.addPropertyChangeListener("Date", new JPanelDateChange(this.jDateSelectorPanel1) ); 
        this.jDateSelectorPanel1.setMinDate(new Date());
        this.jDateSelectorPanel1.setDate(new Date());
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ShowSalesInfo showInfo, oldShowInfo;
        if (!e.getValueIsAdjusting()) {
            int i = jShowList.getSelectedIndex();
            oldShowInfo = this.m_currentShow;
            if (i >= 0) {
                showInfo = (ShowSalesInfo) m_showListModel.getElementAt(i);
                this.m_currentShow = showInfo;
                this.firePropertyChange("Show", oldShowInfo, this.m_currentShow);        
            } else {
                this.m_currentShow = null;
                this.firePropertyChange("Show", oldShowInfo, null);        
            }
        }
    }
    

    private class JPanelDateChange implements PropertyChangeListener {
        private final JDateSelectorPanel me;
        public JPanelDateChange(JDateSelectorPanel p) {
            me = p;
        }
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            m_selectedDate = me.getDate();
            showShowsForDate(m_selectedDate);
        }
    }
    
    
    public ShowSalesInfo getSelectedShow() {
        return m_currentShow;
    }
    
    public Date getSelectedDate() {
        return m_selectedDate;
    }
    
    
    private void showShowsForDate(Date showDate) {
        
        List<ShowSalesInfo> showList;
        
        try {
            showList = m_dlSales.getShowsForDate(showDate);
        } catch (BasicException ex) {
            showList = null;
        }
        
        m_showListModel = new ShowListModel(showList);
        jShowList.setModel(m_showListModel);
        if (m_showListModel.getSize() > 0) {
            jShowList.setSelectedIndex(0);
        }
                
    }
    
    
    private class ShowListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
            ShowSalesInfo show = (ShowSalesInfo) value;
            setText(show.getButtonText());
            setIcon(new ImageIcon(m_thumbBuilder.getThumbNail(show.getButtonImage())));
            return this;
        }
    }
    
    
    
    private class ShowListModel extends AbstractListModel {

        private final List<ShowSalesInfo> m_showList;

        public ShowListModel(List<ShowSalesInfo> showList) {
            m_showList = showList;
        }

        @Override
        public int getSize() {
            return m_showList.size();
        }

        @Override
        public Object getElementAt(int i) {
            return m_showList.get(i);
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

        jDateSelectorPanel1 = new uk.chromis.beans.JDateSelectorPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jShowList = new javax.swing.JList<>();

        setLayout(new java.awt.BorderLayout());
        add(jDateSelectorPanel1, java.awt.BorderLayout.PAGE_START);

        jShowList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jShowList);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uk.chromis.beans.JDateSelectorPanel jDateSelectorPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> jShowList;
    // End of variables declaration//GEN-END:variables
}
