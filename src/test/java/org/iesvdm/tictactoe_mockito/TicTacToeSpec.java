package org.iesvdm.tictactoe_mockito;

import com.mongodb.MongoException;
import org.jongo.MongoCollection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Si falla en Run > Edit Configuration > añadir argjvm: --add-opens java.base/java.lang=ALL-UNNAMED
 */
@ExtendWith(MockitoExtension.class)
public class TicTacToeSpec {


    private TicTacToe ticTacToe;
    private TicTacToeCollection collection;
    private TicTacToeBean bean;
    private MongoCollection mongoCollection;

    @BeforeEach
    public final void beforeEach() {
        ticTacToe = new TicTacToe();
        collection = spy(new TicTacToeCollection());

        bean = new TicTacToeBean(3, 2, 1, 'Y');
        mongoCollection = mock(MongoCollection.class);

        ticTacToe.setTicTacToeCollection(collection);

    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
       assertThrows(RuntimeException.class, () -> ticTacToe.play(5, 2));
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> ticTacToe.play(2, 5));
    }

    @Test
    public void whenOccupiedThenRuntimeException() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        ticTacToe.play(1, 1);
        ticTacToe.play(2, 1);
        assertThrows(RuntimeException.class, () -> ticTacToe.play(2, 1));
    }

    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        assertEquals('X', ticTacToe.nextPlayer());
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));

        ticTacToe.play(1, 1);
        assertEquals('O', ticTacToe.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));

        String actual = ticTacToe.play(1, 1);
        assertEquals("No winner", actual);
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 1); // X
        ticTacToe.play(2, 2); // O
        String actual = ticTacToe.play(3, 1); // X
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        ticTacToe.play(2, 1); // X
        ticTacToe.play(1, 1); // O
        ticTacToe.play(3, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        String actual = ticTacToe.play(1, 3); // O
        assertEquals("O is the winner", actual);
    }

    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));

        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 3); // O
        String actual = ticTacToe.play(3, 3); // O
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        ticTacToe.play(1, 3); // X
        ticTacToe.play(1, 1); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 2); // O
        String actual = ticTacToe.play(3, 1); // O
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2);
        ticTacToe.play(1, 3);
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 3);
        ticTacToe.play(2, 2);
        ticTacToe.play(3, 1);
        ticTacToe.play(3, 3);
        String actual = ticTacToe.play(3, 2);
        assertEquals("The result is draw", actual);
    }

    @Test
    public void whenInstantiatedThenMongoHasDbNameTicTacToe() {
        assertEquals("tic-tac-toe", collection.getMongoCollection().getDBCollection().getDB().getName());
    }

    @Test
    public void whenInstantiatedThenMongoCollectionHasNameGame() {
        assertEquals("game", collection.getMongoCollection().getName());

    }

    @Test
    public void whenSaveMoveThenInvokeMongoCollectionSave() {
        doReturn(mongoCollection).when(collection).getMongoCollection();

        collection.saveMove(bean);
        verify(mongoCollection, times(1)).save(bean);
    }

    @Test
    public void whenSaveMoveThenReturnTrue() {
        doReturn(mongoCollection).when(collection).getMongoCollection();

        assertTrue(collection.saveMove(bean));
    }

    @Test
    public void givenExceptionWhenSaveMoveThenReturnFalse() {
        doThrow(new MongoException("blah blah")).when(mongoCollection).save(any(TicTacToeBean.class));

        doReturn(mongoCollection).when(collection).getMongoCollection();

        assertFalse(collection.saveMove(bean));
    }

    @Test
    public void whenDropThenReturnTrue(){
        doReturn(mongoCollection).when(collection).getMongoCollection();

        assertTrue(collection.drop());
    }

    @Test
    public void whenInstantiatedThenSetCollection() {
        assertNotNull(ticTacToe.getCollection());
    }


    @Test
    public void whenPlayThenSaveMoveInvoked() {
        //en el spy collection se modifica el comportamiento de saveMove, como si guardara la jugada
        //independientemente de que exista mongoDB, ¿qué pasa si comentas esta línea?
        doReturn(true).when(collection).saveMove(any(TicTacToeBean.class));
        TicTacToeBean bean = new TicTacToeBean(1, 1, 1, 'X');
        ticTacToe.play(bean.getX(), bean.getY());
        verify(collection, times(1)).saveMove(bean);
    }

    @Test
    public void whenPlayAndSaveReturnsFalseThenException() {

        doReturn(false).when(collection).saveMove(any(TicTacToeBean.class));
        TicTacToeBean move = new TicTacToeBean(1, 1, 1, 'Y');
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> ticTacToe.play(move.getX(), move.getY()));
        assertEquals("saving to DB failed", thrown.getMessage());

    }
}
