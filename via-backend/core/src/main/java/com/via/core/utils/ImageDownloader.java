package com.via.core.utils;

import com.via.core.dto.ImageDownloadResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public class ImageDownloader {
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(CONNECT_TIMEOUT)
            .followRedirects(HttpClient.Redirect.NORMAL) // Auto redirect
            .build();

    public static ImageDownloadResult download(String imageUrl) throws IOException, InterruptedException {
        URI uri = URI.create(imageUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(REQUEST_TIMEOUT)
                .header("User-Agent", "Mozilla/5.0")
                .GET()
                .build();

        HttpResponse<byte[]> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) throw new IOException("HTTP error code: " + response.statusCode());

        byte[] imageData = response.body();
        long size = imageData.length;

        if (size > MAX_FILE_SIZE) throw new IOException("Too large image: " + size + " bytes(max: " + MAX_FILE_SIZE + ")");

        String contentType = extractContentType(response.headers(), imageUrl);
        String filename = extractFilename(response.headers(), imageUrl);
        InputStream inputStream = new ByteArrayInputStream(imageData);

        return new ImageDownloadResult(inputStream, filename, contentType, size);
    }

    private static String extractContentType(HttpHeaders headers, String imageUrl) {
        Optional<String> contentType = headers.firstValue("Content-Type");

        if (contentType.isPresent()) {
            String type = contentType.get();
            if (type.startsWith("image/")) {
                // Handle cases like "image/jpeg; charset=utf-8"
                int semicolonIndex = type.indexOf(';');
                if (semicolonIndex > 0) {
                    type = type.substring(0, semicolonIndex);
                }
                return type.trim();
            }
        }

        return inferContentTypeFromExtension(imageUrl);
    }

    private static String extractFilename(HttpHeaders headers, String imageUrl) {
        Optional<String> contentDisposition = headers.firstValue("Content-Disposition");
        if (contentDisposition.isPresent()) {
            String filename = parseFilenameFromContentDisposition(contentDisposition.get());
            if (filename != null) {
                return filename;
            }
        }

        return extractFilenameFromUrl(imageUrl);
    }

    private static String parseFilenameFromContentDisposition(String contentDisposition) {
        String[] parts = contentDisposition.split(";");
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("filename=")) {
                String filename = part.substring("filename=".length());
                // Remove quotes
                filename = filename.replaceAll("^\"|\"$", "");
                return filename;
            } else if (part.startsWith("filename*=")) {
                String filename = part.substring("filename*=".length());
                // Extract filename from UTF-8''filename.jpg format
                int lastQuoteIndex = filename.lastIndexOf("''");
                if (lastQuoteIndex >= 0) {
                    filename = filename.substring(lastQuoteIndex + 2);
                }
                return filename;
            }
        }
        return null;
    }

    private static String extractFilenameFromUrl(String imageUrl) {
        try {
            URI uri = URI.create(imageUrl);
            String path = uri.getPath();

            if (path == null || path.isEmpty()) {
                return generateDefaultFilename(imageUrl);
            }

            int lastSlashIndex = path.lastIndexOf('/');
            String filename = (lastSlashIndex >= 0) ? path.substring(lastSlashIndex + 1) : path;

            if (filename.isEmpty() || !filename.contains(".")) {
                return generateDefaultFilename(imageUrl);
            }

            return filename;

        } catch (Exception e) {
            return generateDefaultFilename(imageUrl);
        }
    }

    private static String generateDefaultFilename(String imageUrl) {
        String extension = extractExtension(imageUrl);
        if (extension.isEmpty()) {
            extension = ".jpg"; // Default extension
        }
        return UUID.randomUUID().toString() + extension;
    }

    private static String inferContentTypeFromExtension(String imageUrl) {
        String extension = extractExtension(imageUrl).toLowerCase();

        return switch (extension) {
            case ".jpg", ".jpeg" -> "image/jpeg";
            case ".png" -> "image/png";
            case ".gif" -> "image/gif";
            case ".webp" -> "image/webp";
            case ".svg" -> "image/svg+xml";
            case ".bmp" -> "image/bmp";
            case ".ico" -> "image/x-icon";
            case ".tiff", ".tif" -> "image/tiff";
            case ".avif" -> "image/avif";
            case ".heic", ".heif" -> "image/heic";
            default -> "image/jpeg"; // Default value
        };
    }

    private static String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }

        try {
            URI uri = URI.create(filename);
            String path = uri.getPath();
            if (path != null) {
                filename = path;
            }
        } catch (Exception e) {

        }

        int lastDotIndex = filename.lastIndexOf(".");
        int lastSlashIndex = filename.lastIndexOf("/");

        if (lastDotIndex > lastSlashIndex && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex);
        }

        return "";
    }
}
