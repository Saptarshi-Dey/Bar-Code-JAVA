import java.awt.EventQueue;  
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JComboBox;

public class CodeWriter extends JFrame {

	private static final long serialVersionUID = 4341229095332807488L; //Nothing Important
	private JPanel contentPane;
    private static Color oncolor=new Color(0xFF000000),offcolor=new Color(0xFFFFFFFF);
    @SuppressWarnings("unused")
	private JColorChooser colorChooser = new JColorChooser();
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CodeWriter frame = new CodeWriter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String getEXT(String file) {
		int x=file.indexOf('.');
		return file.substring(x+1, file.length());
	}
	
	final JTextPane TextData = new JTextPane();
	JComboBox<String> comboBox = new JComboBox<String>();
	ImageIcon barImage=new ImageIcon();
	private JTextField textField = new JTextField("500");
	private JTextField textField_1 = new JTextField("300");
	
	private BufferedImage ImageParser() throws Exception {
		String text=TextData.getText();
		if(text.equals("")) throw new Exception("Please enter some Text!!!");
		int width=Integer.parseInt(textField.getText());
        int height=Integer.parseInt(textField_1.getText());
        BufferedImage code=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BitMatrix matrix=new BitMatrix(1,1);
		int ind=comboBox.getSelectedIndex();
		switch(ind) {
		  case 0:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_39, width, height);
			  break;
		  }
		  case 1:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height);
			  break;
		  }
		  case 2:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.UPC_A, width, height);
			  break;
		  }
		  case 3:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.EAN_8, width, height);
			  break;
		  }
		  case 4:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.EAN_13, width, height);
			  break;
		  }
		  case 5:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.PDF_417, width, height);
			  break;
		  }
		  case 6:{
			  matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
			  break;
		  }
		}
		MatrixToImageConfig conf = new MatrixToImageConfig(oncolor.hashCode(),offcolor.hashCode());
		code = MatrixToImageWriter.toBufferedImage(matrix,conf); 
		return code;
	}
	public CodeWriter() {
		setResizable(false);
		setType(Type.UTILITY);
		setTitle("Code Writer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		TextData.setFont(new Font("DejaVu Sans", Font.BOLD, 13));
		
		TextData.setForeground(new Color(0, 0, 128));
		TextData.setToolTipText("Enter your Text here");
		JScrollPane SP= new JScrollPane(TextData,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		SP.setBounds(29, 166, 542, 162);
		contentPane.add(SP);
		
		final JLabel imgLabel = new JLabel("Your Preview will be shown here");
		imgLabel.setBounds(29, 0, 264, 154);
		contentPane.add(imgLabel);
		
		JButton btnBarCode = new JButton("Bar CODE");
		btnBarCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedImage qrcode = ImageParser();
					Image BarImg = qrcode.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
					imgLabel.setIcon(new ImageIcon(BarImg));
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(null,exc.getMessage(),"IO ERROR",0);
				}
			}
		});
		btnBarCode.setBounds(450, 53, 117, 25);
		contentPane.add(btnBarCode);
		
		JButton btnExport = new JButton("EXPORT");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Export to Image");
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setAcceptAllFileFilterUsed(false);
					FileFilter imgFilter = new FileTypeFilter(".png","Portable Network Graphics");
					FileFilter imgFilter2 = new FileTypeFilter(".jpg", "Joint Photographic Experts Group");
					fileChooser.addChoosableFileFilter(imgFilter);
					fileChooser.addChoosableFileFilter(imgFilter2);
					
					int check=fileChooser.showSaveDialog(null);
					if(check==JFileChooser.APPROVE_OPTION) {
						BufferedImage qrcode = ImageParser();
						File outputfile = fileChooser.getSelectedFile();
						String ext=getEXT(outputfile.getName());
						if(!(ext.equals("png") || ext.equals("jpg"))) {
							FileFilter temp= fileChooser.getFileFilter();
							if(temp==imgFilter2) ext="jpg";
							else ext="png";
							File save=new File(outputfile.getAbsolutePath()+"."+ext);
							ImageIO.write(qrcode,ext,save);
						}
						else ImageIO.write(qrcode,ext,outputfile);
					}
				}catch(Exception exc) {
					JOptionPane.showMessageDialog(null,exc.getMessage(),"IO ERROR",0);
				}
			}
		});
		btnExport.setBounds(450, 6, 117, 25);
		contentPane.add(btnExport);
		
		final JButton btnColorPicker = new JButton("Background");
		btnColorPicker.setToolTipText("Select Background Color");
		btnColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Color x = JColorChooser.showDialog(btnColorPicker, "Pick a Background Color", Color.black);
					if(x!=null) {
						offcolor=x;
						BufferedImage qrcode = ImageParser();
						Image BarImg = qrcode.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
						imgLabel.setIcon(new ImageIcon(BarImg));
					}
				}catch(Exception exc){
					//System.out.println(exc.getMessage());
				}
			}
		});
		btnColorPicker.setBounds(281, 340, 139, 25);
		contentPane.add(btnColorPicker);
		
		final JButton btnForegroundColor = new JButton("Foreground");
		btnForegroundColor.setToolTipText("Select Foreground Color");
		btnForegroundColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Color x = JColorChooser.showDialog(btnColorPicker, "Pick a Background Color", Color.black);
					if(x!=null) {
						oncolor=x;
						BufferedImage qrcode = ImageParser();
						Image BarImg = qrcode.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
						imgLabel.setIcon(new ImageIcon(BarImg));
					}
				}catch(Exception exc){
					System.out.println(exc.getMessage());
				}
			}
		});
		btnForegroundColor.setBounds(431, 340, 140, 25);
		contentPane.add(btnForegroundColor);
		textField.setToolTipText("Select width of Image");
		
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c=evt.getKeyChar();
				if(!((c>='0' && c<='9') || c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE)) evt.consume(); 
			}
		});
		textField.setBounds(29, 340, 80, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		textField_1.setToolTipText("Select height of Image ");
		
		textField_1.setBounds(121, 340, 73, 25);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedImage qrcode = ImageParser();
					Image BarImg = qrcode.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(),Image.SCALE_SMOOTH);
					imgLabel.setIcon(new ImageIcon(BarImg));
				}catch(Exception exc) {
					//System.out.println(exc.getMessage());
				}
			}
		});
		
		comboBox.addItem("Code39");
		comboBox.addItem("Code128");
		comboBox.addItem("UPC-A");
		comboBox.addItem("EAN8");
		comboBox.addItem("EAN13");
		comboBox.addItem("PDF417");
		comboBox.addItem("QR Code");
		comboBox.setToolTipText("Select the type of Code you want to generate");
		comboBox.setBounds(335, 6, 103, 25);
		comboBox.setSelectedIndex(1);
		contentPane.add(comboBox);
		
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c=evt.getKeyChar();
				if(!((c>='0' && c<='9') || c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE)) evt.consume();				
			}
		});
		
	}
}
