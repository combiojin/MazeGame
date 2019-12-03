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
 * 1 = 벽
 * 0 = 길
 * e = 이동캐릭터
 * m = 뮤츠
 * x = 몬스터볼
 */
public class GameMaze extends JPanel {
	Toolkit tk = Toolkit.getDefaultToolkit();

	@Override
	protected void paintComponent(Graphics g) { // 백그라운드 이미지 삽입.
		super.paintComponent(g);
		Image img = new ImageIcon("src/Image/tlqkf.gif").getImage();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null); // 프레임 크기에 맞게 이미지 적용.
	}

	public GameMaze() {
		mkey listener = new mkey(); // mkey listener 생성
		addKeyListener(listener); // 키보드 이벤트에 listener 적용
		setFocusable(true); // 프로그램 키입력이 캐치되지 않을수 있으니 포커스 유지를 위해 적용
		RandomMiro(); // 랜덤하게 미로생성
		new Thread(new PaintThread()).start(); // 게임 쓰레드 시작
	}

	void Wincheck() {
		int count = 0;
		for (int i = 0; i < Default.MAZE_BORD_HEIGHT; i++) { // 뮤츠 다 잡았는지 확인
			for (int j = 0; j < Default.MAZE_BORD_WIDTH; j++) {
				if (Default.maze[i][j] == 'm')
					count++;
			}
		}
		if (count == 0) {
			Default.maze[12][12] = 'x'; // 몬스터볼 생성 위치
			if (Default.xpos == 12 && Default.ypos == 12) {
				JOptionPane.showMessageDialog(null, "뮤츠 때려눕히고 몬스터볼 들어가기 성공"); // 이동 캐릭터가 탈출문에 도착하면 메시지창 출력
				System.exit(0); // 게임종료
			}
		}
	}

	class mkey implements KeyListener { // 키보드 이벤트 추가
		// 이동 캐릭터가 길이나 탈출문은 이동 가능하고 이동하면 이전캐릭터의 위치는 길로 이동한위치는 캐릭터로 변경
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
		// 모든배열요소 벽으로 설정
		int i, j, x = 1, y = 1, rotate;
		for (i = 0; i < Default.MAZE_BORD_HEIGHT; i++) {
			for (j = 0; j < Default.MAZE_BORD_WIDTH; j++)
				Default.maze[i][j] = '1';
		}
		// 배열 가운데 이동캐릭터 지정
		Default.maze[12][12] = 'e';
		// 랜덤한 값을 받아서 왼쪽, 아래쪽 , 오른쪽, 위쪽에 벽이면 2칸씩 길로 만들고 벽이 아니면 위치만 이동
		for (i = 0; i < 10000; i++) {
			rotate = random.nextInt(4) + 1;
			if (rotate == 1) { // 왼쪽방향 길 만들기
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
			if (rotate == 2) { // 아래쪽방향 길 만들기
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
			if (rotate == 3) { // 오른쪽방향 길 만들기
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
			if (rotate == 4) { // 위쪽방향 길 만들기
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
		// 모서리 4곳에 아카이누 생성
		Default.maze[Default.MAZE_BORD_HEIGHT - 2][Default.MAZE_BORD_WIDTH - 2] = 'm';
		Default.maze[1][Default.MAZE_BORD_WIDTH - 2] = 'm';
		Default.maze[Default.MAZE_BORD_HEIGHT - 2][1] = 'm';
		Default.maze[1][1] = 'm';

	}

//	void Rotate() {
//		/*
//		 * temp변수에 [0][0] 의 값을 저장 [0][0] 에 [24][0] 의 값을 저장 [24][0] 에 [24][24] 의 값을 저장
//		 * [24][24] 에 [0][24] 의 값을 저장 [0][24] 에 temp 의 값을 저장 이러한 방법으로 나머지 부분도 회전
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
//		0.05초 마다 화면을 다시 그려준다.
//		10초마다 배열을 90도 회전
//		화면을 다시 그려주지 않으면 움직이는 그림이 멈춰있어 보기가 좋지 않다.
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
		// 화면에 미로에 표시될 이미지를 그려준다.
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
