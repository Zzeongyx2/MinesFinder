
package pt.technic.apps.minesfinder;

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
import javax.swing.JOptionPane;

/**
 *
 * @author Gabriel Massadas
 */
public class MinesFinder extends javax.swing.JFrame {

	private RecordTable recordEasy;
	private RecordTable recordMedium;
	private RecordTable recordHard;
	private RecordTable recordClick;
	private RecordTable recordMediumClick;
	private RecordTable recordHardClick;
	private static Clip clip;

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

	public static void playSound_bgm(String string)
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
		panelTitle = new javax.swing.JLabel();
		panelRecords = new javax.swing.JPanel();
		Records = new javax.swing.JLabel();
		labelEasy = new javax.swing.JLabel();
		labelEasyName = new javax.swing.JLabel();
		labelEasyPoints = new javax.swing.JLabel();
		labelMedium = new javax.swing.JLabel();
		labelMediumName = new javax.swing.JLabel();
		labelMediumPoints = new javax.swing.JLabel();
		labelHard = new javax.swing.JLabel();
		labelHardName = new javax.swing.JLabel();
		labelHardPoints = new javax.swing.JLabel();
		
		labelClick = new javax.swing.JLabel();
		labelMediumClick = new javax.swing.JLabel();
		labelHardClick = new javax.swing.JLabel();
		labelClickName = new javax.swing.JLabel();
		labelClickPoints = new javax.swing.JLabel();
		labelTime = new javax.swing.JLabel();
		labelMediumTime = new javax.swing.JLabel();
		labelHardTime = new javax.swing.JLabel();
		labelMediumClickName = new javax.swing.JLabel();
		labelMediumClickPoints = new javax.swing.JLabel();
		labelHardClickName = new javax.swing.JLabel();
		labelHardClickPoints = new javax.swing.JLabel();
		
		panelBtns = new javax.swing.JPanel();
		btnEasy = new javax.swing.JButton();
		btnMedium = new javax.swing.JButton();
		btnHard = new javax.swing.JButton();
		btnExit = new javax.swing.JButton();
		btnUser = new javax.swing.JButton();
		btnMulti = new javax.swing.JButton();
		btnPractice = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("MinesFinder");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setPreferredSize(new java.awt.Dimension(600, 450));
		setResizable(false);

		panelTitle.setBackground(new java.awt.Color(136, 135, 217));
		panelTitle.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
		panelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		panelTitle.setText("Minesfinder");
		panelTitle.setOpaque(true);
		getContentPane().add(panelTitle, java.awt.BorderLayout.PAGE_START);

		panelRecords.setBackground(new java.awt.Color(118, 206, 108));

		Records.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
		Records.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		Records.setText("Records");

		labelEasy.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
		labelEasy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelEasy.setText("Easy");
		labelTime.setText("Time:");
		labelEasyName.setText("Player");
		labelEasyPoints.setText("9999");	
		labelClick.setText("Click:");
		labelClickName.setText("Player");
		labelClickPoints.setText("9999");

		labelMedium.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
		labelMedium.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelMedium.setText("Medium");
		labelMediumTime.setText("Time:");
		labelMediumName.setText("Player");
		labelMediumPoints.setText("9999");
		labelMediumClick.setText("Click:");
		labelMediumClickName.setText("Player");
		labelMediumClickPoints.setText("9999");
		
		labelHard.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
		labelHard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelHard.setText("Hard");
		labelHardTime.setText("Time:");
		labelHardName.setText("Player");
		labelHardPoints.setText("9999");
		labelHardClick.setText("Click:");
		labelHardClickName.setText("Player");
		labelHardClickPoints.setText("9999");
		
//		
//		//클릭수,시간랭킹 라벨
//		labelClick.setText("Click:");
//		labelMediumClick.setText("Click:");
//		labelHardClick.setText("Click:");
//		labelTime.setText("Time:");
//		labelMediumTime.setText("Time:");
//		labelHardTime.setText("Time:");
		
		
		
		//레코드 테이블 부분 컴포넌트들
		javax.swing.GroupLayout panelRecordsLayout = new javax.swing.GroupLayout(panelRecords);
		panelRecords.setLayout(panelRecordsLayout);
		
		//수직그룹
		panelRecordsLayout.setHorizontalGroup(panelRecordsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(Records, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(labelEasy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(panelRecordsLayout.createSequentialGroup().addContainerGap()
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelTime)
										.addComponent(labelEasyName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(labelEasyPoints))								
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelClick)
										.addComponent(labelClickName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelClickPoints))
								
								.addComponent(labelMedium, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelMediumTime)
										.addComponent(labelMediumName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelMediumPoints))
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelMediumClick)
										.addComponent(labelMediumClickName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelMediumClickPoints))
								
								.addComponent(labelHard, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelHardTime)
										.addComponent(labelHardName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelHardPoints)))
								.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(labelHardClick)
										.addComponent(labelHardClickName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelHardClickPoints))
						.addContainerGap()));
		
		//수직그룹
		panelRecordsLayout.setVerticalGroup(panelRecordsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelRecordsLayout.createSequentialGroup().addComponent(Records).addGap(18, 18, 18)
						.addComponent(labelEasy).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)						
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)			//parallel
								.addComponent(labelEasyPoints).addComponent(labelEasyName).addComponent(labelTime))						
						.addGap(1, 18, 18)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelClickPoints).addComponent(labelClickName).addComponent(labelClick))						
						
						.addGap(18, 18, 18).addComponent(labelMedium)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelMediumPoints).addComponent(labelMediumName).addComponent(labelMediumTime))
					//	.addGap(1, 18, 18)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelMediumClickPoints).addComponent(labelMediumClickName).addComponent(labelMediumClick))
						
						.addGap(18, 18, 18).addComponent(labelHard)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelHardPoints).addComponent(labelHardName).addComponent(labelHardTime))						
						.addGap(1, 18, 18)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panelRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelHardClickPoints).addComponent(labelHardClickName).addComponent(labelHardClick))
						
						.addGap(0, 109, Short.MAX_VALUE)));

		getContentPane().add(panelRecords, java.awt.BorderLayout.LINE_START);

		panelBtns.setLayout(new java.awt.GridLayout(2, 0));

		btnEasy.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/pt/technic/apps/minesfinder/resources/easy.png"))); // NOI18N
		btnEasy.setText("Easy");
		btnEasy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEasyActionPerformed(evt);
			}
		});
		panelBtns.add(btnEasy);

		btnMedium.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/pt/technic/apps/minesfinder/resources/medium.png"))); // NOI18N
		btnMedium.setText("Medium");
		btnMedium.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMediumActionPerformed(evt);
			}
		});
		panelBtns.add(btnMedium);

		btnHard.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/pt/technic/apps/minesfinder/resources/hard.png"))); // NOI18N
		btnHard.setText("Hard");
		btnHard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHardActionPerformed(evt);
			}
		});
		panelBtns.add(btnHard);

		btnMulti = new javax.swing.JButton();
		; // 2인용 모드 버튼 추가

		btnMulti.setText("MultiPlayersMode");
		btnMulti.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMultiActionPerformed(evt);
			}
		});
		panelBtns.add(btnMulti);

		btnUser.setText("UserMode");
		btnUser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnUserActionPerformed(evt);
			}
		});
		panelBtns.add(btnUser);
		
		btnPractice.setText("PracticeMode");
		btnPractice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPracticeActionPerformed(evt);
			}
		});
		panelBtns.add(btnPractice);

		btnExit.setText("Exit");
		btnExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnExitActionPerformed(evt);
			}
		});
		panelBtns.add(btnExit);

		getContentPane().add(panelBtns, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnEasyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEasyActionPerformed
		GameWindow gameWindow = new GameWindow(new Minefield(9, 9, 2), recordEasy, recordClick);
		gameWindow.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnEasyActionPerformed

	
	private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExitActionPerformed
		System.exit(0);
	}// GEN-LAST:event_btnExitActionPerformed

	private void btnMediumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMediumActionPerformed
		GameWindow gameWindow = new GameWindow(new Minefield(16, 16, 2), recordMedium, recordMediumClick);
		gameWindow.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnMediumActionPerformed

	private void btnHardActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed
		GameWindow gameWindow = new GameWindow(new Minefield(16, 30, 2), recordHard, recordHardClick);
		gameWindow.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnHardActionPerformed

	private void btnMultiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed //멀티
																			// 버튼 어디로 가는지
		MultiMode multimode = new MultiMode(new Minefield(9, 9, 10), new Minefield(9, 9, 10));
		multimode.setVisible(true);
		stop_bgm();

	}// GEN-LAST:event_btnHardActionPerformed

	private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed
		String x = JOptionPane.showInputDialog(null, "행");
		String y = JOptionPane.showInputDialog(null, "열");
		String n = JOptionPane.showInputDialog(null, "지뢰개수");
		UserMode usermode = new UserMode(new Minefield(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(n)));
		usermode.setVisible(true);
		stop_bgm();
	}// GEN-LAST:event_btnHardActionPerformed
	
	private void btnPracticeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHardActionPerformed
		String x = JOptionPane.showInputDialog(null, "행");
		String y = JOptionPane.showInputDialog(null, "열");
		String n = JOptionPane.showInputDialog(null, "지뢰개수");
		String l = JOptionPane.showInputDialog(null, "라이프 수");
		if(Integer.parseInt(l)<Integer.parseInt(n)) {
		PracticeMode practicemode = new PracticeMode(new Minefield(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(n),Integer.parseInt(l)));
		practicemode.setVisible(true);
		stop_bgm();
		}
		else {
			JOptionPane.showMessageDialog(null,"지뢰개수보다 라이프 수가 작아야합니다.");
		}
	}// GEN-LAST:event_btnHardActionPerformed

	/**
	 * @param args the command line arguments
	 */

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
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MinesFinder.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		try { // 배경음악
			playSound_bgm(System.getProperty("user.dir") + "/src/pt/technic/apps/minesfinder/resources/minesound/"
					+ "mine_bgm.wav");
		} catch (MalformedURLException ex) {
			Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
		} catch (LineUnavailableException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedAudioFileException ex) {
			Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
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
	private javax.swing.JLabel Records;
	private javax.swing.JButton btnEasy;
	private javax.swing.JButton btnExit;
	private javax.swing.JButton btnHard;
	private javax.swing.JButton btnMedium;
	private javax.swing.JButton btnMulti;
	private javax.swing.JButton btnUser;
	private javax.swing.JButton btnPractice;
	private javax.swing.JLabel labelEasy;
	private javax.swing.JLabel labelEasyName;
	private javax.swing.JLabel labelEasyPoints;
	private javax.swing.JLabel labelHard;
	private javax.swing.JLabel labelHardName;
	private javax.swing.JLabel labelHardPoints;
	private javax.swing.JLabel labelMedium;
	private javax.swing.JLabel labelMediumName;
	private javax.swing.JLabel labelMediumPoints;
	
	private javax.swing.JLabel labelClick;
	private javax.swing.JLabel labelMediumClick;
	private javax.swing.JLabel labelHardClick;
	private javax.swing.JLabel labelClickName;
	private javax.swing.JLabel labelClickPoints;
	private javax.swing.JLabel labelTime;
	private javax.swing.JLabel labelMediumTime;
	private javax.swing.JLabel labelHardTime;
	private javax.swing.JLabel labelMediumClickName;
	private javax.swing.JLabel labelMediumClickPoints;
	private javax.swing.JLabel labelHardClickName;
	private javax.swing.JLabel labelHardClickPoints;
	
	private javax.swing.JPanel panelBtns;
	private javax.swing.JPanel panelRecords;
	private javax.swing.JLabel panelTitle;
	// End of variables declaration//GEN-END:variables
}
