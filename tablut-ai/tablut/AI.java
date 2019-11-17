package tablut;

import java.util.List;
import java.util.Random;
import java.util.Collections;

import static java.lang.Math.*;

import static tablut.Square.sq;
import static tablut.Move.*;
import static tablut.Board.*;

/** A Player that automatically generates moves.
 *  @author Anirudhan Badrinath
 */
class AI extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = 1000000;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;
    /** Weights for each static heuristic. */
    private static final int BLACK = 25,
                             WHITE = 50;
    /** Caps for search depths. */
    private static final int CAP1 = 14,
                             CAP2 = 18,
                             CAP3 = 22;

    /** A new AI with no piece or controller (intended to produce
     *  a template). */
    AI() {
        this(null, null);
    }

    /** A new AI playing PIECE under control of CONTROLLER. */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        _moves++;
        return findMove().toString();
    }

    @Override
    boolean isManual() {
        return false;
    }

    @Override
    void reset() {
        this._moves = 0;
    }
    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        this._lastFoundMove = null;
        findMove(b, maxDepth(b), true,
                 1, -INFTY, INFTY);
        System.out.println("* " + this._lastFoundMove);
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        if (depth == 0 || gameOver(board)) {
            return staticScore(board, depth);
        }
        Piece team = sense == 1 ? myPiece() : enemy(myPiece());
        int value = sense == 1 ? -INFTY : INFTY;
        Move best = null; List<Move> legalMoves = board.legalMoves(team);
        for (Move m: legalMoves) {
            board.makeMove(m);
            int heuristic = findMove(board, depth - 1, false,
                                     -sense, alpha, beta);
            if (heuristic > value && sense == 1) {
                value = heuristic;
                alpha = max(alpha, value);
                if (saveMove) {
                    best = m;
                }
            } else if (heuristic < value && sense == -1) {
                value = heuristic;
                beta = min(beta, value);
                if (saveMove) {
                    best = m;
                }
            }
            board.undo();
            if (beta <= alpha) {
                break;
            }
        }
        if (saveMove) {
            this._lastFoundMove = best;
        }
        return value;
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        if (this._moves > CAP3) {
            return 1;
        } else if (this._moves > CAP2) {
            return 2;
        } else if (this._moves > CAP1) {
            return 3;
        } else {
            return 4;
        }
    }

    /** Return a heuristic value for BOARD at depth DEPTH. */
    private int staticScore(Board board, int depth) {
        return getPieceStrength(board, myPiece(), depth);
    }

    /** Shuffle the board's LEGALMOVES using a random number
     *  generator R that is consistent. Only shuffles White
     *  and Black TEAM moves, not the King's for consistency
     *  using the last King move's INDEX. */
    private void shuffle(List<Move> legalMoves, Piece team,
                         int index, Random r) {
        if (team != Piece.WHITE) {
            Collections.shuffle(legalMoves, r);
        } else {
            int size = legalMoves.size();
            Collections.shuffle(legalMoves.subList(min(index, size), size), r);
        }
    }
    /** Return the piece strength for a particular BOARD and TEAM at depth
     *  DEPTH. **/
    private int getPieceStrength(Board board, Piece team, int depth) {
        if (board.winner() != null) {
            return myPiece() == board.winner() ? WINNING_VALUE * (depth + 1)
                                               : -WINNING_VALUE * (depth + 1);
        } else if (team == Piece.BLACK) {
            return (int) (BLACK * (board.pieces(team)
                                   - board.pieces(enemy(team)))
                   + (WHITE * board.surroundingPieces(board.kingPosition(),
                                                      Piece.BLACK, true)));
        } else {
            int a = getKingStatus(board, (maxDepth(board) + 1) / 2);
            int b = (int) (getKingPieceDensity(board, board.kingPosition())
                     * WHITE / this._moves);
            return a - b;
        }
    }

    /** Return whether the game is over on Board BOARD. */
    private boolean gameOver(Board board) {
        return board.winner() != null;
    }

    /** Returns the number of hostile surrounders for a king at KINGPOS
     *  on a Board BOARD. */
    private double getKingPieceDensity(Board board, Square kingPos) {
        int i = kingPos.col() <= 8 - kingPos.col() ? 0 : 8;
        int j = kingPos.row() <= 8 - kingPos.row() ? 0 : 8;
        boolean incrI = i == 0, incrJ = j == 0;
        double b = 0;
        for (int x = i; incrI ? x < kingPos.col() : x > kingPos.col();
                        x = incrI ? x + 1 : x - 1) {
            for (int y = j; incrJ ? y < kingPos.row() : y > kingPos.row();
                        y = incrJ ? y + 1 : y - 1) {
                Piece p = board.get(sq(x, y));
                if (p == Piece.BLACK) {
                    b += 1;
                }
            }
        }
        return b;
    }

    /** Return the minimum DEPTH traversed to reach the BOARD's edge. */
    private int getKingStatus(Board board, int depth) {
        List<Move> kingMoves = board.legalMoves(Piece.KING);
        int count = 0;
        for (Move m: kingMoves) {
            if (m.to().isEdge()) {
                count += WHITE * depth; break;
            }
        }
        if (count < depth && depth > 0) {
            Square kingPos = board.kingPosition();
            for (Move m: kingMoves) {
                board.put(Piece.EMPTY, board.kingPosition());
                board.put(Piece.KING, m.to());
                count += getKingStatus(board, depth - 1);
                board.put(Piece.KING, kingPos);
                board.put(Piece.EMPTY, m.to());
                if (count > 0 && this._moves > 1) {
                    break;
                }
            }
        }
        return count == 0 ? kingMoves.size()
                          : (this._moves < 2 ? count + kingMoves.size()
                                             : count);
    }

    /** Return the enemy of a particular team T. */
    private Piece enemy(Piece t) {
        return t == Piece.BLACK ? Piece.WHITE : Piece.BLACK;
    }

    /** Number of moves currently logged for this player. */
    private int _moves;
}
