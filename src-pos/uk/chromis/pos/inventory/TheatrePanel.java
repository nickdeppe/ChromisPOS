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

/**
 *
 * @author adrian
 */
public class TheatrePanel extends JPanelTable2 {
    
    private EditorRecord editor;

    /** Creates a new instance of JPanelCategories */
    public TheatrePanel() {        
    }

    /**
     *
     */
    @Override
    protected void init() {          
        
        row = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("Label.Name"), Datas.STRING, Formats.STRING, true, true, true),
                new Field("CAPACITYMODE", Datas.STRING, Formats.STRING),
                new Field("CAPACITY", Datas.INT, Formats.INT),
                new Field("HARDLIMIT", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ACTIVE", Datas.BOOLEAN, Formats.BOOLEAN)
        );
        
        Table table = new Table(
                "THEATRES",
                new PrimaryKey("ID"),
                new Column("NAME"),
                new Column("CAPACITYMODE"),
                new Column("CAPACITY"),
                new Column("HARDLIMIT"),
                new Column("ACTIVE")
        );
        
        lpr = row.getListProvider(app.getSession(), table);
        spr = row.getSaveProvider(app.getSession(), table);        
        
        editor = new TheatreEditor(dirty);    
    }

    /**
     *
     * @return
     */
    @Override
    public EditorRecord getEditor() {
        return editor;
    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Theatres");
    }        
}
