package com.agendamento.servicos_tecnicos.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String tipo;
    private Long usuarioId;
    private String nome;
    private String email;
    private String role;
}