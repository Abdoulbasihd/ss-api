package com.spacestudent.ssapi.repository;

import com.spacestudent.ssapi.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
