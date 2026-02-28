import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame {
    private final int ROWS = 3;
    private final int COLS = 3;
    private final TicTacToeTile[][] boardButtons = new TicTacToeTile[3][3];

    JPanel mainPnl;
    JPanel boardPnl;
    JPanel quitBtnPnl;

    // INITIATING VARIABLES
    int row = 0;
    int col = 0;
    int move = 0;
    int rowMoveWasIn;
    int colMoveWasIn;
    String player;
    boolean xFlag = false;
    boolean oFlag = false;

    JButton quitBtn;


    public TicTacToeFrame() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(2, 1));

        displayBoard();
        mainPnl.add(boardPnl);

        createQuitBtnPnl();
        mainPnl.add(quitBtnPnl);

        add(mainPnl);
        {
            // CENTER FRAME IN SCREEN...CODE ADAPTED FROM CAY HORSTMANN
            Toolkit kit = Toolkit.getDefaultToolkit();                                  // getting toolkit from system and naming it "kit"
            Dimension screenSize = kit.getScreenSize();                                 // getting screen size from toolkit
            int screenHeight = screenSize.height;                                       // getting height from screen size and naming it "screenHeight"
            int screenWidth = screenSize.width;                                         // getting width from screen size and naming it "screenWidth"

            setSize(screenWidth / 11, screenHeight / 4);
            double twoPointTwo = 2.2;
            setLocation((int) (screenWidth / twoPointTwo), screenHeight / 3);


            setTitle("Fortune Teller");                                                 // name JFrame Fortune Teller
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                             // make program end when user closes JFramee
            setVisible(true);                                                           // make JFrame visible
        }
    }

    // METHODS...
    private void clearBoard() {
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                boardButtons[row][col].setText(" ");
            }
        }
    }

    private void createQuitBtnPnl() {
        quitBtnPnl = new JPanel();

        quitBtn = new JButton("Quit");
        quitBtn.setFont(new Font("Copperplate", Font.PLAIN, 45));
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        quitBtnPnl.add(quitBtn);
    }

    private void determineAndDisplayWhetherPlayerWasXorO() {
        if(move == 1 | move == 3 | move == 5 | move == 7 | move == 9)
        {
            boardButtons[rowMoveWasIn][colMoveWasIn].setText("X");
            boardButtons[rowMoveWasIn][colMoveWasIn].setEnabled(false);
            player = "X";

        }
        if(move == 2 | move == 4 | move == 6 | move == 8) {
            boardButtons[rowMoveWasIn][colMoveWasIn].setText("O");
            boardButtons[rowMoveWasIn][colMoveWasIn].setEnabled(false);
            player = "O";
        }
    }

    private void displayBoard() {
        initializeButtonsAndMakeActionable();
    }

    private void initializeButtonsAndMakeActionable() {
        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(3, 3));
        int row;
        int col;
        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < boardButtons[row].length; col++) {
                boardButtons[row][col] = new TicTacToeTile(row, col);
                boardButtons[row][col].setText(" ");
                boardButtons[row][col].setSize(2, 3);
                boardButtons[row][col].addActionListener((ActionEvent actionEvent) -> {
                    move++;
                    int rowIndex;
                    int colIndex;
                    for (rowIndex = 0; rowIndex < boardButtons.length; rowIndex++) {
                        for (colIndex = 0; colIndex < boardButtons.length; colIndex++) {
                            if(actionEvent.getSource() == boardButtons[rowIndex][colIndex]) {
                                rowMoveWasIn = rowIndex;
                                colMoveWasIn = colIndex;
                                determineAndDisplayWhetherPlayerWasXorO();
                                boolean isWin = isWin();
                                boolean isTie = isTie();
                                if (move >= 5 && isWin) {
                                    JOptionPane.showMessageDialog(null,player + " wins!");
                                    Object[] yesOrNoOptions = {"Yes", "No"};
                                    int n = JOptionPane.showOptionDialog(null, "Would you like\nto play again?", "Play Again?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesOrNoOptions, yesOrNoOptions[1]);
                                    if (n == 0) {
                                        for (int i = 0; i < ROWS; i++) {
                                            for (int j = 0; j < boardButtons[i].length; j++) {
                                                boardButtons[i][j].setText(" ");
                                                boardButtons[i][j].setEnabled(true);
                                                move = 0;
                                            }
                                        }
                                    }
                                    if (n == 1) {
                                        System.exit(0);
                                    }
                                }
                                if (move >= 7 && isTie) {
                                    JOptionPane.showMessageDialog(null,"It's a tie!");
                                    Object[] yesOrNoOptions = {"Yes", "No"};
                                    int optionsInt = JOptionPane.showOptionDialog(null, "Would you like\nto play again?", "Play Again?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesOrNoOptions, yesOrNoOptions[1]);
                                    if (optionsInt == 0) {
                                        for (int i = 0; i < ROWS; i++) {
                                            for (int j = 0; j < boardButtons[i].length; j++) {
                                                boardButtons[i][j].setText(" ");
                                                boardButtons[i][j].setEnabled(true);
                                                move = 0;
                                            }
                                        }
                                    }
                                    if (optionsInt == 1) {
                                        System.exit(0);
                                    }
                                }
                            }
                        }
                    }
                });
                boardPnl.add(boardButtons[row][col]);
            }
        }
    }

    private boolean isWin() {
        return isColWin() || isRowWin() || isDiagonalWin();
    }
    private boolean isRowWin() {
        for(int row = 0; row < ROWS; row++) {
            if(boardButtons[row][0].getText().equals(player) && boardButtons[row][1].getText().equals(player) && boardButtons[row][2].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isColWin() {
        for(int col = 0; col < ROWS; col++) {
            if(boardButtons[0][col].getText().equals(player) && boardButtons[1][col].getText().equals(player) && boardButtons[2][col].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin() {
        for(int row = 0; row < ROWS; row++) {
            if(boardButtons[0][0].getText().equals(player) && boardButtons[1][1].getText().equals(player) && boardButtons[2][2].getText().equals(player)) {
                return true;
            }
            if(boardButtons[2][0].getText().equals(player) && boardButtons[1][1].getText().equals(player) && boardButtons[0][2].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTie()
    {
// Check all 8 win vectors for an X and O so
// no win is possible
// Check for row ties
        for(int row=0; row < ROWS; row++)
        {
            if(boardButtons[row][0].getText().equals("X") ||
               boardButtons[row][1].getText().equals("X") ||
               boardButtons[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(boardButtons[row][0].getText().equals("O") ||
               boardButtons[row][1].getText().equals("O") ||
               boardButtons[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }
            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }
            xFlag = oFlag = false;
        }
// Now scan the columns
        for(int col=0; col < COLS; col++)
        {
            if(boardButtons[0][col].getText().equals("X") ||
               boardButtons[1][col].getText().equals("X") ||
               boardButtons[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(boardButtons[0][col].getText().equals("O") ||
               boardButtons[1][col].getText().equals("O") ||
               boardButtons[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }
            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
// Now check for the diagonals
        xFlag = oFlag = false;
        if(boardButtons[0][0].getText().equals("X") ||
           boardButtons[1][1].getText().equals("X") ||
           boardButtons[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(boardButtons[0][0].getText().equals("O") ||
           boardButtons[1][1].getText().equals("O") ||
           boardButtons[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;
        if(boardButtons[0][2].getText().equals("X") ||
           boardButtons[1][1].getText().equals("X") ||
           boardButtons[2][0].getText().equals("X") )
        {
            xFlag = true;
        }
        if(boardButtons[0][2].getText().equals("O") ||
           boardButtons[1][1].getText().equals("O") ||
           boardButtons[2][0].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diagonal win
        }
// Checked every vector so I know I have a tie
        return true;
    }

    public class TicTacToeTile extends JButton {
        private final int row;
        private final int col;
        public TicTacToeTile(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }
        public int getRow() {
            return row;
        }
        public int getCol() {
            return col;
        }
    }
}