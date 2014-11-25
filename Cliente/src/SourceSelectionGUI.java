import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SourceSelectionGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private int opt;
	private String ip;
	private String port;
	private String source;
	private BoolObj isStop;
	private User user;

	public SourceSelectionGUI(final int opt, final String ip, final String port, final User user) {
		isStop = new BoolObj();
		isStop.value = false;
		final SourceSelectionGUI me = this;
		this.opt = opt;
		this.ip = ip;
		this.port = port;
		this.user = user;

		switch (opt) {
		case 0:
			source = "audio.mp3";
			break;
		case 1:
			source = "video.avi";
			break;

		default:
			System.out.println("Error en selección");
			break;
		}

		setTitle("JStreamer");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 468, 245);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JButton btnNewButton_1 = new JButton("Stop");
		btnNewButton_1.setBounds(46, 120, 117, 25);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Stop!!");
				user.AddCommand("stop");
				isStop.value = true;

			}
		});

		JLabel lblNewLabel = new JLabel("Select path of source: ");
		lblNewLabel.setBounds(42, 39, 160, 15);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(46, 66, 218, 19);
		contentPane.add(textField);
		textField.setText(source);
		textField.setColumns(10);

		final JButton btnNewButton = new JButton("Start");
		btnNewButton.setBounds(294, 120, 117, 25);
		contentPane.add(btnNewButton);

		JButton btnOpen = new JButton("Open...");

		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = null;
				if (opt == 0) {
					filter = new FileNameExtensionFilter("Mp3 & WMA files ",
							"mp3", "wma");
				} else {
					filter = new FileNameExtensionFilter("Avi & Mp4 files ",
							"avi", "mp4", "mov");
				}

				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: "
							+ chooser.getSelectedFile().getPath());
					textField.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		btnOpen.setBounds(294, 63, 117, 25);
		contentPane.add(btnOpen);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Start");
				btnNewButton_1.setEnabled(true);
				btnNewButton.setEnabled(false);
				source = textField.getText();

				new Thread() {
					public void run() {
						StreamBackend.initStreaming(opt, ip, port, source,
								isStop);
						me.setVisible(false);
					}
				}.start();

			}
		});

	}
}
