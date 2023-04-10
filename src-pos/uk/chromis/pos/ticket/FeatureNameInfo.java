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
public class FeatureNameInfo implements IKeyed {

    private String m_sID;
    private String m_sName;

    /**
     * Creates new CategoryInfo
     *
     * @param id
     * @param name
     */
    public FeatureNameInfo(String id, String name ) {
        m_sID = id;
        m_sName = name;
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

    @Override
    public String toString() {
        return m_sName;
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new FeatureNameInfo(dr.getString(1), dr.getString(2));
            }
        };
    }
}
