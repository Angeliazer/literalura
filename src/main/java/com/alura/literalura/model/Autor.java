package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Autor(
        @JsonAlias("name") String autor,
        @JsonAlias("birth_year") int anoNascimento,
        @JsonAlias("death_year") int anoFalecimento) {
}
