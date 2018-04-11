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
import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.DataRead;
import uk.chromis.data.loader.IKeyed;
import uk.chromis.data.loader.ImageUtils;
import uk.chromis.data.loader.SerializerRead;
import uk.chromis.format.Formats;
import uk.chromis.pos.util.StringUtils;

/**
 *
 * @author Adrian
 * @version
 */
public class FeatureInfo implements IKeyed {

    private String m_sID;
    private String m_sName;
    private BufferedImage m_Image;
    private Integer m_iRuntime;
    private String m_sRatingID;
    private String m_sExchangeID;
    private Boolean m_bActive;

    /**
     * Creates new CategoryInfo
     *
     * @param id
     * @param name
     * @param image
     * @param runtime
     * @param ratingID
     * @param active
     */
    public FeatureInfo(String id, String name, BufferedImage image, Integer runtime, String ratingID, String exchangeID, Boolean active ) {
        m_sID = id;
        m_sName = name;
        m_Image = image;
        m_iRuntime = runtime;
        m_sRatingID = ratingID;
        m_sExchangeID = exchangeID;
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
    
    public final String printID() {
        return StringUtils.encodeXML(getID());
    }

    public String getName() {
        return m_sName;
    }
    
    public final String printName() {
        return StringUtils.encodeXML(getName());
    }

    public void setName(String sName) {
        m_sName = sName;
    }

    public BufferedImage getImage() {
        return m_Image;
    }

    public void setImage(BufferedImage img) {
        m_Image = img;
    }

    @Override
    public String toString() {
        return m_sName;
    }

    public void setRuntime(Integer runtime) {
            m_iRuntime = runtime;
    }

    public Integer getRuntime() {
            return m_iRuntime;
    }

    public String printRuntime() {
        return StringUtils.encodeXML(Formats.INT.formatValue(getRuntime()));
    }
    
    public void setRatingID(String ratingID) {
            m_sRatingID = ratingID;
    }

    public String getRatingID() {
            return m_sRatingID;
    }
    
    public String printRatingID() {
        return StringUtils.encodeXML(getRatingID());
    }
    
    
    public void setExchangeID(String exchangeID) {
        m_sExchangeID = exchangeID;
    }
    
    public String getExchangeID() {
        return m_sExchangeID;
    }
    
    public String printExchangeID() {
        return StringUtils.encodeXML(getExchangeID());
    }
    

    public void setActive(Boolean active) {
            m_bActive = active;
    }

    public Boolean getActive() {
            return m_bActive;
    }
    
    public String printActive() {
        return StringUtils.encodeXML(Formats.BOOLEAN.formatValue(getActive()));
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new FeatureInfo(dr.getString(1), dr.getString(2), ImageUtils.readImage(dr.getBytes(3)), dr.getInt(4), dr.getString(5), dr.getString(6), dr.getBoolean(7));
            }
        };
    }
}
