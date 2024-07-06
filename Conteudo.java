import org.bson.types.ObjectId;

public class Conteudo {
    private ObjectId id;
    private String titulo;
    private String texto;

    public Conteudo(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void consumir() {
        System.out.println("Consumindo conteúdo: " + titulo);
        System.out.println(texto);
    }

    // Métodos CRUD para Conteudo no MongoDB
    public static void inserirConteudo(Conteudo conteudo) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("conteudos");
            Gson gson = new Gson();
            String json = gson.toJson(conteudo);
            Document doc = Document.parse(json);
            collection.insertOne(doc);
            System.out.println("Conteúdo inserido com sucesso.");
        }
    }

    public static Conteudo buscarConteudoPorId(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("conteudos");
            Document doc = collection.find(new Document("_id", new ObjectId(id))).first();
            if (doc != null) {
                Gson gson = new Gson();
                return gson.fromJson(doc.toJson(), Conteudo.class);
            }
        }
        return null;
    }

    public static void atualizarConteudo(Conteudo conteudo) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("conteudos");
            Gson gson = new Gson();
            String json = gson.toJson(conteudo);
            Document doc = Document.parse(json);
            collection.updateOne(new Document("_id", conteudo.getId()), new Document("$set", doc));
            System.out.println("Conteúdo atualizado com sucesso.");
        }
    }

    public static void deletarConteudo(String id) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("sistema_cursos");
            MongoCollection<Document> collection = database.getCollection("conteudos");
            collection.deleteOne(new Document("_id", new ObjectId(id)));
            System.out.println("Conteúdo deletado com sucesso.");
        }
    }
}
