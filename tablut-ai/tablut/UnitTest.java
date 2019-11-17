package tablut;

import org.junit.Test;
import static org.junit.Assert.*;
import static tablut.Square.*;
import static tablut.Piece.*;
import ucb.junit.textui;

/** The suite of all JUnit tests for the enigma package.
 *  @author Anirudhan Badrinath
 */
public class UnitTest {

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    @Test
    public void initTest() {
        Board b = new Board();
        assertTrue("e5 should have king", b.get(sq("e5")) == Piece.KING);
        assertTrue("b5 should have black", b.get(sq("b5")) == Piece.BLACK);
        assertTrue("e6 should have white", b.get(sq("e6")) == Piece.WHITE);
        assertTrue("i1 should have empty", b.get(sq("i1")) == Piece.EMPTY);
    }

    @Test
    public void putTest1() {
        Board b = new Board();
        b.put(Piece.KING, sq("i1"));
        assertTrue("i1 should have king", b.get(sq("i1")) == Piece.KING);
    }

    @Test
    public void putTest2() {
        Board b = new Board();
        assertTrue("there should be 9 pieces", b.pieces(Piece.WHITE) == 9);
        assertTrue("there should be 16 pieces", b.pieces(Piece.BLACK) == 16);
        b.put(Piece.EMPTY, sq("e5"));
        assertTrue("there should be 8 pieces", b.pieces(Piece.WHITE) == 8);
        b.put(Piece.BLACK, sq("i1"));
        assertTrue("there should be 17 pieces", b.pieces(Piece.BLACK) == 17);
    }

    @Test
    public void kingPosTest() {
        Board b = new Board();
        b.makeMove(sq("b5"), sq("b8"));
        b.makeMove(sq("d5"), sq("d8"));
        b.makeMove(sq("a6"), sq("a9"));
        b.makeMove(sq("e5"), sq("d5"));
        assertTrue("king is at e4", b.kingPosition() == sq("d5"));
        b.undo(); b.undo();
        assertTrue("king is at e5", b.kingPosition() == sq("e5"));
        b.makeMove(sq("f1"), sq("g1"));
        b.makeMove(sq("e5"), sq("d5"));
        b.makeMove(sq("d1"), sq("d4"));
        b.makeMove(sq("c5"), sq("c8"));
        b.makeMove(sq("a6"), sq("d6"));
        assertTrue("black has not won", b.kingPosition() == sq("d5"));
        b.makeMove(sq("d5"), sq("c5"));
        b.makeMove(sq("d6"), sq("c6"));
        b.makeMove(sq("g5"), sq("g8"));
        b.makeMove(sq("d4"), sq("c4"));
        assertTrue("black has won", b.winner() == Piece.BLACK
                                    && b.kingPosition() == null);
        b.undo(); b.undo();
        assertTrue("black has not won", b.kingPosition() == sq("c5"));
    }
}


