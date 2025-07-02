package com.alura.literalura.principal;

import com.alura.literalura.model.Livro;
import com.alura.literalura.model.LivroResponse;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;

import javax.swing.event.ListDataListener;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    Scanner leitura = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverteDados converteDados = new ConverteDados();

    private final LivroRepository livroRepository;

    public Principal(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    String endereco = "https://gutendex.com/books/?search=";

    public void exibirMenu() {

        var menu =
                """
                        ******* Escolha o número de sua opção ******
                        1. Buscar livro pelo título (Pesquisa API)
                        2. Listar livros registrados
                        3. Listar autores registrados
                        4. Listar autores vivos em determinado ano
                        5. Listar livros em um determinado idioma
                        0. Sair
                        """;
        var opcao = 9;
        while (opcao != 0) {
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    buscarLivroApi();
                    break;
                case 2:
                    buscarLivrosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    livrosPorIdioma();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção Inválida....!");
                    break;
            }
        }
    }

    private void buscarLivroApi() {

        System.out.println("Insira o nome do livro que você deseja procurar: ");
        var nome = leitura.nextLine();

        var json = consumoApi.obterDados(endereco + nome.replace(" ", "+"));

        var livroResponse = converteDados.getData(json, LivroResponse.class);

        Livro livro = new Livro();

        livro.setAutor(livroResponse.dadosLivro().get(0).authors().get(0).autor());
        livro.setTitulo(livroResponse.dadosLivro().get(0).titulo());
        livro.setIdioma(livroResponse.dadosLivro().get(0).idioma().get(0));
        livro.setNumeroDownload(livroResponse.dadosLivro().get(0).download());
        livro.setAnoNascimento(livroResponse.dadosLivro().get(0).authors().get(0).anoNascimento());
        livro.setAnoFalecimento(livroResponse.dadosLivro().get(0).authors().get(0).anoFalecimento());
        livroRepository.save(livro);

    }

    private void buscarLivrosRegistrados() {
        List<Livro> livrosRegistrados = livroRepository.listaLivrosRegistrados();
        livrosRegistrados.forEach(System.out::println);
    }

    private void autoresRegistrados() {
        List<Livro> autoresRegistrados = livroRepository.autoresRegistrados();
        autoresRegistrados.forEach(a -> System.out.println(
                "Autor : " + a.getAutor() + "\n" +
                        "Ano Nascimento : " + a.getAnoNascimento() + "\n" +
                        "Ano Falecimento : " + a.getAnoFalecimento() + "\n" +
                        "Livros : " + a.getTitulo() + "\n"

        ));
    }

    private void autoresVivosEmDeterminadoAno() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        var ano = leitura.nextInt();
        leitura.nextLine();
        List<Livro> autoresVivos = livroRepository.autoresVivosEmDeterminadoAno(ano);
        autoresVivos.forEach(a -> System.out.println(
                "Autor : " + a.getAutor() + "\n" +
                        "Ano Nascimento : " + a.getAnoNascimento() + "\n" +
                        "Ano Falecimento : " + a.getAnoFalecimento() + "\n" +
                        "Livros : " + a.getTitulo() + "\n"

        ));
    }

    private void livrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca: ");
        var idiomas = """
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """;
        System.out.println(idiomas);
        var idioma = leitura.nextLine().toLowerCase();
        List<Livro> livrosPorIdioma = livroRepository.livrosPorIdioma(idioma);
        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Não existem livros nesse idioma no banco de dados");
        } else {
            livrosPorIdioma.forEach(System.out::println);
        }
    }
}
