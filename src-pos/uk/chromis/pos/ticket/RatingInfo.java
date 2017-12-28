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
package uk.chromis.pos.ticket;

import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.DataRead;
import uk.chromis.data.loader.IKeyed;
import uk.chromis.data.loader.SerializerRead;

/**
 *
 * @author Adrian
 * @version
 */
public class RatingInfo implements IKeyed {

    private String m_sID;
    private String m_sName;
    private Boolean m_bActive;

    /**
     * Creates new CategoryInfo
     *
    * @param id
    * @param name
    * @param capacityMode
    * @param capacity
    * @param hardLimit
    * @param active
     */
    public RatingInfo(String id, String name, Boolean active ) {
        m_sID = id;
        m_sName = name;
        m_bActive = active;
    }

    @Override
    public Object getKey() {
        return m_sID;
    }

    public void setID(String sID) {
        m_sID = sID;
    }

    public String getID() {
        return m_sID;
    }

    public String getName() {
        return m_sName;
    }

    public void setName(String sName) {
        m_sName = sName;
    }

    public void setActive(Boolean active) {
        m_bActive = active;
    }

    public Boolean getActive() {
        return m_bActive;
    }


    @Override
    public String toString() {
        return m_sName;
    }

    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new RatingInfo(dr.getString(1), dr.getString(2), dr.getBoolean(3));
            }
        };
    }
}
