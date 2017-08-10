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
import uk.chromis.data.loader.SerializerRead;
import uk.chromis.format.Formats;

/**
 *
 * @author adrianromero Created on 21 de marzo de 2007, 21:28
 *
 */
public class ProductShowInfo {

    protected String m_ID;
	 protected String m_sProductID;
	 protected String m_sShowID;
    protected Date m_dStartDate;
    protected Date m_dEndDate;
    protected Date m_dReportStartDate;
    protected Date m_dReportEndDate;
    protected Double m_dDistributionRate;
    protected String m_sDateText;

    /**
     * Creates a new instance of ProductShowInfoList
     */
    public ProductShowInfo() {
        m_ID = null;
        m_sProductID = null;
        m_sShowID = null;
		  m_sDateText = null;
		  m_dStartDate = null;
		  m_dEndDate = null;
		  m_dReportStartDate = null;
		  m_dReportEndDate = null;
		  m_dDistributionRate = null;
    }

		public ProductShowInfo( String id, String productID, String showID, Date startDate, Date endDate, Date reportStartDate, Date reportEndDate, Double distRate ) {
        m_ID = id;
		  m_sProductID = productID;
		  m_sShowID = showID;
		  m_dStartDate = startDate;
		  m_dEndDate = endDate;
		  m_dReportStartDate = reportStartDate;
		  m_dReportEndDate = reportEndDate;
		  m_dDistributionRate = distRate;
		  buildDateString();
    }


    public final String getID() {
        return m_ID;
    }

    public final void setID(String id) {
        m_ID = id;
    }

    public final String getProductID() {
        return m_sProductID;
    }

    public final void setProductID(String id) {
        m_sProductID = id;
    }

    public final String getShowID() {
        return m_sShowID;
    }

    public final void setShowID(String id) {
        m_sShowID = id;
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

	 public final Double getDistributionRate() {
		 return m_dDistributionRate;
	 }

	 public final void setDistributionRate( Double distRate ) {
		 m_dDistributionRate = distRate;
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
                return new ProductShowInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getTimestamp(4), dr.getTimestamp(5), dr.getTimestamp(6), dr.getTimestamp(7), dr.getDouble(8) );
            }
        };
    }


}
