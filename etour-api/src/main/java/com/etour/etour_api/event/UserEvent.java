package com.etour.etour_api.event;

import com.etour.etour_api.entity.UserEntity;
import com.etour.etour_api.enumeration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @version 1.0
 * @project etour-api
 * @since 27-01-2025
 */

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;
}
