import java.awt.EventQueue; 

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class CodeReader extends JFrame {

	private static final long serialVersionUID = 2223518679386900469L;  //Nothing Important
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CodeReader frame = new CodeReader();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CodeReader() {
		setResizable(false);
		setType(Type.UTILITY);
		setTitle("Code Reader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTextArea ShowText = new JTextArea();
		ShowText.setText("Your Text will be displayed here");
		JScrollPane SP= new JScrollPane(ShowText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		SP.setBounds(12, 0, 444, 183);
		contentPane.add(SP);
		
		JButton btnSelectImage = new JButton("Select Image");
		btnSelectImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Export to Image");
					fileChooser.setMultiSelectionEnabled(false);
					
					FileFilter imgFilter = new FileTypeFilter(".png","Portable Network Graphics");
					FileFilter imgFilter2 = new FileTypeFilter(".jpg", "Joint Photographic Experts Group");
					fileChooser.addChoosableFileFilter(imgFilter);
					fileChooser.addChoosableFileFilter(imgFilter2);
		
					int check=fileChooser.showOpenDialog(null);
					if(check==JFileChooser.APPROVE_OPTION) {
						File outputfile = fileChooser.getSelectedFile();
						BufferedImage x= ImageIO.read(new FileInputStream(outputfile.getAbsolutePath()));
			    		BinaryBitmap bmp=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(x)));
			    		Result rs=new MultiFormatReader().decode(bmp);
			    		ShowText.setText(rs.getText());
					}
				}catch(Exception exc) {
					System.out.println(exc.toString());
					JOptionPane.showMessageDialog(null,"Sorry, no text was found in the given barcode","IO ERROR",0);
				}
			}
		});
		btnSelectImage.setBounds(157, 197, 138, 31);
		contentPane.add(btnSelectImage);
		
	}
}
