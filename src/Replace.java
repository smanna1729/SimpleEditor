import java.io.* ;
import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.text.* ;
import javax.swing.event.* ;

public class Replace extends JFrame implements ActionListener , ItemListener
{
	private JCheckBox matchcase , matchentireword ;
	private JButton replaceall , replacenext ;
	private JTextComponent editor ;
	private JTextField jtf , jtf1 ;
	
	Replace ( JTextComponent editor )
	{
		super ( "Find-Replace" ) ;
		
		this.editor = editor ;
		
		matchcase = new JCheckBox ( "Match Case" ) ;
		matchcase.setMnemonic( 'M' ) ;
		matchcase.addItemListener( this ) ;
		
		matchentireword = new JCheckBox ( "Match entire word only" ) ;
		matchentireword.setMnemonic( 'e' ) ;
		matchentireword.addItemListener( this ) ;
		 
 		JLabel jlab = new JLabel ( "Search For : " ) ;
		jtf = new JTextField ( ) ;
		JPanel jp = new JPanel ( ) ;
		jp.setLayout( new BoxLayout ( jp , BoxLayout.X_AXIS ) ) ;
		jp.add( Box.createHorizontalStrut( 20 ) ) ;
		jp.add( jlab ) ;
		jp.add( Box.createHorizontalStrut( 10 ) ) ;
		jp.add( jtf ) ;
		jp.add( Box.createHorizontalStrut( 20 ) ) ; 
		
 		JLabel jlab_r = new JLabel ( "Replace :" ) ;
		jtf1 = new JTextField ( ) ;
		JPanel jp_r = new JPanel ( ) ;
		jp_r.setLayout( new BoxLayout ( jp_r , BoxLayout.X_AXIS ) ) ;
		jp_r.add( Box.createHorizontalStrut( 20 ) ) ;
		jp_r.add( jlab_r ) ;
		jp_r.add( Box.createHorizontalStrut( 29 ) ) ;
		jp_r.add( jtf1 ) ;
		jp_r.add( Box.createHorizontalStrut( 20 ) ) ; 		
		
		JPanel jp1 = new JPanel ( ) ;
		jp1.setLayout( new BoxLayout ( jp1 , BoxLayout.Y_AXIS ) ) ;
		jp1.add( matchcase ) ;
		jp1.add( Box.createVerticalStrut( 10 ) ) ;
		jp1.add( matchentireword ) ;
		jp1.add( Box.createVerticalStrut( 10 ) ) ;
		//jp1.add( Box.createVerticalStrut( 10 ) ) ; 
		
		JPanel temp_jp1 = new JPanel ( ) ;
		temp_jp1.setLayout( new BoxLayout ( temp_jp1 , BoxLayout.X_AXIS ) ) ;
		temp_jp1.add( Box.createHorizontalStrut( 20 ) ) ;
		temp_jp1.add( jp1 ) ;
		temp_jp1.add( Box.createHorizontalStrut( 20 ) ) ;
			
		replaceall = new JButton ( "Replace All" ) ;
		replaceall.addActionListener( this ) ;
		replacenext = new JButton ( "Replace Next" ) ;
		replacenext.addActionListener( this ) ;
		
		JPanel jp2 = new JPanel ( ) ;
		jp2.setLayout( new BoxLayout ( jp2 , BoxLayout.X_AXIS) ) ;
		jp2.add( Box.createHorizontalStrut( 100 ) ) ;
		jp2.add( replacenext ) ;
		jp2.add( Box.createHorizontalStrut( 15 ) ) ;
		jp2.add( replaceall ) ;
		jp2.add( Box.createHorizontalStrut( 20 ) ) ;
		
		JPanel intermediatepanel = new JPanel ( ) ;
		intermediatepanel.setLayout( new BoxLayout ( intermediatepanel , BoxLayout.Y_AXIS ) ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( jp ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( jp_r ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( temp_jp1 ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( jp2 ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		
		Container c = getContentPane() ;
		c.setLayout( new BorderLayout ( ) ) ;
		c.add( intermediatepanel ) ;
		
		setLocation( 400 , 150 ) ;
		setSize( 350 , 268 ) ;
		setResizable( false ) ;
		setVisible( true ) ;
		
		WindowListener wl = new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				((Window) we.getSource()).dispose();
				
			}
		};
		this.addWindowListener(wl);
	}
	
	public void itemStateChanged ( ItemEvent ie )
	{
		if ( ie.getSource() == matchcase )
		{
			System.out.println ( "Match Case : " + ie.getStateChange() ) ;
		}
		else if ( ie.getSource() == matchentireword )
		{
			System.out.println ( "Match Entire Word Only : " + ie.getStateChange() ) ;
		}
	}
	
	public void actionPerformed ( ActionEvent ae )
	{
		if ( ae.getSource() == replaceall )
		{
			if ( jtf.getText().equals("") )
			{
				JOptionPane.showMessageDialog( null , "Please Enter a word to Find!!" ) ;
				return ;
			}
			if ( jtf1.getText().equals("") )
			{
				JOptionPane.showMessageDialog( null , "Please Enter a word to Replace!!" ) ;
				return ;
			}
			highlight ( this.editor , jtf.getText().trim() ) ;
		}
		else if ( ae.getSource() == replacenext )
		{
			if ( jtf.getText().equals("") )
			{
				JOptionPane.showMessageDialog( null , "Please Enter a word to Find!!" ) ;
				return ;
			}
			if ( jtf1.getText().equals("") )
			{
				JOptionPane.showMessageDialog( null , "Please Enter a word to Replace!!" ) ;
				return ;
			}			
		}
	}

/*	private void replaceAll ( String searchstring , String tobereplaced )
	{
		Document doc = editor.getDocument() ;
		String text = doc.getText(0, doc.getLength());
        int pos = 0 , oldpos = 0 ;
        
        while ( ( pos = text.indexOf( searchstring , pos ) ) >= 0 )
        {
        	
        }	
	}*/

	private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);
	
	public class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter 
	{
    	public MyHighlightPainter(Color color) 
    	{
        	super(color);
    	}
	}
	
	private void removeHighlights(JTextComponent textComp) 
	{
    	Highlighter hilite = textComp.getHighlighter();
    	Highlighter.Highlight[] hilites = hilite.getHighlights();

    	for (int i=0; i<hilites.length; i++) 
    	{
        	if (hilites[i].getPainter() instanceof MyHighlightPainter) 
        	{
            	hilite.removeHighlight(hilites[i]);
        	}
    	}
	}
	
	public void highlight(JTextComponent textComp, String pattern) 
	{
    	// First remove all old highlights
    	removeHighlights(textComp);

    	try 
    	{
        	Highlighter hilite = textComp.getHighlighter();
        	Document doc = textComp.getDocument();
        	String text = doc.getText(0, doc.getLength());
        	int pos = 0;
			
        // Search for pattern
        	while ((pos = text.indexOf(pattern, pos)) >= 0) 
        	{
            // Create highlighter using private painter and apply around pattern
            	hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
            	pos += pattern.length();
        	}
    	} 
    	catch (BadLocationException e) 
    	{
    	}
    }
	
	public static void main (String[] args) 
	{
		Replace r = new Replace ( new JTextPane( ) )  ;	
	}
}