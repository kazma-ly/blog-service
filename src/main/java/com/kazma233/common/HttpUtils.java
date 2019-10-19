package com.kazma233.common;

import com.kazma233.blog.entity.common.exception.HttpException;
import com.kazma233.blog.entity.result.enums.Status;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
public class HttpUtils {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

    public static String getContentByURL(String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder().
                GET().
                uri(URI.create(url)).
                timeout(Duration.ofSeconds(30)).
                build();

        try {
            return HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
        } catch (IOException | InterruptedException e) {
            log.error("request {} failed, cause: {}", url, e);
            throw new HttpException(Status.HTTP_ERROR);
        }
    }

}
