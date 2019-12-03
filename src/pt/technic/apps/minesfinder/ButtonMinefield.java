package pt.technic.apps.minesfinder;

import java.awt.Color;						//�����丵6
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 *
 * @author Gabriel Massadas
 */
public class ButtonMinefield extends JButton {
	private int state;			//�����丵7
	private int col;
	private int line;

	public ButtonMinefield(int col, int line) {
    	addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
				throw new UnsupportedOperationException();	//�ڵ� �޼ҵ� ȣ��(���߿� �ʿ�ÿ� �׳� �Ѿ�� �ȵǼ� �̸� ����)		//�����丵1
    		}
    	});
        this.col = col;
        this.line = line;
        state=Minefield.COVERED;
    }

	public void setEstado(int state) { // ĭ ��, ��ȣ ����
		this.state = state;
		switch (state) {
		case Minefield.EMPTY:
			setText("");
			setBackground(Color.gray);
			break;
		case Minefield.COVERED:
			setText("");
			setBackground(null);
			break;
		case Minefield.QUESTION:
			setText("?");
			setBackground(Color.yellow);
			break;
		case Minefield.MARKED:
			setText("!");
			setBackground(Color.red);
			break;
		case Minefield.BUSTED:
			setText("*");
			setBackground(Color.orange);
			break;
		default:
			setText(String.valueOf(state));
			setBackground(Color.gray);
			break;
		}
	}

	public int getState() {
		return state;
	}

	public int getCol() {
		return col;
	}

	public int getLine() {
		return line;
	}


}
