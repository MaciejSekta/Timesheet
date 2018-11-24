package com.msekta.timesheet.DTOs;

import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonPropertyOrder(value = { "timestamp", "status", "error", "message", "path", "exception", "trace" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseDTO {

	@Getter
	private final String timestamp = LocalDateTime.now().toString();
	private final HttpStatus httpStatus;
	private final Throwable exception;
	private final boolean enableStackTrace;

	@Getter
	private final String path;

	@Builder
	public ErrorResponseDTO(HttpStatus httpStatus, Throwable exception, boolean enableStackTrace, String path) {
		this.httpStatus = httpStatus;
		this.exception = exception;
		this.enableStackTrace = enableStackTrace;
		this.path = path;
	}

	public Integer getStatus() {
		if (httpStatus != null) {
			return httpStatus.value();
		} else {
			return null;
		}
	}

	public String getError() {
		if (httpStatus != null) {
			return httpStatus.getReasonPhrase();
		} else {
			return null;
		}
	}

	public String getMessage() {
		if (exception != null) {
			return exception.getMessage();
		} else {
			return null;
		}
	}

	public String getTrace() {
		if (enableStackTrace && exception != null) {
			StringWriter stackTrace = new StringWriter();
			log.error(stackTrace.toString());
			stackTrace.flush();
			return stackTrace.toString();
		} else {
			return null;
		}
	}

	public String getException() {
		if (enableStackTrace && exception != null) {
			return exception.getClass().getName();
		} else {
			return null;
		}
	}
}