package com.agendamento.servicos_tecnicos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {
    private Long id;
    @NotBlank(message = "Nome do serviço é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;
    @NotNull(message = "Duração é obrigatória")
    @Min(
            value = 15,
            message = "Duração mínima é 15 minutos"
    )
    @Max(
            value = 480,
            message = "Duração máxima é 8 horas (480 minutos)"
    )
    private Integer duracao;
    @Size(
            max = 500,
            message = "Descrição pode ter no máximo 500 caracteres"
    )
    private String descricao;
}