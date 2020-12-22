package com.h3c.vdi.viewscreen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Date 2020/10/20 11:29
 * @Created by lgw2845
 * logo定制
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_logo_custom")
@Accessors(chain = true)
public class LogoCustom implements Serializable {

    private static final long serialVersionUID = -1113260275276242244L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "temporary_image", columnDefinition = "mediumblob")
    private byte[] temporaryImage;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "real_image", columnDefinition = "mediumblob")
    private byte[] realImage;

    @Column(name = "logic_delete")
    private String logicDelete;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;


}
