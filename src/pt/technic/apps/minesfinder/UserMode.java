package pt.technic.apps.minesfinder;

import javax.swing.GroupLayout.Alignment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author Gabriel Massadas
 */
public class UserMode extends javax.swing.JFrame {

	private ButtonMinefield[][] buttons;
	private Minefield minefield;
	//리팩토링4


	JPanel mainPanel = new JPanel();
	JPanel statePanel = new JPanel();
	JPanel gridPanel = new JPanel();
	JLabel time = new JLabel();
	JLabel timerLabel = new JLabel();
	JLabel clicknum = new JLabel();
	JLabel click = new JLabel();
	JLabel minesleft = new JLabel();
	JLabel mines = new JLabel();

	GameSound sound = new GameSound();

	/**
	 * ` Creates new form GameWindow
	 */
	public UserMode() {

		initComponents();
	}

	public void pressedLeft(int x, int y) { // 왼쪽키 눌렀을 때
		clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));
		minefield.revealGrid(x, y);
		sound.GameSound("click.wav");
		updateButtonsStates();
		if (minefield.isGameFinished()) {
			if (minefield.isPlayerDefeated()) {
				minefield.revealMines();
				updateButtonsStates();
				sound.GameSound("bomb.wav");
				sound.GameSound("over.wav");
				JOptionPane.showMessageDialog(null, "Oh, a mine broke", // 게임 실패
						"Lost!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				sound.GameSound("win.wav");
				JOptionPane.showMessageDialog(null, "Congratulations\n. You managed to discover all the mines in " // 게임
								// 성공
								+ (minefield.getGameDuration() / 1000) + " seconds\n" + "You Clicked " + clicknum.getText(),
						"victory", JOptionPane.INFORMATION_MESSAGE);
			}
			setVisible(false);
		}
	}

	public UserMode(Minefield minefield) {

		initComponents();

		this.minefield = minefield;
		//리팩토링4

		int left;
		left = minefield.getNumMines();
		minesleft.setText(Integer.toString(left));

		buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];

		gridPanel.setLayout(new GridLayout(minefield.getWidth(), // 여기에 +1하면 칸하나 늘어남
				minefield.getHeight()));

		statePanel.setPreferredSize(new Dimension(700, 100));

		TimerThread th = new TimerThread(timerLabel);
		th.start();

		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (minefield.isGameFinished() ) {			//리팩토링4
					th.interrupt();
				}
			}
		};


		MouseListener mouseListener = new MouseListener() {

			public void mouseLeft(MouseEvent e) { // 마우스 좌클릭
				ButtonMinefield button = (ButtonMinefield) e.getSource(); // 버튼 좌표 얻기
				int x = button.getCol();
				int y = button.getLine();
				pressedLeft(x, y);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) { // 마우스 우클릭
					ButtonMinefield botao = (ButtonMinefield) e.getSource();
					clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));
					int x = botao.getCol();
					int y = botao.getLine();
					if (minefield.getGridState(x, y) == Minefield.COVERED) {			//리팩토링12
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));
						minefield.setMineMarked(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.MARKED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) + 1));
						minefield.setMineQuestion(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.QUESTION) {
						minefield.setMineCovered(x, y);
					}
					sound.GameSound("flag_mine.wav");
					updateButtonsStates();
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					mouseLeft(e);
				}

			}

			@Override
			public void mouseClicked(MouseEvent me) {
			}

			@Override
			public void mouseReleased(MouseEvent me) {
			}

			@Override
			public void mouseEntered(MouseEvent me) {
			}

			@Override
			public void mouseExited(MouseEvent me) {
			}
		};

		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				ButtonMinefield botao = (ButtonMinefield) e.getSource();

				int x = botao.getCol();
				int y = botao.getLine();

				if (e.getKeyCode() == KeyEvent.VK_LEFT && y > 0) { // 1p
					buttons[x][y - 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP && x > 0) {
					buttons[x - 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && y < minefield.getHeight() - 1) {
					buttons[x][y + 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN && x < minefield.getWidth() - 1) {
					buttons[x + 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_M) {
					clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));
					if (minefield.getGridState(x, y) == Minefield.COVERED) {			//리팩토링12
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));
						minefield.setMineMarked(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.MARKED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) + 1));
						minefield.setMineQuestion(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.QUESTION) {
						minefield.setMineCovered(x, y);
					}
					sound.GameSound("flag_mine.wav");
					updateButtonsStates();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					pressedLeft(x, y);
				}

			}

			@Override
			public void keyTyped(KeyEvent ke) {
			}

			@Override
			public void keyReleased(KeyEvent ke) {
			}
		};

		// Create buttons for the player
		for (int x = 0; x < minefield.getWidth(); x++) {
			for (int y = 0; y < minefield.getHeight(); y++) {
				buttons[x][y] = new ButtonMinefield(x, y);
				buttons[x][y].addMouseListener(mouseListener);
				buttons[x][y].addKeyListener(keyListener);
				buttons[x][y].addActionListener(action);
				gridPanel.add(buttons[x][y]);
			}
		}
	}


	private void updateButtonsStates() {
		for (int x = 0; x < minefield.getWidth(); x++) {
			for (int y = 0; y < minefield.getHeight(); y++) {
				buttons[x][y].setEstado(minefield.getGridState(x, y));
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Game");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		javax.swing.GroupLayout layout1 = new javax.swing.GroupLayout(gridPanel);
		layout1.setHorizontalGroup(layout1.createParallelGroup(Alignment.LEADING).addGap(0, 700, Short.MAX_VALUE));
		layout1.setVerticalGroup(layout1.createParallelGroup(Alignment.LEADING).addGap(0, 700, Short.MAX_VALUE));
		gridPanel.setLayout(layout1);

		mainPanel.add(gridPanel, "Center");
		click.setText("Click : ");
		time.setText("Time : ");
		clicknum.setText("0");
		mines.setText("Left : ");

		statePanel.add(click);
		statePanel.add(clicknum);
		statePanel.add(time);
		statePanel.add(timerLabel);
		statePanel.add(mines);
		statePanel.add(minesleft);

		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(statePanel, "North");
		getContentPane().add(gridPanel, "Center");

		pack();

	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException |IllegalAccessException | UnsupportedLookAndFeelException ex) {		//리팩토링5
			java.util.logging.Logger.getLogger(UserMode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new UserMode().setVisible(true);
			}
		});
	}
}
