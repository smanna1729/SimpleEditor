import java.io.* ;
import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.text.* ;
import javax.swing.event.* ;
import java.util.StringTokenizer ;

public class Find extends JFrame implements ActionListener , ItemListener
{
	private JCheckBox matchcase , matchentireword , searchback ;
	private JButton findall , findnext ;
	private JTextComponent editor ;
	private JTextField jtf ;
	private boolean boolmatchcase , boolmatchentireword , boolsearchback ;
		
	Find ( JTextComponent editor )
	{
		super ( "Find" ) ;
		
		this.editor = editor ;
		
		matchcase = new JCheckBox ( "Match Case" ) ;
		matchcase.setMnemonic( 'M' ) ;
		matchcase.addItemListener( this ) ;
		
		matchentireword = new JCheckBox ( "Match entire word only" ) ;
		matchentireword.setMnemonic( 'e' ) ;
		matchentireword.addItemListener( this ) ;
		 
 		searchback = new JCheckBox ( "Search Backwards" ) ;
		searchback.setMnemonic( 'b' ) ;
		searchback.addItemListener( this ) ;
		
 		JLabel jlab = new JLabel ( "Search For" ) ;
		jtf = new JTextField ( ) ;
		JPanel jp = new JPanel ( ) ;
		jp.setLayout( new BoxLayout ( jp , BoxLayout.X_AXIS ) ) ;
		jp.add( Box.createHorizontalStrut( 20 ) ) ;
		jp.add( jlab ) ;
		jp.add( Box.createHorizontalStrut( 10 ) ) ;
		jp.add( jtf ) ;
		jp.add( Box.createHorizontalStrut( 20 ) ) ; 
		
		JPanel jp1 = new JPanel ( ) ;
		jp1.setLayout( new BoxLayout ( jp1 , BoxLayout.Y_AXIS ) ) ;
		jp1.add( matchcase ) ;
		jp1.add( Box.createVerticalStrut( 10 ) ) ;
		jp1.add( matchentireword ) ;
		jp1.add( Box.createVerticalStrut( 10 ) ) ;
		jp1.add( searchback ) ;
		//jp1.add( Box.createVerticalStrut( 10 ) ) ; 
		
		JPanel temp_jp1 = new JPanel ( ) ;
		temp_jp1.setLayout( new BoxLayout ( temp_jp1 , BoxLayout.X_AXIS ) ) ;
		temp_jp1.add( Box.createHorizontalStrut( 20 ) ) ;
		temp_jp1.add( jp1 ) ;
		temp_jp1.add( Box.createHorizontalStrut( 20 ) ) ;
			
		findall = new JButton ( "Find All" ) ;
		findall.addActionListener( this ) ;
		findnext = new JButton ( "Find Next" ) ;
		findnext.addActionListener( this ) ;
		
		JPanel jp2 = new JPanel ( ) ;
		jp2.setLayout( new BoxLayout ( jp2 , BoxLayout.X_AXIS) ) ;
		jp2.add( Box.createHorizontalStrut( 145 ) ) ;
		jp2.add( findnext ) ;
		jp2.add( Box.createHorizontalStrut( 15 ) ) ;
		jp2.add( findall ) ;
		jp2.add( Box.createHorizontalStrut( 20 ) ) ;
		
		JPanel intermediatepanel = new JPanel ( ) ;
		intermediatepanel.setLayout( new BoxLayout ( intermediatepanel , BoxLayout.Y_AXIS ) ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( jp ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( temp_jp1 ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatepanel.add( jp2 ) ;
		intermediatepanel.add( Box.createVerticalStrut( 15 ) ) ;
		
		Container c = getContentPane() ;
		c.setLayout( new BorderLayout ( ) ) ;
		c.add( intermediatepanel ) ;
		
		setLocation( 400 , 150 ) ;
		setSize( 350 , 232 ) ;
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
			if ( ie.getStateChange() == 1 )
				boolmatchcase = true ;
			else boolmatchcase = false ;
		}
		else if ( ie.getSource() == matchentireword )
		{
			if ( ie.getStateChange() == 1 )
				boolmatchentireword = true ;
			else boolmatchentireword = false ;
		}
		else if ( ie.getSource() == searchback )
		{
			if ( ie.getStateChange() == 1 )
				boolsearchback = true ;
			else boolsearchback = false ;
		}
	}
	
	public void actionPerformed ( ActionEvent ae )
	{
		if ( ae.getSource() == findall )
		{
			if ( jtf.getText().equals("") )
			{
				JOptionPane.showMessageDialog( null , "Please Enter a word to Find!!" ) ;
				return ;
			}
			
			highlight ( this.editor , jtf.getText().trim() ) ;
		}
		else if ( ae.getSource() == findnext )
		{
			if ( jtf.getText().equals("") )
			{
				JOptionPane.showMessageDialog( null , "Please Enter a word to Find!!" ) ;
				return ;
			}			
		}
	}

	private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);
	
	private void removeHighlights(JTextComponent textComp) 
	{
    	Highlighter hilite = textComp.getHighlighter();
    	Highlighter.Highlight[] hilites = hilite.getHighlights();

    	for (int i=0; i<hilites.length; i++) 
    	{
        	if (hilites[i].getPainter() instanceof MyHighlightPainter ) 
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
    		e.printStackTrace() ;
    	}
    }
	
	public void highlightAll ( JTextComponent textComp , String pattern )
	{
		try
		{
        	Highlighter hilite = textComp.getHighlighter();
        	Document doc = textComp.getDocument();
        	String text = doc.getText(0, doc.getLength());
        	int pos = 0 , totalLength = text.length() , patternLength = pattern.length() ;
        	
        	if ( boolmatchentireword )
        	{
        		StringTokenizer str = new StringTokenizer ( text ) ;
        		int tokenCount = str.countTokens() ;
        		for ( int i = 0 ; i < tokenCount ; i ++ )
        		{
        			
        		}
        		
        	}
        	else 
        	{
        		
        	}
        	
        	while ( pos < ( totalLength - patternLength )  )
        	{
        		String fragment = text.substring( pos , patternLength + pos ) ;
        		if ( boolmatchcase )
        		{
        			if ( fragment.equals( pattern ) )
        			{
        				hilite.addHighlight( pos , pos + patternLength , myHighlightPainter ) ;
        				pos = pos + patternLength ;
        			}
        			else pos ++ ;
        		}
        		else
        		{
        			if ( fragment.equalsIgnoreCase( pattern ) ) 
        			{
        				hilite.addHighlight( pos , pos + patternLength , myHighlightPainter ) ;
        				pos = pos + patternLength ;
        			}
        			else pos ++ ;
        		}
        	}			
		}
		catch ( BadLocationException ble )
		{
			ble.printStackTrace() ;
		}
	}
	
	public static void main (String[] args) 
	{
		Find f = new Find ( new JTextPane( ) )  ;	
	}
}