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
import javax.swing.ListCellRenderer;
import uk.chromis.basic.BasicException;
import uk.chromis.data.gui.ListCellRendererBasic;
import uk.chromis.data.loader.Datas;
import uk.chromis.data.loader.QBFBuilder;
import uk.chromis.data.loader.SerializerWriteBasic;
import uk.chromis.data.loader.StaticSentence;
import uk.chromis.data.loader.TableDefinition;
import uk.chromis.data.model.Column;
import uk.chromis.data.model.Field;
import uk.chromis.data.model.PrimaryKey;
import uk.chromis.data.model.Row;
import uk.chromis.data.model.Table;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.data.user.ListProvider;
import uk.chromis.data.user.ListProviderCreator;
import uk.chromis.data.user.SaveProvider;
import uk.chromis.format.Formats;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.panels.JPanelTable2;

/**
 *
 * @author 
 */
public class ShowsPanel extends JPanelTable2 {

    private DataLogicSales m_dlSales;
    
    private ShowsEditor editor;
    private ShowsFilter filter;

    private TableDefinition tShows;

    private String m_initialFilter = "";


    public ShowsPanel() {
    }

    public ShowsPanel(String szFilter) {
        // Set initial filter
        m_initialFilter = szFilter;
    }



    /**
     *
     */
    @Override
    protected void init() {

    

        m_dlSales = (DataLogicSales) app.getBean("uk.chromis.pos.forms.DataLogicSales");
        
        filter = new ShowsFilter();
        filter.init(app);
        filter.addActionListener(new ShowsPanel.ReloadActionListener());


        row = new Row(
            new Field("ID", Datas.STRING, Formats.STRING),
            new Field("THEATREID", Datas.STRING, Formats.STRING, false, true, true),
            new Field("STARTDATE", Datas.DATE, Formats.DATE, true, true, true),
            new Field("ENDDATE", Datas.DATE, Formats.DATE, true, false, false),
            new Field("REPORTSTARTDATE", Datas.DATE, Formats.DATE),
            new Field("REPORTSTARTDATE", Datas.DATE, Formats.DATE),
            new Field("THEATRENAME", Datas.STRING, Formats.STRING, true, false, false)
        );
        

        Table table = new Table(
                "SHOWS",
                new PrimaryKey("ID"),
                new Column("THEATREID"),
                new Column("STARTDATE"),
                new Column("ENDDATE"),
                new Column("REPORTSTARTDATE"),
                new Column("REPORTENDDATE")
        );
        
        lpr = new ListProviderCreator(
            new StaticSentence(app.getSession(),
                new QBFBuilder(
                    "SELECT "
                    + "S.ID, "
                    + "S.THEATREID, "
                    + "S.STARTDATE, "
                    + "S.ENDDATE, "
                    + "S.REPORTSTARTDATE, "
                    + "S.REPORTENDDATE, "
                    + "T.NAME AS THEATRENAME "
                    + "FROM SHOWS S "
                    + "INNER JOIN THEATRES T ON S.THEATREID = T.ID "
                    + "WHERE ?(QBF_FILTER) "
                    + "ORDER BY S.STARTDATE, S.ENDDATE, T.NAME",
                    new String[] {
                        "S.THEATREID",
                        "S.ENDDATE"
                    },
                    true
                ),
                new SerializerWriteBasic(
                    new Datas[]{
                        Datas.OBJECT, Datas.STRING,
                        Datas.OBJECT, Datas.DATE
                    }
                ),
                row.getSerializerRead()
            ),
            filter
        );
        
        spr = row.getSaveProvider(app.getSession(), table);

        try {        
            editor = new ShowsEditor(m_dlSales, dirty, filter);
        } catch (BasicException ex) {
            Logger.getLogger(ShowsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (AppConfig.getInstance().getBoolean("display.longnames")) { 
            setListWidth(350);
        } else {
            setListWidth(300);
        }


    }


    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
        
        filter.addActionListener(new ReloadActionListener());
        
        editor.activate();
        filter.activate();

        setLoadOnActivation(true);
        
        super.activate();

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

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Shows");
    }
    
    
    private class ReloadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                bd.actionLoad();
            } catch (BasicException w) {
            }
        }
    }
    
    
}