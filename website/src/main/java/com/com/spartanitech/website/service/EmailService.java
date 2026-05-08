package com.spartanitech.website.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    private final String FROM_EMAIL = "Spartanitech.hrd@gmail.com";

    // =========================
    // NORMAL MAIL
    // =========================

    public void sendMail(String to, String subject, String text) {

        try {

            System.out.println("MAIL METHOD CALLED");

            JSONObject sender = new JSONObject();
            sender.put("name", "Spartan I-Tech Team");
            sender.put("email", FROM_EMAIL);

            JSONObject receiver = new JSONObject();
            receiver.put("email", to);

            JSONArray toArray = new JSONArray();
            toArray.put(receiver);

            JSONObject body = new JSONObject();

            body.put("sender", sender);
            body.put("to", toArray);
            body.put("subject", subject);
            body.put("textContent", text);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", brevoApiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("MAIL RESPONSE: " + response.body());

        } catch (Exception e) {

            System.out.println("MAIL FAILED");
            e.printStackTrace();
        }
    }

    // =========================
    // MAIL WITH ATTACHMENT
    // =========================

    public void sendMailWithAttachment(String to,
                                       String subject,
                                       String text,
                                       String filePath) {

        try {

            System.out.println("HR MAIL METHOD CALLED");

            File file = new File(filePath);

            if (!file.exists()) {

                System.out.println("Resume file not found");
                return;
            }

            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String encodedFile =
                    Base64.getEncoder().encodeToString(fileBytes);

            JSONObject sender = new JSONObject();
            sender.put("name", "Spartan I-Tech HR");
            sender.put("email", FROM_EMAIL);

            JSONObject receiver = new JSONObject();
            receiver.put("email", to);

            JSONArray toArray = new JSONArray();
            toArray.put(receiver);

            JSONObject attachment = new JSONObject();
            attachment.put("content", encodedFile);
            attachment.put("name", file.getName());

            JSONArray attachments = new JSONArray();
            attachments.put(attachment);

            JSONObject body = new JSONObject();

            body.put("sender", sender);
            body.put("to", toArray);
            body.put("subject", subject);
            body.put("textContent", text);
            body.put("attachment", attachments);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", brevoApiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("HR MAIL RESPONSE: " + response.body());

        } catch (Exception e) {

            System.out.println("HR MAIL FAILED");
            e.printStackTrace();
        }
    }
}