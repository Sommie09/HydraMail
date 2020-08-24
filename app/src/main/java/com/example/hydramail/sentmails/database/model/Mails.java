package com.example.hydramail.sentmails.database.model;

public class Mails {

    public static final String TABLE_NAME = "mails";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPIENT = "recipient";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_TIMESTAMP = "timestamp";


    private int id;
    private String recipient;
    private String subject;
    private String message;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_RECIPIENT + " TEXT,"
                    + COLUMN_SUBJECT + " TEXT,"
                    + COLUMN_MESSAGE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Mails() {
    }

    public Mails(int id, String recipient, String subject, String message, String timestamp) {
        this.id = id;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
