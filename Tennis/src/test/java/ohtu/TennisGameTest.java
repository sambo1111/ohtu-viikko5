package ohtu;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TennisGameTest {

    private int player1Score;
    private int player2Score;
    private String expectedScore;

    public TennisGameTest(int player1Score, int player2Score, String expectedScore) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.expectedScore = expectedScore;
    }

    @Parameters
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][]{
            {0, 0, "Love-All"},
            {1, 1, "Fifteen-All"},
            {2, 2, "Thirty-All"},
            {3, 3, "Forty-All"},
            {4, 4, "Deuce"},
            //xxxxxxxxxxxxxxxxxx

            {1, 0, "Fifteen-Love"}, //x
            {0, 1, "Love-Fifteen"},//x
            {2, 0, "Thirty-Love"},//x
            {0, 2, "Love-Thirty"},//x
            {3, 0, "Forty-Love"},//x
            {0, 3, "Love-Forty"},//x
            {4, 0, "Win for player1"},//x
            {0, 4, "Win for player2"},
            //
            {2, 1, "Thirty-Fifteen"}, //x
            {1, 2, "Fifteen-Thirty"},//x
            {3, 1, "Forty-Fifteen"},//x
            {1, 3, "Fifteen-Forty"},//x
            {4, 1, "Win for player1"},//x
            {1, 4, "Win for player2"},//x
            //
            {3, 2, "Forty-Thirty"}, //x
            {2, 3, "Thirty-Forty"},//x
            {4, 2, "Win for player1"},//x
            {2, 4, "Win for player2"},//x
            //
            {4, 3, "Advantage player1"}, //x
            {3, 4, "Advantage player2"},//x
            //
            {5, 4, "Advantage player1"},//x
            {4, 5, "Advantage player2"},//x
            //
            {15, 14, "Advantage player1"},//x
            {14, 15, "Advantage player2"},//x
            //
            {6, 4, "Win for player1"},//x
            {4, 6, "Win for player2"},//x
            //
            {16, 14, "Win for player1"},//x
            {14, 16, "Win for player2"},}); //x
    }

    public void checkAllScores(TennisGame game) {
        int highestScore = Math.max(this.player1Score, this.player2Score);
        for (int i = 0; i < highestScore; i++) {
            if (i < this.player1Score) {
                game.wonPoint("player1");
            }
            if (i < this.player2Score) {
                game.wonPoint("player2");
            }
        }
        assertEquals(this.expectedScore, game.getScore());
    }

//    @Test
//    public void checkAllScoresTennisGame() {
//        TennisGame game = new TennisGame("player1", "player2");
//        checkAllScores(game);
//    }
    @Test
    public void checkWhenDraw() {
        TennisGame game = new TennisGame("player1", "player2");
        assertEquals("Love-All", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Fifteen-All", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Thirty-All", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Forty-All", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Deuce", game.getScore());
    }

    @Test
    public void checkWhenPlayer1LeadingWithOnePoint() {
        TennisGame game = new TennisGame("player1", "player2");

        game.wonPoint("player1");
        assertEquals("Fifteen-Love", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Thirty-Fifteen", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Forty-Thirty", game.getScore());

        for (int i = 0; i < 14; i++) {
            game.wonPoint("player1");
            game.wonPoint("player2");
            assertEquals("Advantage player1", game.getScore());
        }

    }

    @Test
    public void checkWhenPlayer2LeadingWithOnePoint() {
        TennisGame game = new TennisGame("player1", "player2");

        game.wonPoint("player2");
        assertEquals("Love-Fifteen", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Fifteen-Thirty", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Thirty-Forty", game.getScore());

        for (int i = 0; i < 14; i++) {
            game.wonPoint("player1");
            game.wonPoint("player2");
            assertEquals("Advantage player2", game.getScore());
        }

    }

    @Test
    public void checkWhenPlayer1LeadingWithTwoPoints() {
        TennisGame game = new TennisGame("player1", "player2");

        game.wonPoint("player1");
        game.wonPoint("player1");
        assertEquals("Thirty-Love", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Forty-Fifteen", game.getScore());

        for (int i = 0; i < 14; i++) {
            game.wonPoint("player1");
            game.wonPoint("player2");
            assertEquals("Win for player1", game.getScore());
        }
    }

    @Test
    public void checkWhenPlayer2LeadingWithTwoPoints() {
        TennisGame game = new TennisGame("player1", "player2");

        game.wonPoint("player2");
        game.wonPoint("player2");
        assertEquals("Love-Thirty", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Fifteen-Forty", game.getScore());

        for (int i = 0; i < 14; i++) {
            game.wonPoint("player1");
            game.wonPoint("player2");
            assertEquals("Win for player2", game.getScore());
        }
    }

    @Test
    public void checkWhenPlayer1LeadingWithThreePoints() {
        TennisGame game = new TennisGame("player1", "player2");

        game.wonPoint("player1");
        game.wonPoint("player1");
        game.wonPoint("player1");
        assertEquals("Forty-Love", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Win for player1", game.getScore());
    }

    @Test
    public void checkWhenPlayer2LeadingWithThreePoints() {
        TennisGame game = new TennisGame("player1", "player2");

        game.wonPoint("player2");
        game.wonPoint("player2");
        game.wonPoint("player2");
        assertEquals("Love-Forty", game.getScore());

        game.wonPoint("player1");
        game.wonPoint("player2");
        assertEquals("Win for player2", game.getScore());
    }

    @Test
    public void checkWhenPlayer1LeadingWithFourPoints() {
        TennisGame game = new TennisGame("player1", "player2");
        
        game.wonPoint("player1");
        game.wonPoint("player1");
        game.wonPoint("player1");
        game.wonPoint("player1");
        assertEquals("Win for player1", game.getScore());
    }
    
    @Test
    public void checkWhenPlayer2LeadingWithFourPoints() {
        TennisGame game = new TennisGame("player1", "player2");
        
        game.wonPoint("player2");
        game.wonPoint("player2");
        game.wonPoint("player2");
        game.wonPoint("player2");
        assertEquals("Win for player2", game.getScore());
    }
}
