package com.h3c.vdi.viewscreen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname Role
 * @Date 2020/7/21 11:06
 * @Created by lgw2845
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -7177650924086567887L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

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

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<User> users;
}
