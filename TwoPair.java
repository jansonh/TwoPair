import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.accessibility.*;

import java.util.*;

public class TwoPair extends JFrame implements ActionListener {
   private final int BOARD_SIZE = 6;
   private final int MAX_CARD = (BOARD_SIZE * BOARD_SIZE / 2);
   
   private int[][] board;
   private JButton[][] button;

   private int[] openedCard;
   private int pairFound;
   
   public TwoPair() {
      super("Two Pair");
      setBounds(100, 100, 500, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      init();
      randomBoard();
      createBoard();

      setVisible(true);
   }

   private void init() {
      board = new int[BOARD_SIZE][BOARD_SIZE];
      button = new JButton[BOARD_SIZE][BOARD_SIZE];

      for (int i=0; i<BOARD_SIZE; i++)
         for (int j=0; j<BOARD_SIZE; j++)
            board[i][j] = -1;

      openedCard = null;
      pairFound = 0;
   }

   private void randomBoard() {
      Random rand = new Random();
      for (int i=1; i<=MAX_CARD; i++) {
         for (int j=0; j<2; j++) {
            int x, y;
            do {
               x = rand.nextInt(BOARD_SIZE);
               y = rand.nextInt(BOARD_SIZE);
            } while (board[x][y] != -1);
            board[x][y] = i;
         }
      }
   }

   private void createBoard() {
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

      Container container = this.getContentPane();
      container.add(panel);

      for (int i=0; i<BOARD_SIZE; i++)
         for (int j=0; j<BOARD_SIZE; j++) {
            button[i][j] = new JButton();
            button[i][j].addActionListener(this);

            AccessibleContext info = button[i][j].getAccessibleContext();
            info.setAccessibleDescription(i + "," + j);

            panel.add(button[i][j]);
         }
   }

   private boolean win() {
      return (pairFound == MAX_CARD);
   }

   private int[] getButtonLocation(JButton btn) {
      String info = btn.getAccessibleContext().getAccessibleDescription();
      String[] split = info.split(",");
      int[] retval = {Integer.parseInt(split[0]), Integer.parseInt(split[1])};
      return retval;
   }

   public void actionPerformed(ActionEvent e) {
      int[] location = getButtonLocation((JButton)e.getSource());
      int x = location[0], y = location[1];

      if (openedCard != null) {
         if (board[openedCard[0]][openedCard[1]] == board[x][y]) {
            button[x][y].setText("" + board[x][y]);
            button[x][y].setEnabled(false);
            pairFound++;

            if (win()) {
               JOptionPane.showMessageDialog(this, "Congratulations!");
            }
         }
         else {
            button[openedCard[0]][openedCard[1]].setText("");
            button[openedCard[0]][openedCard[1]].setEnabled(true);
         }
         openedCard = null;
      }
      else {
         openedCard = new int[2];
         openedCard[0] = x;
         openedCard[1] = y;
         
         button[x][y].setText("" + board[x][y]);
         button[x][y].setEnabled(false);
      }
   }
   
   public static void main(String[] args) {
      TwoPair frm = new TwoPair();
   }
}
