package com.h3c.vdi.viewscreen.entity;

/**
 * Created by x19765 on 2020/10/15.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_subtitle")
@Accessors(chain = true)
public class SubTitle implements Serializable {

    private static final long serialVersionUID = -79643005390874746L;

    public static final String LOGO_TITLE = "logoTitle";
    public static final String MAP = "map";
    public static final String SITE_DYNAMICS = "siteDynamics";
    public static final String SITE_INFORMATION = "siteInformation";
    public static final String REGIONAL_DISTRIBUTION = "regionalDistribution";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

}
