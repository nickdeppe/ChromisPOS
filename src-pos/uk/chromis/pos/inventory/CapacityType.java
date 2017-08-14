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
package uk.chromis.pos.inventory;

import uk.chromis.data.loader.IKeyed;

/**
 *
 * @author adrianromero
 */
public class CapacityType implements IKeyed {

    /**
     *
     */

	public static final CapacityType BY_TRANSACTIONS = new CapacityType("R", "By Transaction Count");
	public static final CapacityType BY_TICKETS = new CapacityType("T", "By Ticket Count");

    /**
     *
     */
    protected String m_sKey;

    /**
     *
     */
    protected String m_sValue;

    private CapacityType(String key, String value) {
        m_sKey = key;
        m_sValue = value;
    }

    /**
     *
     * @return
     */
    @Override
    public Object getKey() {
        return m_sKey;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return m_sValue;
    }

    @Override
    public String toString() {
        return m_sValue;
    }
}
