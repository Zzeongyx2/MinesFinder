
package pt.technic.apps.minesfinder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;		//리팩토링10
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;		//리팩토링10

/**
 *
 * @author Gabriel Massadas
 */
public class MinesFinder extends JFrame {				//리팩토링10

	private RecordTable recordEasy;
	private RecordTable recordMedium;
	private RecordTable recordHard;
	private RecordTable recordClick;
	private RecordTable recordMediumClick;
	private RecordTable recordHardClick;
	private static Clip clip;

	JTextField widthtext = new JTextField(2);
	JTextField heighttext = new JTextField(2);
	JTextField minestext = new JTextField(2);
	JTextField lifetext = new JTextField(2);

	JTextField userwidthtext = new JTextField(2);
	JTextField userheighttext = new JTextField(2);
	JTextField userminestext = new JTextField(2);

	JLabel panelTitle = new JLabel();		//리팩토링10, 리팩토링11
	JPanel panelRecords = new JPanel();
	JLabel records = new JLabel();			//리팩토링8
	JLabel labelEasy = new JLabel();
	JLabel labelEasyName = new JLabel();
	JLabel labelEasyPoints = new JLabel();
	JLabel labelMedium = new JLabel();
	JLabel labelMediumName = new JLabel();
	JLabel labelMediumPoints = new JLabel();
	JLabel labelHard = new JLabel();
	JLabel labelHardName = new JLabel();
	JLabel labelHardPoints = new JLabel();

	JLabel labelClick = new JLabel();
	JLabel labelMediumClick = new JLabel();
	JLabel labelHardClick = new JLabel();
	JLabel labelClickName = new JLabel();
	JLabel labelClickPoints = new JLabel();
	JLabel labelTime = new JLabel();
	JLabel labelMediumTime = new JLabel();
	JLabel labelHardTime = new JLabel();
	JLabel labelMediumClickName = new JLabel();
	JLabel labelMediumClickPoints = new JLabel();
	JLabel labelHardClickName = new JLabel();
	JLabel labelHardClickPoints = new JLabel();

	JPanel panelBtns = new JPanel();
	JButton btnEasy = new JButton();
	JButton btnMedium = new JButton();
	JButton btnHard = new JButton();
	JButton btnExit = new JButton();
	JButton btnUser = new JButton();
	JButton btnMulti = new JButton();
	JButton btnPractice = new JButton();
	JPanel userfield = new JPanel();
	JPanel practicefield = new JPanel();

	/**
	 * Creates new form MinesFinder
	 */
	public MinesFinder() {
		initComponents();
		recordEasy = new RecordTable();
		recordMedium = new RecordTable();
		recordHard = new RecordTable();
		recordClick = new RecordTable();
		recordMediumClick = new RecordTable();
		recordHardClick =new RecordTable();
		
		readGameRecords();

		labelEasyName.setText(recordEasy.getName());
		labelEasyPoints.setText(Long.toString(recordEasy.getScore() / 1000));
		labelMediumName.setText(recordMedium.getName());
		labelMediumPoints.setText(Long.toString(recordMedium.getScore() / 1000));
		labelHardName.setText(recordHard.getName());
		labelHardPoints.setText(Long.toString(recordHard.getScore() / 1000));

		labelClickName.setText(recordClick.getClickname());
		labelClickPoints.setText(Integer.toString(recordClick.getClickPoints()));
		labelMediumClickName.setText(recordMediumClick.getClickname());
		labelMediumClickPoints.setText(Integer.toString(recordMediumClick.getClickPoints()));
		labelHardClickName.setText(recordHardClick.getClickname());
		labelHardClickPoints.setText(Integer.toString(recordHardClick.getClickPoints()));
		
		recordEasy.addRecordTableListener(new RecordTableListener() {
			@Override
			public void recordUpdated(RecordTable record) {
				recordEasyUpdated(record);
			}
		});
		
		recordClick.addRecordTableListener(new RecordTableListener() {
			@Override
			public void recordUpdated(RecordTable record) {
				recordClickUpdated(record);
			}
		});
		
		recordMediumClick.addRecordTableListener(new RecordTableListener() {
			@Override
			public void recordUpdated(RecordTable record) {
				recordMediumClickUpdated(record);
			}
			
		});
		
		recordHardClick.addRecordTableListener(new RecordTableListener() {
			@Override
			public void recordUpdated(RecordTable record) {
				recordHardClickUpdated(record);
			}
		});
		
		recordMedium.addRecordTableListener(new RecordTableListener() {
			@Override
			public void recordUpdated(RecordTable record) {
				recordMediumUpdated(record);
			}
		});

		recordHard.addRecordTableListener(new RecordTableListener() {
			@Override
			public void recordUpdated(RecordTable record) {
				recordHardUpdated(record);
			}
		});
	}

	private void recordEasyUpdated(RecordTable record) {
		labelEasyName.setText(record.getName());
		labelEasyPoints.setText(Long.toString(record.getScore() / 1000));
		saveGameRecords();
	}
	

	private void recordMediumUpdated(RecordTable record) {
		labelMediumName.setText(record.getName());
		labelMediumPoints.setText(Long.toString(record.getScore()/ 1000));
		saveGameRecords();
	}

	private void recordHardUpdated(RecordTable record) {
		labelHardName.setText(record.getName());
		labelHardPoints.setText(Long.toString(record.getScore()/ 1000));
		saveGameRecords();
	}

	private void recordClickUpdated(RecordTable record) {
		labelClickName.setText(record.getClickname());
		labelClickPoints.setText(Integer.toString(record.getClickPoints()));
		saveGameRecords();
	}
	private void recordMediumClickUpdated(RecordTable record) {
		labelMediumClickName.setText(record.getClickname());
		labelMediumClickPoints.setText(Integer.toString(record.getClickPoints()));
		saveGameRecords();
	}
	private void recordHardClickUpdated(RecordTable record) {
		labelHardClickName.setText(record.getClickname());
		labelHardClickPoints.setText(Integer.toString(record.getClickPoints()));
		saveGameRecords();
	}
	
	
	
	
	private void saveGameRecords() {
		ObjectOutputStream oos = null;
		try {
			File f = new File(System.getProperty("user.home") + File.separator + ".minesfinder.records");
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(recordEasy);
			oos.writeObject(recordMedium); 
			oos.writeObject(recordHard);
			oos.writeObject(recordClick);
			oos.writeObject(recordMediumClick);
			oos.writeObject(recordHardClick);
			oos.close();
		} catch (IOException ex) {
			Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void readGameRecords() {
		ObjectInputStream ois = null;
		File f = new File(System.getProperty("user.home") + File.separator + ".minesfinder.records");
		if (f.canRead()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(f));
				recordEasy = (RecordTable) ois.readObject();
				recordMedium = (RecordTable) ois.readObject();
				recordHard = (RecordTable) ois.readObject();
				recordClick =(RecordTable) ois.readObject();
				recordMediumClick = (RecordTable) ois.readObject();
				recordHardClick  = (RecordTable) ois.readObject();
				ois.close();
			} catch (IOException | ClassNotFoundException ex) {
				Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	public static void playSound_bgm()			//리팩토링13
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		File url = new File(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/"
				+ "mine_bgm.wav");

		clip = AudioSystem.getClip();

		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip.open(ais);
		clip.start();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		practicefield.add(new JLabel("width"));				//연습모드 옵션페인
		practicefield.add(widthtext);
		practicefield.add(Box.createHorizontalStrut(2));
		practicefield.add(new JLabel("height"));
		practicefield.add(heighttext);
		practicefield.add(Box.createHorizontalStrut(2));
		practicefield.add(new JLabel("miens"));
		practicefield.add(minestext);
		practicefield.add(Box.createHorizontalStrut(2));
		practicefield.add(new JLabel("lifes"));
		practicefield.add(lifetext);
		practicefield.add(Box.createHorizontalStrut(2));

		userfield.add(new JLabel("width"));				//유저모드 옵션페인
		userfield.add(userwidthtext);
		userfield.add(Box.createHorizontalStrut(2));
		userfield.add(new JLabel("height"));
		userfield.add(userheighttext);
		userfield.add(Box.createHorizontalStrut(2));
		userfield.add(new JLabel("miens"));
		userfield.add(userminestext);
		userfield.add(Box.createHorizontalStrut(2));

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);			//리팩토링10
		setTitle("MinesFinder");
		setCursor(new Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setPreferredSize(new Dimension(600, 450));
		setResizable(false);

		panelTitle.setBackground(new Color(136, 135, 217));
		panelTitle.setFont(new Font("Ubuntu", 1, 24)); // NOI18N
		panelTitle.setHorizontalAlignment(SwingConstants.CENTER);				//리팩토링10
		panelTitle.setText("Minesfinder");
		panelTitle.setOpaque(true);
		getContentPane().add(panelTitle,BorderLayout.PAGE_START);

		panelRecords.setBackground(new java.awt.Color(118, 206, 108));

		records.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
		records.setHorizontalAlignment(SwingConstants.CENTER);
		records.setText("Records");

		labelEasy.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
		labelEasy.setHorizontalAlignment(SwingConstants.CENTER);
		labelEasy.setText("Easy");
		labelTime.setText("Time:");
		labelEasyName.setText("Player");
		labelEasyPoints.setText("9999");	
		labelClick.setText("Click:");
		labelClickName.setText("Player");
		labelClickPoints.setText("9999");

		labelMedium.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
		labelMedium.setHorizontalAlignment(SwingConstants.CENTER);
		labelMedium.setText("Medium");
		labelMediumTime.setText("Time:");
		labelMediumName.setText("Player");
		labelMediumPoints.setText("9999");
		labelMediumClick.setText("Click:");
		labelMediumClickName.setText("Player");
		labelMediumClickPoints.setText("9999");
		
		labelHard.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
		labelHard.setHorizontalAlignment(SwingConstants.CENTER);
		labelHard.setText("Hard");
		labelHardTime.setText("Time:");
		labelHardName.setText("Player");
		labelHardPoints.setText("9999");
		labelHardClick.setText("Click:");
		labelHardClickName.setText("Player");
		labelHardClickPoints.setText("9999");
		
		
		//레코드 테이블 부분 컴포넌트들
		GroupLayout panelRecordsLayout = new GroupLayout(panelRecords);
		panelRecords.setLayout(panelRecordsLayout);
		
		//수직그룹
		panelRecordsLayout.setHorizontalGroup(panelRecordsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(records,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(labelEasy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(panelRecordsLayout.createSequentialGroup().addContainerGap()
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelTime)
										.addComponent(labelEasyName)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(labelEasyPoints))								
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelClick)
										.addComponent(labelClickName)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelClickPoints))
								
								.addComponent(labelMedium, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelMediumTime)
										.addComponent(labelMediumName)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelMediumPoints))
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelMediumClick)
										.addComponent(labelMediumClickName)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelMediumClickPoints))
								
								.addComponent(labelHard,GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelHardTime)
										.addComponent(labelHardName)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelHardPoints)))
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelHardClick)
										.addComponent(labelHardClickName)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelHardClickPoints))
						.addContainerGap()));
		
		//수직그룹
		panelRecordsLayout.setVerticalGroup(panelRecordsLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(records).addGap(18, 18, 18)
						.addComponent(labelEasy).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)			//parallel
								.addComponent(labelEasyPoints).addComponent(labelEasyName).addComponent(labelTime))						
						.addGap(1, 18, 18)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labelClickPoints).addComponent(labelClickName).addComponent(labelClick))						
						
						.addGap(18, 18, 18).addComponent(labelMedium)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labelMediumPoints).addComponent(labelMediumName).addComponent(labelMediumTime))
					//	.addGap(1, 18, 18)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labelMediumClickPoints).addComponent(labelMediumClickName).addComponent(labelMediumClick))
						
						.addGap(18, 18, 18).addComponent(labelHard)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labelHardPoints).addComponent(labelHardName).addComponent(labelHardTime))						
						.addGap(1, 18, 18)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labelHardClickPoints).addComponent(labelHardClickName).addComponent(labelHardClick))
						
						.addGap(0, 109, Short.MAX_VALUE)));

		getContentPane().add(panelRecords, java.awt.BorderLayout.LINE_START);

		panelBtns.setLayout(new java.awt.GridLayout(2, 0));

		btnEasy.setIcon(
				new ImageIcon(getClass().getResource("/pt/technic/apps/minesfinder/resources/easy.png"))); // NOI18N
		btnEasy.setText("Easy");
		btnEasy.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEasyActionPerformed(evt);
			}
		});
		panelBtns.add(btnEasy);

		btnMedium.setIcon(
				new ImageIcon(getClass().getResource("/pt/technic/apps/minesfinder/resources/medium.png"))); // NOI18N
		btnMedium.setText("Medium");
		btnMedium.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(ActionEvent evt) {
				btnMediumActionPerformed(evt);
			}		//리팩토링10
		});
		panelBtns.add(btnMedium);

		btnHard.setIcon(
				new ImageIcon(getClass().getResource("/pt/technic/apps/minesfinder/resources/hard.png"))); // NOI18N
		btnHard.setText("Hard");
		btnHard.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(ActionEvent evt) {		//리팩토링10
				btnHardActionPerformed(evt);
			}
		});
		panelBtns.add(btnHard);

		btnMulti = new JButton();
		 // 2인용 모드 버튼 추가

		btnMulti.setText("MultiPlayersMode");
		btnMulti.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(ActionEvent evt) {		//리팩토링10
				btnMultiActionPerformed(evt);
			}
		});
		panelBtns.add(btnMulti);

		btnUser.setText("UserMode");
		btnUser.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(ActionEvent evt) {		//리팩토링10
				btnUserActionPerformed(evt);
			}
		});
		panelBtns.add(btnUser);
		
		btnPractice.setText("PracticeMode");
		btnPractice.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(ActionEvent evt) {		//리팩토링10
				btnPracticeActionPerformed(evt);
			}
		});
		panelBtns.add(btnPractice);

		btnExit.setText("Exit");
		btnExit.addActionListener(new ActionListener() {		//리팩토링10
			public void actionPerformed(ActionEvent evt) {		//리팩토링10
				btnExitActionPerformed(evt);
			}
		});
		panelBtns.add(btnExit);

		getContentPane().add(panelBtns, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnEasyActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnEasyActionPerformed				//리팩토링10
		GameWindow gameWindow = new GameWindow(new Minefield(9, 9, 2), recordEasy, recordClick);
		gameWindow.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnEasyActionPerformed

	
	private void btnExitActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnExitActionPerformed		//리팩토링10
		System.exit(0);
	}// GEN-LAST:event_btnExitActionPerformed

	private void btnMediumActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnMediumActionPerformed		//리팩토링10
		GameWindow gameWindow = new GameWindow(new Minefield(16, 16, 2), recordMedium, recordMediumClick);
		gameWindow.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnMediumActionPerformed

	private void btnHardActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed		//리팩토링10
		GameWindow gameWindow = new GameWindow(new Minefield(16, 30, 2), recordHard, recordHardClick);
		gameWindow.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnHardActionPerformed

	private void btnMultiActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed //멀티		//리팩토링10
																			// 버튼 어디로 가는지
		MultiMode multimode = new MultiMode(new Minefield(9, 9, 10), new Minefield(9, 9, 10));
		multimode.setVisible(true);
		stop_bgm();

	}// GEN-LAST:event_btnHardActionPerformed



	private void btnUserActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed		//리팩토링10
		int result = JOptionPane.showConfirmDialog(null, userfield, "UserMode Setting", JOptionPane.YES_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			UserMode usermode = new UserMode(new Minefield(Integer.parseInt(userwidthtext.getText()), Integer.parseInt(userheighttext.getText()), Integer.parseInt(userminestext.getText())));
			usermode.setVisible(true);
			stop_bgm();
		}

	}// GEN-LAST:event_btnHardActionPerformed
	
	private void btnPracticeActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed		//리팩토링10
		int result = JOptionPane.showConfirmDialog(null, practicefield, "PracticeMode Setting", JOptionPane.YES_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			if (Integer.parseInt(minestext.getText()) > Integer.parseInt(lifetext.getText())) {
				PracticeMode practicemode = new PracticeMode(new Minefield(Integer.parseInt(widthtext.getText()), Integer.parseInt(heighttext.getText()),
																			Integer.parseInt(minestext.getText()), Integer.parseInt(lifetext.getText())));
				practicemode.setVisible(true);
				stop_bgm();
			}
			else{
				throw new IllegalArgumentException("라이프 수가 지뢰 개수보다 작아야합니다.");
			}
		}
		else {
			JOptionPane.showMessageDialog(null,"지뢰개수보다 라이프 수가 작아야합니다.");
		}

	}// GEN-LAST:event_btnHardActionPerformed

//	/**
//	 * @param args the command line arguments
//	 */

	public static void stop_bgm() {
		clip.stop();
	}

	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */

		// hello
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException|UnsupportedLookAndFeelException ex) {		//리팩토링5
			java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		try { // 배경음악
			playSound_bgm();
		} catch (MalformedURLException ex) {
			Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
		} catch (LineUnavailableException|UnsupportedAudioFileException|IOException ex) {		//리팩토링5
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		}

		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MinesFinder().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables


	// End of variables declaration//GEN-END:variables
}
