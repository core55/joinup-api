/*
  Authors: S. Stefani
 */

package io.github.core55.response;

/**
 * Wrapper for a "Unauthorized" error response with custom message.
 */
public class ErrorUnauthorized extends ErrorResponse {

    public ErrorUnauthorized(String message) {
        super();
        this.setStatus(401);
        this.setError("Unauthorized");
        this.setMessage(message);
    }
}
