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

package uk.chromis.pos.payment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.util.TouchUI;

public class ConfigPaymentPanelShift4 extends javax.swing.JPanel implements PaymentConfiguration {

    private final JTextField jtxtEndpoint = new JTextField();
    private final JTextField jtxtInterfaceName = new JTextField();
    private final JTextField jtxtInterfaceVersion = new JTextField();
    private final JTextField jtxtCompanyName = new JTextField();
    private final JTextField jtxtTerminalId = new JTextField();
    private final JTextField jtxtClerkId = new JTextField();
    private final JTextField jtxtReceiptColumns = new JTextField();
    private final JTextField jtxtApiOptions = new JTextField();
    private final JTextField jtxtClientGuid = new JTextField();
    private final JPasswordField jtxtAuthToken = new JPasswordField();
    private final JPasswordField jtxtAccessToken = new JPasswordField();
    private final JButton jbtnExchangeToken = new JButton("Exchange Token");
    private final JButton jbtnTestConnection = new JButton("Test Connection");

    public ConfigPaymentPanelShift4() {
        initComponents();
        TouchUI.applyReportFilterDefaults(this);
        configureTouchSizes();
    }

    @Override
    public JPanel getComponent() {
        return this;
    }

    @Override
    public void loadProperties() {
        jtxtEndpoint.setText(property("payment.shift4.url", "https://api.shift4test.com/api/rest/v1"));
        jtxtInterfaceName.setText(property("payment.shift4.interfacename", AppLocal.APP_ID));
        jtxtInterfaceVersion.setText(property("payment.shift4.interfaceversion", AppLocal.APP_VERSION));
        jtxtCompanyName.setText(property("payment.shift4.companyname", ""));
        jtxtTerminalId.setText(property("payment.shift4.terminalid", ""));
        jtxtClerkId.setText(property("payment.shift4.clerkid", "1"));
        jtxtReceiptColumns.setText(property("payment.shift4.receiptcolumns", "40"));
        jtxtApiOptions.setText(property("payment.shift4.apioptions", ""));
        jtxtClientGuid.setText(property("payment.shift4.clientguid", ""));
        jtxtAuthToken.setText("");
        jtxtAccessToken.setText(Shift4Client.decrypt(property("payment.shift4.accesstoken", "")));
    }

    @Override
    public void saveProperties() {
        AppConfig.getInstance().setProperty("payment.shift4.url", jtxtEndpoint.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.interfacename", jtxtInterfaceName.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.interfaceversion", jtxtInterfaceVersion.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.companyname", jtxtCompanyName.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.terminalid", jtxtTerminalId.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.clerkid", jtxtClerkId.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.receiptcolumns", jtxtReceiptColumns.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.apioptions", jtxtApiOptions.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.clientguid", jtxtClientGuid.getText().trim());
        AppConfig.getInstance().setProperty("payment.shift4.accesstoken", Shift4Client.encrypt(new String(jtxtAccessToken.getPassword())));
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setPreferredSize(new java.awt.Dimension(500, 520));

        int row = 0;
        addRow(row++, "Endpoint URL", jtxtEndpoint);
        addRow(row++, "Client GUID", jtxtClientGuid);
        addRow(row++, "Auth Token", jtxtAuthToken);
        addRow(row++, "Access Token", jtxtAccessToken);
        addRow(row++, "Terminal ID", jtxtTerminalId);
        addRow(row++, "Clerk ID", jtxtClerkId);
        addRow(row++, "Interface Name", jtxtInterfaceName);
        addRow(row++, "Interface Version", jtxtInterfaceVersion);
        addRow(row++, "Company Name", jtxtCompanyName);
        addRow(row++, "Receipt Columns", jtxtReceiptColumns);
        addRow(row++, "API Options", jtxtApiOptions);
        addButtonRow(row++, jbtnExchangeToken);
        addButtonRow(row, jbtnTestConnection);

        jbtnExchangeToken.addActionListener(e -> exchangeToken());
        jbtnTestConnection.addActionListener(e -> testConnection());
    }

    private void addRow(int row, String label, JTextField field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new java.awt.Dimension(145, 30));

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(0, 0, 6, 8);
        add(jLabel, labelConstraints);

        field.setPreferredSize(new java.awt.Dimension(315, 30));
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.anchor = GridBagConstraints.WEST;
        fieldConstraints.insets = new Insets(0, 0, 6, 0);
        add(field, fieldConstraints);
    }

    private void addButtonRow(int row, JButton button) {
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = row;
        buttonConstraints.anchor = GridBagConstraints.WEST;
        buttonConstraints.insets = new Insets(4, 0, 0, 0);
        add(button, buttonConstraints);
    }

    private void configureTouchSizes() {
        java.awt.Dimension fieldSize = new java.awt.Dimension(315, 30);
        java.awt.Dimension buttonSize = new java.awt.Dimension(180, 44);
        jtxtEndpoint.setMinimumSize(fieldSize);
        jtxtEndpoint.setPreferredSize(fieldSize);
        jtxtClientGuid.setMinimumSize(fieldSize);
        jtxtClientGuid.setPreferredSize(fieldSize);
        jtxtAuthToken.setMinimumSize(fieldSize);
        jtxtAuthToken.setPreferredSize(fieldSize);
        jtxtAccessToken.setMinimumSize(fieldSize);
        jtxtAccessToken.setPreferredSize(fieldSize);
        jtxtTerminalId.setMinimumSize(fieldSize);
        jtxtTerminalId.setPreferredSize(fieldSize);
        jtxtClerkId.setMinimumSize(fieldSize);
        jtxtClerkId.setPreferredSize(fieldSize);
        jtxtInterfaceName.setMinimumSize(fieldSize);
        jtxtInterfaceName.setPreferredSize(fieldSize);
        jtxtInterfaceVersion.setMinimumSize(fieldSize);
        jtxtInterfaceVersion.setPreferredSize(fieldSize);
        jtxtCompanyName.setMinimumSize(fieldSize);
        jtxtCompanyName.setPreferredSize(fieldSize);
        jtxtReceiptColumns.setMinimumSize(fieldSize);
        jtxtReceiptColumns.setPreferredSize(fieldSize);
        jtxtApiOptions.setMinimumSize(fieldSize);
        jtxtApiOptions.setPreferredSize(fieldSize);
        jbtnExchangeToken.setMinimumSize(buttonSize);
        jbtnExchangeToken.setPreferredSize(buttonSize);
        jbtnTestConnection.setMinimumSize(buttonSize);
        jbtnTestConnection.setPreferredSize(buttonSize);
    }

    private void exchangeToken() {
        Shift4Client client = new Shift4Client(settingsFromFields());
        String clientGuid = jtxtClientGuid.getText();
        String authToken = new String(jtxtAuthToken.getPassword());
        String validationMessage = client.validateTokenExchangeConfiguration(clientGuid, authToken);
        if (validationMessage != null) {
            JOptionPane.showMessageDialog(this, validationMessage, "Shift4 Token Exchange", JOptionPane.WARNING_MESSAGE);
            return;
        }

        setButtonsEnabled(false);
        jbtnExchangeToken.setText("Exchanging...");
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws IOException {
                return client.exchangeAccessToken(clientGuid, authToken);
            }

            @Override
            protected void done() {
                setButtonsEnabled(true);
                jbtnExchangeToken.setText("Exchange Token");
                try {
                    String accessToken = get();
                    jtxtAccessToken.setText(accessToken);
                    jtxtAuthToken.setText("");
                    JOptionPane.showMessageDialog(ConfigPaymentPanelShift4.this,
                            "Access token received. Save the configuration to keep it.",
                            "Shift4 Token Exchange",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showExchangeFailure("Token exchange was interrupted.");
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                    showExchangeFailure(cause.getMessage());
                }
            }
        }.execute();
    }

    private void testConnection() {
        Shift4Client client = new Shift4Client(settingsFromFields());
        String validationMessage = client.validateBaseConfiguration();
        if (validationMessage != null) {
            JOptionPane.showMessageDialog(this, validationMessage, "Shift4 Connection Test", JOptionPane.WARNING_MESSAGE);
            return;
        }

        setButtonsEnabled(false);
        jbtnTestConnection.setText("Testing...");
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws IOException {
                return client.getDeviceInfo();
            }

            @Override
            protected void done() {
                setButtonsEnabled(true);
                jbtnTestConnection.setText("Test Connection");
                try {
                    showSuccess(get());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    showFailure(client, "Connection test was interrupted.");
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                    showFailure(client, cause.getMessage());
                }
            }
        }.execute();
    }

    private void setButtonsEnabled(boolean enabled) {
        jbtnExchangeToken.setEnabled(enabled);
        jbtnTestConnection.setEnabled(enabled);
    }

    private Shift4Client.Settings settingsFromFields() {
        return new Shift4Client.Settings(
                jtxtEndpoint.getText(),
                new String(jtxtAccessToken.getPassword()),
                jtxtClientGuid.getText(),
                jtxtTerminalId.getText(),
                jtxtClerkId.getText(),
                jtxtInterfaceName.getText(),
                jtxtInterfaceVersion.getText(),
                jtxtCompanyName.getText(),
                jtxtReceiptColumns.getText(),
                jtxtApiOptions.getText());
    }

    private void showSuccess(String response) {
        JOptionPane.showMessageDialog(this,
                "Connected to Shift4 UTG and terminal was found.\n\n" + summarizeResponse(response),
                "Shift4 Connection Test",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showFailure(Shift4Client client, String message) {
        JOptionPane.showMessageDialog(this,
                "Unable to connect to Shift4 UTG.\n\n"
                + "Endpoint: " + client.getEndpointFor("/devices/info") + "\n\n"
                + (message == null ? "" : message),
                "Shift4 Connection Test",
                JOptionPane.ERROR_MESSAGE);
    }

    private void showExchangeFailure(String message) {
        JOptionPane.showMessageDialog(this,
                "Unable to exchange the Shift4 access token.\n\n" + (message == null ? "" : message),
                "Shift4 Token Exchange",
                JOptionPane.ERROR_MESSAGE);
    }

    private String summarizeResponse(String response) {
        if (response == null || response.trim().isEmpty()) {
            return "Shift4 returned an empty success response.";
        }
        return response.length() <= 700 ? response : response.substring(0, 700) + "...";
    }

    private String property(String key, String defaultValue) {
        String value = AppConfig.getInstance().getProperty(key);
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }

}
