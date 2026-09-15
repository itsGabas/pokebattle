package com.pokemon.pokebattle.service;

import com.pokemon.pokebattle.dto.request.AtaqueRequestDTO;
import com.pokemon.pokebattle.dto.response.TurnoResponseDTO;
import com.pokemon.pokebattle.exception.BusinessException;
import com.pokemon.pokebattle.exception.PokemonNotFoundException;
import com.pokemon.pokebattle.model.*;
import com.pokemon.pokebattle.model.enums.StatusBatalha;
import com.pokemon.pokebattle.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatalhaService {

    private final BatalhaRepository batalhaRepository;
    private final PokemonRepository pokemonRepository;
    private final MovimentoRepository movimentoRepository;
    private final TurnoRepository turnoRepository;
    private final EfetividadeService efetividadeService;

    public Batalha iniciarBatalha(Long pokemonAId, Long pokemonBId) {
        Pokemon pokemonA = pokemonRepository.findById(pokemonAId).orElseThrow(() -> new PokemonNotFoundException("Pokémon A não encontrado"));
        Pokemon pokemonB = pokemonRepository.findById(pokemonBId).orElseThrow(() -> new PokemonNotFoundException("Pokémon B não encontrado"));

        pokemonA.setHpAtual(pokemonA.getHp());
        pokemonB.setHpAtual(pokemonB.getHp());
        pokemonRepository.save(pokemonA);
        pokemonRepository.save(pokemonB);

        Batalha batalha = new Batalha();
        batalha.setPokemonA(pokemonA);
        batalha.setPokemonB(pokemonB);
        batalha.setStatus(StatusBatalha.EM_ANDAMENTO);

        return batalhaRepository.save(batalha);
    }

    public TurnoResponseDTO atacar(Long batalhaId, AtaqueRequestDTO dto) {
        Batalha batalha = batalhaRepository.findById(batalhaId).orElseThrow(() -> new BusinessException("Batalha não encontrada"));

        if (batalha.getStatus() == StatusBatalha.FINALIZADA) {
            throw new BusinessException("Essa batalha já foi finalizada");
        }

        Pokemon atacante = pokemonRepository.findById(dto.atacanteId()).orElseThrow(() -> new PokemonNotFoundException("Atacante não encontrado"));

        Pokemon defensor = resolverDefensor(batalha, atacante);

        Movimento movimento = movimentoRepository.findById(dto.movimentoId()).orElseThrow(() -> new BusinessException("Movimento não encontrado"));

        double multiplicador = efetividadeService.calcularMultiplicador(
                movimento.getTipo().getNome(), defensor.getTipos()
        );

        int dano = calcularDano(atacante, defensor, movimento, multiplicador);

        int hpRestante = Math.max(0, defensor.getHpAtual() - dano);
        defensor.setHpAtual(hpRestante);
        pokemonRepository.save(defensor);

        if (hpRestante == 0) {
            batalha.setStatus(StatusBatalha.FINALIZADA);
            batalhaRepository.save(batalha);
        }

        Turno turno = new Turno();
        turno.setNumero(batalha.getTurnos().size() + 1);
        turno.setAtacante(atacante);
        turno.setMovimentoUsado(movimento);
        turno.setDanoCausado(dano);
        turno.setBatalha(batalha);
        turnoRepository.save(turno);

        return new TurnoResponseDTO(
                turno.getNumero(), atacante.getNome(), movimento.getNome(), dano, hpRestante, batalha.getStatus().name()
        );
    }

    private Pokemon resolverDefensor(Batalha batalha, Pokemon atacante) {
        if (atacante.getId().equals(batalha.getPokemonA().getId())) {
            return batalha.getPokemonB();
        }
        else if (atacante.getId().equals(batalha.getPokemonB().getId())) {
            return batalha.getPokemonA();
        }
        throw new BusinessException("Esse pokémon não faz parte dessa batalha");
    }

    private int calcularDano(Pokemon atacante, Pokemon defensor, Movimento movimento, double multiplicador) {
        double base = ((double) atacante.getAtk() * movimento.getDanoBase()) / defensor.getDef();
        return (int) Math.round(base * multiplicador);
    }
}