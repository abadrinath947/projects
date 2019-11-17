package tablut;

import ucb.gui2.Pad;

import java.util.concurrent.ArrayBlockingQueue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.MouseEvent;

import static tablut.Piece.*;
import static tablut.Square.sq;

/** A widget that displays a Tablut game.
 *  @author Anirudhan Badrinath
 */
class BoardWidget extends Pad {

    /* Parameters controlling sizes, speeds, colors, and fonts. */

    /** Squares on each side of the board. */
    static final int SIZE = Board.SIZE;

    /** Colors of empty squares, pieces, grid lines, and boundaries. */
    static final Color
        SQUARE_COLOR = new Color(238, 207, 161),
        THRONE_COLOR = new Color(180, 255, 180),
        ADJACENT_THRONE_COLOR = new Color(200, 220, 200),
        CLICKED_SQUARE_COLOR = new Color(255, 255, 100),
        GRID_LINE_COLOR = Color.black,
        WHITE_COLOR = Color.white,
        BLACK_COLOR = Color.black;

    /** Margins. */
    static final int
        OFFSET = 2,
        MARGIN = 16;

    /** Side of single square and of board (in pixels). */
    static final int
        SQUARE_SIDE = 30,
        BOARD_SIDE = SQUARE_SIDE * SIZE + 2 * OFFSET + MARGIN;

    /** The font in which to render the "K" in the king. */
    static final Font KING_FONT = new Font("Serif", Font.BOLD, 18);
    /** The font for labeling rows and columns. */
    static final Font ROW_COL_FONT = new Font("SanSerif", Font.PLAIN, 10);

    /** Squares adjacent to the throne. */
    static final Square[] ADJACENT_THRONE = {
        Board.NTHRONE, Board.ETHRONE, Board.STHRONE, Board.WTHRONE
    };

    /** A graphical representation of a Tablut board that sends commands
     *  derived from mouse clicks to COMMANDS.  */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        setMouseHandler("click", this::mouseClicked);
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);
        _acceptingMoves = false;
    }

    /** Draw the bare board G.  */
    private void drawGrid(Graphics2D g) {
        g.setColor(SQUARE_COLOR);
        g.fillRect(0, 0, BOARD_SIDE, BOARD_SIDE);
        g.setColor(THRONE_COLOR);
        g.fillRect(cx(Board.THRONE), cy(Board.THRONE),
                   SQUARE_SIDE, SQUARE_SIDE);
        for (Square s: ADJACENT_THRONE) {
            g.setColor(ADJACENT_THRONE_COLOR);
            g.fillRect(cx(s), cy(s), SQUARE_SIDE, SQUARE_SIDE);
        }
        g.setColor(GRID_LINE_COLOR);
        for (int k = 0; k <= SIZE; k += 1) {
            g.drawLine(cx(0), cy(k - 1), cx(SIZE), cy(k - 1));
            g.drawLine(cx(k), cy(-1), cx(k), cy(SIZE - 1));
        }
        for (int k = 1; k <= SIZE; k += 1) {
            g.drawString("" + k, cx(0) - SQUARE_SIDE / 2,
                         (cy(k - 2) + cy(k - 1)) / 2);
            g.drawString("" + (char) ('a' + k - 1), (cx(k - 1) + cx(k)) / 2,
                         cy(0) + (3 * SQUARE_SIDE) / 2);
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        drawGrid(g);
        Square.SQUARE_LIST.iterator().forEachRemaining(s -> drawPiece(g, s));
    }

    /** Draw the contents of S on G. */
    private void drawPiece(Graphics2D g, Square s) {
        if (this._from == s) {
            g.setColor(CLICKED_SQUARE_COLOR);
            g.fillRect(cx(s), cy(s),
                       SQUARE_SIDE, SQUARE_SIDE);
        }
        char piece = this._board.get(s).toString().charAt(0);
        int x = cx(s) + SQUARE_SIDE / 4 + OFFSET,
            y = cy(s.row() - 1) - SQUARE_SIDE / 4 - OFFSET,
            x1 = cx(s), y1 = cy(s);
        switch (piece) {
        case 'B':
            g.setColor(BLACK_COLOR);
            g.setFont(new Font("default", Font.BOLD, 16));
            break;
        case 'W':
            g.setColor(WHITE_COLOR);
            g.setFont(new Font("default", Font.BOLD, 16));
            break;
        case 'K':
            g.setColor(WHITE_COLOR);
            g.setFont(KING_FONT);
            break;
        default:
            return;
        }
        g.fillOval(x1, y1, SQUARE_SIDE, SQUARE_SIDE);
        g.setColor(Color.red);
        g.drawString("" + piece, x, y);
    }

    /** Handle a click on S. */
    private void click(Square s) {
        boolean sameTeam = (this._board.turn() == this._board.get(s)
                            || this._board.turn() == Piece.WHITE
                            && this._board.get(s) == Piece.KING);
        if (this._from == null && !sameTeam) {
            return;
        } else if (this._from != null && this._board.isLegal(this._from, s)) {
            if (this._from.col() == s.col()) {
                this._commands.offer(this._from.toString()
                                     + "-" + s.toString().charAt(1));
            } else {
                this._commands.offer(this._from.toString()
                                     + "-" + s.toString().charAt(0));
            }
            this._from = null;
        } else if (sameTeam) {
            this._from = s;
        }
        repaint();
    }

    /** Handle mouse click event E. */
    private synchronized void mouseClicked(String unused, MouseEvent e) {
        int xpos = e.getX(), ypos = e.getY();
        int x = (xpos - OFFSET - MARGIN) / SQUARE_SIDE,
            y = (OFFSET - ypos) / SQUARE_SIDE + SIZE - 1;
        if (_acceptingMoves
            && x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            click(sq(x, y));
        }
    }

    /** Revise the displayed board according to BOARD. */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /** Turn on move collection iff COLLECTING, and clear any current
     *  partial selection.  When move collection is off, ignore clicks on
     *  the board. */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /** Return x-pixel coordinate of the left corners of column X
     *  relative to the upper-left corner of the board. */
    private int cx(int x) {
        return x * SQUARE_SIDE + OFFSET + MARGIN;
    }

    /** Return y-pixel coordinate of the upper corners of row Y
     *  relative to the upper-left corner of the board. */
    private int cy(int y) {
        return (SIZE - y - 1) * SQUARE_SIDE + OFFSET;
    }

    /** Return x-pixel coordinate of the left corner of S
     *  relative to the upper-left corner of the board. */
    private int cx(Square s) {
        return cx(s.col());
    }

    /** Return y-pixel coordinate of the upper corner of S
     *  relative to the upper-left corner of the board. */
    private int cy(Square s) {
        return cy(s.row());
    }

    /** Queue on which to post move commands (from mouse clicks). */
    private ArrayBlockingQueue<String> _commands;
    /** Board being displayed. */
    private final Board _board = new Board();

    /** True iff accepting moves from user. */
    private boolean _acceptingMoves;

    /** Check whether the first click for move has happened. */
    private Square _from;

}
