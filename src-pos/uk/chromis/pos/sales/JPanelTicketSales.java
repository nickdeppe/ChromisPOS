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
package uk.chromis.pos.sales;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import uk.chromis.basic.BasicException;
import uk.chromis.pos.catalog.CatalogSelector;
import uk.chromis.pos.catalog.JBoxOfficeDialog;
import uk.chromis.pos.catalog.JCatalog;
import uk.chromis.pos.catalog.JCatalogBoxOffice;
import uk.chromis.pos.catalog.JCatalogFull;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppView;
import uk.chromis.pos.ticket.ProductInfoExt;
import uk.chromis.pos.ticket.ShowSalesInfo;

/**
 *
 *   
 */
public class JPanelTicketSales extends JPanelTicket {

    private CatalogSelector m_cat;

    /**
     * Creates a new instance of JPanelTicketSales
     */
    public JPanelTicketSales() {
    }

    /**
     *
     * @param app
     */
    @Override
    public void init(AppView app) {
        super.init(app);
        m_ticketlines.addListSelectionListener(new CatalogSelectionListener());
    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    protected Component getSouthComponent() {
        
        if (AppConfig.getInstance().getProperty("machine.ticketsbag").equals("boxoffice")) {
            m_cat = new JCatalogBoxOffice(dlSales,
                    "true".equals(m_jbtnconfig.getProperty("pricevisible")),
                    "true".equals(m_jbtnconfig.getProperty("taxesincluded")),
                    Integer.parseInt(m_jbtnconfig.getProperty("img-width", "64")),
                    Integer.parseInt(m_jbtnconfig.getProperty("img-height", "54")),
                    Integer.parseInt(m_jbtnconfig.getProperty("boxoffice-show-img-size", "50"))
            );
        } else if (AppConfig.getInstance().getBoolean("sales.newscreen")){
            m_cat = new JCatalogFull(dlSales,
                    "true".equals(m_jbtnconfig.getProperty("pricevisible")),
                    "true".equals(m_jbtnconfig.getProperty("taxesincluded")),
                    Integer.parseInt(m_jbtnconfig.getProperty("img-width", "64")),
                    Integer.parseInt(m_jbtnconfig.getProperty("img-height", "54")));
        } else {
        m_cat = new JCatalog(dlSales,
                "true".equals(m_jbtnconfig.getProperty("pricevisible")),
                "true".equals(m_jbtnconfig.getProperty("taxesincluded")),
                Integer.parseInt(m_jbtnconfig.getProperty("img-width", "64")),
                Integer.parseInt(m_jbtnconfig.getProperty("img-height", "54")));
        }

        //   Integer.parseInt(m_jbtnconfig.getProperty("img-width", "32")),
        //   Integer.parseInt(m_jbtnconfig.getProperty("img-height", "32")));
        m_cat.addActionListener(new CatalogListener());
        m_cat.getComponent().setPreferredSize(new Dimension(
                0,
                Integer.parseInt(m_jbtnconfig.getProperty("cat-height", "245"))));
        return m_cat.getComponent();
    }

    /**
     *
     */
    @Override
    protected void resetSouthComponent() {
        m_cat.showCatalogPanel(null);
    }

    /**
     *
     * @return
     */
    @Override
    protected JTicketsBag getJTicketsBag() {
        return JTicketsBag.createTicketsBag(AppConfig.getInstance().getProperty("machine.ticketsbag"), m_App, this);
    }

    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
        super.activate();
        m_cat.loadCatalog();
    }

    @Override
    public void reLoadCatalog() {
        try {
            m_cat.loadCatalog();
        } catch (BasicException ex) {
        }

    }
    
    
    public void finishTicket() {
        m_cat.postSave();
    }
    
    private class CatalogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductInfoExt prod = (ProductInfoExt) e.getSource();
            ShowSalesInfo show = prod.getShowSalesInfo();
            Date showDate = prod.getShowDate();
            if (prod.getIsBoxOffice() && ( show == null || showDate == null )) {
                // It's a box office product and no show is selected
                if (JBoxOfficeDialog.showDialog(m_ticketlines, dlSales) ) {
                    buttonTransition(prod, JBoxOfficeDialog.getSelectedShow(), JBoxOfficeDialog.getSelectedShowDate());
                }
            } else if ( prod.getIsBoxOffice() && show != null && showDate != null) {
                buttonTransition(prod, show, showDate);            
            } else {
                buttonTransition(prod);
            }
        }
    }

    private class CatalogSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (!e.getValueIsAdjusting()) {
                int i = m_ticketlines.getSelectedIndex();

                if (i >= 0) {
                    // Look for the first non auxiliar product.
                    while (i >= 0 && m_oTicket.getLine(i).isProductCom()) {
                        i--;
                    }

                    // Show the accurate catalog panel...
                    if (i >= 0) {
                        m_cat.showCatalogPanel(m_oTicket.getLine(i).getProductID());
                    } else {
                        m_cat.showCatalogPanel(null);
                    }
                }
            }
        }
    }
}
