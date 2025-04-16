package com.example.Backend_gestion_cabinet_medical.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class ConsultationRequest {
    private long id;
    private String diagnostique;
    private LocalDateTime date;
}
