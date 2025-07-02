package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("Select l from Livro l")
    List<Livro> listaLivrosRegistrados();

    @Query("Select l from Livro l")
    List<Livro> autoresRegistrados();

    @Query("Select l from Livro l where :ano >= l.anoNascimento and :ano <= l.anoFalecimento")
    List<Livro> autoresVivosEmDeterminadoAno(Integer ano);

    @Query("Select l from Livro l where TRIM(l.idioma) = :idioma")
    List<Livro> livrosPorIdioma(String idioma);
}

