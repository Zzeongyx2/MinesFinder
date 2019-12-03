package pt.technic.apps.minesfinder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

/**
 *
 * @author Gabriel Massadas
 */
public class PracticeMode extends javax.swing.JFrame {

	private ButtonMinefield[][] buttons;
	private Minefield minefield;

	JPanel mainPanel = new JPanel();
	JPanel statePanel = new JPanel();
	JPanel gridPanel = new JPanel();
	JLabel time = new JLabel();
	JLabel timerLabel = new JLabel();
	JLabel clicknum = new JLabel();
	JLabel click = new JLabel();
	JLabel minesleft = new JLabel();
	JLabel mines = new JLabel();
	JLabel life = new JLabel();
	JLabel lifenum = new JLabel();

	int leftLife;

	/**
	 * ` Creates new form GameWindow
	 */
	public PracticeMode() {

		initComponents();
	}

	public void pressedLeft(int x, int y) { // øﬁ¬ ≈∞ ¥≠∑∂¿ª ∂ß
		clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));
		minefield.revealGrid2(x, y);
		try {
			playSound_click();
		} catch (MalformedURLException ex) {
			Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
		}  catch (LineUnavailableException|UnsupportedAudioFileException|IOException ex) {           //∏Æ∆—≈‰∏µ5
			Logger.getLogger(PracticeMode.class.getName()).log(Level.SEVERE, null, ex);
		}
		updateButtonsStates();
		
		updateLifeStates();

		if (leftLife == 0)
			minefield.finishGame();		//∏Æ∆—≈‰∏µ8

		if (minefield.isGameFinished()) {
			if (minefield.isPlayerDefeated()) {
				minefield.revealMines();
				try {
					playSound_bomb();		//∏Æ∆—≈‰∏µ13
					playSound_over();
				} catch (MalformedURLException ex) {
					Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
				}  catch (LineUnavailableException|UnsupportedAudioFileException|IOException ex) {           //∏Æ∆—≈‰∏µ5
					Logger.getLogger(PracticeMode.class.getName()).log(Level.SEVERE, null, ex);
				}
				JOptionPane.showMessageDialog(null, "Oh, a mine broke", // ∞‘¿” Ω«∆–
						"Lost!", JOptionPane.INFORMATION_MESSAGE);
			}
		 else {
			try {
				playSound_win();
			} catch (MalformedURLException ex) {
				Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
			}  catch (LineUnavailableException|UnsupportedAudioFileException|IOException ex) {           //∏Æ∆—≈‰∏µ5
				Logger.getLogger(PracticeMode.class.getName()).log(Level.SEVERE, null, ex);
			}
			JOptionPane.showMessageDialog(null, "Congratulations\n. You managed to discover all the mines in " // ∞‘¿”
																												// º∫∞¯
					+ (minefield.getGameDuration() / 1000) + " seconds\n" + "You Clicked " + clicknum.getText(),
					"victory", JOptionPane.INFORMATION_MESSAGE);
		}
		setVisible(false);
	}
	}

	public PracticeMode(Minefield minefield) {

		initComponents();

		this.minefield = minefield;
		int left;
		leftLife = minefield.getLife();
		lifenum.setText(Integer.toString(leftLife));
		left = minefield.getNumMines();
		minesleft.setText(Integer.toString(left));

		buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];

		gridPanel.setLayout(new GridLayout(minefield.getWidth(), // ø©±‚ø° +1«œ∏È ƒ≠«œ≥™ ¥√æÓ≥≤
				minefield.getHeight()));

		statePanel.setPreferredSize(new Dimension(700, 100));

		TimerThread th = new TimerThread(timerLabel);
		th.start();

		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (minefield.isGameFinished()) {		//∏Æ∆—≈‰∏µ4
					th.interrupt();
				}
			}
		};

		MouseListener mouseListener = new MouseListener() {

			public void mouseLeft(MouseEvent e) { // ∏∂øÏΩ∫ ¡¬≈¨∏Ø
				ButtonMinefield button = (ButtonMinefield) e.getSource(); // πˆ∆∞ ¡¬«• æÚ±‚
				int x = button.getCol();
				int y = button.getLine();
				pressedLeft(x, y);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) { // ∏∂øÏΩ∫ øÏ≈¨∏Ø
					ButtonMinefield botao = (ButtonMinefield) e.getSource();
					clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));
					int x = botao.getCol();
					int y = botao.getLine();
					if (minefield.getGridState(x, y) == Minefield.COVERED) {		//∏Æ∆—≈‰∏µ12
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));
						minefield.setMineMarked(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.MARKED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) + 1));
						minefield.setMineQuestion(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.QUESTION) {
						minefield.setMineCovered(x, y);
					}
					updateButtonsStates();
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					mouseLeft(e);
				}

			}

			@Override
			public void mouseClicked(MouseEvent me) {
				throw new UnsupportedOperationException();		//∏Æ∆—≈‰∏µ1
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				throw new UnsupportedOperationException();		//∏Æ∆—≈‰∏µ1
			}

			@Override
			public void mouseEntered(MouseEvent me) {
				throw new UnsupportedOperationException();		//∏Æ∆—≈‰∏µ1
			}

			@Override
			public void mouseExited(MouseEvent me) {
				throw new UnsupportedOperationException();		//∏Æ∆—≈‰∏µ1
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
					if (minefield.getGridState(x, y) == Minefield.COVERED) {		//∏Æ∆—≈‰∏µ12
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));
						minefield.setMineMarked(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.MARKED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) + 1));
						minefield.setMineQuestion(x, y);
					} else if (minefield.getGridState(x, y) == Minefield.QUESTION) {
						minefield.setMineCovered(x, y);
					}
					updateButtonsStates();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					pressedLeft(x, y);
				}

			}

			@Override
			public void keyTyped(KeyEvent ke) {
				throw new UnsupportedOperationException();		//∏Æ∆—≈‰∏µ1
			}

			@Override
			public void keyReleased(KeyEvent ke) {
				throw new UnsupportedOperationException();		//∏Æ∆—≈‰∏µ1
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

	String path = System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/";		//∏Æ∆—≈‰∏µ3

	public void playSound_click()
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(path+ "click.wav");

		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.start();
	}

	public void playSound_flag()
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(path + "flag_mine.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.start();
	}

	public void playSound_bomb()
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(
				path + "bomb.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.start();
	}

	public void playSound_win()
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(path + "win.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.start();
	}

	public void playSound_over()
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(path + "over.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.start();
	}

	private void updateButtonsStates() {
		for (int x = 0; x < minefield.getWidth(); x++) {
			for (int y = 0; y < minefield.getHeight(); y++) {
				buttons[x][y].setEstado(minefield.getGridState(x, y));
			}
		}
	}

	private void updateLifeStates() {
		leftLife = minefield.getLife();
		if (Integer.parseInt(lifenum.getText()) > leftLife) {
			minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));
		}
		lifenum.setText(Integer.toString(leftLife));
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
		life.setText("Life : ");

		statePanel.add(click);
		statePanel.add(clicknum);
		statePanel.add(time);
		statePanel.add(timerLabel);
		statePanel.add(mines);
		statePanel.add(minesleft);
		statePanel.add(life);
		statePanel.add(lifenum);

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
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException| UnsupportedLookAndFeelException ex) {		//∏Æ∆—≈‰∏µ5
			java.util.logging.Logger.getLogger(PracticeMode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PracticeMode().setVisible(true);
			}
		});
	}
}
