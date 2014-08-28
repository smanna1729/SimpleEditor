import java.io.* ;
import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.event.* ;
import java.util.ArrayList ;

public class FontSelector extends JFrame implements ActionListener
{
	private JTextField fontname , fontsize , fontstyle ;
	private JComboBox fontnamelist , fontsizelist , fontstylelist ;
	private JButton apply , clear ;
	private String[] font , size , style ;
	private int hold = -1 ;
	private String[] fonts = { "Dialouge" , "TimesRoman" , "DialougeInput" , "Monospaced" , "Sanserif" , "Serif" , "GothicE" , "Agencey FB" , "Arial" , "Arial Black" , "Arial Narrow" ,
								   "Britannic Bold" , "Chiller" , "Comic Sans MS" , "Courier New" , "Monotxt" , "Monotype Corsiva" , "Papyrus" , "Verdana" } ;
	private String[] sizes = { "8" , "10" , "12" , "14" , "16" , "18" , "20" , "24" , "26" , "28" } ;
	private String[] styles = { "Plain" , "Bold" , "Italics" } ;
	
	private int[] corresStyle = { Font.PLAIN , Font.BOLD , Font.ITALIC } ;
 
 	private JTextPane editor ;
 
	public FontSelector ( JTextPane editor )
	{
		super ( "Font-Size-Style" ) ;
		
		this.editor = editor ;
		
		JPanel jp1 = new JPanel ( ) ;
		JLabel jl1 = new JLabel ( "Font Name :" ) ;
		jp1.setLayout( new BoxLayout ( jp1 , BoxLayout.X_AXIS ) ) ;
		jp1.add( Box.createHorizontalStrut( 20 ) ) ;
		jp1.add( jl1 ) ;
		fontname = new JTextField ( ) ;
		fontname.setEnabled( false ) ;
		jp1.add( Box.createHorizontalStrut( 20 ) ) ;
		jp1.add( fontname ) ;
		jp1.add( Box.createHorizontalStrut( 20 ) ) ;
		
		JPanel jp2 = new JPanel ( ) ;
		jp2.setLayout( new BoxLayout ( jp2 , BoxLayout.X_AXIS ) ) ;
		jp2.add( Box.createHorizontalStrut( 20 ) ) ;		
		JLabel jl2 = new JLabel ( "Font Size :" ) ;		
		jp2.add( jl2 ) ;
		jp2.add( Box.createHorizontalStrut( 20 ) ) ;
		fontsize = new JTextField ( ) ;
		fontsize.setEnabled( false ) ;
		jp2.add( fontsize ) ;		
		jp2.add( Box.createHorizontalStrut( 20 ) ) ;
		
		JPanel jp3 = new JPanel ( ) ;
		jp3.setLayout( new BoxLayout( jp3 , BoxLayout.X_AXIS ) ) ;
		JLabel jl3 = new JLabel ( "Font Style :" ) ;
		jp3.add( Box.createHorizontalStrut( 20 ) ) ;	
		jp3.add( jl3 ) ;
		jp3.add( Box.createHorizontalStrut( 20 ) ) ;	
		fontstyle = new JTextField ( ) ;
		fontstyle.setEnabled( false ) ;
		jp3.add( fontstyle ) ;
		jp3.add( Box.createHorizontalStrut( 20 ) ) ;
	
		JPanel jp4 = new JPanel ( ) ;
		jp4.setLayout( new BoxLayout ( jp4 , BoxLayout.X_AXIS ) ) ;	
		jp4.add ( Box.createHorizontalStrut( 40 ) ) ;
		fontnamelist = new JComboBox ( fonts ) ;
		fontnamelist.addActionListener( this ) ;		
		JScrollPane jsp1 = new JScrollPane ( fontnamelist ) ;
		jp4.add( jsp1 ) ;
		jp4.add ( Box.createHorizontalStrut( 40 ) ) ;
		
		JPanel jp5 = new JPanel ( ) ;
		jp5.setLayout( new BoxLayout ( jp5 , BoxLayout.X_AXIS ) ) ;	
		jp5.add ( Box.createHorizontalStrut( 40 ) ) ;
		fontsizelist = new JComboBox ( sizes ) ;
		fontsizelist.addActionListener( this ) ;
		JScrollPane jsp2 = new JScrollPane ( fontsizelist ) ;
		jp5.add ( jsp2 ) ;
		jp5.add ( Box.createHorizontalStrut( 40 ) ) ;
		
		JPanel jp6 = new JPanel ( ) ;
		jp6.setLayout( new BoxLayout ( jp6 , BoxLayout.X_AXIS ) ) ;
		jp6.add ( Box.createHorizontalStrut( 40 ) ) ; 
		fontstylelist = new JComboBox ( styles ) ;
		fontstylelist.addActionListener( this ) ;
		JScrollPane jsp3 = new JScrollPane ( fontstylelist ) ;
		jp6.add ( jsp3 ) ;
		jp6.add ( Box.createHorizontalStrut( 40 ) ) ;
/*		JPanel jp3 = new JPanel ( ) ;
		jp3.setLayout( new BoxLayout ( jp3 , BoxLayout.X_AXIS ) ) ;
		jp3.add( Box.createHorizontalStrut( 20 ) ) ;
		jp3.add( fontnamelist ) ;
		jp3.add( Box.createHorizontalStrut( 20 ) ) ;
		jp3.add( fontsizelist ) ;
		jp3.add( Box.createHorizontalStrut( 20 ) ) ;
		jp3.add( fontstylelist ) ;
		jp3.add( Box.createHorizontalGlue() ) ;
*/		

		JPanel buttonpanel = new JPanel ( ) ;
		buttonpanel.setLayout( new BoxLayout ( buttonpanel , BoxLayout.X_AXIS ) ) ;
		apply = new JButton ( "Apply" ) ;
		apply.addActionListener( this ) ;
		clear = new JButton ( "Clear" ) ;
		clear.addActionListener( this ) ;
		buttonpanel.add( Box.createHorizontalStrut( 180 ) ) ;
		buttonpanel.add( apply ) ;	
		buttonpanel.add( Box.createHorizontalStrut( 10 ) ) ;
		buttonpanel.add( clear ) ;
		buttonpanel.add( Box.createHorizontalStrut( 20 ) ) ;
			
		JPanel intermediatePanel = new JPanel (  ) ;
		intermediatePanel.setLayout( new BoxLayout ( intermediatePanel , BoxLayout.Y_AXIS ) ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;
		intermediatePanel.add( jp1 ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( jp4 ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( jp2 ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( jp5 ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( jp3 ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( jp6 ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( buttonpanel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;		
				
		Container c = getContentPane() ;
		c.setLayout( new BorderLayout ( ) ) ;
		c.add( intermediatePanel ) ;
		
		setLocation( 400 , 300 ) ;
		setSize( 350 , 340 ) ;
		setResizable( false ) ;
		setVisible( true ) ;
		
		WindowListener wl = new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				((Window) we.getSource()).dispose();
				System.exit(0);
			}
		};
		this.addWindowListener(wl);
	}
	
	public void actionPerformed ( ActionEvent ae )
	{
		if ( ae.getSource() == apply )
		{
			Font temp = editor.getFont() ;
			int editorstyle = temp.getStyle() ;
			int editorsize = temp.getSize() ;
			String editorname = temp.getFamily() ;
			
			if ( !fontname.getText().equals("") )
				editorname = fontname.getText() ;
			if ( !fontsize.getText().equals( "" ) )
				editorsize = Integer.parseInt( fontsize.getText() ) ;
			if ( !fontstyle.getText().equals( "" ) )
				editorstyle = corresStyle[fontstylelist.getSelectedIndex()] ;
			temp = new Font ( editorname , editorstyle , editorsize ) ;
			editor.setFont( temp ) ;
			editor.revalidate() ;
			editor.repaint() ;
			this.dispose() ;
		}
		else if ( ae.getSource() == clear )
		{
			fontname.setText( "" ) ;
			fontsize.setText( "" ) ;
			fontstyle.setText( "" ) ;
			/*fontnamelist.setSelectedIndex( 0 ) ;
			fontsizelist.setSelectedIndex( 0 ) ;
			fontstylelist.setSelectedIndex( 0 ) ;*/
			//fontnamelist.setName( (String) fontnamelist.getItemAt( 0 ) ) ;
		}
		else if ( ae.getSource() == fontnamelist )
		{
			fontname.setText( (String) fontnamelist.getSelectedItem() ) ;
		}
		else if ( ae.getSource() == fontsizelist )
		{
			fontsize.setText( (String)fontsizelist.getSelectedItem() ) ;
		}
		else if ( ae.getSource() == fontstylelist ) 
		{
			hold = fontstylelist.getSelectedIndex() ;
			fontstyle.setText( (String)fontstylelist.getSelectedItem() ) ;
		}
	}
	
				
	public static void main (String[] args) 
	{
		FontSelector fs = new FontSelector ( new JTextPane( ) ) ;
	}
}