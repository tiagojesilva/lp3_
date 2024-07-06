
import com.google.gson.Gson;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    // Atributos
    private ObjectId id;
    private String nome;
    private int idade;
    private String matricula;
    private String curso;
    private String endereco;
    private String telefone;
    private String email;
    private double notaMedia;
    private int anoDeIngresso;
    private List<Curso> cursosComprados;
    private List<Avaliacao> avaliacoesRespondidas;

    // Construtor padrão
    public Aluno() {
        this.cursosComprados = new ArrayList<>();
        this.avaliacoesRespondidas = new ArrayList<>();
    }

    // Construtor com parâmetros
    public Aluno(String nome, int idade, String matricula, String curso, String endereco, String telefone, String email, double notaMedia, int anoDeIngresso) {
        this();
        this.nome = nome;
        this.idade = idade;
        this.matricula = matricula;
        this.curso = curso;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.notaMedia = notaMedia;
        this.anoDeIngresso = anoDeIngresso;
        this.cursosComprados = new ArrayList<>();
        this.avaliacoesRespondidas = new ArrayList<>();
    }

    // Métodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public int getAnoDeIngresso() {
        return anoDeIngresso;
    }

    public void setAnoDeIngresso(int anoDeIngresso) {
        this.anoDeIngresso = anoDeIngresso;
    }


    // Métodos adicionais
    public void comprarCurso(Curso curso) {
        cursosComprados.add(curso);
        System.out.println("Curso " + curso.getNome() + " comprado com sucesso.");
    }

    public void responderAvaliacao(Avaliacao avaliacao, String resposta) {
        avaliacao.responder(resposta);
        avaliacoesRespondidas.add(avaliacao);
        System.out.println("Avaliação respondida: " + avaliacao.getPergunta() + " - Resposta: " + resposta);
    }

    public void consumirConteudo(Conteudo conteudo) {
        conteudo.consumir();
    }

    public static void inserirAluno(Aluno aluno) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("alunos");
            Gson gson = new Gson();
            String json = gson.toJson(aluno);
            Document doc = Document.parse(json);
            collection.insertOne(doc);
            System.out.println("Aluno inserido com sucesso.");
        }
    }

    public static Aluno buscarAlunoPorId(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("alunos");
            Document doc = collection.find(new Document("_id", new ObjectId(id))).first();
            if (doc != null) {
                Gson gson = new Gson();
                return gson.fromJson(doc.toJson(), Aluno.class);
            }
        }
        return null;
    }

    public static void atualizarAluno(Aluno aluno) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("alunos");
            Gson gson = new Gson();
            String json = gson.toJson(aluno);
            Document doc = Document.parse(json);
            collection.updateOne(new Document("_id", aluno.getId()), new Document("$set", doc));
            System.out.println("Aluno atualizado com sucesso.");
        }
    }

    public static void deletarAluno(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("alunos");
            collection.deleteOne(new Document("_id", new ObjectId(id)));
            System.out.println("Aluno deletado com sucesso.");
        }
    }

    // Método para exibir informações do aluno
    public void exibirInformacoes() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Curso: " + curso);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Nota Média: " + notaMedia);
        System.out.println("Ano de Ingresso: " + anoDeIngresso);
        System.out.println("Cursos Comprados: ");
        for (Curso c : cursosComprados) {
            System.out.println(" - " + c.getNome());
        }
        System.out.println("Avaliações Respondidas: ");
        for (Avaliacao a : avaliacoesRespondidas) {
            System.out.println(" - Pergunta: " + a.getPergunta() + " | Resposta: " + a.getResposta());
        }
    }

    // Método main para testar a classe
    public static void main(String[] args) {
        // Criando um objeto Aluno
        Aluno aluno = new Aluno("João Silva", 20, "2023001", "Engenharia de Computação", "Rua A, 123", "11987654321", "joao.silva@example.com", 8.5, 2023);
        
        // Inserindo o aluno no MongoDB
        inserirAluno(aluno);
                
        // Criando e comprando cursos
        Curso curso1 = new Curso("Java para Iniciantes", "Curso básico de Java", 99.90);
        aluno.comprarCurso(curso1);

        // Respondendo avaliações
        Avaliacao avaliacao1 = new Avaliacao("O que é Java?");
        aluno.responderAvaliacao(avaliacao1, "Java é uma linguagem de programação.");

        // Consumindo conteúdo
        Conteudo conteudo1 = new Conteudo("Aula 1 - Introdução ao Java", "Nesta aula, vamos aprender sobre os conceitos básicos de Java...");
        aluno.consumirConteudo(conteudo1);

        // Exibindo as informações do aluno
        aluno.exibirInformacoes();

        // Buscando o aluno pelo ID (substitua pelo ID correto após inserção)
        Aluno alunoBuscado = buscarAlunoPorId(aluno.getId().toHexString());
        if (alunoBuscado != null) {
            alunoBuscado.exibirInformacoes();
        }

        // Atualizando informações do aluno
        aluno.setCurso("Engenharia de Software");
        atualizarAluno(aluno);

        // Deletando o aluno pelo ID
        deletarAluno(aluno.getId().toHexString());
    }

    // Getters e setters adicionais para o ID
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
