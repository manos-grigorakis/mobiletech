package com.mgrigorakis.mobiletech.storage;

public interface FileStorageService {
    void store(String key, byte[] content, String contentType);

    String getUrl(String prefix, String key);
}
