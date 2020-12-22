package com.h3c.vdi.viewscreen.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2020/10/14 15:51
 * @Created by lgw2845
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestData<T> {
    private T data;
}
