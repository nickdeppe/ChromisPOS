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

package uk.chromis.data.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import uk.chromis.data.loader.IKeyGetter;
import uk.chromis.data.loader.KeyGetterBuilder;

/**
 *
 * @author  adrian
 */
public class ListValModel<E> extends AbstractListModel<E> implements ListModel<E> {  
   
    private List<E> m_aData;
    private IKeyGetter m_keygetter;
    
    /** Creates a new instance of ComboBoxValModel
     * @param aData
     * @param keygetter */
    public ListValModel(List<E> aData, IKeyGetter keygetter) {
        m_aData = aData;
        m_keygetter = keygetter;
    }

    /**
     *
     * @param aData
     */
    public ListValModel(List<E> aData) {
        this(aData, KeyGetterBuilder.INSTANCE);
    }

    /**
     *
     * @param keygetter
     */
    public ListValModel(IKeyGetter keygetter) {
        this(new ArrayList<E>(), keygetter);
    }

    /**
     *
     */
    public ListValModel() {
        this(new ArrayList<E>(), KeyGetterBuilder.INSTANCE);
    }
    
    /**
     *
     * @param c
     */
    public void add(E c) {
        m_aData.add(c);
    }

    /**
     *
     * @param c
     */
    public void del(E c) {
        m_aData.remove(c);
    }

    /**
     *
     * @param index
     * @param c
     */
    public void add(int index, E c) {
        m_aData.add(index, c);
    }
    
    /**
     *
     * @param aData
     */
    public void refresh(List<E> aData) {
        m_aData = aData;
    }
  
    /**
     *
     * @param aKey
     * @return
     */
    public Object getElementByKey(Object aKey) {
        if (aKey != null) {
            Iterator<E> it = m_aData.iterator();
            while (it.hasNext()) {
                E value = it.next();
                if (aKey.equals(m_keygetter.getKey(value))) {
                    return value;
                }
            }           
        }
        return null;
    }
    
    @Override
    public E getElementAt(int index) {
        return m_aData.get(index);
    }
    
    @Override
    public int getSize() {
        return m_aData.size();
    }
    
}
