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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import uk.chromis.pos.forms.AppConfig;
import uk.chromis.pos.forms.AppLocal;
import uk.chromis.pos.util.AltEncrypter;

public class Shift4Client {

    public static final String CRYPT_PREFIX = "crypt:";
    public static final String CYPHER_KEY = "cypherkeyshift4";

    private final Gson gson = new Gson();
    private final Settings settings;

    public Shift4Client(Settings settings) {
        this.settings = settings;
    }

    public static Shift4Client fromConfig() {
        return new Shift4Client(Settings.fromConfig());
    }

    public String validateBaseConfiguration() {
        String headerValidation = validateHeaderConfiguration();
        if (headerValidation != null) {
            return headerValidation;
        }
        if (!notBlank(settings.accessToken)) {
            return "Shift4 access token is not configured.";
        }
        if (!notBlank(settings.terminalId)) {
            return "Shift4 terminal ID is not configured.";
        }
        return null;
    }

    public String validateTokenExchangeConfiguration(String clientGuid, String authToken) {
        String headerValidation = validateHeaderConfiguration();
        if (headerValidation != null) {
            return headerValidation;
        }
        if (!notBlank(clientGuid)) {
            return "Shift4 client GUID is not configured.";
        }
        if (!notBlank(authToken)) {
            return "Shift4 auth token is not configured.";
        }
        return null;
    }

    private String validateHeaderConfiguration() {
        if (!notBlank(settings.endpointUrl)) {
            return "Shift4 endpoint URL is not configured.";
        }
        if (!notBlank(settings.interfaceName)) {
            return "Shift4 interface name is not configured.";
        }
        if (!notBlank(settings.interfaceVersion)) {
            return "Shift4 interface version is not configured.";
        }
        if (!notBlank(settings.companyName)) {
            return "Shift4 company name is not configured.";
        }
        return null;
    }

    public String exchangeAccessToken(String clientGuid, String authToken) throws IOException {
        JsonObject credential = new JsonObject();
        credential.addProperty("clientGuid", clientGuid.trim());
        credential.addProperty("authToken", authToken.trim());

        JsonObject request = new JsonObject();
        request.addProperty("dateTime", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        request.add("credential", credential);

        String response = request("POST", "/credentials/accesstoken", gson.toJson(request).getBytes(StandardCharsets.UTF_8), 30000, false, false);
        String accessToken = accessTokenFromResponse(response);
        if (!notBlank(accessToken)) {
            throw new IOException("Shift4 did not return an access token.\n" + response);
        }
        return accessToken;
    }

    public String getDeviceInfo() throws IOException {
        return request("GET", "/devices/info", null, 30000, true, true);
    }

    public String getEndpointFor(String path) {
        return settings.endpointUrl + path;
    }

    public String postJson(String path, JsonObject request, int readTimeout) throws IOException {
        return request("POST", path, gson.toJson(request).getBytes(StandardCharsets.UTF_8), readTimeout, true, true);
    }

    private String request(String method, String path, byte[] body, int readTimeout, boolean includeAccessToken, boolean includeTerminalId) throws IOException {
        URL endpoint = URI.create(getEndpointFor(path)).toURL();
        try {
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("InterfaceVersion", settings.interfaceVersion);
            connection.setRequestProperty("InterfaceName", settings.interfaceName);
            connection.setRequestProperty("CompanyName", settings.companyName);
            if (includeAccessToken) {
                connection.setRequestProperty("AccessToken", settings.accessToken);
            }
            if (includeTerminalId) {
                connection.setRequestProperty("TerminalId", settings.terminalId);
            }

            if (body != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                    out.write(body);
                    out.flush();
                }
            }

            int responseCode = connection.getResponseCode();
            InputStream stream = responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream();
            String response = readResponse(stream);
            if (responseCode >= 400) {
                throw new IOException("HTTP " + responseCode + ": " + response);
            }
            return response;
        } catch (IOException ex) {
            throw new IOException(method + " " + endpoint.toExternalForm() + " failed: " + ex.getMessage(), ex);
        }
    }

    private String readResponse(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private String accessTokenFromResponse(String response) {
        JsonElement rootElement = new JsonParser().parse(response);
        if (!rootElement.isJsonObject()) {
            return null;
        }
        JsonArray result = getArray(rootElement.getAsJsonObject(), "result");
        if (result == null || result.size() == 0 || !result.get(0).isJsonObject()) {
            return null;
        }
        JsonObject credential = getObject(result.get(0).getAsJsonObject(), "credential");
        return getString(credential, "accessToken");
    }

    private JsonObject getObject(JsonObject object, String key) {
        JsonElement value = object == null ? null : object.get(key);
        return value != null && value.isJsonObject() ? value.getAsJsonObject() : null;
    }

    private JsonArray getArray(JsonObject object, String key) {
        JsonElement value = object == null ? null : object.get(key);
        return value != null && value.isJsonArray() ? value.getAsJsonArray() : null;
    }

    private String getString(JsonObject object, String key) {
        JsonElement value = object == null ? null : object.get(key);
        return value == null || value.isJsonNull() ? null : value.getAsString();
    }

    public Settings getSettings() {
        return settings;
    }

    public static String encrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        AltEncrypter cypher = new AltEncrypter(CYPHER_KEY);
        return CRYPT_PREFIX + cypher.encrypt(value);
    }

    public static String decrypt(String value) {
        if (!notBlank(value)) {
            return "";
        }
        if (value.startsWith(CRYPT_PREFIX)) {
            AltEncrypter cypher = new AltEncrypter(CYPHER_KEY);
            return cypher.decrypt(value.substring(CRYPT_PREFIX.length()));
        }
        return value;
    }

    public static String trimTrailingSlash(String value) {
        if (value == null) {
            return "";
        }
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static boolean notBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static class Settings {

        public final String endpointUrl;
        public final String accessToken;
        public final String clientGuid;
        public final String terminalId;
        public final String clerkId;
        public final String interfaceName;
        public final String interfaceVersion;
        public final String companyName;
        public final String receiptColumns;
        public final String apiOptions;

        public Settings(String endpointUrl, String accessToken, String clientGuid, String terminalId, String clerkId,
                String interfaceName, String interfaceVersion, String companyName,
                String receiptColumns, String apiOptions) {
            this.endpointUrl = trimTrailingSlash(endpointUrl == null ? "" : endpointUrl.trim());
            this.accessToken = accessToken == null ? "" : accessToken.trim();
            this.clientGuid = clientGuid == null ? "" : clientGuid.trim();
            this.terminalId = terminalId == null ? "" : terminalId.trim();
            this.clerkId = clerkId == null || clerkId.trim().isEmpty() ? "1" : clerkId.trim();
            this.interfaceName = interfaceName == null || interfaceName.trim().isEmpty() ? AppLocal.APP_ID : interfaceName.trim();
            this.interfaceVersion = interfaceVersion == null || interfaceVersion.trim().isEmpty() ? AppLocal.APP_VERSION : interfaceVersion.trim();
            this.companyName = companyName == null ? "" : companyName.trim();
            this.receiptColumns = receiptColumns == null || receiptColumns.trim().isEmpty() ? "40" : receiptColumns.trim();
            this.apiOptions = apiOptions == null ? "" : apiOptions.trim();
        }

        public static Settings fromConfig() {
            return new Settings(
                    property("payment.shift4.url"),
                    decrypt(property("payment.shift4.accesstoken")),
                    property("payment.shift4.clientguid"),
                    property("payment.shift4.terminalid"),
                    property("payment.shift4.clerkid", "1"),
                    property("payment.shift4.interfacename", AppLocal.APP_ID),
                    property("payment.shift4.interfaceversion", AppLocal.APP_VERSION),
                    property("payment.shift4.companyname"),
                    property("payment.shift4.receiptcolumns", "40"),
                    property("payment.shift4.apioptions"));
        }

        private static String property(String key) {
            return property(key, "");
        }

        private static String property(String key, String defaultValue) {
            String value = AppConfig.getInstance().getProperty(key);
            return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
        }
    }
}
