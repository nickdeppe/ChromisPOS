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
import uk.chromis.format.Formats;

/**
 *
 * @author adrianromero Created on 21 de marzo de 2007, 21:28
 *
 */
public class ShowScheduleDistributionInfo implements IKeyed {

    protected String m_ID;
    protected String m_sScheduleID;
    protected String m_sName;
    protected Double m_dRate;

    /**
     * Creates a new instance of ProductShowInfoList
     */
    public ShowScheduleDistributionInfo() {
        m_ID = null;
        m_sScheduleID = null;
        m_sName = null;
        m_dRate = null;
    }

    public ShowScheduleDistributionInfo( String id, String scheduleID, String name, Double rate ) {
        m_ID = id;
        m_sScheduleID = scheduleID;
        m_sName = name;
        m_dRate = rate;
    }


    @Override
    public Object getKey() {
        return m_ID;
    }

    public final String getID() {
        return m_ID;
    }

    public final void setID(String id) {
        m_ID = id;
    }

    public final String getScheduleID() {
        return m_sScheduleID;
    }

    public final void setScheduleID(String id) {
        m_sScheduleID = id;
    }
    

    public final String getName() {
        return m_sName;
    }

    public final void setName(String name) {
        m_sName = name;
    }

    public final Double getRate() {
        return m_dRate;
    }
    
    public final void setRate(Double rate) {
        m_dRate = rate;
    }
    

    @Override
    public final String toString() {
        return m_sName;
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowScheduleDistributionInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getDouble(4) );
            }
        };
    }


}
