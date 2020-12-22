package com.h3c.vdi.viewscreen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname User
 * @Date 2020/7/21 11:06
 * @Created by lgw2845
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_user")
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 7548076778545265514L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name = "logic_delete")
    private String logicDelete;

    @Column(name = "reserver")
    private String reserver;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "version")
    private String version;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonBackReference
    private List<Role> roles;
}
