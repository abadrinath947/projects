package tablut;

import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;

import static tablut.Piece.*;
import static tablut.Square.*;
import static tablut.Move.mv;
import static java.lang.Math.*;
import static java.util.Arrays.*;

/** The state of a Tablut Game.
 *  @author Anirudhan Badrinath
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 9;

    /** The throne (or castle) square and its four surrounding squares.. */
    static final Square THRONE = sq(4, 4),
        NTHRONE = sq(4, 5),
        STHRONE = sq(4, 3),
        WTHRONE = sq(3, 4),
        ETHRONE = sq(5, 4);

    /** The throne and surrounding positions in a Set (easy search). */
    static final Set<Square> SURROUNDING_THRONE =
        new HashSet<Square>(asList(new Square[] {THRONE, NTHRONE, STHRONE,
                                                 WTHRONE, ETHRONE}));

    /** Initial positions of attackers. */
    static final Square[] INITIAL_ATTACKERS = {
        sq(0, 3), sq(0, 4), sq(0, 5), sq(1, 4),
        sq(8, 3), sq(8, 4), sq(8, 5), sq(7, 4),
        sq(3, 0), sq(4, 0), sq(5, 0), sq(4, 1),
        sq(3, 8), sq(4, 8), sq(5, 8), sq(4, 7)
    };

    /** Initial positions of defenders of the king. */
    static final Square[] INITIAL_DEFENDERS = {
        NTHRONE, ETHRONE, STHRONE, WTHRONE,
        sq(4, 6), sq(4, 2), sq(2, 4), sq(6, 4)
    };

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        if (model == this) {
            return;
        }
        this._board = new HashMap<Square, Piece>(model._board);
        this._turn = model._turn;
        this._repeated = model._repeated;
        this._boards = new ArrayList<Map<Square, Piece>>(model._boards);
        this._moveCount = model._moveCount;
        this._moveLimit = model._moveLimit;
        this._winner = model._winner;
        this._kingPosition = model._kingPosition;
    }

    /** Clears the board to the initial position. */
    void init() {
        this._board = new HashMap<Square, Piece>();
        put(Piece.KING, THRONE);
        for (Square s: INITIAL_ATTACKERS) {
            put(Piece.BLACK, s);
        }
        for (Square s: INITIAL_DEFENDERS) {
            put(Piece.WHITE, s);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (get(sq(i, j)) == null) {
                    put(Piece.EMPTY, sq(i, j));
                }
            }
        }
        this._boards = new ArrayList<Map<Square, Piece>>();
        checkRepeated();
        this._turn = Piece.BLACK;
        this._repeated = false;
        this._moveCount = 0;
        this._moveLimit = -1;
        this._winner = null;
        this._kingPosition = kingPosition();
    }

    /** Set the move limit to LIM.  It is an error if 2*LIM <= moveCount(). */
    void setMoveLimit(int lim) {
        if (2 * lim <= moveCount()) {
            System.err.println("Error: bad limit argument\n");
            return;
        }
        this._moveLimit = 2 * lim;
    }

    /** Return a Piece representing whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the winner in the current position, or null if there is no winner
     *  yet. */
    Piece winner() {
        return _winner;
    }

    /** Returns true iff this is a win due to a repeated position. */
    boolean repeatedPosition() {
        return _repeated;
    }

    /** Returns the king's index for a shuffle. */
    int kingIndex() {
        return _kingIndex;
    }

    /** Returns number of pieces remaining for a particular TEAM. */
    int pieces(Piece team) {
        int count = 0;
        for (Piece p: this._board.values()) {
            if (p == team || (team == Piece.WHITE && p == Piece.KING)) {
                count++;
            }
        }
        return count;
    }

    /** Record current position and set winner() next mover if the current
     *  position is a repeat. */
    private void checkRepeated() {
        if (this._boards.contains(this._board)
            && this._boards.indexOf(this._board) % 2 == this._moveCount % 2) {
            this._repeated = true;
            this._winner = this._turn;
        }
        this._boards.add(new HashMap<Square, Piece>(this._board));
    }

    /** Return the number of moves since the initial position that have not been
     *  undone. */
    int moveCount() {
        return _moveCount;
    }

    /** Return location of the king. */
    Square kingPosition() {
        Square king = this._kingPosition;
        if (king == null || get(king) != Piece.KING) {
            for (Map.Entry<Square, Piece> sp: this._board.entrySet()) {
                if (sp.getValue() == Piece.KING) {
                    this._kingPosition = sp.getKey();
                    return sp.getKey();
                }
            }
        }
        if (king != null && get(king) != Piece.KING) {
            return null;
        }
        return king;
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return get(s.col(), s.row());
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        return this._board.get(sq(col, row));
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(row - '1', col - 'a');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        this._board.put(s, p);
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, sq(col - 'a', row - '1'));
    }

    /** Return true iff FROM - TO is an unblocked rook move on the current
     *  board.  For this to be true, FROM-TO must be a rook move and the
     *  squares along it, other than FROM, must be empty. */
    boolean isUnblockedMove(Square from, Square to) {
        if (from == to || (from.col() != to.col() && from.row() != to.row())) {
            return false;
        }
        String dim = from.col() == to.col() ? "c" : "r";
        int[] range = dim.equals("c") ? new int[] {from.row(), to.row()}
                                      : new int[] {from.col(), to.col()};
        sort(range);
        for (int i = range[0]; i <= range[1]; i++) {
            Square s = dim.equals("c") ? sq(from.col(), i) : sq(i, from.row());
            if (s != from && get(s) != Piece.EMPTY) {
                return false;
            }
        }
        return true;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        return get(from) == _turn
               || (_turn == Piece.WHITE ? get(from) == Piece.KING : false);
    }

    /** Return true iff FROM-TO is a valid move. */
    boolean isLegal(Square from, Square to) {
        return isLegal(from) && isUnblockedMove(from, to)
               && (to == THRONE ? get(from) == Piece.KING : true);
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to());
    }

    /** Move FROM-TO, assuming this is a legal move. */
    void makeMove(Square from, Square to) {
        assert isLegal(from, to);
        put(get(from), to);
        put(Piece.EMPTY, from);
        if (get(to) == Piece.KING) {
            this._kingPosition = to;
        }
        if (kingPosition() != null && kingPosition().isEdge()) {
            this._winner = Piece.WHITE;
        }
        capture(checkCaptures(to, this._turn));
        checkMoveLimit();
        this._turn = enemy(this._turn);
        checkRepeated();
        checkHasMove();
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        makeMove(move.from(), move.to());
    }

    /** Returns the potential captures for a piece of type
     *  TEAM at Square POS. */
    private Set<Square> checkCaptures(Square pos, Piece team) {
        Set<Square> captures = new HashSet<Square>();
        Piece K = Piece.KING, B = Piece.BLACK, W = Piece.WHITE, E = Piece.EMPTY;
        boolean isThroneEmpty = get(THRONE) == E,
                surroundingKing = surroundingPieces(THRONE, B, false) == 3;
        for (int dir = 0; dir < 4; dir++) {
            Square possibleCapture = pos.rookMove(dir, 1),
                   possibleAlly = pos.rookMove(dir, 2);
            boolean canCapture = possibleCapture != null
                                 && (get(possibleCapture) == enemy(team)
                                 || (team == B
                                     ? get(possibleCapture) == K : false));
            boolean hostile = possibleAlly != null
                              && (get(possibleAlly) == team
                                  || (possibleAlly == THRONE
                                      && isThroneEmpty)
                                  || (team == W
                                      ? get(possibleAlly) == K
                                      : (get(possibleAlly) == K
                                         && possibleAlly == THRONE
                                         && surroundingKing)));
            if (canCapture && get(possibleCapture) == K
                && SURROUNDING_THRONE.contains(possibleCapture)) {
                hostile = surroundingPieces(possibleCapture, B, true) == 4;
            }
            if (hostile && canCapture) {
                captures.add(possibleCapture);
            }
        }
        return captures;
    }

    /** Return the number of enemy pieces P surrounding Square S, with
     *  an option to COUNTHOSTILE (accounts for throne piece). */
    int surroundingPieces(Square s, Piece p, boolean countHostile) {
        int count = 0;
        for (int dir = 0; dir < 4; dir++) {
            Square surrounding = s.rookMove(dir, 1);
            if (surrounding != null && (get(surrounding) == p
                || (countHostile && surrounding == THRONE
                     && get(surrounding) == Piece.EMPTY))) {
                count++;
            }
        }
        return count;
    }

    /** Capture the pieces SQ assuming the necessary conditions
     *  are satisfied. */
    private void capture(Set<Square> sq) {
        for (Square s: sq) {
            if (get(s) == Piece.KING) {
                this._winner = Piece.BLACK;
            }
            put(Piece.EMPTY, s);
        }
    }

    /** Checks if the move limit is within its required bounds. */
    private void checkMoveLimit() {
        if (++this._moveCount == this._moveLimit) {
            this._winner = this._turn;
        }
    }

    /** Checks if there are any moves left for the opposing team. */
    private void checkHasMove() {
        if (!hasMove(this._turn)) {
            this._winner = enemy(this._turn);
        }
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        if (_moveCount > 0) {
            undoPosition();
            int index = this._boards.size() - 1;
            this._board = new HashMap<Square, Piece>(this._boards.get(index));
            this._winner = null;
            this._repeated = false;
            this._turn = enemy(this._turn);
            this._moveCount--;
        }
    }

    /** Remove record of current position in the set of positions encountered,
     *  unless it is a repeated position or we are at the first move. */
    private void undoPosition() {
        if (this._moveCount > 0) {
            this._boards.remove(this._boards.size() - 1);
        }
    }

    /** Clear the undo stack and board-position counts. Does not modify the
     *  current position or win status. */
    void clearUndo() {
        this._moveCount = 0;
        this._boards = new ArrayList<Map<Square, Piece>>();
    }

    /** Return a new mutable list of all legal moves on the current board for
     *  SIDE (ignoring whose turn it is at the moment). */
    List<Move> legalMoves(Piece side) {
        int kingIndex = 0;
        List<Move> legalMoves = new ArrayList<Move>();
        for (Square s: pieceLocations(side)) {
            for (int dir = 0; dir < 4; dir++) {
                for (int steps = 1; s.rookMove(dir, steps) != null; steps++) {
                    Square t = s.rookMove(dir, steps);
                    if (isUnblockedMove(s, t) && (t == THRONE
                                ? get(s) == Piece.KING : true)) {
                        if (get(s) == Piece.KING) {
                            legalMoves.add(0, mv(s, t));
                            kingIndex++;
                        } else {
                            legalMoves.add(mv(s, t));
                        }
                    }
                }
            }
        }
        this._kingIndex = kingIndex;
        return legalMoves;
    }

    /** Return true iff SIDE has a legal move. */
    boolean hasMove(Piece side) {
        return !legalMoves(side).isEmpty();
    }

    @Override
    public String toString() {
        return toString(true);
    }

    /** Return a text representation of this Board.  If COORDINATES, then row
     *  and column designations are included along the left and bottom sides.
     */
    String toString(boolean coordinates) {
        Formatter out = new Formatter();
        for (int r = SIZE - 1; r >= 0; r -= 1) {
            if (coordinates) {
                out.format("%2d", r + 1);
            } else {
                out.format("  ");
            }
            for (int c = 0; c < SIZE; c += 1) {
                out.format(" %s", get(c, r));
            }
            out.format("%n");
        }
        if (coordinates) {
            out.format("  ");
            for (char c = 'a'; c <= 'i'; c += 1) {
                out.format(" %c", c);
            }
            out.format("%n");
        }
        return out.toString();
    }

    /** Return the locations of all pieces on SIDE. */
    Set<Square> pieceLocations(Piece side) {
        assert side != EMPTY;
        Set<Square> sideSquares = new HashSet<Square>();
        if (side == Piece.KING) {
            sideSquares.add(kingPosition());
        } else {
            for (Map.Entry<Square, Piece> sp: this._board.entrySet()) {
                if (sp.getValue() == side || (side == Piece.WHITE
                                              && sp.getValue() == Piece.KING)) {
                    sideSquares.add(sp.getKey());
                }
            }
        }
        return sideSquares;
    }

    /** Return the contents of _board in the order of SQUARE_LIST as a sequence
     *  of characters: the toString values of the current turn and Pieces. */
    String encodedBoard() {
        char[] result = new char[Square.SQUARE_LIST.size() + 1];
        result[0] = turn().toString().charAt(0);
        for (Square sq : SQUARE_LIST) {
            result[sq.index() + 1] = get(sq).toString().charAt(0);
        }
        return new String(result);
    }

    @Override
    public boolean equals(Object o) {
        return ((Board) o)._board.equals(this._board);
    }

    @Override
    public int hashCode() {
        int r = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Square s = sq(i, j); Piece p = get(s);
                r = (r << 5) - r + s.index() * (p == Piece.BLACK ? 2 : p == Piece.WHITE ? 1 : p == Piece.KING ? -10 : -50);
            }
        }
        return r;
    }

    /** Return the enemy of Piece CURR. */
    private Piece enemy(Piece curr) {
        return curr == Piece.BLACK ? Piece.WHITE : Piece.BLACK;
    }

    /** All logged moves (not undone). */
    private List<Map<Square, Piece>> _boards;
    /** Mapping between Squares and Pieces on the board. */
    private Map<Square, Piece> _board;
    /** Piece whose turn it is (WHITE or BLACK). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;
    /** King's current position. */
    private Square _kingPosition;
    /** Number of (still undone) moves since initial position. */
    private int _moveCount,
                _moveLimit = -1;
    /** True when current board is a repeated position (ending the game). */
    private boolean _repeated;
    /** Index of king's legal moves for shuffling. */
    private int _kingIndex;
}
