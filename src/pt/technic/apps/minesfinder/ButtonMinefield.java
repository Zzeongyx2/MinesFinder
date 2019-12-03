package pt.technic.apps.minesfinder;

import java.awt.Color;						//리팩토링6
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 *
 * @author Gabriel Massadas
 */
public class ButtonMinefield extends JButton {
	private int state;			//리팩토링7
	private int col;
	private int line;

	public ButtonMinefield(int col, int line) {
    	addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
				throw new UnsupportedOperationException();	//자동 메소드 호출(나중에 필요시에 그냥 넘어가면 안되서 미리 방지)		//리팩토링1
    		}
    	});
        this.col = col;
        this.line = line;
        state=Minefield.COVERED;
    }

	public void setEstado(int state) { // 칸 색, 기호 설정
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
