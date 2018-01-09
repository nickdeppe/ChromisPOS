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
import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.DataRead;
import uk.chromis.data.loader.IKeyed;
import uk.chromis.data.loader.ImageUtils;
import uk.chromis.data.loader.SerializerRead;

/**
 *
 * @author adrianromero Created on 21 de marzo de 2007, 21:28
 *
 */
public class ShowFeaturesInfo implements IKeyed {

    protected String m_ID;
    protected String m_sShowID;
    protected String m_sFeatureID;
    protected Integer m_iSequence;
    protected String m_sStartTime;
    protected String m_sFeatureName;
    protected BufferedImage m_Image;
    protected Integer m_iRuntime;
    protected String m_sRatingID;
    protected Boolean m_bActive;
    protected String m_sRatingName;
    

    /**
     * Creates a new instance of ProductShowInfoList
     */
    public ShowFeaturesInfo() {
        m_ID = null;
        m_sShowID = null;
        m_sFeatureID = null;
        m_iSequence = null;
        m_sStartTime = null;
        m_sFeatureName = null;
        m_Image = null;
        m_iRuntime = null;
        m_sRatingID = null;
        m_bActive = null;
        m_sRatingName = null;
    }

    public ShowFeaturesInfo( String id, String showID, String featureID, Integer sequence, String startTime, String featureName, BufferedImage image, Integer runtime, String ratingID, Boolean active, String ratingName ) {
        m_ID = id;
        m_sShowID = showID;
        m_sFeatureID = featureID;
        m_iSequence = sequence;
        m_sStartTime = startTime;
        m_sFeatureName = featureName;
        m_Image = image;
        m_iRuntime = runtime;
        m_sRatingID = ratingID;
        m_bActive = active;
        m_sRatingName = ratingName;
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

    public final String getShowID() {
        return m_sShowID;
    }
    
    public final void setShowID(String showID) {
        m_sShowID = showID;
    }
    
    public final String getFeatureID() {
        return m_sFeatureID;
    }
    
    public final void setFeatureID(String featureID) {
        m_sFeatureID = featureID;
    }
    
    public final Integer getSequence() {
        return m_iSequence;
    }
    
    public final void setSequence(Integer sequence) {
        m_iSequence = sequence;
    }
    
    public final String getStartTime() {
        return m_sStartTime;
    }
    
    public final void setStartTime(String startTime) {
        m_sStartTime = startTime;
    }
    
    public final String getFeatureName() {
        return m_sFeatureName;
    }
    
    public final void setFeatureName(String featureName) {
        m_sFeatureName = featureName;
    }
    
    public final BufferedImage getImage() {
        return m_Image;
    }
    
    public final void setImage(BufferedImage image) {
        m_Image = image;
    }
    
    public final Integer getRuntime() {
        return m_iRuntime;
    }
    
    public final void setRuntme(Integer runtime) {
        m_iRuntime = runtime;
    }

    public final String getRatingID() {
        return m_sRatingID;
    }
    
    public final void setRatingID(String ratingID) {
        m_sRatingID = ratingID;
    }
    
    public final Boolean getActive() {
        return m_bActive;
    }
    
    public final void setActive(Boolean active) {
        m_bActive = active;
    }
    
    public final String getRatingName() {
        return m_sRatingName;
    }
    
    public final void setRatingName(String ratingName) {
        m_sRatingName = ratingName;
    }
    

    @Override
    public final String toString() {
        return m_sStartTime + " - " + m_sFeatureName + " - " + m_sRatingName;
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowFeaturesInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getInt(4), dr.getString(5), dr.getString(6), ImageUtils.readImage(dr.getBytes(7)), dr.getInt(8), dr.getString(9), dr.getBoolean(10), dr.getString(11));
            }
        };
    }


}
