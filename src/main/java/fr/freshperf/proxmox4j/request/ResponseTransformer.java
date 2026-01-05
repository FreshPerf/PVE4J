package fr.freshperf.proxmox4j.request;

import com.google.gson.JsonElement;

public interface ResponseTransformer {
    
    JsonElement transform(JsonElement jsonElement, Class<?> targetClass);
    
}

