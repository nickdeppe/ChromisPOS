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

public class ShowListInfo implements IKeyed {

    private String m_showID;
    private String m_showText;
    private BufferedImage m_image;

    public ShowListInfo() {
        m_showID = null;
        m_showText = null;
        m_image = null;
    }


    public ShowListInfo(String showID, String showText, BufferedImage image) {
        m_showID = showID;
        m_showText = showText;
        m_image = image;
    }


    @Override
    public Object getKey() {
        return m_showID;
    }

    public String getShowID() {
        return m_showID;
    }

    public void setShowID(String showID) {
        m_showID = showID;
    }

    public String getText() {
        return m_showText;
    }

    public void setText(String text) {
        m_showText = text;
    }

    public BufferedImage getImage() {
        return m_image;
    }

    public void setImage(BufferedImage image) {
        m_image = image;
    }


    @Override
    public final String toString() {
        return m_showID;
    }


    public static SerializerRead getSerializerRead() {
        return new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new ShowListInfo(dr.getString(1), dr.getString(2), ImageUtils.readImage(dr.getBytes(3)) );
            }
        };
    }



}


