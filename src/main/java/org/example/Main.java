package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            User user = new User(1, 1, "delectus aut autem", false);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInputString = objectMapper.writeValueAsString(user);

            byte[] jsonBytes = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonBytes, 0, jsonBytes.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class User {
        private int userId;
        private int id;
        private String title;
        private boolean completed;

        @JsonCreator
        public User(@JsonProperty("userId") int userId,
                    @JsonProperty("id") int id,
                    @JsonProperty("title") String title,
                    @JsonProperty("completed") boolean completed) {
            this.userId = userId;
            this.id = id;
            this.title = title;
            this.completed = completed;
        }

        public int getUserId() {
            return userId;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public boolean isCompleted() {
            return completed;
        }
    }
}