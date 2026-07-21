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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.Font;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
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
    public static final int CLOSE_CASH_TABLE_ROW_HEIGHT = 36;
    public static final int REPORT_FILTER_FIELD_HEIGHT = 36;
    public static final int REPORT_VIEWER_BUTTON_SIZE = 40;
    public static final int MESSAGE_DIALOG_WIDTH = 560;
    public static final int MESSAGE_DIALOG_HEIGHT = 230;
    public static final int MESSAGE_DIALOG_DETAILS_HEIGHT = 420;
    public static final int CONFIRM_DIALOG_WIDTH = 620;
    public static final int CONFIRM_DIALOG_HEIGHT = 280;
    public static final int OPERATION_DIALOG_WIDTH = 780;
    public static final int OPERATION_DIALOG_HEIGHT = 560;

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
        UIManager.put("OptionPane.buttonMinimumWidth", 110);
        UIManager.put("OptionPane.minimumSize", new Dimension(MESSAGE_DIALOG_WIDTH, MESSAGE_DIALOG_HEIGHT));
        UIManager.put("OptionPane.buttonAreaBorder", javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        UIManager.put("OptionPane.messageAreaBorder", javax.swing.BorderFactory.createEmptyBorder(12, 12, 8, 12));
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

    public static Dimension closeCashButtonSize() {
        return new Dimension(160, 52);
    }

    public static Dimension closeCashTableSize() {
        return new Dimension(285, 300);
    }

    public static Dimension finderDialogSize() {
        return new Dimension(760, 580);
    }

    public static Dimension ticketFinderDialogSize() {
        return new Dimension(780, 600);
    }

    public static Dimension finderIconButtonSize() {
        return new Dimension(58, SALES_POPUP_FIELD_HEIGHT);
    }

    public static Dimension reportToolbarButtonSize() {
        return new Dimension(REPORT_VIEWER_BUTTON_SIZE, REPORT_VIEWER_BUTTON_SIZE);
    }

    public static Dimension reportFilterButtonSize() {
        return new Dimension(58, REPORT_FILTER_FIELD_HEIGHT);
    }

    public static Dimension reportFilterFieldSize() {
        return new Dimension(220, REPORT_FILTER_FIELD_HEIGHT);
    }

    public static Dimension reportFilterShortFieldSize() {
        return new Dimension(80, REPORT_FILTER_FIELD_HEIGHT);
    }

    public static Dimension dialogButtonSize() {
        return new Dimension(150, 56);
    }

    public static Dimension smallDialogButtonSize() {
        return new Dimension(130, 52);
    }

    public static Dimension dialogIconButtonSize() {
        return new Dimension(64, 52);
    }

    public static Dimension operationDialogSize() {
        return new Dimension(OPERATION_DIALOG_WIDTH, OPERATION_DIALOG_HEIGHT);
    }

    public static Dimension messageDialogSize() {
        return new Dimension(MESSAGE_DIALOG_WIDTH, MESSAGE_DIALOG_HEIGHT);
    }

    public static Dimension messageDialogDetailsSize() {
        return new Dimension(MESSAGE_DIALOG_WIDTH, MESSAGE_DIALOG_DETAILS_HEIGHT);
    }

    public static int showConfirmDialog(Component parent, Object message, String title, int optionType, int messageType) {
        final int[] result = new int[]{JOptionPane.CLOSED_OPTION};
        Window owner = parent == null ? null : SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(owner, title, java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        JPanel content = new JPanel(new BorderLayout(24, 16));
        content.setBorder(BorderFactory.createEmptyBorder(28, 32, 26, 32));

        JLabel icon = new JLabel(UIManager.getIcon(iconKeyForMessageType(messageType)));
        icon.setVerticalAlignment(JLabel.TOP);
        content.add(icon, BorderLayout.WEST);

        JLabel label = new JLabel(String.valueOf(message));
        label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));
        label.setVerticalAlignment(JLabel.CENTER);
        content.add(label, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        if (optionType == JOptionPane.OK_CANCEL_OPTION) {
            addConfirmButton(buttons, "OK", JOptionPane.OK_OPTION, result, dialog);
            addConfirmButton(buttons, "Cancel", JOptionPane.CANCEL_OPTION, result, dialog);
        } else {
            addConfirmButton(buttons, "Yes", JOptionPane.YES_OPTION, result, dialog);
            addConfirmButton(buttons, "No", JOptionPane.NO_OPTION, result, dialog);
            if (optionType == JOptionPane.YES_NO_CANCEL_OPTION) {
                addConfirmButton(buttons, "Cancel", JOptionPane.CANCEL_OPTION, result, dialog);
            }
        }
        content.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.setMinimumSize(new Dimension(CONFIRM_DIALOG_WIDTH, CONFIRM_DIALOG_HEIGHT));
        dialog.setSize(new Dimension(CONFIRM_DIALOG_WIDTH, CONFIRM_DIALOG_HEIGHT));
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result[0];
    }

    private static void addConfirmButton(JPanel buttons, String text, int value, int[] result, JDialog dialog) {
        JButton button = new JButton(text);
        Dimension size = dialogButtonSize();
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.addActionListener(e -> {
            result[0] = value;
            dialog.dispose();
        });
        buttons.add(button);
    }

    private static String iconKeyForMessageType(int messageType) {
        switch (messageType) {
            case JOptionPane.ERROR_MESSAGE:
                return "OptionPane.errorIcon";
            case JOptionPane.WARNING_MESSAGE:
                return "OptionPane.warningIcon";
            case JOptionPane.QUESTION_MESSAGE:
                return "OptionPane.questionIcon";
            case JOptionPane.INFORMATION_MESSAGE:
                return "OptionPane.informationIcon";
            default:
                return "OptionPane.informationIcon";
        }
    }

    public static void applyDialogButtonDefaults(Container container) {
        for (Component child : container.getComponents()) {
            if (child instanceof AbstractButton) {
                sizeComponent(child, dialogButtonSize(), false);
            }
            if (child instanceof Container) {
                applyDialogButtonDefaults((Container) child);
            }
        }
    }

    public static void applyReportFilterDefaults(Component component) {
        applyReportFilterDefaults(component, false);
    }

    private static void applyReportFilterDefaults(Component component, boolean parentUsesFixedLayout) {
        if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLLBAR_WIDTH);
            scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLLBAR_WIDTH);
        } else if (component instanceof JTable) {
            ((JTable) component).setRowHeight(ROW_HEIGHT);
        } else if (component instanceof JList) {
            ((JList<?>) component).setFixedCellHeight(ROW_HEIGHT);
        } else if (component instanceof JTree) {
            ((JTree) component).setRowHeight(ROW_HEIGHT);
        } else if (component instanceof AbstractButton) {
            sizeComponent(component, reportFilterButtonSize(), parentUsesFixedLayout);
        } else if (component instanceof JComboBox) {
            sizeComponent(component, reportFilterFieldSize(), parentUsesFixedLayout);
        } else if (component instanceof JTextField) {
            Dimension current = component.getPreferredSize();
            int width = Math.max(current == null ? 0 : current.width, reportFilterFieldSize().width);
            sizeComponent(component, new Dimension(width, REPORT_FILTER_FIELD_HEIGHT), parentUsesFixedLayout);
        }

        if (component instanceof Container) {
            Container container = (Container) component;
            boolean fixedLayout = container.getLayout() == null;
            for (Component child : container.getComponents()) {
                applyReportFilterDefaults(child, fixedLayout);
            }
        }
    }

    private static void sizeComponent(Component component, Dimension minimum, boolean parentUsesFixedLayout) {
        Dimension preferred = component.getPreferredSize();
        int width = Math.max(preferred == null ? 0 : preferred.width, minimum.width);
        int height = Math.max(preferred == null ? 0 : preferred.height, minimum.height);
        Dimension size = new Dimension(width, height);
        component.setMinimumSize(size);
        component.setPreferredSize(size);

        if (parentUsesFixedLayout) {
            Rectangle bounds = component.getBounds();
            component.setBounds(bounds.x, bounds.y, Math.max(bounds.width, width), Math.max(bounds.height, height));
        }
    }
}
