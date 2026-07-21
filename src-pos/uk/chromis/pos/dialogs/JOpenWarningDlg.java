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
//    along with Chromis POS.  If not, see <http://www.gnu.org/licenses/>
//
package uk.chromis.pos.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.util.TouchUI;

/**
 *
 * @author John Lewis
 */
public class JOpenWarningDlg extends JDialog {

    static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int RETRY = 3;
    public static final int EXIT = 2;
    public static final int CONFIG = 1;
    public static int CHOICE = 0;
    private static final int DIALOG_WIDTH = 560;
    private Font font;
    private int height;

    public JOpenWarningDlg(String eMessage, String message, boolean bRetry, boolean bConfig) {

        JButton btnRetry = new JButton(AppLocal.getIntString("Button.Retry"));
        btnRetry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CHOICE = RETRY;
                dispose();
            }
        });

        JButton btnExit = new JButton(AppLocal.getIntString("Button.Exit"));
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CHOICE = EXIT;
                dispose();
            }
        });

        JButton btnConfig = new JButton(AppLocal.getIntString("Button.Configuration"));
        btnConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CHOICE = CONFIG;
                dispose();
            }
        });

        int size = (eMessage.length() / 48) + 1;
        height = 390;
        if (!bRetry && !bConfig) {
            height = 390 + ((size - 5) * 12);
        }

        MigLayout layout = new MigLayout("", "[fill]");
        JPanel mainPanel = new JPanel(layout);
        JPanel dialogPanel = new JPanel();
        JPanel errorPanel = new JPanel();
        JPanel btnPanel = new JPanel();

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/uk/chromis/images/chromis_main.png")));
        mainPanel.add(logoLabel, "wrap");

        dialogPanel.setBackground(Color.white);
        errorPanel.setBackground(Color.white);

        JTextArea eMessageArea = new JTextArea();
        eMessageArea.setEditable(false);
        eMessageArea.setColumns(48);
        eMessageArea.setRows(2);
        eMessageArea.setText(eMessage);
        eMessageArea.setLineWrap(true);
        eMessageArea.setWrapStyleWord(true);


        font = new Font("Arial", Font.BOLD, 14);
        eMessageArea.setFont(font);
        eMessageArea.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        eMessageArea.setEnabled(false);
        eMessageArea.setFocusable(false);
        eMessageArea.setOpaque(false);
        eMessageArea.setRequestFocusEnabled(false);
        errorPanel.add(eMessageArea);
        errorPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        mainPanel.add(errorPanel, "wrap");

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setColumns(48);
        messageArea.setRows(4);
        messageArea.setText(message);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        font = new Font("Arial", Font.BOLD, 14);
        messageArea.setFont(font);
        messageArea.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        messageArea.setEnabled(false);
        messageArea.setFocusable(false);
        messageArea.setOpaque(false);
        messageArea.setRequestFocusEnabled(false);
        dialogPanel.add(messageArea);
        dialogPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        mainPanel.add(dialogPanel, "wrap");

        if (bRetry) {
            btnPanel.add(btnRetry, "split,right, width 130!, height 48!");
        }
        if (bConfig) {
            btnPanel.add(btnConfig, " width 130!, height 48!");
        }
        btnPanel.add(btnExit, " width 130!, height 48!");
        mainPanel.add(btnPanel, "right, wrap");
        mainPanel.add(new JLabel(), "wrap");
        mainPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        getContentPane().add(mainPanel);

        int dialogWidth = DIALOG_WIDTH;
        int dialogHeight = height;
        int dialogX = SCREEN_DIMENSION.width / 2 - dialogWidth / 2;
        int dialogY = SCREEN_DIMENSION.height / 2 - dialogHeight / 2;

        btnRetry.setPreferredSize(TouchUI.dialogButtonSize());
        btnConfig.setPreferredSize(TouchUI.dialogButtonSize());
        btnExit.setPreferredSize(TouchUI.dialogButtonSize());

        setBounds(dialogX, dialogY, DIALOG_WIDTH, height);
        setUndecorated(true);
        CHOICE = 0;
    }

}
