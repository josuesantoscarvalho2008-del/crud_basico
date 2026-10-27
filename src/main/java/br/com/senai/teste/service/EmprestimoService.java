package br.com.senai.teste.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.senai.teste.model.Aluno;
import br.com.senai.teste.model.Emprestimo;
import br.com.senai.teste.model.Livro;
import br.com.senai.teste.repository.AlunoRepository;
import br.com.senai.teste.repository.EmprestimoRepository;
import br.com.senai.teste.repository.LivroRepository;

@Service 
public class EmprestimoService {
   
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final AlunoRepository alunoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository, AlunoRepository alunoRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.alunoRepository = alunoRepository;
    }
    public Optional<Emprestimo> cadastrar(
        Integer livroId, Integer alunoId, LocalDate dataPrevistaDevolucao) {

        Optional<Livro> livro = livroRepository.findById(livroId);
        Optional<Aluno> aluno = alunoRepository.findById(alunoId);

        if(aluno. isEmpty() || livro.isEmpty()) {
            return Optional.empty();
        }

        boolean livroEmprestado = emprestimoRepository
        .existsByLivroIdAndDataDevolucaoIsNull(livroId);

        if (livroEmprestado) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "O livro já está emprestado");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro.get());
        emprestimo.setAluno(aluno.get());
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevistaDevolucao(dataPrevistaDevolucao);

        return Optional.of(emprestimoRepository.save(emprestimo));  

    }
    public List<Emprestimo> listar() {
        return emprestimoRepository.findAll();
    }
    public Optional<Emprestimo> buscarPorId(Integer id) {
        return emprestimoRepository.findById(id);
    }
    public Optional<Emprestimo> devolver(Integer id) {
        Optional<Emprestimo> encontrado = emprestimoRepository.findById(id);

        if (encontrado.isEmpty()) {
            return Optional.empty();
        }
        
        Emprestimo emprestimo = encontrado.get();

        if (emprestimo.getDataDevolucao() == null) {
            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimoRepository.save(emprestimo);
        }
        return Optional.of(emprestimo);
    }
    public List<Emprestimo> listarAtivos() {
        return emprestimoRepository.findByDataDevolucaoIsNull();
    }
    public List<Emprestimo> listarPorAluno(Integer alunoId) {
        return emprestimoRepository.findByAlunoId(alunoId);
    }
    public List<Emprestimo> listarAtrasados(){
        return emprestimoRepository.findByDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(
            LocalDate.now());
    }
    public List<Emprestimo> listarAtrasadosPorAluno(Integer alunoId){
        return emprestimoRepository.findByAlunoIdAndDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(
            alunoId, LocalDate.now());
    }
}
