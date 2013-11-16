import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.accessibility.*;
import java.util.*;

public class MemoryGame extends JFrame implements ActionListener {
   private final int BOARD_SIZE = 4;
   private final int MAX_CARD = BOARD_SIZE * BOARD_SIZE / 2;

   private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
   private JButton[][] button = new JButton[BOARD_SIZE][BOARD_SIZE];

   private JButton lastButton = null;
   private int pairFound = 0;

   public MemoryGame() {
      setTitle("Memory Game");
      setSize(200, 200);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      randomBoard();
      drawBoard();

      setVisible(true);
   }

   private void randomBoard() {
      Random rand = new Random();
      for (int i=0; i<BOARD_SIZE; i++) Arrays.fill(board[i], 0);

      for (int i=1; i<=MAX_CARD; i++) {
         for (int j=0; j<2; j++) {
            int x, y;
            do {
               x = rand.nextInt(BOARD_SIZE);
               y = rand.nextInt(BOARD_SIZE);
            } while (board[x][y] != 0);
            board[x][y] = i;
         }
      }
   }

   private void drawBoard() {
      JPanel panel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
      for (int i=0; i<BOARD_SIZE; i++) {
         for (int j=0; j<BOARD_SIZE; j++) {
            button[i][j] = new JButton();
            button[i][j].addActionListener(this);
            panel.add(button[i][j]);
         }
      }
      add(panel);
   }

   private int[] getButtonPressed(Object source) {
      for (int i=0; i<BOARD_SIZE; i++)
         for (int j=0; j<BOARD_SIZE; j++)
            if (button[i][j] == source) {
               int retval[] = {i, j};
               return retval;
            }
      return null;
   }

   private void checkWinner() {
      if (pairFound == MAX_CARD)
         JOptionPane.showMessageDialog(this, "You win!");
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      
      int idx[] = getButtonPressed(src);
      int x = idx[0], y = idx[1];
      JButton pressed = button[x][y];

      if (pressed.getText().length() == 0) {
         if (lastButton == null) {
            lastButton = pressed;
            lastButton.setText("" + board[x][y]);
         }
         else {
            if (Integer.parseInt(lastButton.getText()) == board[x][y]) {
               pressed.setText("" + board[x][y]);
               pairFound++;
               checkWinner();
            }
            else {
               lastButton.setText("");
            }
            lastButton = null;
         }
      }
      
   }

   public static void main(String[] args) {
      MemoryGame frm = new MemoryGame();
   }

   private static void debug(String str) {
      System.out.println(str);
   }
}
