package com.pokemon.pokebattle.service;

import com.pokemon.pokebattle.dto.request.PokemonRequestDTO;
import com.pokemon.pokebattle.dto.response.PokemonResponseDTO;
import com.pokemon.pokebattle.exception.BusinessException;
import com.pokemon.pokebattle.exception.PokemonNotFoundException;
import com.pokemon.pokebattle.exception.TimeCompletoException;
import com.pokemon.pokebattle.model.Movimento;
import com.pokemon.pokebattle.model.Pokemon;
import com.pokemon.pokebattle.model.Tipo;
import com.pokemon.pokebattle.model.Treinador;
import com.pokemon.pokebattle.repository.MovimentoRepository;
import com.pokemon.pokebattle.repository.PokemonRepository;
import com.pokemon.pokebattle.repository.TipoRepository;
import com.pokemon.pokebattle.repository.TreinadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TipoRepository tipoRepository;
    private final MovimentoRepository movimentoRepository;
    private final TreinadorRepository treinadorRepository;

    public PokemonResponseDTO criar(PokemonRequestDTO dto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setNome(dto.nome());
        pokemon.setHp(dto.hp());
        pokemon.setAtk(dto.atk());
        pokemon.setDef(dto.def());
        pokemon.setSpd(dto.spd());
        pokemon.setHpAtual(dto.hp());

        List<Tipo> tipos = tipoRepository.findAllById(dto.tipoIds());
        pokemon.setTipos(tipos);

        List<Movimento> moves = movimentoRepository.findAllById(dto.moveIds());
        pokemon.setMoves(moves);

        if (dto.treinadorId() != null) {
            associarTreinador(pokemon, dto.treinadorId());
        }

        Pokemon salvo = pokemonRepository.save(pokemon);
        return toResponseDTO(salvo);
    }

    public PokemonResponseDTO buscarPorId(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokémon não encontrado: " + id));
        return toResponseDTO(pokemon);
    }

    public List<PokemonResponseDTO> listarTodos() {
        return pokemonRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void deletar(Long id) {
        if (!pokemonRepository.existsById(id)) {
            throw new PokemonNotFoundException("Pokémon não encontrado: " + id);
        }
        pokemonRepository.deleteById(id);
    }

    public PokemonResponseDTO adicionarAoTime(Long pokemonId, Long treinadorId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokémon não encontrado: " + pokemonId));

        associarTreinador(pokemon, treinadorId);

        return toResponseDTO(pokemonRepository.save(pokemon));
    }

    private void associarTreinador(Pokemon pokemon, Long treinadorId) {
        Treinador treinador = treinadorRepository.findById(treinadorId)
                .orElseThrow(() -> new BusinessException("Treinador não encontrado: " + treinadorId));

        long quantidadeAtual = pokemonRepository.findByTreinadorId(treinadorId).size();
        if (quantidadeAtual >= 6) {
            throw new TimeCompletoException("O time desse treinador já tem 6 pokémon");
        }

        pokemon.setTreinador(treinador);
    }

    private PokemonResponseDTO toResponseDTO(Pokemon pokemon) {
        return new PokemonResponseDTO(
                pokemon.getId(), pokemon.getNome(), pokemon.getHp(),
                pokemon.getAtk(), pokemon.getDef(), pokemon.getSpd(),
                pokemon.getTipos().stream().map(Tipo::getNome).toList(),
                pokemon.getMoves().stream().map(Movimento::getNome).toList()
        );
    }
}
