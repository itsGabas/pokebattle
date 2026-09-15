package com.pokemon.pokebattle.service;

import com.pokemon.pokebattle.model.Tipo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class EfetividadeService {

    private final Map<String, Map<String, Double>> tabela = Map.ofEntries(
            Map.entry("NORMAL", Map.of(
                    "PEDRA", 0.5, "ACO", 0.5,
                    "FANTASMA", 0.0
            )),
            Map.entry("FOGO", Map.of(
                    "PLANTA", 2.0, "GELO", 2.0, "INSETO", 2.0, "ACO", 2.0,
                    "FOGO", 0.5, "AGUA", 0.5, "PEDRA", 0.5, "DRAGAO", 0.5
            )),
            Map.entry("AGUA", Map.of(
                    "FOGO", 2.0, "TERRA", 2.0, "PEDRA", 2.0,
                    "AGUA", 0.5, "PLANTA", 0.5, "DRAGAO", 0.5
            )),
            Map.entry("ELETRICO", Map.of(
                    "AGUA", 2.0, "VOADOR", 2.0,
                    "PLANTA", 0.5, "ELETRICO", 0.5, "DRAGAO", 0.5,
                    "TERRA", 0.0
            )),
            Map.entry("PLANTA", Map.of(
                    "AGUA", 2.0, "TERRA", 2.0, "PEDRA", 2.0,
                    "FOGO", 0.5, "PLANTA", 0.5, "VENENO", 0.5, "VOADOR", 0.5,
                    "INSETO", 0.5, "DRAGAO", 0.5, "ACO", 0.5
            )),
            Map.entry("GELO", Map.of(
                    "PLANTA", 2.0, "TERRA", 2.0, "VOADOR", 2.0, "DRAGAO", 2.0,
                    "FOGO", 0.5, "AGUA", 0.5, "GELO", 0.5, "ACO", 0.5
            )),
            Map.entry("LUTADOR", Map.ofEntries(
                    Map.entry("NORMAL", 2.0), Map.entry("GELO", 2.0), Map.entry("PEDRA", 2.0),
                    Map.entry("SOMBRIO", 2.0), Map.entry("ACO", 2.0),
                    Map.entry("VENENO", 0.5), Map.entry("VOADOR", 0.5), Map.entry("PSIQUICO", 0.5),
                    Map.entry("INSETO", 0.5), Map.entry("FADA", 0.5),
                    Map.entry("FANTASMA", 0.0)
            )),
            Map.entry("VENENO", Map.of(
                    "PLANTA", 2.0, "FADA", 2.0,
                    "VENENO", 0.5, "TERRA", 0.5, "PEDRA", 0.5, "FANTASMA", 0.5,
                    "ACO", 0.0
            )),
            Map.entry("TERRA", Map.of(
                    "FOGO", 2.0, "ELETRICO", 2.0, "VENENO", 2.0, "PEDRA", 2.0, "ACO", 2.0,
                    "PLANTA", 0.5, "INSETO", 0.5,
                    "VOADOR", 0.0
            )),
            Map.entry("VOADOR", Map.of(
                    "PLANTA", 2.0, "LUTADOR", 2.0, "INSETO", 2.0,
                    "ELETRICO", 0.5, "PEDRA", 0.5, "ACO", 0.5
            )),
            Map.entry("PSIQUICO", Map.of(
                    "LUTADOR", 2.0, "VENENO", 2.0,
                    "PSIQUICO", 0.5, "ACO", 0.5,
                    "SOMBRIO", 0.0
            )),
            Map.entry("INSETO", Map.of(
                    "PLANTA", 2.0, "PSIQUICO", 2.0, "SOMBRIO", 2.0,
                    "FOGO", 0.5, "LUTADOR", 0.5, "VENENO", 0.5, "VOADOR", 0.5,
                    "FANTASMA", 0.5, "ACO", 0.5, "FADA", 0.5
            )),
            Map.entry("PEDRA", Map.of(
                    "FOGO", 2.0, "GELO", 2.0, "VOADOR", 2.0, "INSETO", 2.0,
                    "LUTADOR", 0.5, "TERRA", 0.5, "ACO", 0.5
            )),
            Map.entry("FANTASMA", Map.of(
                    "FANTASMA", 2.0, "PSIQUICO", 2.0,
                    "SOMBRIO", 0.5,
                    "NORMAL", 0.0
            )),
            Map.entry("DRAGAO", Map.of(
                    "DRAGAO", 2.0,
                    "ACO", 0.5,
                    "FADA", 0.0
            )),
            Map.entry("SOMBRIO", Map.of(
                    "FANTASMA", 2.0, "PSIQUICO", 2.0,
                    "LUTADOR", 0.5, "SOMBRIO", 0.5, "FADA", 0.5
            )),
            Map.entry("ACO", Map.of(
                    "GELO", 2.0, "PEDRA", 2.0, "FADA", 2.0,
                    "FOGO", 0.5, "AGUA", 0.5, "ELETRICO", 0.5, "ACO", 0.5
            )),
            Map.entry("FADA", Map.of(
                    "LUTADOR", 2.0, "DRAGAO", 2.0, "SOMBRIO", 2.0,
                    "FOGO", 0.5, "VENENO", 0.5, "ACO", 0.5
            ))
    );

    public double calcularMultiplicador(String tipoAtaque, String tipoDefesa) {
        return tabela.getOrDefault(tipoAtaque, Map.of()).getOrDefault(tipoDefesa, 1.0);
    }

    public double calcularMultiplicador(String tipoAtaque, List<Tipo> tiposDefensor) {
        double resultado = 1.0;
        for (Tipo tipo : tiposDefensor) {
            resultado *= calcularMultiplicador(tipoAtaque, tipo.getNome());

        }

        return resultado;
    }

}
