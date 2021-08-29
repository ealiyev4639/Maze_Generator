import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeGenerator {
	static int SIZE; // maze grid size
	static DFSSolve solver;

	public static void main(String[] args) {
		/* ask the user for maze size */
		JFrame ask = new JFrame();
		ask.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ask.pack();
		ask.setSize(200, 100);
		ask.setLayout(null);

		JTextField text = new JTextField();
		text.setBounds(10, 10, 50, 40);
		text.setText("50");

		JButton enter = new JButton("generate");
		enter.setBounds(70, 10, 100, 40);
		ask.add(text);
		ask.add(enter);

		ask.setVisible(true);

		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SIZE = Integer.parseInt(text.getText());

				ask.dispose();
				generate();
			}
		});

	}

	public static void generate() { // generate the maze and GUI
		JFrame frame = new JFrame("Maze");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.DARK_GRAY);

		frame.pack();
		frame.setSize(600, 600); // change size for larger mazes.
		frame.setResizable(false);

		/* MAZE: comment/uncomment the other line */
		// ======================================
//		Maze maze = new Maze(SIZE);
		AldousBroderGen maze = new AldousBroderGen(SIZE);
		// ======================================
		frame.add(maze);
		frame.setVisible(true);

		/* panel */
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setVisible(true);
		panel.setBackground(Color.DARK_GRAY);

		JButton b = new JButton("Solve");
		JButton reset = new JButton("Reset");

		JLabel sclabel = new JLabel("Score: " + maze.score);
		sclabel.setForeground(Color.RED);

		panel.add(b);
		panel.add(reset);
		panel.add(sclabel);

		frame.add(panel, BorderLayout.EAST);
		frame.setVisible(true);

		/* solve the maze */
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				solver = new DFSSolve(maze, maze.curr);

				frame.add(solver);
				frame.setVisible(true);

				return;
			}
		});

		/* reset the maze */
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				maze.requestFocus();
				maze.curr = maze.grid[0][0];
				maze.score = 0;

				if (solver != null) {
					solver.reset();
					solver = null;
				}
				maze.repaint();

				return;
			}
		});

		/* update score */
		new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sclabel.setText("Score: " + maze.score);
			}
		}).start();

	}
}