/*
  Authors: S. Stefani
 */

package io.github.core55.response;

/**
 * Wrapper for a "Unprocessable Entity" error response with custom message.
 */
public class ErrorUnprocessableEntity extends ErrorResponse {

    public ErrorUnprocessableEntity(String message) {
        super();
        this.setStatus(422);
        this.setError("Unprocessable Entity");
        this.setMessage(message);
    }
}
