package io.github.meixxi.zplbox.controller.util;

import io.github.meixxi.zplbox.controller.v1.model.ProblemDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

public class Responses {

    /**
     * Helper method to create an OK response.
     *
     * @return The state object response entity object.
     */
    public static <T> ResponseEntity<T> createOkResponse() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Helper method to create an OK response.
     *
     * @param obj The object to be returned.
     * @return The state object response entity object.
     */
    public static <T> ResponseEntity<T> createOkResponse(T obj, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        return new ResponseEntity<>(obj, headers, HttpStatus.OK);
    }

    /**
     * Helper method to create a BadRequest Response.
     *
     * @param request   The http request.
     * @param exception The exception causing the problem details.
     * @return The problem details response entity object.
     */
    public static ResponseEntity<ProblemDetails> createBadRequestResponse(HttpServletRequest request, Exception exception) {

        // create problem details
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetails problemDetails = new ProblemDetails(httpStatus, request.getRequestURI(), exception.getMessage());

        // build response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_PROBLEM_JSON_VALUE);
        return new ResponseEntity<>(problemDetails, headers, httpStatus);
    }
}
