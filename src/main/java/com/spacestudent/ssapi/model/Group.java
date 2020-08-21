package com.spacestudent.ssapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "ss_group")
public class Group extends AbstractModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    //@OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    //private Set<Message> messages;

    //@OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    //private Set<User> users;
}
