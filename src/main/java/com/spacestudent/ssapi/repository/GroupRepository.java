package com.spacestudent.ssapi.repository;

import com.spacestudent.ssapi.model.Group;
import com.spacestudent.ssapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
}
