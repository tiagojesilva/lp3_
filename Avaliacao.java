import org.bson.types.ObjectId;

public class Avaliacao {
    private ObjectId id;
    private String pergunta;
    private String resposta;

    public Avaliacao(String pergunta) {
        this.pergunta = pergunta;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void responder(String resposta) {
        this.resposta = resposta;
    }

    // Métodos CRUD para Avaliacao no MongoDB
    public static void inserirAvaliacao(Avaliacao avaliacao) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("avaliacoes");
            Gson gson = new Gson();
            String json = gson.toJson(avaliacao);
            Document doc = Document.parse(json);
            collection.insertOne(doc);
            System.out.println("Avaliação inserida com sucesso.");
        }
    }

    public static Avaliacao buscarAvaliacaoPorId(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("avaliacoes");
            Document doc = collection.find(new Document("_id", new ObjectId(id))).first();
            if (doc != null) {
                Gson gson = new Gson();
                return gson.fromJson(doc.toJson(), Avaliacao.class);
            }
        }
        return null;
    }

    public static void atualizarAvaliacao(Avaliacao avaliacao) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("avaliacoes");
            Gson gson = new Gson();
            String json = gson.toJson(avaliacao);
            Document doc = Document.parse(json);
            collection.updateOne(new Document("_id", avaliacao.getId()), new Document("$set", doc));
            System.out.println("Avaliação atualizada com sucesso.");
        }
    }

    public static void deletarAvaliacao(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("avaliacoes");
            collection.deleteOne(new Document("_id", new ObjectId(id)));
            System.out.println("Avaliação deletada com sucesso.");
        }
    }
}
