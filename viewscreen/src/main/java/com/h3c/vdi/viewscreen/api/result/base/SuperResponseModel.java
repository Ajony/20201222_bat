package com.h3c.vdi.viewscreen.api.result.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础通用Model
 * </p>
 *
 * @author lgq
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "基础通用Model")
public class SuperResponseModel extends SuperModel {

    private static final long serialVersionUID = 6712476263796560176L;
}