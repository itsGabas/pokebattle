package com.pokemon.pokebattle.service;

import com.pokemon.pokebattle.dto.request.TreinadorRequestDTO;
import com.pokemon.pokebattle.dto.response.TreinadorResponseDTO;
import com.pokemon.pokebattle.exception.BusinessException;
import com.pokemon.pokebattle.model.Pokemon;
import com.pokemon.pokebattle.model.Treinador;
import com.pokemon.pokebattle.repository.TreinadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreinadorService {

    private final TreinadorRepository treinadorRepository;

    public TreinadorResponseDTO criar(TreinadorRequestDTO dto){
        Treinador treinador = new Treinador();
        treinador.setNome(dto.nome());
        return toResponseDTO(treinadorRepository.save(treinador));

    }

    public TreinadorResponseDTO buscarPorId(Long id){
        Treinador treinador = treinadorRepository.findById(id).orElseThrow(() -> new BusinessException("Treinador não encontrado: " + id));
        return toResponseDTO(treinador);
    }

    public List<TreinadorResponseDTO> listarTodos(){
        return treinadorRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    private TreinadorResponseDTO toResponseDTO(Treinador treinador){
        List<String> nomesPokemons = treinador.getPokemons() == null ? List.of() : treinador.getPokemons().stream().map(Pokemon::getNome).toList();
        return new TreinadorResponseDTO(treinador.getId(), treinador.getNome(), nomesPokemons);
    }
}
