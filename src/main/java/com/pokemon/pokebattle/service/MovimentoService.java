package com.pokemon.pokebattle.service;

import com.pokemon.pokebattle.dto.request.MovimentoRequestDTO;
import com.pokemon.pokebattle.dto.response.MovimentoResponseDTO;
import com.pokemon.pokebattle.exception.BusinessException;
import com.pokemon.pokebattle.model.Movimento;
import com.pokemon.pokebattle.model.Tipo;
import com.pokemon.pokebattle.repository.MovimentoRepository;
import com.pokemon.pokebattle.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentoService {

    private final MovimentoRepository movimentoRepository;
    private final TipoRepository tipoRepository;

    public MovimentoResponseDTO criar(MovimentoRequestDTO dto) {
        Tipo tipo = tipoRepository.findById(dto.tipoId()).orElseThrow(() -> new BusinessException("Tipo não encontrado: " + dto.tipoId()));

        Movimento movimento = new Movimento();
        movimento.setNome(dto.nome());
        movimento.setDanoBase(dto.danoBase());
        movimento.setPrecisao(dto.precisao());
        movimento.setTipo(tipo);

        return toResponseDTO(movimentoRepository.save(movimento));
    }

    public MovimentoResponseDTO buscarPorId(Long id) {
        Movimento movimento = movimentoRepository.findById(id).orElseThrow(() -> new BusinessException("Movimento não encontrado: " + id));
        return toResponseDTO(movimento);
    }

    public List<MovimentoResponseDTO> listarTodos() {
        return movimentoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    private MovimentoResponseDTO toResponseDTO(Movimento movimento) {
        return new MovimentoResponseDTO(movimento.getId(), movimento.getNome(), movimento.getDanoBase(), movimento.getPrecisao(), movimento.getTipo().getNome()
        );
    }
}