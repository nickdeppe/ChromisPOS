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
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.panels.JPanelTable2;
import uk.chromis.pos.forms.DataLogicSales;

/**
 *
 * @author adrianromero
 */
public class BoxOfficeProductSetProductsPanel extends JPanelTable2 {

    private BoxOfficeProductSetProductsEditor editor;
    private BoxOfficeProductSetProductsFilter filter;

    /**
     *
     */
    @Override
    protected void init() {

        DataLogicSales dlSales = (DataLogicSales) app.getBean("uk.chromis.pos.forms.DataLogicSales");
        
        filter = new BoxOfficeProductSetProductsFilter(dlSales);
        filter.init(app);
        filter.addActionListener(new ReloadActionListener());

        row = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field("BOXOFFICEPRODUCTSETID", Datas.STRING, Formats.STRING),
                new Field("PRODUCTID", Datas.STRING, Formats.STRING),
                new Field("SEQUENCE", Datas.INT, Formats.INT, false, false, true),
                new Field(AppLocal.getIntString("label.name"), Datas.STRING, Formats.STRING, true, true, true)
        );

        Table table = new Table(
                "BOXOFFICEPRODUCTSETS_PRODUCTS",
                new PrimaryKey("ID"),
                new Column("BOXOFFICEPRODUCTSETID"),
                new Column("PRODUCTID"),
                new Column("SEQUENCE")
        );

        lpr = row.getListProvider(
                app.getSession(),
                "SELECT "
                        + "PSP.ID, "
                        + "PSP.BOXOFFICEPRODUCTSETID, "
                        + "PSP.PRODUCTID, "
                        + "PSP.SEQUENCE, "
                        + "P.NAME "
                + "FROM "
                        + "BOXOFFICEPRODUCTSETS_PRODUCTS PSP "
                        + "INNER JOIN PRODUCTS P ON PSP.PRODUCTID = P.ID "
                + "WHERE "
                        + "PSP.BOXOFFICEPRODUCTSETID = ? "
                + "ORDER BY "
                        + "PSP.SEQUENCE, "
                        + "P.NAME ",
                filter);
        spr = row.getSaveProvider(app.getSession(), table);

        try {
            editor = new BoxOfficeProductSetProductsEditor(dlSales, dirty, filter);
        } catch (BasicException ex) {
            Logger.getLogger(BoxOfficeProductSetProductsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
        filter.activate();
        editor.activate();

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

        String boxofficeproductsetid = (String) filter.createValue();
        editor.setInsertId(boxofficeproductsetid); // must be set before load
        bd.setEditable(boxofficeproductsetid != null);
        bd.actionLoad();

    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.BoxOfficeProductSetProducts");
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
