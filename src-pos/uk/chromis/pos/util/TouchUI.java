//    Chromis POS  - The New Face of Open Source POS
//    Copyright (c) (c) 2015-2016
//    http://www.chromis.co.uk
//
//    This file is part of Chromis POS
//
//    Chromis POS is free software: you can redistribute it and/or modify
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
package uk.chromis.pos.util;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.UIManager;

/**
 * Shared Swing defaults for touch-operated POS terminals.
 */
public final class TouchUI {

    public static final int MAINTENANCE_LIST_WIDTH = 260;
    public static final int MAINTENANCE_LIST_LONG_WIDTH = 360;
    public static final int SALES_LINE_BUTTON_PANEL_WIDTH = 72;
    public static final int SALES_TICKET_LINE_ROW_HEIGHT = 44;
    public static final int PAYMENT_HEADER_HEIGHT = 88;
    public static final int PAYMENT_DIALOG_WIDTH = 780;
    public static final int PAYMENT_DIALOG_HEIGHT = 600;
    public static final int SALES_POPUP_FIELD_HEIGHT = 36;
    public static final int SALES_POPUP_LIST_ROW_HEIGHT = 44;

    private static final int ROW_HEIGHT = 32;
    public static final int SCROLLBAR_WIDTH = 44;
    private static final Insets BUTTON_MARGIN = new Insets(8, 12, 8, 12);
    private static final Insets TAB_INSETS = new Insets(8, 14, 8, 14);

    private TouchUI() {
    }

    public static void installDefaults() {
        UIManager.put("Button.margin", BUTTON_MARGIN);
        UIManager.put("ToggleButton.margin", BUTTON_MARGIN);
        UIManager.put("ScrollBar.width", SCROLLBAR_WIDTH);
        UIManager.put("TabbedPane.tabInsets", TAB_INSETS);
        UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(4, 4, 4, 4));
        UIManager.put("Table.rowHeight", ROW_HEIGHT);
        UIManager.put("Tree.rowHeight", ROW_HEIGHT);
        UIManager.put("List.fixedCellHeight", ROW_HEIGHT);
    }

    public static Insets buttonMargin() {
        return new Insets(BUTTON_MARGIN.top, BUTTON_MARGIN.left, BUTTON_MARGIN.bottom, BUTTON_MARGIN.right);
    }

    public static Dimension salesLineButtonSize() {
        return new Dimension(58, 44);
    }

    public static Dimension paymentAmountFieldSize() {
        return new Dimension(190, 36);
    }

    public static Dimension paymentLargeAmountFieldSize() {
        return new Dimension(210, 36);
    }

    public static Dimension paymentActionButtonSize() {
        return new Dimension(64, 48);
    }

    public static Dimension paymentConfirmButtonSize() {
        return new Dimension(150, 56);
    }

    public static Dimension paymentPrintButtonSize() {
        return new Dimension(72, 52);
    }

    public static Dimension quickPaymentButtonSize() {
        return new Dimension(88, 64);
    }

    public static Dimension paymentDialogSize() {
        return new Dimension(PAYMENT_DIALOG_WIDTH, PAYMENT_DIALOG_HEIGHT);
    }

    public static Dimension salesPopupButtonSize() {
        return new Dimension(130, 52);
    }

    public static Dimension salesPopupWideButtonSize() {
        return new Dimension(170, 52);
    }

    public static Dimension salesPopupFieldSize() {
        return new Dimension(240, SALES_POPUP_FIELD_HEIGHT);
    }

    public static Dimension salesPopupLabelSize() {
        return new Dimension(130, SALES_POPUP_FIELD_HEIGHT);
    }

    public static Dimension customerFinderDialogSize() {
        return new Dimension(720, 560);
    }

    public static Dimension editLineDialogSize() {
        return new Dimension(640, 430);
    }

    public static Dimension attributeDialogSize() {
        return new Dimension(760, 500);
    }

    public static Dimension sharedTicketDialogSize() {
        return new Dimension(560, 420);
    }

    public static Dimension sharedTicketButtonSize() {
        return new Dimension(500, 56);
    }
}
