import java.awt.* ;
import javax.swing.* ;
import javax.swing.text.* ;
import java.awt.event.* ;

public class TextPaneDemo extends JPanel
{
	public TextPaneDemo ( )
	{
		Font f14 = new Font ( "SansSerif" , Font.PLAIN , 14 ) ;
		Font if14 = new Font ( "SansSerif" , Font.ITALIC , 14 ) ;
		
		Dimension dim = new Dimension ( 300 , 30 ) ;
		
		String newline = System.getProperty( "line.seperator" ) ;
		
		JTextPane textpane1 = new JTextPane ( ) ;
		
		textpane1.setEditable( false );
		
		initializeStyles ( textpane1 ) ;
		setPaneText ( textpane1 ) ;
		
		JScrollPane scrollpane1 = new JScrollPane ( textpane1 ) ;
		//scrollpane1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ) ;
		//scrollpane1.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS ) ;
		scrollpane1.setPreferredSize( new Dimension ( 380 , 275 ) ) ;
		
		Box box = Box.createVerticalBox() ;
		box.add( scrollpane1 ) ;
		add( box ) ;
		
		WindowListener wl = new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				((Window) we.getSource()).dispose();
				System.exit(0);
			}
		};
		//addWindowListener(wl);
	}
	
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,String description) 
    {
        java.net.URL imgURL = TextPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
	public void initializeStyles ( JTextPane pane ) 
	{
		StyleContext context = StyleContext.getDefaultStyleContext() ;
		Style defaultStyle = context.getStyle( StyleContext.DEFAULT_STYLE ) ;
		
		Style newStyle = pane.addStyle( "normal" , defaultStyle ) ;
		StyleConstants.setFontFamily( newStyle , "SansSerif" ) ;
		StyleConstants.setFontSize( newStyle , 16 ) ;
		
		newStyle = pane.addStyle( "dialouge" , defaultStyle ) ;
		StyleConstants.setFontFamily( newStyle , "dialouge" ) ;
		StyleConstants.setBold( newStyle , true ) ;
		StyleConstants.setForeground( newStyle , Color.RED ) ;
		StyleConstants.setFontSize( newStyle , 16 ) ;
		
		newStyle = pane.addStyle( "italic-16" , defaultStyle ) ;
		StyleConstants.setItalic( newStyle , true ) ;
		StyleConstants.setFontFamily( newStyle , "serif" ) ;
		StyleConstants.setFontSize( newStyle , 16 ) ;
		
		newStyle = pane.addStyle( "monospaced" , defaultStyle ) ;
		StyleConstants.setFontFamily( newStyle , "monospaced" ) ;
		StyleConstants.setFontSize( newStyle , 18 ) ;
		StyleConstants.setUnderline( newStyle , true ) ;
		
		
		newStyle = pane.addStyle( "bold" , defaultStyle ) ;
		StyleConstants.setBold( newStyle , true ) ;
		StyleConstants.setFontSize( newStyle , 16 ) ;
		
		newStyle = pane.addStyle( "serif-14" , defaultStyle ) ;
		StyleConstants.setFontFamily( newStyle , "Serif" ) ;
		StyleConstants.setFontSize( newStyle , 14 ) ;
		
		newStyle = pane.addStyle( "icon" , defaultStyle ) ;
        StyleConstants.setAlignment( newStyle , StyleConstants.ALIGN_CENTER ) ;
        ImageIcon pigIcon = createImageIcon("ke.png", "ke");
        if (pigIcon != null) 
        {
            StyleConstants.setIcon( newStyle , pigIcon);
        }
		
		newStyle = pane.addStyle( "normal" , defaultStyle ) ;
		StyleConstants.setBold( newStyle , false ) ;
		StyleConstants.setFontFamily( newStyle , "Serif" ) ;
		StyleConstants.setFontSize( newStyle , 16 ) ;
		
		newStyle = pane.addStyle( "bold-italic-18" , defaultStyle ) ;
		StyleConstants.setBold( newStyle , true ) ;
		StyleConstants.setItalic( newStyle , true ) ;
		StyleConstants.setFontSize( newStyle , 18 ) ;
		StyleConstants.setUnderline( newStyle , true ) ;
	}
	
	public void setPaneText ( JTextPane pane )
	{
		Document document = pane.getDocument() ;
		try
		{			
			document.insertString( document.getLength() , "\t" , pane.getStyle( "icon" ) ) ;
			StringBuffer str ;
			str = new StringBuffer ( "\nThis Editor is still now Under Construction\n" ) ;
			document.insertString( document.getLength() , str.toString() , pane.getStyle( "bold-italic-18" ) ) ;
			
			str = new StringBuffer ( "MAIN DEVELOPER :-" ) ;
			document.insertString( document.getLength() , str.toString() , pane.getStyle( "monospaced" ) ) ;
			
			str = new StringBuffer ( "\tKANISHKA" ) ;
			document.insertString( document.getLength() , str.toString() , pane.getStyle( "bold" ) ) ;
			
			str = new StringBuffer ( "\nPARTNER :-" ) ;
			document.insertString( document.getLength() , str.toString() , pane.getStyle( "monospaced" ) ) ;
			
			str = new StringBuffer ( "\t\tSANDIPAN" ) ;
			document.insertString( document.getLength() , str.toString() , pane.getStyle( "bold" ) ) ;					
		}
		catch ( BadLocationException ble )
		{
			System.out.println ( ble.getMessage() ) ;
			ble.printStackTrace() ;
		}
	}
	public static void main (String[] args) 
	{
		JFrame f = new JFrame ( "About K-Editor" ) ;
		TextPaneDemo tpd = new TextPaneDemo ( ) ;
		f.getContentPane().add( tpd ) ;
		
		f.pack() ;
		f.setVisible( true ) ;
	}
}