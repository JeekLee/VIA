package com.via.core.dto;

import java.io.IOException;
import java.io.InputStream;

public record ImageDownloadResult(
        InputStream inputStream,
        String filename,
        String contentType,
        long size
) implements AutoCloseable {
    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }
}
