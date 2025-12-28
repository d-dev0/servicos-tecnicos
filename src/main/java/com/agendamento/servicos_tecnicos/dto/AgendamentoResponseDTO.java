package com.agendamento.servicos_tecnicos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponseDTO {
    private Long id;
    private LocalDateTime dataHora;
    private Long usuarioId;
    private String nomeUsuario;
    private String emailUsuario;
    private Long servicoId;
    private String nomeServico;
    private Integer duracaoServico;
    private String status;
}