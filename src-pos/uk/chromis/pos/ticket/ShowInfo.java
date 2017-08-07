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

/**
 *
 * @author Adrian
 * @version
 */
public class ShowInfo implements IKeyed {

    private String m_sID;
    private String m_sName;
    private BufferedImage m_Image;
    private Integer m_iShowOrder;
	 private Integer m_iShowLength;
	 private String m_sScheduleMode;
	 private Boolean m_bActive;

    /**
     * Creates new CategoryInfo
     *
     * @param id
     * @param name
     * @param image
     * @param texttip
     * @param catshowname
     */
    public ShowInfo(String id, String name, BufferedImage image, Integer showOrder, Integer showLength, String scheduleMode, Boolean active ) {
			m_sID = id;
			m_sName = name;
			m_Image = image;
			m_iShowOrder = showOrder;
			m_iShowLength = showLength;
			m_sScheduleMode = scheduleMode;
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

	 public void setShowOrder(Integer showOrder) {
		 m_iShowOrder = showOrder;
	 }

	 public Integer getShowOrder() {
		 return m_iShowOrder;
	 }

	 public void setShowLength(Integer showLength) {
		 m_iShowLength = showLength;
	 }

	 public Integer getShowLength() {
		 return m_iShowLength;
	 }

	 public void setScheduleMode(String scheduleMode) {
		 m_sScheduleMode = scheduleMode;
	 }

	 public String getScheduleMode() {
		 return m_sScheduleMode;
	 }

	 public void setActive(Boolean active) {
		 m_bActive = active;
	 }

	 public Boolean getActive() {
		 return m_bActive;
	 }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowInfo(dr.getString(1), dr.getString(2),  ImageUtils.readImage(dr.getBytes(3)), dr.getInt(4), dr.getInt(5), dr.getString(6), dr.getBoolean(7));
            }
        };
    }
}
