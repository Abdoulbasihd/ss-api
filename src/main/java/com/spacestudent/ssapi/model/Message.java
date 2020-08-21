package com.spacestudent.ssapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "ss_message")
public class Message extends AbstractModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String messageText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_author", nullable = false)
    private User user;

}
