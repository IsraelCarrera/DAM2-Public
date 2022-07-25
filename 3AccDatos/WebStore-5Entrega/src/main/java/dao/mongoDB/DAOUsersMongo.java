package dao.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import configuration.ConfigProperties;
import dao.DAOUsers;
import lombok.extern.log4j.Log4j2;
import model.Converters;
import model.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Log4j2
public class DAOUsersMongo implements DAOUsers {
    MongoClient mongo = MongoClients.create(ConfigProperties.getInstance().getProperty("urlMongoDB"));
    MongoDatabase db = mongo.getDatabase(ConfigProperties.getInstance().getProperty("mongoDBName"));
    MongoCollection<Document> est = db.getCollection("User");
    List<Document> users = new ArrayList<>();
    Converters converters = new Converters();

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User getUserLoggin(String username, String password) {
        User user = null;
        try {
            List<User> userList = est
                    .find(and(eq("username", username), eq("password", password)))
                    .into(users)
                    .stream()
                    .map(it -> converters.convertDocumentToUser(it))
                    .collect(Collectors.toList());
            if (!userList.isEmpty()) {
                user = userList.get(0);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return user;
    }

    @Override
    public boolean addUser(User u) {
        boolean isAdd = false;
        try {
            Document document = converters.convertUserToDocument(u);
            est.insertOne(document);
            isAdd = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isAdd;
    }

    @Override
    public boolean deleteUser(String dni) {
        boolean isDelete = false;
        //Borrar un item.
        try {
            est.deleteOne(eq("_id", dni));
            isDelete = true;
        } catch (Exception e) {
            log.error(e);
        } finally {
            mongo.close();
        }
        return isDelete;
    }
}
