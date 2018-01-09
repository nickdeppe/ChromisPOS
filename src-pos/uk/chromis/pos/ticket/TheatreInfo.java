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
public class TheatreInfo implements IKeyed {

    private String m_sID;
    private String m_sName;
    private String m_sExtraDescription;
    private String m_sCapacityMode;
    private Integer m_iCapacity;
    private Boolean m_bHardLimit;
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
    public TheatreInfo(String id, String name, String extraDescription, String capacityMode, Integer capacity, Boolean hardLimit, Boolean active ) {
        m_sID = id;
        m_sName = name;
        m_sExtraDescription = extraDescription;
        m_sCapacityMode = capacityMode;
        m_iCapacity = capacity;
        m_bHardLimit = hardLimit;
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

    public String getExtraDescription() {
        return m_sExtraDescription;
    }
    
    public void setExtraDescription(String extraDescription) {
        m_sExtraDescription = extraDescription;
    }
    
    public String getCapacityMode() {
        return m_sCapacityMode;
    }

    public void setCapacityMode(String capacityMode) {
        m_sCapacityMode = capacityMode;
    }


    public Integer getCapacity() {
        return m_iCapacity;
    }

    public void setCapacity(Integer capacity) {
        m_iCapacity = capacity;
    }



    public void setHardLimit(Boolean hardLimit) {
        m_bHardLimit = hardLimit;
    }

    public Boolean getHardLimit() {
        return m_bHardLimit;
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
                return new TheatreInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getInt(5), dr.getBoolean(6), dr.getBoolean(7));
            }
        };
    }
}
