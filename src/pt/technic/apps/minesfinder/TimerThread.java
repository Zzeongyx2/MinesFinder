package pt.technic.apps.minesfinder;

import javax.swing.JLabel;

public class TimerThread extends Thread{
	JLabel timerLabel;
	
	public TimerThread(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	@Override			//�������̵� ǥ��(�����丵)
	public void run() {
		int n = 0;
		
		while(true) {
			timerLabel.setText(Integer.toString(n));
			n++;
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();			//�����丵�ε� �� �����丵���� �𸣰ڤФ�
				 return;
			}
		}
	}
}
