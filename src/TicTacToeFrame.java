import javax.swing.*;
import java.awt.*;


public class TicTacToeFrame extends JFrame
{
    String player = "X";
    int moveCnt;
    int row = -1;
    int col = -1;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;

    TicTacToeTile topLef = new TicTacToeTile(0,0);
    TicTacToeTile topMid = new TicTacToeTile(0,1);
    TicTacToeTile topRit = new TicTacToeTile(0,2);
    TicTacToeTile midLef = new TicTacToeTile(1,0);
    TicTacToeTile midMid = new TicTacToeTile(1,1);
    TicTacToeTile midRit = new TicTacToeTile(1,2);
    TicTacToeTile botLef = new TicTacToeTile(2,0);
    TicTacToeTile botMid = new TicTacToeTile(2,1);
    TicTacToeTile botRit = new TicTacToeTile(2,2);

    static TicTacToeTile[][] board;

    {
        board = new TicTacToeTile[3][3];
        board[0][0] = topLef;
        board[0][1] = topMid;
        board[0][2] = topRit;
        board[1][0] = midLef;
        board[1][1] = midMid;
        board[1][2] = midRit;
        board[2][0] = botLef;
        board[2][1] = botMid;
        board[2][2] = botRit;
    }



    JButton resetBtn = new JButton("Reset Board");
    JButton quitBtn = new JButton("Quit");

    JOptionPane illegalPane = new JOptionPane(JOptionPane.WARNING_MESSAGE);
    JOptionPane resultPane = new JOptionPane(JOptionPane.INFORMATION_MESSAGE);


    JPanel main = new JPanel();
    JPanel playPnl = new JPanel();
    JPanel controlPnl = new JPanel();




    public TicTacToeFrame()
    {
        main.setLayout(new BorderLayout());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createPlayPanel();
        createControlPanel();

        add(main);
        moveCnt = 1;
        for (TicTacToeTile[] t : board)
        {
            for (TicTacToeTile m : t)
            {
                m.setText("");
            }
        }
    }

    private void createPlayPanel()
    {
        playPnl.setLayout(new GridLayout(3,3));
        for (TicTacToeTile[] t : board)
        {
            for (TicTacToeTile m : t)
            {
                m.setText("");
                m.setFont(new Font("Bold", Font.BOLD, 50));
                m.addActionListener(e ->
                {
                    if(m.getText().equals(""))
                    {
                        if (moveCnt%2 == 1)
                        {
                            player = "X";
                            m.setState(player);
                        }
                        else
                        {
                            player = "O";
                            m.setState(player);
                        }
                        moveCnt++;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(illegalPane, "Illegal Move, occupied square.");
                    }

                    checkBoard(player);
                });
                playPnl.add(m);

            }
        }

        main.add(playPnl, BorderLayout.CENTER);
    }

    private static boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }
    private static boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < 3; col++)
        {
            if(board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private static boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < 3; row++)
        {
            if(board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }

    private static boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player) )
        {
            return true;
        }
        if(board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player) )
        {
            return true;
        }
        return false;
    }

    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < 3; row++)
        {
            if(board[row][0].getText().equals("X") ||
                    board[row][1].getText().equals("X") ||
                    board[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].getText().equals("O") ||
                    board[row][1].getText().equals("O") ||
                    board[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {

                return false; // No tie can still have a row win
            }

            xFlag = false;
            oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < 3; col++)
        {
            if(board[0][col].getText().equals("X") ||
                    board[1][col].getText().equals("X") ||
                    board[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].getText().equals("O") ||
                    board[1][col].getText().equals("O") ||
                    board[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {

                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = false;
        oFlag = false;

        if(board[0][0].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {

            return false; // No tie can still have a diag win
        }
        xFlag = false;
        oFlag = false;

        if(board[0][2].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {

            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

    private void checkBoard(String player)
    {
        if(moveCnt>4)
        {
            if(isWin(player))
            {
                JOptionPane.showMessageDialog(resultPane, player + " has won!");
                for (TicTacToeTile[] t : board)
                {
                    for (TicTacToeTile m : t)
                    {
                        m.setEnabled(false);
                    }
                }
            }
        }
        if (moveCnt>6)
        {
            if(isTie())
            {
                JOptionPane.showMessageDialog(resultPane, "It's a tie!");
                for (TicTacToeTile[] t : board)
                {
                    for (TicTacToeTile m : t)
                    {
                        m.setEnabled(false);
                    }
                }
            }
        }

    }

    private void createControlPanel()
    {
        controlPnl.setLayout(new GridLayout(1,2));
        resetBtn.setFont(new Font("Plain", Font.PLAIN, 12));
        quitBtn.setFont(new Font("Plain", Font.PLAIN, 12));
        resetBtn.addActionListener(e ->
        {
            moveCnt = 1;
            for (TicTacToeTile[] t : board)
            {
                for (TicTacToeTile m : t)
                {
                    m.setText("");
                    m.setEnabled(true);
                }
            }
        });
        controlPnl.add(resetBtn);
        quitBtn.addActionListener(e -> System.exit(0));
        controlPnl.add(quitBtn);

        main.add(BorderLayout.SOUTH, controlPnl);
    }


}