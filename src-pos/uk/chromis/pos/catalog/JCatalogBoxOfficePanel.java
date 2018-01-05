/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.chromis.pos.catalog;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.ImageIcon;
import uk.chromis.basic.BasicException;
import uk.chromis.beans.jDateSelectorPanel;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.ticket.ShowFeaturesInfo;
import uk.chromis.pos.ticket.ShowSalesInfo;
import uk.chromis.pos.util.ThumbNailBuilder;


/**
 *
 * @author nick
 */
public class JCatalogBoxOfficePanel extends JPanel {

    private Date m_selectedDate;
    private DataLogicSales m_dlSales;
    private final Set<String> m_showsSet = new HashSet<>();
    private ThumbNailBuilder m_thumbBuilder;
    private ShowSalesInfo currentShowSales;
    
    
    public JCatalogBoxOfficePanel(DataLogicSales dlSales) {
        
        initComponents();
        
        m_dlSales = dlSales;
        
        m_thumbBuilder = new ThumbNailBuilder(300, 200, "uk/chromis/images/package.png");

        initializeDates();        
        
    }
    
    
    private final void initializeDates() {
        
        this.jDateSelectorPanel1.addPropertyChangeListener("Date", new JPanelDateChange(this.jDateSelectorPanel1) ); 
        this.jDateSelectorPanel1.setMinDate(new Date());
        this.jDateSelectorPanel1.setDate(new Date());
        
    }
    

    private class JPanelDateChange implements PropertyChangeListener {
        private final jDateSelectorPanel me;
        public JPanelDateChange(jDateSelectorPanel p) {
            me = p;
        }
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            m_selectedDate = me.getDate();
            showShowsForDate(m_selectedDate);
        }
    }
    
    
    public ShowSalesInfo getSelectedShow() {
        return currentShowSales;
    }
    
    
    private class SelectedShow implements ActionListener {
        private final ShowSalesInfo show;
        public SelectedShow(ShowSalesInfo show) {
            this.show = show;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowSalesInfo oldShowSales = currentShowSales;
            currentShowSales = this.show;
            firePropertyChange("Show", oldShowSales, currentShowSales);
        }
    }
    
    private final void showShowsForDate(Date showDate) {
        List<ShowSalesInfo> showList;
        List<ShowFeaturesInfo> featuresList;
        BufferedImage thumbImage;
        String dateKey = Formats.DATE.formatValue(showDate);
        String buttonText, textTip;
        
        if (!m_showsSet.contains(dateKey)) {
            // Need to load the shows for the given date
            JCatalogTab jCurrTab = new JCatalogTab();
            m_jPanelShows.add(jCurrTab, dateKey);
            m_showsSet.add(dateKey);
        
            try {
                showList = m_dlSales.getShowsForDate(m_selectedDate);
            } catch (BasicException ex) {
                showList = null;
            }
            
            if ( showList != null ) {
                for (ShowSalesInfo show : showList) {
                    thumbImage = null;
                    buttonText = "<html>" + show.getTheatreName();
                    textTip = show.getTheatreName();
                    // Get features list
                    try {
                        featuresList = m_dlSales.getFeaturesForShow(show.getID());
                    } catch (BasicException ex) {
                        featuresList = null;
                    }
                    
                    if (featuresList != null) {
                        // Get the features 
                        for(int i = 0; i < featuresList.size(); i++) {

                            ShowFeaturesInfo feature = featuresList.get(i);
                            buttonText += "<br>" + feature.toString();
                            textTip += ", " + feature.toString();
                            
                            if(thumbImage == null) {
                                thumbImage = feature.getImage();
                            }
                                
                        }
                    }
                    buttonText += "</html>";
                    
                    jCurrTab.addButton(new ImageIcon(m_thumbBuilder.getThumbNailText(thumbImage, buttonText), ""), new SelectedShow(show), textTip, "");
                    
                }
            }
            
        }
        
        CardLayout c1 = (CardLayout) (m_jPanelShows.getLayout());
        c1.show(m_jPanelShows, dateKey);
        
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jPanelShows = new javax.swing.JPanel();
        jDateSelectorPanel1 = new uk.chromis.beans.jDateSelectorPanel();

        m_jPanelShows.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_jPanelShows, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateSelectorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDateSelectorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jPanelShows, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uk.chromis.beans.jDateSelectorPanel jDateSelectorPanel1;
    private javax.swing.JPanel m_jPanelShows;
    // End of variables declaration//GEN-END:variables
}
