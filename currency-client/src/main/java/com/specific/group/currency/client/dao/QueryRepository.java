package com.specific.group.currency.client.dao;

import com.specific.group.currency.client.model.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueryRepository  extends ReactiveCassandraRepository<Query, UUID> {
}
