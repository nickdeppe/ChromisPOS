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
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import uk.chromis.pos.forms.AppLocal;

public class PaymentGatewayShift4 implements PaymentGateway {

    private final Shift4Client client;
    private final Shift4Client.Settings settings;

    public PaymentGatewayShift4() {
        client = Shift4Client.fromConfig();
        settings = client.getSettings();
    }

    @Override
    public void execute(PaymentInfoMagcard payinfo) {
        String validationMessage = client.validateBaseConfiguration();
        if (validationMessage != null) {
            payinfo.paymentError(AppLocal.getIntString("message.paymenterror"), validationMessage);
            return;
        }

        try {
            String operation = payinfo.getTotal() >= 0.0 ? "sale" : "refund";
            JsonObject request = createTransactionRequest(payinfo);
            String response = client.postJson("/transactions/" + operation, request, 120000);
            handleResponse(payinfo, response);
        } catch (IOException | IllegalArgumentException ex) {
            payinfo.paymentError(AppLocal.getIntString("message.paymentexceptionservice"), ex.getMessage());
        }
    }

    private JsonObject createTransactionRequest(PaymentInfoMagcard payinfo) {
        JsonObject request = new JsonObject();
        request.addProperty("dateTime", ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        JsonObject amount = new JsonObject();
        amount.addProperty("total", BigDecimal.valueOf(Math.abs(payinfo.getTotal())).setScale(2, RoundingMode.HALF_UP));
        request.add("amount", amount);

        JsonObject clerk = new JsonObject();
        clerk.addProperty("numericId", parseClerkId());
        request.add("clerk", clerk);

        JsonObject transaction = new JsonObject();
        transaction.addProperty("invoice", payinfo.getTransactionID());
        request.add("transaction", transaction);

        JsonObject card = new JsonObject();
        card.addProperty("present", "Y");
        addIfNotBlank(card, "track1", payinfo.getTrack1(false));
        addIfNotBlank(card, "track2", payinfo.getTrack2(false));
        request.add("card", card);

        JsonObject device = new JsonObject();
        device.addProperty("terminalId", settings.terminalId);
        request.add("device", device);

        int columns = parsePositiveInt(settings.receiptColumns, 0);
        if (columns > 0) {
            JsonObject receipt = new JsonObject();
            receipt.addProperty("columns", columns);
            request.add("receipt", receipt);
        }

        JsonArray options = apiOptions();
        if (options.size() > 0) {
            request.add("apiOptions", options);
        }

        return request;
    }

    private void handleResponse(PaymentInfoMagcard payinfo, String response) {
        JsonObject result = firstResult(response);
        if (result == null) {
            payinfo.paymentError(AppLocal.getIntString("message.paymenterror"), response);
            return;
        }

        String errorMessage = errorMessage(result);
        if (errorMessage != null) {
            payinfo.paymentError(AppLocal.getIntString("message.paymenterror"), errorMessage);
            return;
        }

        JsonObject transaction = getObject(result, "transaction");
        String responseCode = getString(transaction, "responseCode");
        String authorizationCode = getString(transaction, "authorizationCode");
        if ("A".equalsIgnoreCase(responseCode) || notBlank(authorizationCode)) {
            String transactionId = firstNotBlank(getString(transaction, "invoice"),
                    getString(transaction, "retrievalReference"),
                    payinfo.getTransactionID());
            payinfo.paymentOK(firstNotBlank(authorizationCode, responseCode, "APPROVED"), transactionId, response);
        } else {
            payinfo.paymentError(AppLocal.getIntString("message.paymenterror"), firstNotBlank(errorMessage, response));
        }
    }

    private JsonObject firstResult(String response) {
        JsonElement rootElement = new JsonParser().parse(response);
        if (!rootElement.isJsonObject()) {
            return null;
        }

        JsonObject root = rootElement.getAsJsonObject();
        JsonArray result = getArray(root, "result");
        if (result != null && result.size() > 0 && result.get(0).isJsonObject()) {
            return result.get(0).getAsJsonObject();
        }
        return root;
    }

    private String errorMessage(JsonObject result) {
        JsonArray errors = getArray(result, "errors");
        if (errors != null && errors.size() > 0) {
            StringBuilder message = new StringBuilder();
            for (JsonElement errorElement : errors) {
                if (errorElement.isJsonObject()) {
                    JsonObject error = errorElement.getAsJsonObject();
                    appendMessage(message, firstNotBlank(getString(error, "description"), getString(error, "message"), getString(error, "code")));
                }
            }
            return message.length() == 0 ? null : message.toString();
        }
        return firstNotBlank(getString(result, "error"), getString(result, "message"));
    }

    private void appendMessage(StringBuilder message, String line) {
        if (notBlank(line)) {
            if (message.length() > 0) {
                message.append('\n');
            }
            message.append(line);
        }
    }

    private JsonArray apiOptions() {
        JsonArray options = new JsonArray();
        if (settings.apiOptions == null || settings.apiOptions.trim().isEmpty()) {
            return options;
        }
        String[] values = settings.apiOptions.split(",");
        for (String value : values) {
            String option = value.trim();
            if (!option.isEmpty()) {
                options.add(new JsonPrimitive(option));
            }
        }
        return options;
    }

    private void addIfNotBlank(JsonObject object, String key, String value) {
        if (notBlank(value)) {
            object.addProperty(key, value);
        }
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

    private int parseClerkId() {
        return parsePositiveInt(settings.clerkId, 1);
    }

    private int parsePositiveInt(String value, int defaultValue) {
        if (!notBlank(value)) {
            return defaultValue;
        }
        try {
            return Math.max(Integer.parseInt(value.trim()), defaultValue);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private boolean notBlank(String value) {
        return Shift4Client.notBlank(value);
    }

    private String firstNotBlank(String... values) {
        for (String value : values) {
            if (notBlank(value)) {
                return value;
            }
        }
        return null;
    }
}
