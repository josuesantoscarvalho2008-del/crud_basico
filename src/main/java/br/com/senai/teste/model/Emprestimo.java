package br.com.senai.teste.model;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "emprestimo")
public class Emprestimo {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @ManyToOne 
    @JoinColumn (name = "livro_id", nullable = false)
    private Livro livro;
    private LocalDate dataEmprestimo;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;
    private LocalDate dataDevolucao; 
    private LocalDate dataPrevistaDevolucao;  

    public Emprestimo() {
    }
    public Emprestimo( LocalDate dataPrevistaDevolucao, Livro livro, LocalDate dataEmprestimo,LocalDate dataDevolucao, Aluno aluno) {
      
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.aluno = aluno;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }
    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }
    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    public int getId() {
        return id;
    }
    public Livro getLivro() {
        return livro;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public Aluno getAluno() {
        return aluno;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }



}
