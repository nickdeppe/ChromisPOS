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
 * @author adrianromero
 */
public class ShowSchedulePanel extends JPanelTable {

    private DataLogicSales m_dlSales;
    
    private ShowScheduleEditor editor;
    private ShowScheduleFilter filter;

    private TableDefinition tShowSchedule;

    private String m_initialFilter = "";


    public ShowSchedulePanel() {
    }

    public ShowSchedulePanel(String szFilter) {
        // Set initial filter
        m_initialFilter = szFilter;
    }



    /**
     *
     */
    @Override
    protected void init() {

        m_dlSales = (DataLogicSales) app.getBean("uk.chromis.pos.forms.DataLogicSales");
        tShowSchedule = m_dlSales.getTableShowSchedule();
        
        filter = new ShowScheduleFilter();
        filter.init(app);
       

        try {
            editor = new ShowScheduleEditor(m_dlSales, dirty);
        } catch (BasicException ex) {
            Logger.getLogger(ShowSchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }


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
        return new ListProviderCreator(m_dlSales.getShowScheduleQBF(), filter);
    }

    /**
     *
     * @return
     */
    @Override
    public SaveProvider getSaveProvider() {
        return new SaveProvider(m_dlSales.getShowScheduleUpdate(), m_dlSales.getShowScheduleInsert(), m_dlSales.getShowScheduleDelete());
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
    public ListCellRenderer getListCellRenderer() {
        return new ListCellRendererBasic(tShowSchedule.getRenderStringBasic(new int[]{3,4}));
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
        return AppLocal.getIntString("Menu.ShowSchedule");
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