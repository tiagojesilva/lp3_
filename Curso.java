import org.bson.types.ObjectId;

public class Curso {
    private ObjectId id;
    private String nome;
    private String descricao;
    private double preco;

    public Curso(String nome, String descricao, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Métodos CRUD para Curso no MongoDB
    public static void inserirCurso(Curso curso) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("cursos");
            Gson gson = new Gson();
            String json = gson.toJson(curso);
            Document doc = Document.parse(json);
            collection.insertOne(doc);
            System.out.println("Curso inserido com sucesso.");
        }
    }

    public static Curso buscarCursoPorId(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("cursos");
            Document doc = collection.find(new Document("_id", new ObjectId(id))).first();
            if (doc != null) {
                Gson gson = new Gson();
                return gson.fromJson(doc.toJson(), Curso.class);
            }
        }
        return null;
    }

    public static void atualizarCurso(Curso curso) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("cursos");
            Gson gson = new Gson();
            String json = gson.toJson(curso);
            Document doc = Document.parse(json);
            collection.updateOne(new Document("_id", curso.getId()), new Document("$set", doc));
            System.out.println("Curso atualizado com sucesso.");
        }
    }

    public static void deletarCurso(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("cursos");
            collection.deleteOne(new Document("_id", new ObjectId(id)));
            System.out.println("Curso deletado com sucesso.");
        }
    }
}
