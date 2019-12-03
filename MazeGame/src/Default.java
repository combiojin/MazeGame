import javax.swing.JTextField;
/*
 * 1 = 벽
 * 0 = 길
 * e = 이동캐릭터
 * m = 뮤츠
 * x = 몬스터볼
 */
public class Default {
	static int MAZE_BORD_HEIGHT= 25; //배열 높이
	static int MAZE_BORD_WIDTH= 25; // 배열 너비
	static int xpos; // 이동좌표
	static int ypos; // 이동좌표
	static int rotate;
	static int[][] maze = new int[25][25]; // 배열초기화
	
}
