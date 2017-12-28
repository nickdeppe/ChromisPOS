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
 * @author adrianromero Created on 21 de marzo de 2007, 21:28
 *
 */
public class ShowInfo implements IKeyed {

    protected String m_ID;
    protected String m_sTheatreID;
    protected Date m_dStartDate;
    protected Date m_dEndDate;
    protected Date m_dReportStartDate;
    protected Date m_dReportEndDate;
    protected String m_sDateText;

    /**
     * Creates a new instance of ProductShowInfoList
     */
    public ShowInfo() {
        m_ID = null;
        m_sTheatreID = null;
        m_sDateText = null;
        m_dStartDate = null;
        m_dEndDate = null;
        m_dReportStartDate = null;
        m_dReportEndDate = null;
    }

    public ShowInfo( String id, String theatreID, Date startDate, Date endDate, Date reportStartDate, Date reportEndDate ) {
        m_ID = id;
        m_sTheatreID = theatreID;
        m_dStartDate = startDate;
        m_dEndDate = endDate;
        m_dReportStartDate = reportStartDate;
        m_dReportEndDate = reportEndDate;
        buildDateString();
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

    public final String getTheatreID() {
        return m_sTheatreID;
    }

    public final void setTheatreID(String id) {
        m_sTheatreID = id;
    }
    
    public final String getDateText() {
        return m_sDateText;
    }

    public final Date getStartDate() {
        return m_dStartDate;
    }

    public final void setStartDate( Date startDate ) {
        m_dStartDate = startDate;
        buildDateString();
    }

    public final Date getEndDate() {
        return m_dStartDate;
    }

    public final void setEndDate( Date endDate ) {
        m_dEndDate = endDate;
        buildDateString();
    }

    public final Date getReportStartDate() {
        return m_dReportStartDate;
    }

    public final void setReportStartDate( Date startDate ) {
        m_dReportStartDate = startDate;
    }

    public final Date getReportEndDate() {
        return m_dReportStartDate;
    }

    public final void setReportEndDate( Date endDate ) {
        m_dReportEndDate = endDate;
    }

    private void buildDateString() {
        if ( m_dStartDate == null && m_dEndDate == null ) {
            m_sDateText = null;
        } else if (m_dEndDate == null) {
            m_sDateText = Formats.DATE.formatValue(m_dStartDate) + " - ??????????";
        } else if (m_dStartDate == null) {
            m_sDateText = "?????????? - " + Formats.DATE.formatValue(m_dEndDate);
        } else {
            m_sDateText = Formats.DATE.formatValue(m_dStartDate) + " - " + Formats.DATE.formatValue(m_dEndDate);
        }
    }


    @Override
    public final String toString() {
        return m_sDateText;
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowInfo(dr.getString(1), dr.getString(2), dr.getDate(3), dr.getDate(4), dr.getDate(5), dr.getDate(6) );
            }
        };
    }


}
