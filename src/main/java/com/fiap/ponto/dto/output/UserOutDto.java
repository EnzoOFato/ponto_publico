package com.fiap.ponto.dto.output;

import java.time.LocalDateTime;
import java.util.Set;

public record UserOutDto(
    Long id,
    String name,
    Set<Long> lessonsIds,
    LocalDateTime lastLogin
) {
    
}
