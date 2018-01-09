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

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.DataRead;
import uk.chromis.data.loader.IKeyed;
import uk.chromis.data.loader.SerializerRead;

/**
 *
 * @author adrianromero Created on 21 de marzo de 2007, 21:28
 *
 */
public class ShowSalesInfo implements IKeyed {

    protected String m_ID;
    protected String m_sTheatreID;
    protected Date m_dStartDate;
    protected Date m_dEndDate;
    protected Date m_dReportStartDate;
    protected Date m_dReportEndDate;
    protected List<ShowFeaturesInfo> m_oShowFeatures;
    protected TheatreInfo m_oTheatre;

    /**
     * Creates a new instance of ProductShowInfoList
     */
    public ShowSalesInfo() {
        m_ID = null;
        m_sTheatreID = null;
        m_dStartDate = null;
        m_dEndDate = null;
        m_dReportStartDate = null;
        m_dReportEndDate = null;
    }

    public ShowSalesInfo( String id, String theatreID, Date startDate, Date endDate, Date reportStartDate, Date reportEndDate ) {
        m_ID = id;
        m_sTheatreID = theatreID;
        m_dStartDate = startDate;
        m_dEndDate = endDate;
        m_dReportStartDate = reportStartDate;
        m_dReportEndDate = reportEndDate;
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
    
    public final Date getStartDate() {
        return m_dStartDate;
    }

    public final void setStartDate( Date startDate ) {
        m_dStartDate = startDate;
    }

    public final Date getEndDate() {
        return m_dStartDate;
    }

    public final void setEndDate( Date endDate ) {
        m_dEndDate = endDate;
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

    public final String getTheatreName() {
        return m_oTheatre.getName();
    }
    
    public final List<ShowFeaturesInfo> getShowFeatures() {
        return m_oShowFeatures;
    }
    
    public final void setShowFeatures(List<ShowFeaturesInfo> showFeatures) {
        m_oShowFeatures = showFeatures;
    }
    
    public final void setTheatre(TheatreInfo theatre) {
        m_oTheatre = theatre;
    }
    
    public final TheatreInfo getTheatre() {
        return m_oTheatre;
    }
    

    @Override
    public final String toString() {
        return getTheatreName();
    }
    
    public final String getButtonText() {
        String buttonText = "<html><font size='+1'>" + getTheatreName() + "</font>";
        for(int i = 0; i < m_oShowFeatures.size(); i++) {
            ShowFeaturesInfo feature = m_oShowFeatures.get(i);
            buttonText += "<br>" + feature.toString();
        }
        return buttonText;
    }
    
    public final String getButtonToolTip() {
        String toolTip = getTheatreName();
        for(int i = 0; i < m_oShowFeatures.size(); i++) {
            ShowFeaturesInfo feature = m_oShowFeatures.get(i);
            toolTip += ", " + feature.toString();
        }
        return toolTip;
    }
    
    
    public final BufferedImage getButtonImage() {
        BufferedImage image;
        for (int i = 0; i < m_oShowFeatures.size(); i++) {
            image = m_oShowFeatures.get(i).getImage();
            if ( image != null ) {
                return image;
            }
        }
        return null;  // If we reached this far, we didn't find a feature with an image
    }
    
    
    public final String getShowFeaturesText() {
        String featuresText = "";
        ShowFeaturesInfo feature;
        for (int i = 0; i < m_oShowFeatures.size(); i++) {
            feature = m_oShowFeatures.get(i);
            if (!featuresText.equals(""))
                featuresText += ", ";
            featuresText += feature.getFeatureName();
        }
        return featuresText;
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowSalesInfo(dr.getString(1), dr.getString(2), dr.getDate(3), dr.getDate(4), dr.getDate(5), dr.getDate(6) );
            }
        };
    }


}
