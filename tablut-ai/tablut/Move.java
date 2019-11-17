package tablut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static tablut.Square.sq;
import static tablut.Square.NUM_SQUARES;
import static tablut.Square.ROOK_SQUARES;
import static tablut.Square.SQUARE_LIST;
import static tablut.Utils.*;

/** A move in the game of Tablut. As for Squares, Moves are immutable
 *  and unique: there is only one move object for each possible move
 *  (generated by the factory method  mv, the constructor being private).
 *  As for Squares, you can freely use == to compare Moves.
 *  @author Anirudhan Badrinath
 */
final class Move {

    /** The syntax for a move, using either forms CR-C (horizontal moves)
     *  or CR-R (vertical moves), where C is a column letter (a-i or A-I) and
     *  R is a row number (1-9). */
    static final Pattern MOVE_PATTERN =
        Pattern.compile(String.format("(([a-i])([1-9]))-(?:([a-i])|([1-9]))"));

    /** Return the square moved from. */
    Square from() {
        return _from;
    }

    /** Return the square moved to. */
    Square to() {
        return _to;
    }

    /** Return the unique Move FROM-TO, or null if this is not a valid
     *  move. */
    static Move mv(Square from, Square to) {
        if ((from.row() != to.row() && from.col() != to.col()) || from == to) {
            return null;
        }

        if (MOVES[from.index()][to.index()] == null) {
            MOVES[from.index()][to.index()] = new Move(from, to);
        }
        return MOVES[from.index()][to.index()];
    }

    /** Return the Move denoted by STR, if STR denotes a move with
     *  valid syntax, and null otherwise. */
    static Move mv(String str) {
        _moveMatcher.reset(str);
        if (_moveMatcher.matches()) {
            Square from = sq(_moveMatcher.group(1));
            Square to;
            if (_moveMatcher.group(5) == null) {
                to = sq(_moveMatcher.group(4), _moveMatcher.group(3));
            } else {
                to = sq(_moveMatcher.group(2), _moveMatcher.group(5));
            }
            return mv(from, to);
        }
        return null;
    }

    /** Return true iff STR has the right format for a Move. */
    static boolean isGrammaticalMove(String str) {
        _moveMatcher.reset(str);
        return _moveMatcher.matches();
    }

    @Override
    public String toString() {
        return _str;
    }

    /** A convenience class to represent mutable lists of Moves.  This
     *  class may safely be used as the element type in an array. */
    static class MoveList extends ArrayList<Move> {
        /** An initially empty list. */
        MoveList() {
        }

        /** A list initialize to INIT. */
        MoveList(Collection<Move> init) {
            super(init);
        }
    }

    /** Construct the Move FROM-TO. */
    private Move(Square from, Square to) {
        _from = from; _to = to;
        if (_from.col() == _to.col()) {
            _str = String.format("%s-%c", from, (char) to.row() + '1');
        } else {
            _str = String.format("%s-%c", from, (char) to.col() + 'a');
        }
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return (_from.index() << 6) + _to.index();
    }

    /** The cache of all Moves created. */
    private static final Move[][] MOVES = new Move[NUM_SQUARES][NUM_SQUARES];

    /** ROOK_MOVES[i][d] is a list of all rook moves in direction
     *  d from the square with index i. Direction displacements are defined
     *  as in Square. Lists are in order of increasing distance from
     *  square i. */
    static final MoveList[][] ROOK_MOVES = new MoveList[NUM_SQUARES][4];

    static {
        for (Square sq0 : SQUARE_LIST) {
            int r0 = sq0.row(), c0 = sq0.col(), i0 = sq0.index();
            for (int d = 0; d < 4; d += 1) {
                MoveList L = ROOK_MOVES[i0][d] = new MoveList();
                for (Square sq1 : ROOK_SQUARES[i0][d]) {
                    L.add(mv(sq0, sq1));
                }
            }
        }
    }

    /** The components of this Move. */
    private final Square _from, _to;
    /** The printed form of this Move. */
    private String _str;
    /**  A utility Matcher for moves, using MOVE_PATTERN. */
    private static Matcher _moveMatcher = MOVE_PATTERN.matcher("");
}
