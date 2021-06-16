import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {

	private JFrame frmBarcodeWriter;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
					MainFrame window = new MainFrame();
					window.frmBarcodeWriter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frmBarcodeWriter = new JFrame();
		frmBarcodeWriter.setResizable(false);
		frmBarcodeWriter.setTitle("BarCode Writer & Reader");
		frmBarcodeWriter.setBounds(100, 100, 341, 148);
		frmBarcodeWriter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBarcodeWriter.getContentPane().setLayout(null);
		
		JButton btnCodeWriter = new JButton("Code Writer");
		btnCodeWriter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CodeWriter x=new CodeWriter();
				//x.TextData.setForeground(Color.WHITE);
				//x.TextData.setBackground(Color.DARK_GRAY);
				x.setVisible(true);
				x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnCodeWriter.setBounds(103, 6, 117, 38);
		frmBarcodeWriter.getContentPane().add(btnCodeWriter);
		
		JButton btnCodeReader = new JButton("Code Reader");
		btnCodeReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CodeReader x=new CodeReader();
				x.setVisible(true);
                x.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		btnCodeReader.setBounds(103, 51, 117, 38);
		frmBarcodeWriter.getContentPane().add(btnCodeReader);
	}
}
