package com.spacestudent.ssapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "ss_user")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractModel implements Serializable, UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username; // Phone or ??

    private String password;

    private String names;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private boolean enabled;

    private boolean isStudent;

    private boolean isSecondarySchoolStudent;

    private String examinationLevel;

    @ManyToOne
    @JoinColumn(name = "fk_group_id", nullable = false)
    private Group group;

    //@OneToMany(mappedBy = "user")
    //@JsonBackReference
    //private Set<Message> messages;


    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
