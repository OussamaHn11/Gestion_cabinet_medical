package com.example.Backend_gestion_cabinet_medical.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data

public class ConsultationRequest {
    private long id;
    private String diagnostique;
    private LocalDateTime date;
}
