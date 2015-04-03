package theGUI;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
//import javax.print.DocFlavor.URL;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

import javax.swing.JList;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;

import player.MP3Player;
import player.myClass;

import java.awt.Container;
import java.awt.Image;
import java.awt.List;
import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Label;

public class MyGui {

	private JFrame frmMark;

	/**
	 * Launch the application.
	 */
	
	ArrayList<File> Myfiles = new ArrayList<File>() ;   //File[] Myfiles;
	int i = 0;
	int CurrentOnPlay = 0 ;
	int number=0;
	static JLabel TimeLabel;
	DefaultListModel info = new DefaultListModel();
	static JProgressBar progressBar = new JProgressBar();
	JLabel label;
	String MyStatus="Dummy";
	static JLabel lblArtistBio ;
	
	public static void main(String[] args) {
		 try {
		        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		    } catch(Exception e) {
		        System.out.println("Error setting native LAF: " + e);
		    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGui window = new MyGui();
					window.frmMark.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MyGui() {
		//initialize();
	//}

	/**
	 * Initialize the contents of the frame.
	 */
	//private void initialize() {
		frmMark = new JFrame();
		frmMark.getContentPane().setBackground(new Color(204, 204, 153));
		frmMark.getContentPane().setLayout(null);
		
		
		final JLabel lblSong = new JLabel("Song");
		lblSong.setBounds(94, 9, 146, 14);
		frmMark.getContentPane().add(lblSong);
		
		final JLabel lblArtist = new JLabel("Artist");
		lblArtist.setBounds(93, 23, 185, 14);
		frmMark.getContentPane().add(lblArtist);
		
		final JLabel lblAlbum = new JLabel("Album");
		lblAlbum.setBounds(93, 38, 199, 14);
		frmMark.getContentPane().add(lblAlbum);
		
		
		URL url = null;
		try {
			url = new URL("https://www.jlab.org/images/icon-mp3.gif");
		} catch (MalformedURLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		BufferedImage image;
		try {
			image = ImageIO.read(url);
			  System.out.println("Load image into frame...");
		        label = new JLabel(new ImageIcon(image));
		        label.setBounds(10, 9, 74, 82);
				frmMark.getContentPane().add(label);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
      
		
		progressBar.setBounds(93, 82, 420, 9);
		frmMark.getContentPane().add(progressBar);
		
		final JList list = new JList(info);
		list.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list.setBackground(SystemColor.inactiveCaption);
		list.setBounds(1, 102, 92, 250);
		frmMark.getContentPane().add(list);
		
		//final Task prog = new Task();
		
		TimeLabel = new JLabel("00:00");
		TimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		TimeLabel.setBounds(282, 59, 231, 14);
		frmMark.getContentPane().add(TimeLabel);
		
		final JButton btnPauseresume = new JButton("Pause");
		
		JButton Play = new JButton("Play");
		Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPauseresume.setText("Pause");
				if ((number>0)&&(true) ){//(i < number)&& ( MyStatus!="Playing")
					if(MP3Player.getStatus()=="Done"){
						System.out.println(MP3Player.getStatus());
						MyStatus="Dummy";
						
					}
					if(MyStatus=="Playing"){
						MyStatus="Paused";
						System.out.println(MP3Player.getStatus());
						myClass.MyPause();
						
						System.out.println(MP3Player.getStatus());
						for(long klo=0; klo<999999999; klo++);
						
					}
					if (i >= number){
						i=0;
					}
					
					
						if (Myfiles.get(i).exists()) {
							if (MP3Player.getStatus() != "Playing") {
								MyStatus = "Playing";
								int selected = list.getSelectedIndex();
								if(selected==-1){
									if (i > number){
										i=0;
									}
									selected=i;
								}
								 
								System.out.println(selected);
								myClass.Play(Myfiles.get(selected));
								CurrentOnPlay=selected;
								for(long klo=0; klo<9999999; klo++);
								Task prog = new Task();
								prog.execute();
								prog = null;
								lblSong.setText(MP3Player.getTitle(Myfiles.get(selected).getAbsolutePath()));
								lblArtist.setText(MP3Player.getArtist(Myfiles.get(selected).getAbsolutePath()));
								lblAlbum.setText(MP3Player.getAlbum(Myfiles.get(selected).getAbsolutePath()));
								
								
								Container parent = label.getParent();
								parent.remove(label);
								parent.validate();
								parent.repaint();
								   
								   String path = myClass.getAlbumPicture(Myfiles.get(selected));
				                    System.out.println("Get Image from " + path);
				                    URL url;
									try {
										url = new URL(path);
										BufferedImage image = ImageIO.read(url);
					                    System.out.println("Load image into frame...");
					                    label = new JLabel(new ImageIcon(image));
					                    label.setBounds(10, 9, 74, 82);
					            		frmMark.getContentPane().add(label);
					            		
									} catch (MalformedURLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

								lblArtistBio.setText("<html>"+myClass.getComposerBio(Myfiles.get(selected)));
								
								
								i++;
							}
						}
					
				}
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(new Color(255, 255, 204));
		panel.setBounds(100, 142, 419, 210);
		frmMark.getContentPane().add(panel);
		panel.setLayout(null);
		
		 lblArtistBio = new JLabel("");
		 lblArtistBio.setBounds(4, 25, 410, 185);
		 panel.add(lblArtistBio);
		 lblArtistBio.setHorizontalAlignment(SwingConstants.LEFT);
		 lblArtistBio.setBackground(Color.WHITE);
		
		final Label label = new Label("COMPOSER BIO");
		label.setBounds(164, 7, 125, 22);
		panel.add(label);
		Play.setBounds(93, 102, 73, 23);
		frmMark.getContentPane().add(Play);
		
		JButton btnSkip = new JButton("Skip");
		btnSkip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MyStatus != "Paused") {
					myClass.MySkip();

				}
			}
		});
		btnSkip.setBounds(277, 102, 71, 23);
		frmMark.getContentPane().add(btnSkip);
		
		
		btnPauseresume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MyStatus == "Paused") {
					System.out.println("Resuming");
					myClass.MyResume();
					MyStatus = "Playing";
					btnPauseresume.setText("Pause");

				} else {
					myClass.MyPause();
					MyStatus = "Paused";
					btnPauseresume.setText("Resume");
				}
			}
		});
		btnPauseresume.setBounds(176, 102, 91, 23);
		frmMark.getContentPane().add(btnPauseresume);
		
		
		
		
		
		
		frmMark.setTitle("Mark Mp3 Player");
		frmMark.setResizable(false);
		frmMark.setBounds(100, 100, 529, 412);
		frmMark.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("Button.background"));
		frmMark.setJMenuBar(menuBar);
		
		JMenu FileMenu = new JMenu("File");
		menuBar.add(FileMenu);
		
		JMenuItem Open = new JMenuItem("Open");
		Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new mp3Filter());
				fc.setMultiSelectionEnabled(true);

				int returnVal = fc.showOpenDialog(frmMark);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					int p=number;
					//number += Myfiles.size();
					File help[] = fc.getSelectedFiles();
					int help_counter= help.length;
					number = number+help_counter;
					for (int t=0; t<help_counter; t++){
						String extension = Utils.getExtension(help[t]);
				        if (extension != null) {
				            if (extension.equals(Utils.mp3) ) {
				            	Myfiles.add(help[t]);
				            } else {
				                number--;
				            }
				        }
						
						
					}
					
					//int p=number;
					//number += Myfiles.size();
					while ( p < number ) {
						System.out.println("add" + p);
						String fileName = Myfiles.get(p).getName();
						info.addElement(fileName);
						p++;
					}
					// This is where a real application would open the file.

				} else {

				}
			}
		});
		
		
		FileMenu.add(Open);
		
		JMenuItem Close = new JMenuItem("Close");
		Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model = (DefaultListModel) list.getModel();
				int selectedIndex = list.getSelectedIndex();
				if (selectedIndex != -1) {
					Myfiles.remove(selectedIndex);
					number--;
					MyStatus="Paused";
					if(selectedIndex==CurrentOnPlay){
						myClass.MyPause();
						lblSong.setText("");
						lblArtist.setText("");
						lblAlbum.setText("");
						lblArtistBio.setText("");
						MyGui.progressBar.setValue(0);
						MyGui.TimeLabel.setText("");
						
						//AlbumPhoto.setText("");
					}
				    model.remove(selectedIndex);
				    selectedIndex=-1;
				}
			}
		});
		FileMenu.add(Close);
		
		JMenuItem Exit = new JMenuItem("Exit");
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		FileMenu.add(Exit);
		
		JMenu HelpMenu = new JMenu("Help");
		menuBar.add(HelpMenu);
		
		JMenuItem About = new JMenuItem("About");
		About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputs= "This is an Mp3 App developed from Christos Markou for Multimedia Technology Course Ntua Ece ";
				JOptionPane.showMessageDialog(null, inputs, "About", JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		HelpMenu.add(About);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
