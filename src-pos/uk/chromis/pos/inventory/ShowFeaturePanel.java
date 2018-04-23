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
public class ShowFeaturePanel extends JPanelTable2 {

    private ShowFeatureEditor editor;
    private ShowFeatureFilter filter;

    /**
     *
     */
    @Override
    protected void init() {

        DataLogicSales dlSales = (DataLogicSales) app.getBean("uk.chromis.pos.forms.DataLogicSales");
        
        filter = new ShowFeatureFilter(dlSales);
        filter.init(app);
        filter.addActionListener(new ReloadActionListener());

        row = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field("SHOWID", Datas.STRING, Formats.STRING),
                new Field("FEATUREID", Datas.STRING, Formats.STRING),
                new Field("SEQUENCE", Datas.INT, Formats.INT, false, false, true),
                new Field("STARTTIME", Datas.TIME, Formats.TIME, true, false, false),
                new Field("PRINTTICKET", Datas.BOOLEAN, Formats.BOOLEAN, false, false, false),
                new Field("PRINTREPORT", Datas.BOOLEAN, Formats.BOOLEAN, false, false, false),
                new Field(AppLocal.getIntString("label.name"), Datas.STRING, Formats.STRING, true, true, true)
        );

        Table table = new Table(
                "SHOWFEATURES",
                new PrimaryKey("ID"),
                new Column("SHOWID"),
                new Column("FEATUREID"),
                new Column("SEQUENCE"),
                new Column("STARTTIME"),
                new Column("PRINTTICKET"),
                new Column("PRINTREPORT")
        );

        lpr = row.getListProvider(
                app.getSession(),
                "SELECT "
                        + "SF.ID, "
                        + "SF.SHOWID, "
                        + "SF.FEATUREID, "
                        + "SF.SEQUENCE, "
                        + "SF.STARTTIME, "
                        + "SF.PRINTTICKET, "
                        + "SF.PRINTREPORT, "
                        + "F.NAME "
                + "FROM "
                        + "SHOWFEATURES SF "
                        + "INNER JOIN FEATURES F ON SF.FEATUREID = F.ID "
                + "WHERE "
                        + "SF.SHOWID = ? "
                + "ORDER BY "
                        + "SF.SEQUENCE, "
                        + "F.NAME ",
                filter);
        spr = row.getSaveProvider(app.getSession(), table);

        editor = new ShowFeatureEditor(dlSales, dirty, filter);
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

        String showid = (String) filter.createValue();
        editor.setInsertId(showid); // must be set before load
        bd.setEditable(showid != null);
        bd.actionLoad();

    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.ShowFeatures");
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
