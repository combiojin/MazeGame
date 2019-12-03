import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/*
 * 1 = ��
 * 0 = ��
 * e = �̵�ĳ����
 * m = ����
 * x = ���ͺ�
 */
public class GameMaze extends JPanel {
	Toolkit tk = Toolkit.getDefaultToolkit();

	@Override
	protected void paintComponent(Graphics g) { // ��׶��� �̹��� ����.
		super.paintComponent(g);
		Image img = new ImageIcon("src/Image/tlqkf.gif").getImage();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null); // ������ ũ�⿡ �°� �̹��� ����.
	}

	public GameMaze() {
		mkey listener = new mkey(); // mkey listener ����
		addKeyListener(listener); // Ű���� �̺�Ʈ�� listener ����
		setFocusable(true); // ���α׷� Ű�Է��� ĳġ���� ������ ������ ��Ŀ�� ������ ���� ����
		RandomMiro(); // �����ϰ� �̷λ���
		new Thread(new PaintThread()).start(); // ���� ������ ����
	}

	void Wincheck() {
		int count = 0;
		for (int i = 0; i < Default.MAZE_BORD_HEIGHT; i++) { // ���� �� ��Ҵ��� Ȯ��
			for (int j = 0; j < Default.MAZE_BORD_WIDTH; j++) {
				if (Default.maze[i][j] == 'm')
					count++;
			}
		}
		if (count == 0) {
			Default.maze[12][12] = 'x'; // ���ͺ� ���� ��ġ
			if (Default.xpos == 12 && Default.ypos == 12) {
				JOptionPane.showMessageDialog(null, "���� ���������� ���ͺ� ���� ����"); // �̵� ĳ���Ͱ� Ż�⹮�� �����ϸ� �޽���â ���
				System.exit(0); // ��������
			}
		}
	}

	class mkey implements KeyListener { // Ű���� �̺�Ʈ �߰�
		// �̵� ĳ���Ͱ� ���̳� Ż�⹮�� �̵� �����ϰ� �̵��ϸ� ����ĳ������ ��ġ�� ��� �̵�����ġ�� ĳ���ͷ� ����
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_RIGHT:
				if (Default.xpos + 1 < Default.MAZE_BORD_WIDTH) {
					if (Default.maze[Default.ypos][Default.xpos + 1] == '0'
							|| Default.maze[Default.ypos][Default.xpos + 1] == 'm'
							|| Default.maze[Default.ypos][Default.xpos + 1] == 'x') {
						Default.maze[Default.ypos][Default.xpos] = '0';
						Default.maze[Default.ypos][++Default.xpos] = 'e';
					}
				}
				break;
			case KeyEvent.VK_LEFT:
				if (Default.xpos + 1 < Default.MAZE_BORD_WIDTH) {
					if (Default.maze[Default.ypos][Default.xpos - 1] == '0'
							|| Default.maze[Default.ypos][Default.xpos - 1] == 'm'
							|| Default.maze[Default.ypos][Default.xpos - 1] == 'x') {
						Default.maze[Default.ypos][Default.xpos] = '0';
						Default.maze[Default.ypos][--Default.xpos] = 'e';
					}
				}
				break;
			case KeyEvent.VK_UP:
				if (Default.xpos + 1 < Default.MAZE_BORD_WIDTH) {
					if (Default.maze[Default.ypos - 1][Default.xpos] == '0'
							|| Default.maze[Default.ypos - 1][Default.xpos] == 'm'
							|| Default.maze[Default.ypos - 1][Default.xpos] == 'x') {
						Default.maze[Default.ypos][Default.xpos] = '0';
						Default.maze[--Default.ypos][Default.xpos] = 'e';
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if (Default.xpos + 1 < Default.MAZE_BORD_WIDTH) {
					if (Default.maze[Default.ypos + 1][Default.xpos] == '0'
							|| Default.maze[Default.ypos + 1][Default.xpos] == 'm'
							|| Default.maze[Default.ypos + 1][Default.xpos] == 'x') {
						Default.maze[Default.ypos][Default.xpos] = '0';
						Default.maze[++Default.ypos][Default.xpos] = 'e';
					}
				}
				break;
			}
			Wincheck();

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}

	public void RandomMiro() {
		Random random = new Random();
		// ���迭��� ������ ����
		int i, j, x = 1, y = 1, rotate;
		for (i = 0; i < Default.MAZE_BORD_HEIGHT; i++) {
			for (j = 0; j < Default.MAZE_BORD_WIDTH; j++)
				Default.maze[i][j] = '1';
		}
		// �迭 ��� �̵�ĳ���� ����
		Default.maze[12][12] = 'e';
		// ������ ���� �޾Ƽ� ����, �Ʒ��� , ������, ���ʿ� ���̸� 2ĭ�� ��� ����� ���� �ƴϸ� ��ġ�� �̵�
		for (i = 0; i < 10000; i++) {
			rotate = random.nextInt(4) + 1;
			if (rotate == 1) { // ���ʹ��� �� �����
				if (y == 1)
					continue;
				if (Default.maze[x][y - 2] == '1') {
					y--;
					Default.maze[x][y] = '0';
					y--;
					Default.maze[x][y] = '0';
				} else
					y -= 2;
			}
			if (rotate == 2) { // �Ʒ��ʹ��� �� �����
				if (x == Default.MAZE_BORD_HEIGHT - 2)
					continue;
				if (Default.maze[x + 2][y] == '1') {
					x++;
					Default.maze[x][y] = '0';
					x++;
					Default.maze[x][y] = '0';
				} else
					x += 2;
			}
			if (rotate == 3) { // �����ʹ��� �� �����
				if (y == Default.MAZE_BORD_WIDTH - 2)
					continue;
				if (Default.maze[x][y + 2] == '1') {
					y++;
					Default.maze[x][y] = '0';
					y++;
					Default.maze[x][y] = '0';
				} else
					y += 2;
			}
			if (rotate == 4) { // ���ʹ��� �� �����
				if (x == 1)
					continue;
				if (Default.maze[x - 2][y] == '1') {
					x--;
					Default.maze[x][y] = '0';
					x--;
					Default.maze[x][y] = '0';
				} else
					x -= 2;
			}
		}
		// �𼭸� 4���� ��ī�̴� ����
		Default.maze[Default.MAZE_BORD_HEIGHT - 2][Default.MAZE_BORD_WIDTH - 2] = 'm';
		Default.maze[1][Default.MAZE_BORD_WIDTH - 2] = 'm';
		Default.maze[Default.MAZE_BORD_HEIGHT - 2][1] = 'm';
		Default.maze[1][1] = 'm';

	}

//	void Rotate() {
//		/*
//		 * temp������ [0][0] �� ���� ���� [0][0] �� [24][0] �� ���� ���� [24][0] �� [24][24] �� ���� ����
//		 * [24][24] �� [0][24] �� ���� ���� [0][24] �� temp �� ���� ���� �̷��� ������� ������ �κе� ȸ��
//		 */
//		for (int i = 0; i < 25 / 2; i++) {
//			int first = i;
//			int last = 25 - 1 - i;
//			int index = 0;
//			for (int j = first; j < last; j++) {
//				int temp = Default.maze[first][j];
//				Default.maze[first][j] = Default.maze[last - index][first];
//				Default.maze[last - index][first] = Default.maze[last][last - index];
//				Default.maze[last][last - index] = Default.maze[first + index][last];
//				Default.maze[first + index][last] = temp;
//				index++;
//			}
//		}
//	}

	private class PaintThread implements Runnable {
//		0.05�� ���� ȭ���� �ٽ� �׷��ش�.
//		10�ʸ��� �迭�� 90�� ȸ��
//		ȭ���� �ٽ� �׷����� ������ �����̴� �׸��� �����־� ���Ⱑ ���� �ʴ�.
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Default.rotate++;
//				if (Default.rotate % 200 == 0) {
//					Rotate();
//					Default.rotate = 0;
//				}
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		DrawMiro(g);
	}
	
	public void DrawMiro(Graphics g) {
		// ȭ�鿡 �̷ο� ǥ�õ� �̹����� �׷��ش�.
		for (int y = 1 ; y <= Default.MAZE_BORD_HEIGHT; y++) {
			for(int x = 1 ; x <= Default.MAZE_BORD_WIDTH; x++) {
				if (Default.maze[y - 1][x - 1] == '1') {
					g.setColor(new Color(253, 110, 225, 150));
					g.fillRect(x * 30, y * 30, 29, 29);
				} else if (Default.maze[y - 1][x - 1] == '0') {
					g.setColor(new Color(255, 255, 255, 50));
					g.fillRect(x * 30, y * 30, 29, 29);
				} else if (Default.maze[y - 1][x - 1] == 'e') {
					g.drawImage(tk.getImage("src/Image/pika.gif"), x * 30, y * 30, 30, 30, null);
					Default.xpos = x - 1;
					Default.ypos = y - 1;
				} else if (Default.maze[y - 1][x - 1] == 'm') {
					g.drawImage(tk.getImage("src/Image/me.gif"), x * 30, y * 30, 30, 30, null);
				} else if (Default.maze[y - 1][x - 1] == 'x') {
					g.drawImage(tk.getImage("src/Image/blass.gif"), x * 30, y * 30, 30, 30, null);
				}	
	}
}
	}
}
