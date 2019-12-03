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
		setTitle("���� ���������� ���ͺ��� ����"); // â ���� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �ݱ� ��ư �������� ����
		setBounds(100, 100, 830, 855); // ��ġ�� ũ�⸦ ����
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0)); // �� �� �� ��
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null); // ������ ���߾ӿ� ��ġ
		setContentPane(contentPane);
		
		GameMaze panel = new GameMaze(); //  JPanel  >> GameMaze Ŭ���� �� ����
		contentPane.add(panel, BorderLayout.CENTER);
		
	}

}
