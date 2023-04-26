package org.iesvdm.tictactoe_mockito;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * @author Johnson
 * @date 2020/2/28 13:47
 */
public class TicTacToeCollection {
    private MongoCollection mongoCollection;

    protected MongoCollection getMongoCollection() {
        return mongoCollection;
    }

    public TicTacToeCollection() {
        DB db = new MongoClient().getDB("tic-tac-toe");
        mongoCollection = new Jongo(db).getCollection("game");
    }

    public boolean saveMove(TicTacToeBean bean) {
        try {
            getMongoCollection().save(bean);
        } catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean drop() {
        try {
            getMongoCollection().drop();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
