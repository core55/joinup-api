/*
  Authors: S. Stefani
 */

package io.github.core55.response;

/**
 * Define the structure of a simple response with timestamp in Unix time, and
 * custom message.
 */
public class StringResponse {

    private long timestamp;
    private String message;

    public StringResponse(String message) {
        this.setTimestamp();
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = System.currentTimeMillis() / 1000L;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
