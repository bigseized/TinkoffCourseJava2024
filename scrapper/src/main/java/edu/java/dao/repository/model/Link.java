package edu.java.dao.repository.model;

import java.net.URI;
import java.sql.Timestamp;

public record Link(long id, URI url, Timestamp timestamp) {
}
