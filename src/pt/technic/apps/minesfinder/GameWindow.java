package pt.technic.apps.minesfinder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;

import java.awt.event.*;

import java.util.logging.*;
import java.io.*;
import java.net.MalformedURLException;
import javax.sound.sampled.*;
import java.util.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

/**
 *
 * @author Gabriel Massadas
 */
public class GameWindow extends javax.swing.JFrame {

	private ButtonMinefield[][] buttons;
	private Minefield minefield;
	private RecordTable timerecord;
	private RecordTable clickrecord;
	private MinesFinder minesfinder;

	int ClickPoints = 0;

	JPanel mainPanel = new JPanel();
	JPanel statePanel = new JPanel();
	JPanel gridPanel = new JPanel();
	JLabel time = new JLabel();
	JLabel timerLabel = new JLabel();
	JLabel clicknum = new JLabel();			//클릭수 증가
	JLabel click = new JLabel();
	JLabel minesleft = new JLabel();
	JLabel mines = new JLabel();

	/**
	 * ` Creates new form GameWindow
	 */
	public GameWindow() {

		initComponents();
	}

	public void pressedLeft(int x, int y) { // 왼쪽키 눌렀을 때
		clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));		//클릭수 증가
		minefield.revealGrid(x, y);
		ClickPoints+=1;				//클릭할때 1증가
		updateButtonsStates();
		try {
			playSound_click("click.wav");
		} catch (MalformedURLException ex) {
			Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
		} catch (LineUnavailableException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedAudioFileException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (minefield.isGameFinished()) {
			if (minefield.isPlayerDefeated()) {
				minefield.revealMines();
				updateButtonsStates();
				try {
					playSound_bomb("bomb.wav");
					playSound_over("over.wav");
				} catch (MalformedURLException ex) {
					Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
				} catch (LineUnavailableException ex) {
					Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
				} catch (UnsupportedAudioFileException ex) {
					Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
				}
				JOptionPane.showMessageDialog(null, "Oh, a mine broke", // 게임 실패
						"Lost!", JOptionPane.INFORMATION_MESSAGE);
				int result = JOptionPane.showConfirmDialog(null, "Retry?", "Retry", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					new GameWindow(new Minefield(minefield.getWidth(), minefield.getHeight(), minefield.getNumMines()),
							timerecord, clickrecord).setVisible(true);
				} else {
				}
			} else {
				try {
					playSound_win("win.wav");
				} catch (MalformedURLException ex) {
					Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
				} catch (LineUnavailableException ex) {
					Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
				} catch (UnsupportedAudioFileException ex) {
					Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
				}
				JOptionPane.showMessageDialog(null, "Congratulations\n. You managed to discover all the mines in " // 게임
								// 성공
								+ (minefield.getGameDuration() / 1000) + " seconds\n" + "You Clicked " + clicknum.getText(),
						"victory", JOptionPane.INFORMATION_MESSAGE);
//					long a = minefield.getGameDuration();
//					long b = record.getScore();
				boolean newRecord = minefield.getGameDuration() < timerecord.getScore();
				if (newRecord) { // 기록 갱신
					String name = JOptionPane.showInputDialog("Enter your name");
					if (name != "")
						timerecord.setRecord(name, minefield.getGameDuration());
				}

				boolean newClickRecord = ClickPoints < clickrecord.getClickPoints();
				if(newClickRecord) {
					String Clickname =  JOptionPane.showInputDialog("Enter your Clickname");
					if (Clickname != "")
						clickrecord.setClickRecord(Clickname, ClickPoints);
				}
			}
			setVisible(false);
		}
	}

	public GameWindow(Minefield minefield, RecordTable timerecord, RecordTable clickrecord) {

		initComponents();

		this.minefield = minefield;
		this.timerecord = timerecord;
		this.clickrecord = clickrecord;

		int left;
		left = minefield.getNumMines();
		minesleft.setText(Integer.toString(left));		//남은지뢰개수

		buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];

		gridPanel.setLayout(new GridLayout(minefield.getWidth(), 		//그리드						// 여기에 +1하면 칸하나 늘어남
				minefield.getHeight()));

		statePanel.setPreferredSize(new Dimension(700, 100));		//상태바

		TimerThread th = new TimerThread(timerLabel);		//타이머
		th.start();		//타이머 시작

		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (minefield.isGameFinished() == true) {
					th.interrupt();		//타이머 종료
				}
			}
		};

//        ActionListener action = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {	//버튼 누르면?
////                ButtonMinefield button = (ButtonMinefield) e.getSource();	//버튼 좌표 얻기
////                int x = button.getCol();
////                int y = button.getLine();
////                minefield.revealGrid(x, y);
////                updateButtonsStates();
////                if (minefield.isGameFinished()) {
////                    if (minefield.isPlayerDefeated()) {
////                        JOptionPane.showMessageDialog(null, "Oh, a mine broke",		//게임 실패
////                                "Lost!", JOptionPane.INFORMATION_MESSAGE);
////                    } else {
////                        JOptionPane.showMessageDialog(null, "Congratulations. You managed to discover all the mines in "		//게임 성공
////                                + (minefield.getGameDuration() / 1000) + " seconds",
////                                "victory", JOptionPane.INFORMATION_MESSAGE
////                        );
////                        long a = minefield.getGameDuration();
////                        long b = record.getScore();
////                        boolean newRecord = minefield.getGameDuration() < record.getScore();
////
////                        if (newRecord) {			//기록 갱신
////                            String name = JOptionPane.showInputDialog("Enter your name");
////                            if(name != "")
////                                record.setRecord(name, minefield.getGameDuration());
////                        }
////                    }
////                    setVisible(false);
////                }
//            }
//        };

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
					clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));		//클릭수 증가
					ClickPoints +=1;				//클릭수 증가
					int x = botao.getCol();
					int y = botao.getLine();
					if (minefield.getGridState(x, y) == minefield.COVERED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));	//남은 지뢰개수 감소
						minefield.setMineMarked(x, y);
					} else if (minefield.getGridState(x, y) == minefield.MARKED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) + 1));	//남은 지뢰개수 감소
						minefield.setMineQuestion(x, y);
					} else if (minefield.getGridState(x, y) == minefield.QUESTION) {
						minefield.setMineCovered(x, y);
					}
					updateButtonsStates();
					try {
						playSound_flag("flag_mine.wav");
					} catch (MalformedURLException ex) {
						Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
					} catch (LineUnavailableException ex) {
						Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
					} catch (UnsupportedAudioFileException ex) {
						Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
					} catch (IOException ex) {
						Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
					}
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

				if (e.getKeyCode() == KeyEvent.VK_LEFT && y > 0) {
					buttons[x][y - 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP && x > 0) {
					buttons[x - 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && y < minefield.getHeight() - 1) {
					buttons[x][y + 1].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN && x < minefield.getWidth() - 1) {
					buttons[x + 1][y].requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_M) {
					clicknum.setText(String.valueOf(Integer.valueOf(clicknum.getText()) + 1));	//클릭 수 증가
					if (minefield.getGridState(x, y) == minefield.COVERED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) - 1));	//남은 지뢰개수 감소
						minefield.setMineMarked(x, y);
					} else if (minefield.getGridState(x, y) == minefield.MARKED) {
						minesleft.setText(String.valueOf(Integer.valueOf(minesleft.getText()) + 1));	//남은 지뢰개수 증가
						minefield.setMineQuestion(x, y);
					} else if (minefield.getGridState(x, y) == minefield.QUESTION) {
						minefield.setMineCovered(x, y);
					}
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

	public void playSound_click(String string) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" +"click.wav");

		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.
				getAudioInputStream( url );
		clip.open(ais);
		clip.start();
	}

	public void playSound_flag(String string) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" +"flag_mine.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.
				getAudioInputStream( url );
		clip.open(ais);
		clip.start();
	}


	public void playSound_bomb(String string) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" + "bomb.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.
				getAudioInputStream( url );
		clip.open(ais);
		clip.start();
	}

	public void playSound_win(String string) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" + "win.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.
				getAudioInputStream( url );
		clip.open(ais);
		clip.start();
	}
	public void playSound_over(String string) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/" + "over.wav");
		Clip clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.
				getAudioInputStream( url );
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
		layout1.setHorizontalGroup(layout1.createParallelGroup(Alignment.LEADING).addGap(0, 1094, Short.MAX_VALUE));
		layout1.setVerticalGroup(layout1.createParallelGroup(Alignment.LEADING).addGap(0, 553, Short.MAX_VALUE));
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
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GameWindow().setVisible(true);
			}
		});
	}
}
