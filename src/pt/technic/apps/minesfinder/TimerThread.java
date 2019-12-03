package pt.technic.apps.minesfinder;

import javax.swing.JLabel;

public class TimerThread extends Thread{
	JLabel timerLabel;
	
	public TimerThread(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	@Override			//오버라이드 표시(리팩토링)
	public void run() {
		int n = 0;
		
		while(true) {
			timerLabel.setText(Integer.toString(n));
			n++;
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();			//리팩토링인데 뭔 리팩토링인지 모르겠ㅠㅠ
				 return;
			}
		}
	}
}
