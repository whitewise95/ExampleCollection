package com.example.examplecollection.utils;

import com.bizprint.core.global.exceptions.BadRequestException;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class WebClientUtils {

	public static <T> Mono<T> retrieveGetForMono(WebClient webClient,
												 String url,
												 HttpHeaders httpHeaders,
												 MultiValueMap<String, String> paramMap,
												 Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (Utils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);
		return webClient.mutate()
						.build()
						.get()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}

	public static <T> Mono<T> retrievePostForMono(WebClient webClient,
												  String url,
												  HttpHeaders httpHeaders,
												  MultiValueMap<String, String> paramMap,
												  Map<String, Object> bodyMap,
												  Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (Utils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		if (Utils.isEmpty(bodyMap)) {
			bodyMap = new HashMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);

		MultiValueMap<String, String> multiBodyMap = null;
		if (MediaType.APPLICATION_FORM_URLENCODED.equals(httpHeaders.getContentType())) {
			multiBodyMap = new LinkedMultiValueMap<>();
			multiBodyMap.setAll(bodyMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> String.valueOf(e.getValue()))));
		}

		return webClient.mutate()
						.build()
						.post()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.body(multiBodyMap != null ?
							  BodyInserters.fromValue(multiBodyMap) :
							  BodyInserters.fromValue(bodyMap)
						)
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}

	public static <T> Mono<T> retrievePutForMono(WebClient webClient,
												 String url,
												 HttpHeaders httpHeaders,
												 MultiValueMap<String, String> paramMap,
												 Map<String, Object> bodyMap,
												 Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (Utils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		if (Utils.isEmpty(bodyMap)) {
			bodyMap = new HashMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);

		MultiValueMap<String, String> multiBodyMap = null;
		if (MediaType.APPLICATION_FORM_URLENCODED.equals(httpHeaders.getContentType())) {
			multiBodyMap = new LinkedMultiValueMap<>();
			multiBodyMap.setAll(bodyMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> String.valueOf(e.getValue()))));
		}

		return webClient.mutate()
						.build()
						.put()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.body(multiBodyMap != null ?
							  BodyInserters.fromValue(multiBodyMap) :
							  BodyInserters.fromValue(bodyMap)
						)
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}


	public static <T> Mono<T> retrieveDeleteForMono(WebClient webClient,
													String url,
													HttpHeaders httpHeaders,
													MultiValueMap<String, String> paramMap,
													Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (Utils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);
		return webClient.mutate()
						.build()
						.delete()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}
}
