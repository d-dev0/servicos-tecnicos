package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AgendamentoRequestDTO {
    @NotBlank(message = "Data e hora são obrigatórios")
    private String dataHora;

    @Positive(message = "ID do usuário deve ser positivo")
    private Long usuarioId;

    @NotNull(message = "ID do serviço é obrigatório")
    @Positive(message = "ID do serviço deve ser positivo")
    private Long servicoId;
}