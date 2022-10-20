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

import java.util.Date;
import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.DataRead;
import uk.chromis.data.loader.IKeyed;
import uk.chromis.data.loader.SerializerRead;
import uk.chromis.format.Formats;

/**
 *
 * @author Adrian
 * @version
 */
public class ShowTheatreInfo implements IKeyed {

    private String m_sID;
    private String m_sTheatreName;
    private Date m_dStartDate;
    private Date m_dEndDate;
    private String m_sShowTheatreText;

    /**
     * Creates new CategoryInfo
     *
    * @param id
    * @param theatreName
     * @param startDate
     * @param endDate
     */
    public ShowTheatreInfo(String id, String theatreName, Date startDate, Date endDate ) {
        m_sID = id;
        m_sTheatreName = theatreName;
        m_dStartDate = startDate;
        m_dEndDate = endDate;
        buildDateString();

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
        return m_sTheatreName;
    }

    public void setName(String sName) {
        m_sTheatreName = sName;
        buildDateString();
    }

    public Date getStartDate() {
        return m_dStartDate;
    }
    
    public void setStartDate(Date startDate) {
        m_dStartDate = startDate;
        buildDateString();
    }

    public Date getEndDate() {
        return m_dEndDate;
    }
    
    public void setEndDate(Date endDate) {
        m_dEndDate = endDate;
        buildDateString();
    }

    private void buildDateString() {
        if ( m_dStartDate == null && m_dEndDate == null ) {
            m_sShowTheatreText = m_sTheatreName;
        } else if (m_dEndDate == null) {
            m_sShowTheatreText = m_sTheatreName + " " + Formats.DATE.formatValue(m_dStartDate) + " - ??????????";
        } else if (m_dStartDate == null) {
            m_sShowTheatreText = m_sTheatreName + " ?????????? - " + Formats.DATE.formatValue(m_dEndDate);
        } else {
            m_sShowTheatreText = m_sTheatreName + " " + Formats.DATE.formatValue(m_dStartDate) + " - " + Formats.DATE.formatValue(m_dEndDate);
        }
    }
    
    @Override
    public String toString() {
        return m_sShowTheatreText;
    }

    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowTheatreInfo(dr.getString(1), dr.getString(2), dr.getDate(3), dr.getDate(4));
            }
        };
    }
}
