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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.Datas;
import uk.chromis.data.model.Column;
import uk.chromis.data.model.Field;
import uk.chromis.data.model.PrimaryKey;
import uk.chromis.data.model.Row;
import uk.chromis.data.model.Table;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.data.user.ListProviderCreator;
import uk.chromis.data.user.SaveProvider;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.panels.JPanelTable2;

/**
 *
 * @author adrianromero
 */
public class ProductShowsPanel extends JPanelTable2 {

    private DataLogicSales m_dlSales;
    
    private ProductShowsEditor editor;
    private ProductShowsFilter filter;

    /**
     *
     */
    @Override
    protected void init() {

        m_dlSales = (DataLogicSales) app.getBean("uk.chromis.pos.forms.DataLogicSales");
        
        filter = new ProductShowsFilter();
        filter.init(app);
        filter.addActionListener(new ReloadActionListener());
        
        row = new Row(
            new Field("ID", Datas.STRING, Formats.STRING),
            new Field("PRODUCTID", Datas.STRING, Formats.STRING),
            new Field("SHOWID", Datas.STRING, Formats.STRING),
            new Field("STARTDATE", Datas.TIMESTAMP, Formats.TIMESTAMP),
            new Field("ENDDATE", Datas.TIMESTAMP, Formats.TIMESTAMP),
            new Field("REPORTSTARTDATE", Datas.TIMESTAMP, Formats.TIMESTAMP),
            new Field("REPORTENDDATE", Datas.TIMESTAMP, Formats.TIMESTAMP),
            new Field("DISTRIBUTIONRATE", Datas.DOUBLE, Formats.DOUBLE)
        );

        Table table = new Table(
                "PRODUCTS_BOXOFFICESHOWS",
                new PrimaryKey("ID"),
                new Column("PRODUCTID"),
                new Column("SHOWID"),
                new Column("STARTDATE"),
                new Column("ENDDATE"),
                new Column("REPORTSTARTDATE"),
                new Column("REPORTENDDATE"),
                new Column("DISTRIBUTIONRATE")
        );


        lpr = new ListProviderCreator(m_dlSales.getProductShowsQBF(), filter);

//        lpr = row.getListProvider(app.getSession(),
//                "SELECT ID, PRODUCTID, SHOWID, STARTDATE, ENDDATE, REPORTSTARTDATE, REPORTENDDATE, DISTRIBUTIONRATE FROM PRODUCTS_BOXOFFICESHOWS WHERE PRODUCTID = ? ", filter);
//        spr = row.getSaveProvider(app.getSession(), table);
        spr = new SaveProvider(
                m_dlSales.getProductShowUpdate(),
                m_dlSales.getProductShowInsert(),
                m_dlSales.getProductShowDelete());


		 try {
			 editor = new ProductShowsEditor(m_dlSales, dirty);
		 } catch (BasicException ex) {
			 Logger.getLogger(ProductShowsPanel.class.getName()).log(Level.SEVERE, null, ex);
		 }
    }

    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
        filter.activate();

        //super.activate();
        startNavigation();
        reload();
    }

    /**
     *
     * @return
     */
    @Override
    public Component getFilter(){
        return filter.getComponent();
    }

    /**
     *
     * @return
     */
    @Override
    public EditorRecord getEditor() {
        return editor;
    }

    private void reload() throws BasicException {
        String prodid = (String) filter.createValue();
        editor.setInsertId(prodid); // must be set before load
        bd.setEditable(prodid != null);
        bd.actionLoad();
    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.ProductShows");
    }

    private class ReloadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reload();
            } catch (BasicException w) {
            }
        }
    }
}