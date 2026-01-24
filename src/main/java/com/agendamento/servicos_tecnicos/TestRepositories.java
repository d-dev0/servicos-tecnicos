package com.agendamento.servicos_tecnicos;

import com.agendamento.servicos_tecnicos.entity.*;
import com.agendamento.servicos_tecnicos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

// Esta classe executa automaticamente quando a aplicação iniciar
@Component
@RequiredArgsConstructor
public class TestRepositories implements CommandLineRunner {

    // Spring injeta automaticamente os repositories
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n===== TESTANDO REPOSITORIES =====\n");

        // 1. Criar e salvar usuário (cabeleireiro)
        Usuario cabeleireiro = usuarioRepository.findByEmail("joao@email.com")
                .orElseGet(() -> {
                    Usuario novo = Usuario.builder()
                            .nome("João Silva")
                            .email("joao@email.com")
                            .senha(passwordEncoder.encode("123456"))
                            .role(Usuario.Role.CABELEREIRO)
                            .build();
                    return usuarioRepository.save(novo);
                });

        if (cabeleireiro.getRole() != Usuario.Role.CABELEREIRO) {
            cabeleireiro.setRole(Usuario.Role.CABELEREIRO);
            cabeleireiro = usuarioRepository.save(cabeleireiro);
        }

        if (cabeleireiro.getSenha() == null || !cabeleireiro.getSenha().startsWith("$2")) {
            cabeleireiro.setSenha(passwordEncoder.encode("123456"));
            cabeleireiro = usuarioRepository.save(cabeleireiro);
        }

        System.out.println("✅ Cabeleireiro: ID = " + cabeleireiro.getId());

        // 2. Criar e salvar serviço
        String nomeServicoSeed = "Corte Feminino";

        List<Servico> candidatos = servicoRepository.findByNomeContainingIgnoreCase(nomeServicoSeed);
        Servico servico = candidatos.stream()
                .filter(s -> s.getNome() != null && s.getNome().equalsIgnoreCase(nomeServicoSeed))
                .findFirst()
                .orElseGet(() -> {
                    Servico novo = Servico.builder()
                            .nome(nomeServicoSeed)
                            .duracao(45)
                            .descricao("Corte, finalização e escova")
                            .build();

                    return servicoRepository.save(novo);
                });
        System.out.println("✅ Serviço: ID = " + servico.getId());

        // 3. Criar e salvar agendamento
        LocalDateTime dataHoraAgendamento = LocalDateTime.now()
                .plusDays(1)
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        LocalDate dataAgendamento = dataHoraAgendamento.toLocalDate();
        boolean jaExisteAgendamentoNaData = !agendamentoRepository
                .findByUsuarioAndData(cabeleireiro.getId(), dataAgendamento)
                .isEmpty();

        if (!jaExisteAgendamentoNaData) {
            Agendamento agendamento = Agendamento.builder()
                    .dataHora(dataHoraAgendamento)
                    .usuario(cabeleireiro)
                    .servico(servico)
                    .status(Agendamento.Status.AGENDADO)
                    .build();

            agendamento = agendamentoRepository.save(agendamento);
            System.out.println("✅ Agendamento criado: ID = " + agendamento.getId());
        } else {
            System.out.println("ℹ️ Agendamento já existe para a data " + dataAgendamento);
        }

        // 4. Buscar dados
        long totalUsuarios = usuarioRepository.count();
        long totalServicos = servicoRepository.count();
        long totalAgendamentos = agendamentoRepository.count();

        System.out.println("\n📊 ESTATÍSTICAS:");
        System.out.println("Total de usuários: " + totalUsuarios);
        System.out.println("Total de serviços: " + totalServicos);
        System.out.println("Total de agendamentos: " + totalAgendamentos);

        System.out.println("\n===== TESTES CONCLUÍDOS =====\n");
    }
}