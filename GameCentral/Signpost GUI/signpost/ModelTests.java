package signpost;

import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

import static signpost.Place.pl;
import signpost.Model.Sq;
import static signpost.Utils.msg;

/** Tests of the Model class.
 *  @author P. N. Hilfinger
 */
public class ModelTests {

    /** Given H x W array A, return a W x H array in which the columns of
     *  A, each reversed, are the rows of the result.  That is produces B
     *  so that B[x][y] is A[H - y - 1][x].  This is a convenience method
     *  that allows our test arrays to be arranged on the page to look as
     *  they do when displayed. */
    static int[][] tr(int[][] A) {
        int[][] B = new int[A[0].length][A.length];
        for (int x = 0; x < A[0].length; x += 1) {
            for (int y = 0; y < A.length; y += 1) {
                B[x][y] = A[A.length - y - 1][x];
            }
        }
        return B;
    }

    /** Return true iff the set of elements in EXPECTED is the same as that
     *  in ACTUAL. */
    private <T> void assertSetEquals(String msg,
                                     Collection<T> expected,
                                     Collection<T> actual) {
        assertNotNull(msg, actual);
        assertEquals(msg, new HashSet<T>(expected), new HashSet<T>(actual));
    }

    /** Check that MODEL is a valid representation of the solution in
     *  SOLUTION, and that the fixed numbers in it are precisely FIXED. */
    private void checkNumbers(int[][] solution, Model model,
                              Collection<Integer> fixed) {
        assertEquals("Wrong model width", solution.length, model.width());
        assertEquals("Wrong model height", solution[0].length, model.height());
        assertEquals("Wrong model size", solution.length * solution[0].length,
                     model.size());
        HashSet<Integer> actualFixed = new HashSet<>();
        for (int x = 0; x < model.width(); x += 1) {
            for (int y = 0; y < model.height(); y += 1) {
                Sq sq = model.get(x, y);
                assertEquals(msg("Wrong at (%d, %d)", x, y),
                             solution[x][y], sq.sequenceNum());
                if (sq.hasFixedNum()) {
                    actualFixed.add(sq.sequenceNum());
                }
            }
        }
        assertEquals("Fixed positions differ", new HashSet<Integer>(fixed),
                     actualFixed);
    }

    /** Check that the arrow directions in MODEL agree with DIRS.  The
     *  direction of the arrow at (x, y) in MODEL should be DIRS[x][y].
     */
    private void checkArrows(int[][] dirs, Model model) {
        for (int x = 0; x < model.width(); x += 1) {
            for (int y = 0; y < model.height(); y += 1) {
                assertEquals(msg("Arrows differ at (%d, %d)", x, y),
                             dirs[x][y], model.get(x, y).direction());
            }
        }
    }

    @Test
    public void initTest1() {
        Model model = new Model(tr(SOLN1));
        checkNumbers(tr(BOARD1), model, asList(1, 16));
    }

    @Test
    public void initTest2() {
        Model model = new Model(SOLN2);
        checkNumbers(BOARD2, model, asList(1, 20));
    }

    @Test
    public void initTest3() {
        int[][] soln = tr(SOLN2);
        Model model = new Model(soln);
        model.solve();
        for (int x = 0; x < model.width(); x += 1) {
            YLoop:
            for (int y = 0; y < model.height(); y += 1) {
                for (Sq sq : model) {
                    if (x == sq.x && y == sq.y) {
                        assertEquals(msg("Wrong number at (%d, %d)", x, y),
                                     soln[x][y], sq.sequenceNum());
                        continue YLoop;
                    }
                }
                fail(msg("Did not find square at (%d, %d)", x, y));
            }
        }
    }

    @Test
    public void allPlacesTest() {
        Model model = new Model(tr(SOLN2));
        for (Sq sq : model) {
            assertEquals(msg("Wrong square at (%d, %d)", sq.x, sq.y),
                         sq, model.get(sq.x, sq.y));
            assertEquals(msg("Wrong square at Place %s", sq.pl),
                         sq, model.get(sq.pl));
            assertEquals(msg("Wrong Place at (%d, %d)", sq.x, sq.y),
                         pl(sq.x, sq.y), sq.pl);
        }
    }

    @Test
    public void arrowTest1() {
        Model model = new Model(tr(SOLN1));
        checkArrows(tr(ARROWS1), model);
    }

    @Test public void copyTest() {
        Model model1 = new Model(tr(SOLN1));
        Model model2 = new Model(model1);
        checkNumbers(tr(BOARD1), model2, asList(1, 16));
    }

    @Test
    public void solvedTest1() {
        Model model = new Model(tr(SOLN1));
        assertFalse("Model not solved yet.", model.solved());
        model.solve();
        assertTrue("Model should be solved.", model.solved());
        checkNumbers(tr(SOLN1), model, asList(1, 16));
    }
    @Test
    public void autoconnectTest() {
        Model model = new Model(tr(SOLN1));
        model.get(0, 3).connect(model.get(3, 3));
        model.get(1, 2).setFixedNum(4);
        model.get(2, 3).connect(model.get(1, 2));
        model.autoconnect();
        assertTrue("Autoconnect doesn't work",
                   AUTOCONNECT.equals(model.toString()));
    }
    @Test
    public void disconnectTest() {
        Model model = new Model(tr(SOLN1));
        int[][][] toConnect = new int[][][]
            { {{3, 3}, {2, 3}}, {{0, 3}, {3, 3}},
            {{2, 1}, {2, 2}}, {{2, 0}, {0, 0}} };
        for (int[][] unwrap: toConnect) {
            model.get(unwrap[0][0], unwrap[0][1])
                 .connect(model.get(unwrap[1][0], unwrap[1][1]));
        }
        assertTrue("Connect doesn't work",
                   CONNECT_RESULT.equals(model.toString()));
        model.get(2, 0).disconnect();
        assertTrue("disconnect doesn't work",
                   DISCONNECT1.equals(model.toString()));
        model.get(0, 3).disconnect();
        assertTrue("disconnect doesn't work",
                   DISCONNECT2.equals(model.toString()));

        model.restart();
        for (int[][] unwrap: toConnect) {
            model.get(unwrap[0][0], unwrap[0][1])
                 .connect(model.get(unwrap[1][0], unwrap[1][1]));
        }
        model.get(3, 3).disconnect();
        assertTrue("disconnect doesn't work",
                   DISCONNECT3.equals(model.toString()));
        model.get(2, 2).connect(model.get(1, 1));
        model.get(1, 1).connect(model.get(2, 0));
        model.get(2, 2).disconnect();
        assertTrue("disconnect doesn't work",
                   DISCONNECT4.equals(model.toString()));
        model.get(2, 0).disconnect();
        assertTrue("disconnect doesn't work",
                   DISCONNECT5.equals(model.toString()));
        model.get(0, 3).disconnect();
        assertTrue("disconnect doesn't work",
                   DISCONNECT6.equals(model.toString()));
    }

    @Test
    public void connectTest() {
        Model model = new Model(tr(SOLN1));
        assertTrue("Model should be mostly unconnected",
                   model.unconnected() == 15);
        model.get(3, 3).connect(model.get(2, 3));
        assertTrue("Model should be mostly unconnected.",
                   model.unconnected() == 14);
        assertTrue("Groups are not correctly set.",
                   checkGroups(model.get(2, 3), model.get(3, 3), false));
        assertTrue("Head is not correctly set",
                   checkHead(model.get(2, 3), model.get(3, 3)));
        model.get(0, 3).connect(model.get(3, 3));
        assertTrue("Model should be mostly unconnected.",
                   model.unconnected() == 13);
        assertTrue("Groups are not correctly set.",
                   checkGroups(model.get(0, 3), model.get(3, 3), true));
        assertTrue("Head is not correctly set",
                   checkHead(model.get(2, 3),
                             model.get(3, 3),
                             model.get(0, 3)));
        model.get(2, 1).connect(model.get(2, 2));
        assertTrue("Model should be mostly unconnected.",
                   model.unconnected() == 12);
        assertTrue("Groups are not correctly set.",
                   checkGroups(model.get(2, 1), model.get(2, 2), false));
        assertTrue("Head is not correctly set",
                   checkHead(model.get(2, 1), model.get(2, 2)));
        model.get(0, 3).connect(model.get(3, 3));
        assertTrue("Model should be mostly unconnected.",
                   model.unconnected() == 12);
        assertTrue("Groups are not correctly set.",
                   checkGroups(model.get(0, 3), model.get(3, 3), true));
        assertTrue("Head is not correctly set",
                   checkHead(model.get(2, 3),
                             model.get(3, 3),
                             model.get(0, 3)));
        assertFalse("Should be unable to connect",
                    model.get(3, 0).connect(model.get(3, 3)));
        assertTrue("Model should be mostly unconnected.",
                    model.unconnected() == 12);
        assertTrue("Groups are not correctly set.",
                   checkGroups(model.get(3, 0), model.get(3, 3), true));
        assertTrue("Head is not correctly set",
                   !checkHead(model.get(3, 0),
                              model.get(3, 3),
                              model.get(0, 3)));
        model.get(2, 0).connect(model.get(0, 0));
        assertTrue("Model should be mostly unconnected.",
                   model.unconnected() == 11);
        assertTrue("Groups are not correctly set.",
                   checkGroups(model.get(2, 0), model.get(0, 0), false));
        assertTrue("Groups are not correctly set.",
                   !checkGroups(model.get(0, 0), model.get(2, 1), false));
        assertTrue("Head is not correctly set",
                   checkHead(model.get(2, 0), model.get(0, 0)));

        assertTrue("Final result doesn't match",
                   CONNECT_RESULT.equals(model.toString()));
    }

    /** Returns whether two squares' groups are right. */
    public boolean checkGroups(Sq sq1, Sq sq2, boolean numbered) {
        boolean groupCond1 = numbered ? sq1.group() == 0 && sq2.group() == 0
                                      : sq1.group() != 0 && sq2.group() != 0,
                groupCond2 = numbered ? true
                                      : sq1.group() == sq2.group(),
                groupCond3 = numbered ? true
                                      : sq1.group() != -1 && sq2.group() != -1;
        return groupCond1 && groupCond2 && groupCond3;
    }

    /** Returns whether two squares' heads are right */
    public boolean checkHead(Sq... sq) {
        for (Sq s: sq) {
            if (s.head() != sq[0].head()) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void autoConnectTest1() {
        Model model = new Model(tr(new int[][] { { 1, 2 } }));
        model.autoconnect();
        assertTrue("Trivial puzzle should be solved at birth.", model.solved());
    }

    /* The following array data is written to look on the page like
     * the arrangement of data on the screen, with the first row
     * corresponding to the top row of the puzzle board, etc.  They are
     * transposed by tr into the actual data, in which the first array
     * dimension indexes columns, and the second indexes rows from bottom to
     * top. */

    private static final int[][] SOLN1 = {
        { 1, 13, 3, 2 },
        { 12, 4, 8, 15 },
        { 5, 9, 7, 14 },
        { 11, 6, 10, 16 }
    };

    private static final int[][] ARROWS1 = {
        { 2, 3, 5, 6 },
        { 1, 5, 5, 4 },
        { 3, 3, 8, 8 },
        { 8, 1, 6, 0 }
    };

    private static final int[][] BOARD1 = {
        { 1, 0, 0, 0 },
        { 0, 0, 0, 0 },
        { 0, 0, 0, 0 },
        { 0, 0, 0, 16 } };

    private static final int[][] SOLN2 = {
        { 1, 2, 17, 16, 3 },
        { 9, 7, 15, 6, 8 },
        { 12, 11, 18, 5, 4 },
        { 10, 13, 14, 19, 20 }
    };

    private static final int[][] BOARD2 = {
        { 1, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 20} };

    private static final String CONNECT_RESULT =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |3     |2     |" + "\n"
                            + "|   E  |.o SE | o SW |   W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a     |      |" + "\n"
                            + "|.o SE |.o SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|b+1   |      |b     |+16   |" + "\n"
                            + "| o N  |.o NE |.  W  |.   * |" + "\n"
                            + "+------+------+------+------+";
    private static final String AUTOCONNECT =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |3     |2     |" + "\n"
                            + "|   E  |.o SE |   SW |   W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |+4    |      |      |" + "\n"
                            + "|.o NE | o SW |.o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |      |      |" + "\n"
                            + "|.o SE |.o SE |.o N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |      |+16   |" + "\n"
                            + "|.o N  |.o NE |.o W  |.   * |" + "\n"
                            + "+------+------+------+------+";
    private static final String DISCONNECT1 =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |3     |2     |" + "\n"
                            + "|   E  |.o SE | o SW |   W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a     |      |" + "\n"
                            + "|.o SE |.o SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |      |+16   |" + "\n"
                            + "|.o N  |.o NE |.o W  |.   * |" + "\n"
                            + "+------+------+------+------+";

    private static final String DISCONNECT2 =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |b+1   |b     |" + "\n"
                            + "| o E  |.o SE | o SW |.  W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a     |      |" + "\n"
                            + "|.o SE |.o SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |      |+16   |" + "\n"
                            + "|.o N  |.o NE |.o W  |.   * |" + "\n"
                            + "+------+------+------+------+";

    private static final String DISCONNECT3 =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |      |2     |" + "\n"
                            + "|   E  |.o SE |.o SW | o W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a     |      |" + "\n"
                            + "|.o SE |.o SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|b+1   |      |b     |+16   |" + "\n"
                            + "| o N  |.o NE |.  W  |.   * |" + "\n"
                            + "+------+------+------+------+";

    private static final String DISCONNECT4 =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |      |2     |" + "\n"
                            + "|   E  |.o SE |.o SW | o W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |b     |a     |      |" + "\n"
                            + "|.o SE |.  SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|b+2   |      |b+1   |+16   |" + "\n"
                            + "| o N  |.o NE |   W  |.   * |" + "\n"
                            + "+------+------+------+------+";

    private static final String DISCONNECT5 =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |      |2     |" + "\n"
                            + "|   E  |.o SE |.o SW | o W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |b     |a     |      |" + "\n"
                            + "|.o SE |.  SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |b+1   |+16   |" + "\n"
                            + "|.o N  |.o NE | o W  |.   * |" + "\n"
                            + "+------+------+------+------+";

    private static final String DISCONNECT6 =
                              "+------+------+------+------+" + "\n"
                            + "|+1    |      |      |      |" + "\n"
                            + "| o E  |.o SE |.o SW |.o W  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |a+1   |      |" + "\n"
                            + "|.o NE |.o SW | o SW |.o S  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |b     |a     |      |" + "\n"
                            + "|.o SE |.  SE |.  N  |.o N  |" + "\n"
                            + "+------+------+------+------+" + "\n"
                            + "|      |      |b+1   |+16   |" + "\n"
                            + "|.o N  |.o NE | o W  |.   * |" + "\n"
                            + "+------+------+------+------+";
}
