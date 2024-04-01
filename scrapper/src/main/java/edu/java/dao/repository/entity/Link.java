package edu.java.dao.repository.entity;

import jakarta.annotation.Nullable;
import java.net.URI;
import java.sql.Timestamp;

public record Link(@Nullable Long id, URI resource, @Nullable Timestamp updatedAt) {
}
