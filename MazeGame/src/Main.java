import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class Main extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		setTitle("뮤츠 때려눕히고 몬스터볼에 들어가기"); // 창 제목 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 눌렸을때 종료
		setBounds(100, 100, 830, 855); // 위치와 크기를 설정
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0)); // 상 좌 하 우
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null); // 프레임 정중앙에 위치
		setContentPane(contentPane);
		
		GameMaze panel = new GameMaze(); //  JPanel  >> GameMaze 클래스 로 변경
		contentPane.add(panel, BorderLayout.CENTER);
		
	}

}
