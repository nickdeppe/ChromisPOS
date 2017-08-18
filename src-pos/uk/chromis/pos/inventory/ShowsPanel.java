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

import uk.chromis.pos.ticket.ShowFilter;
import java.awt.Component;
import javax.swing.ListCellRenderer;
import uk.chromis.basic.BasicException;
import uk.chromis.data.gui.ListCellRendererBasic;
import uk.chromis.data.loader.TableDefinition;
import uk.chromis.data.user.EditorRecord;
import uk.chromis.data.user.ListProvider;
import uk.chromis.data.user.ListProviderCreator;
import uk.chromis.data.user.SaveProvider;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.forms.DataLogicSales;
import uk.chromis.pos.panels.JPanelTable;

/**
 *
 * @author adrianromero Created on 1 de marzo de 2007, 22:15
 *
 */
public class ShowsPanel extends JPanelTable {

    private DataLogicSales m_dlSales = null;
    private ShowsEditor jeditor;
    private ShowFilter jShowFilter;
    private TableDefinition tShow;
    private String m_initialFilter = "";

    /**
     * Creates a new instance of ShowsPanel
     */
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
        tShow = m_dlSales.getTableShows();

        jShowFilter = new ShowFilter();
        jShowFilter.init(app);

        // el panel del editor
        jeditor = new ShowsEditor(dirty);

        if (AppConfig.getInstance().getBoolean("display.longnames")) {
            setListWidth(300);
        }
    }

    
    /**
     *
     * @return
     */
    @Override
    public ListProvider getListProvider() {
        return new ListProviderCreator(m_dlSales.getShowQBF() , jShowFilter);
    }
    
    /**
     *
     * @return
     */
    @Override
    public SaveProvider getSaveProvider() {
        return new SaveProvider(m_dlSales.getShowUpdate(), m_dlSales.getShowInsert(), m_dlSales.getShowDelete());      
    }
    
    
    /**
     *
     * @return value
     */
    @Override
    public EditorRecord getEditor() {
        return jeditor;
    }

    /**
     *
     * @return value
     */
    @Override
    public Component getFilter() {
        return jShowFilter.getComponent();
    }
    
    /**
     *
     * @return
     */
    @Override
    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(tShow.getRenderStringBasic(new int[]{1}));
    }
    

    /**
     *
     * @return value
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Shows");
    }

    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {

        jeditor.activate();
        jShowFilter.activate();

        setLoadOnActivation(true);

        super.activate();
    }

}
