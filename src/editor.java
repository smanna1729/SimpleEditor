import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.beans.* ;
import java.util.* ;
import java.net.URL;

import javax.swing.undo.* ;
import javax.swing.*;
import javax.swing.event.* ;
import javax.swing.text.*;

import java.nio.file.*;

public class editor extends JFrame 
{
	//private JTextPane area = new JTextPane( ) ;
	private JTextComponent editor ;
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "K-Editor";
  	private boolean changed = false;
  	private static ResourceBundle resources ;
	private HashSet<String> commands ;
	private JMenuBar menubar;
	private Hashtable menuItems , command ;
	private JToolBar toolbar;
	
    /**
     * Suffix applied to the key used in resource file
     * lookups for tooltip text.
     */
    public static final String tipSuffix = "Tooltip";
    
    /**
     * Suffix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String imageSuffix = "Image";

    /**
     * Suffix applied to the key used in resource file
     * lookups for a label.
     */
    public static final String labelSuffix = "Label";

    /**
     * Suffix applied to the key used in resource file
     * lookups for an action.
     */
    public static final String actionSuffix = "Action";

    /** UndoManager that we add edits to. */
    protected UndoManager undo = new UndoManager();
        
    /**
     * Listener for the edits on the current document.
     */
    protected UndoableEditListener undoHandler = new UndoHandler();
            
    public static final String openAction = "open";
    public static final String newAction  = "new";
    public static final String saveAction = "save";
    public static final String saveasAction = "saveas";
    public static final String exitAction = "exit";
    public static final String searchAction = "find";
    public static final String clearhighlightAction = "clear";
    public static final String replaceAction = "replace";
    public static final String selectallAction = "selectall";
    public static final String insertdateAction = "insertdate";
    public static final String fontAction = "font";
    
  	static 
  	{
    	try 
    	{
      		resources = ResourceBundle.getBundle("properties.Notepad", Locale.getDefault() );
    	} 
    	catch (MissingResourceException mre) 
    	{
      		System.err.println("properties/Notepad.properties not found");
            System.exit(1);
        }
    }
	  
  public editor() 
  {
	//	area.setFont(new Font("Monospaced",Font.PLAIN,12));
			// create the embedded JTextComponent
		editor = createEditor();
	// Add this as a listener for undoable edits.
		editor.getDocument().addUndoableEditListener(undoHandler);
		
		JScrollPane scroll = new JScrollPane( editor ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		
		add(scroll,BorderLayout.CENTER);
		
//		JMenuBar JMB = new JMenuBar();

/*		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		//JMenu font = new JMenu("Font");
		JMB.add(file); 
		JMB.add(edit);
		JMB.add(font);
		file.add(Open);file.add(Save);
		file.add(Quit);file.add(SaveAs);
		file.addSeparator();
		
		for(int i=0; i<4; i++)
			file.getItem(i).setIcon(null);
		
		edit.add(Cut);edit.add(Copy);edit.add(Paste);*/
		
		/*font.add(font);*/

/*		edit.getItem(0).setText("Cut out");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
JToolBar tool = new JToolBar();
		add(tool,BorderLayout.NORTH);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();
		
		JButton cut = tool.add(Cut), cop = tool.add(Copy),pas = tool.add(Paste);
		
		cut.setText(null); cut.setIcon(new ImageIcon("cut.png"));
		cop.setText(null); cop.setIcon(new ImageIcon("copy.gif"));
		pas.setText(null); pas.setIcon(new ImageIcon("paste.gif"));
		*/
		
		
		
/*		Save.setEnabled(false);
		SaveAs.setEnabled(false);*/
		
		// install the command table
		
		command = new Hashtable();
		
		Action[] actions = getActions();
		
		for (int i = 0; i < actions.length; i++) 
		{
	    	Action a = actions[i];
	    //commands.put(a.getText(Action.NAME), a);
	    	command.put(a.getValue(Action.NAME), a);
	    	System.out.println ( a.getValue(Action.NAME) ) ;
		}
		
		add(createToolbar(),BorderLayout.NORTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		editor.addKeyListener(k1);
		setTitle(currentFile);
		setLocation( 100 , 100 ) ;
		setSize( 800 , 600 ) ;
		prepareHashSetCommands ( ) ;
		menuItems = new Hashtable();
		setJMenuBar( createMenubar ( ) );		
		setVisible(true);
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

    /**
     * Create an editor to represent the given document.  
     */
    protected JTextComponent createEditor() 
    {
		JTextComponent c = new JTextPane() ;
		c.setDragEnabled(true);
		c.setFont(new Font("monospaced", Font.PLAIN, 12));
		return c;
    }

    /** 
     * Fetch the editor contained in this panel
     */
    protected JTextComponent getEditor() 
    {
		return editor;
    }

    private String getResourceString(String nm) 
    {
		String str;
		try 
		{
	    	str = resources.getString(nm);
		} 
		catch (MissingResourceException mre) 
		{
	    	str = null;
		}
		return str;
    }
	
	protected URL getResource(String key) 
    {
		String name = getResourceString(key);
		if (name != null) 
		{
	    	URL url = this.getClass().getResource(name);
	    	return url;
		}
		return null;
    }

	protected URL getImageResources (String key)
    {
        String name = getResourceString (key);
        if (null != name)
        {
            try
            {
                // Move to folder from which it is running
                // Sample : /Users/labuser/github/SimpleEditor/src
                Path path = Paths.get(editor.class.getResource(".").toURI());
                
                // filePath -> /Users/labuser/github/SimpleEditor/resources/new.png
                String filePath = path.getParent() + "/" + name;
                
                // url -> file:/Users/labuser/github/SimpleEditor/resources/new.png
                URL url = new File(filePath).toURI().toURL();

                return url;
                
            }
            catch (Exception ex)
            {
                System.out.println ("exception..");
            }
            System.out.println (editor.class.getResource("."));
        }

        return null;
    }
    
	private String[] tokenize ( String instruction )
	{
		StringTokenizer str = new StringTokenizer ( instruction ) ;
		String []cmd = new String [str.countTokens()] ;
		for ( int i = 0 , len = str.countTokens() ; i < len ; i ++ )
			cmd[i] = str.nextToken() ;
			
		return cmd ;
	}
	
	private void prepareHashSetCommands ( )
	{
		String []instructionLabel = tokenize( getResourceString("instructionset" ) );
		commands = new HashSet<String> ( ) ;
		for ( int i = 0 ; i < instructionLabel.length ; i ++ )
		{
			String []labels = tokenize( instructionLabel[i] ) ;
			for ( int j = 0 ; j < labels.length ; j ++ )
				commands.add( labels[j] ) ;
		}
	} 
	
	public boolean containsKeyword ( String key )
	{
		return commands.contains( key.toUpperCase() ) ;
	}

    /**
     * Fetch the menu item that was created for the given
     * command.
     * @param cmd  Name of the action.
     * @returns item created for the given command or null
     *  if one wasn't created.
     */
    protected JMenuItem getMenuItem(String cmd) 
    {
		return (JMenuItem) menuItems.get(cmd);
    }

    protected Action getAction(String cmd) 
    {
		return (Action) command.get(cmd);
    }

    /**
     * Fetch the list of actions supported by this
     * editor.  It is implemented to return the list
     * of actions supported by the embedded JTextComponent
     * augmented with the actions defined locally.
     */
    public Action[] getActions() 
    {
		return TextAction.augmentList( editor.getActions(), defaultActions );
    }

    /**
     * Create the menubar for the app.  By default this pulls the
     * definition of the menu from the associated resource file. 
     */
    protected JMenuBar createMenubar() 
    {
		JMenuItem mi;
		JMenuBar mb = new JMenuBar();

		String[] menuKeys = tokenize(getResourceString("menubar"));
		for (int i = 0; i < menuKeys.length; i++) 
		{
	    	JMenu m = createMenu(menuKeys[i]);
	    	if (m != null) 
	    	{
				mb.add(m);
	    	}
		}
        this.menubar = mb;
		return mb;
    }


    /**
     * Create a menu for the app.  By default this pulls the
     * definition of the menu from the associated resource file.
     */
    protected JMenu createMenu(String key) 
    {
		String[] itemKeys = tokenize(getResourceString(key));
		JMenu menu = new JMenu(getResourceString(key + "Label"));
		for (int i = 0; i < itemKeys.length; i++) 
		{
	    	if (itemKeys[i].equals("-")) 
	    	{
				menu.addSeparator();
	    	} 
	    	else 
	    	{
				JMenuItem mi = createMenuItem(itemKeys[i]);
				menu.add(mi);
	    	}
		}
		return menu;
    }

    /**
     * This is the hook through which all menu items are
     * created.  It registers the result with the menuitem
     * hashtable so that it can be fetched with getMenuItem().
     * @see #getMenuItem
     */
    protected JMenuItem createMenuItem(String cmd) 
    {
		JMenuItem mi = new JMenuItem(getResourceString( cmd + labelSuffix));
        URL url = getImageResources(cmd + imageSuffix);
		if (url != null) 
		{
	    	mi.setHorizontalTextPosition(JButton.RIGHT);
	    	mi.setIcon(new ImageIcon(url));
		}
		String astr = getResourceString(cmd + actionSuffix);
		
		if (astr == null) 
		{
	    	astr = cmd;
		}
		
		mi.setActionCommand(astr);
		Action a = getAction(astr);
		if (a != null) 
		{
			System.out.println ( a.getValue(Action.NAME) ) ;
	    	mi.addActionListener(a);
	    	a.addPropertyChangeListener(createActionChangeListener(mi));
	    	mi.setEnabled(a.isEnabled());
		} 
		else 
		{
	    	mi.setEnabled(false);
		}
		menuItems.put(cmd, mi);
		return mi;
    }

    // Yarked from JMenu, ideally this would be public.
    protected PropertyChangeListener createActionChangeListener(JMenuItem b) 
    {
		return new ActionChangedListener(b);
    }

    // Yarked from JMenu, ideally this would be public.
    private class ActionChangedListener implements PropertyChangeListener 
    {
        JMenuItem menuItem;
        
        ActionChangedListener(JMenuItem mi) 
        {
            super();
            this.menuItem = mi;
        }
        public void propertyChange(PropertyChangeEvent e) 
        {
            String propertyName = e.getPropertyName();
            if (e.getPropertyName().equals(Action.NAME)) 
            {
                String text = (String) e.getNewValue();
                menuItem.setText(text);
            } 
            else if (propertyName.equals("enabled")) 
            {
                Boolean enabledState = (Boolean) e.getNewValue();
                menuItem.setEnabled(enabledState.booleanValue());
            }
        }
    }

    /**
     * Create the toolbar.  By default this reads the 
     * resource file for the definition of the toolbar.
     */
    private Component createToolbar() 
    {
		toolbar = new JToolBar();

		String[] toolKeys = tokenize(getResourceString("toolbar"));
		for (int i = 0; i < toolKeys.length; i++) 
		{
	    	if (toolKeys[i].equals("-")) 
	    	{
				//toolbar.add(Box.createHorizontalStrut(5));
				toolbar.addSeparator() ;
				//toolbar.add(Box.createHorizontalStrut(5));
	    	} 
	    	else 
	    	{
				toolbar.add(createTool(toolKeys[i]));
	    	}
		}
		toolbar.add(Box.createHorizontalGlue());
		return toolbar;
    }

    /**
     * Hook through which every toolbar item is created.
     */
    protected Component createTool(String key) 
    {
		return createToolbarButton(key);
    }
	
	 /**
     * Create a button to go inside of the toolbar.  By default this
     * will load an image resource.  The image filename is relative to
     * the classpath (including the '.' directory if its a part of the
     * classpath), and may either be in a JAR file or a separate file.
     * 
     * @param key The key in the resource file to serve as the basis
     *  of lookups.
     */
    protected JButton createToolbarButton(String key)
    {
        URL url = getImageResources(key + imageSuffix);
        
        JButton b = new JButton(new ImageIcon(url)) 
        {
            public float getAlignmentY() { return 0.5f; }
		};
        b.setRequestFocusEnabled(false);
        b.setMargin(new Insets(1,1,1,1));

		String astr = getResourceString(key + actionSuffix);
		if (astr == null) 
		{
	    	astr = key;
		}
		Action a = getAction(astr);
		if (a != null) 
		{
	    	b.setActionCommand(astr);
	    	b.addActionListener(a);
		} 
		else 
		{
	    	b.setEnabled(false);
		}

		String tip = getResourceString(key + tipSuffix);
		
		if (tip != null) 
		{
	    	b.setToolTipText(tip);
		}
 
        return b;
    }
    
    // --- action implementations -----------------------------------

    private UndoAction undoAction = new UndoAction();
    private RedoAction redoAction = new RedoAction();

    /**
     * Actions defined by the Notepad class
     */
    private Action[] defaultActions = {
	new NewAction(),
	new OpenAction(),
    new SaveAction(),
	new ExitAction(),
	new InsertDateAction(),
	new SelectAllAction(),
	new SaveAsAction() ,
	new FontAction(),
	new SearchAction(),
	new ClearHighlighterAction ( ) ,
	new ReplaceAction() ,
        undoAction,
        redoAction
    };

    /**
     * Resets the undo manager.
     */
    protected void resetUndoManager() 
    {
		undo.discardAllEdits();
		undoAction.update();
		redoAction.update();
    }

	private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

	private void removeHighlights( ) 
	{
    	Highlighter hilite = editor.getHighlighter( ) ;
    	Highlighter.Highlight[] hilites = hilite.getHighlights();

    	for (int i=0; i<hilites.length; i++) 
    	{
        	if (hilites[i].getPainter() instanceof MyHighlightPainter ) 
        	{
            	hilite.removeHighlight(hilites[i]);
        	}
    	}
	}
    
    class ClearHighlighterAction extends AbstractAction
    {
    	ClearHighlighterAction ( )
    	{
    		super ( clearhighlightAction );
    	}
    	public void actionPerformed ( ActionEvent ae) 
    	{
    		removeHighlights ( ) ;
    		editor.revalidate() ;
    		editor.repaint() ;
    	}
    }
        
    class SearchAction extends AbstractAction
    {
    	SearchAction()
    	{
    		super ( searchAction ) ;
    	}
    	public void actionPerformed(ActionEvent e) 
		{
			Find f = new Find ( editor ) ;
		}
    }
    
    class ReplaceAction extends AbstractAction
    {
    	ReplaceAction ( )
    	{
    		super ( replaceAction ) ;
    	}
    	public void actionPerformed ( ActionEvent ae )
    	{
    		Replace r = new Replace( editor ) ;
    	}
    }
    
    class UndoAction extends AbstractAction
    {
		public UndoAction() 
		{
	    	super("Undo");
	    	setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) 
		{
	    	try 
	    	{
                if (undo.canUndo())
                    undo.undo();
	    	} 
	    	catch (CannotUndoException ex) 
	    	{
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
	    	}
	    	update();
	    	redoAction.update();
		}

		protected void update() 
		{
	    	if(undo.canUndo()) 
	    	{
				setEnabled( true ) ;
				putValue( Action.NAME, undo.getUndoPresentationName() ) ;
	    	}
	    	else 
	    	{
				setEnabled( false ) ;
				putValue( Action.NAME, "Undo" ) ;
	    	}
		}  	
    }
    
    class RedoAction extends AbstractAction
    {
		public RedoAction() 
		{
	    	super("Redo");
	    	setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) 
		{
	    	try 
	    	{
                if (undo.canRedo())
                    undo.redo();
	    	} 
	    	catch (CannotRedoException ex) 
	    	{
				System.out.println("Unable to redo: " + ex);
				ex.printStackTrace();
	    	}
	    	update();
	    	undoAction.update();
		}

		protected void update() 
		{
	    	if(undo.canRedo()) 
	    	{
				setEnabled(true);
				putValue(Action.NAME, undo.getRedoPresentationName());
	    	}
	    	else 
	    	{
				setEnabled(false);
				putValue(Action.NAME, "Redo");
	    	}
		}   	
    }        
    
	/**
    * Really lame implementation of an exit command
    */
    class ExitAction extends AbstractAction 
    {
		ExitAction() 
		{
	    	super(exitAction);
		}

        public void actionPerformed(ActionEvent e) 
        {
	    	System.exit(0);
		}  	
    }
    
    public String insertDateTime ( )
    {
    	Date d = new Date ( ) ;
    	String[] datepart = d.toString().split( " " ) ;

    	String date = String.valueOf( d.getMonth() + 1 ) + "/" ;
    	date += String.valueOf( d.getDate() ) + "/" + datepart[datepart.length-1] ;
    	
    	date = datepart[0] + " " + datepart[3] + " " + date ;
    	
    	return date ;
    }
    
    class InsertDateAction extends AbstractAction
    {
    	InsertDateAction()
    	{
    		super ( insertdateAction ) ;
    	}
    	public void actionPerformed ( ActionEvent ae )
    	{
    		Document oldDoc = getEditor().getDocument() ;
    		if ( oldDoc != null )
    		{
    			try
    			{
    				oldDoc.insertString( oldDoc.getLength() , insertDateTime( ) , ((JTextPane)editor).getStyle( "normal" ) ) ;
    			}
    			catch ( BadLocationException ble )
    			{
    				System.out.println ( ble.getMessage() ) ;
    				ble.printStackTrace() ;
    			}
    		}
    		else
    		{
    			System.out.println ( "Unable to Insert Date and Time in the document" ) ;
    		}
    	}
    }
    
    class SelectAllAction extends AbstractAction
    {
    	SelectAllAction ( )
    	{
    		super ( selectallAction ) ;
    	}
    	public void actionPerformed ( ActionEvent ae )
    	{
    		editor.selectAll() ;
    		editor.revalidate() ;
    	}
    }
    
    class FontAction extends AbstractAction
    {    	
    	FontAction ( )
    	{
    		super ( fontAction ) ;    		
    	}

    	public void actionPerformed ( ActionEvent ae )
    	{
    		FontSelector fs = new FontSelector ( (JTextPane)editor ) ;
    	}
    }
    
    class UndoHandler implements UndoableEditListener 
    {

	/**
	 * Messaged when the Document has created an edit, the edit is
	 * added to <code>undo</code>, an instance of UndoManager.
	 */
        public void undoableEditHappened(UndoableEditEvent e) 
        {
	    	undo.addEdit(e.getEdit());
	    	undoAction.update();
	    	redoAction.update();
		}	
    }    

    /**
     * Find the hosting frame, for the file-chooser dialog.
     */
    protected Frame getFrame() 
    {
		for (Container p = getParent(); p != null; p = p.getParent()) 
		{
	    	if (p instanceof Frame) 
	    	{
				return (Frame) p;
	    	}
		}
		return null;
    }
        
    class NewAction extends AbstractAction 
    {    
		NewAction() 
		{
	    	super(newAction);
		}

		NewAction(String nm) 
		{
	    	super(nm);
		}
		    	
        public void actionPerformed(ActionEvent e) 
        {
	    	Document oldDoc = getEditor().getDocument();
	    	if(oldDoc != null)
	    		oldDoc.removeUndoableEditListener(undoHandler);
	    	
	    	//getEditor().setDocument( new  ) ;
	    	try
	    	{
	    		oldDoc.remove( 0 , oldDoc.getLength() ) ;
	    	}
	    	catch ( BadLocationException ble )
	    	{
	    		System.out.println ( ble.getMessage() ) ;
	    		ble.printStackTrace() ;
	    	}
	    	
	    	getEditor().getDocument().addUndoableEditListener(undoHandler);
	    	resetUndoManager();
    //        getFrame().setTitle(resources.getString("Title"));
    		
	    	getEditor().revalidate();        	
        }    
    }
    
    class SaveAction extends AbstractAction 
    {
    	SaveAction() 
		{
	    	super(saveAction);
		}
		
        public void actionPerformed(ActionEvent e) 
        {
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();        	
        }    	
    }
    
    class OpenAction extends NewAction 
    {
		OpenAction() 
		{
	    	super(openAction);
		}
		    	
        public void actionPerformed(ActionEvent e) 
        {
			saveOld() ;
			if( dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) 
			{
				readInFile( dialog.getSelectedFile().getAbsolutePath() ) ;
			}
			//SaveAs.setEnabled(true);    	
        }
    }    
    	
private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			changed = true;
//			Save.setEnabled(true);
//			SaveAs.setEnabled(true);
		}
	};
/*	
Action Open = new AbstractAction("Open", new ImageIcon("open.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
			SaveAs.setEnabled(true);
		}
	};
	
Action Save = new AbstractAction("Save", new ImageIcon("save.gif")) {
		public void actionPerformed(ActionEvent e) {
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();
		}
	};*/
	
	class SaveAsAction extends AbstractAction 
    {
    	SaveAsAction() 
		{
	    	super(saveasAction);
		}
		
        public void actionPerformed(ActionEvent e) 
        {
			saveFileAs();        	
        }    	
    }
    
/*    
Action SaveAs = new AbstractAction("Save as...") {
		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
*/	
Action Quit = new AbstractAction("Quit") {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);
		}
	};
	/*Action font= new AbstractAction("font") {
		public void actionPerformed(ActionEvent e) {
		area.setFont(new Font("Monospaced",Font.PLAIN,12));	
		}
	};*/
	
/*    ActionMap m = area.getActionMap();
	Action Cut = m.get(DefaultEditorKit.cutAction);
	Action Copy = m.get(DefaultEditorKit.copyAction);
	Action Paste = m.get(DefaultEditorKit.pasteAction);
*/	
	private void saveFileAs() 
	{
		if( dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION )
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	
	private void saveOld() 
	{
		if(changed) 
		{
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}
	
	private void readInFile(String fileName) 
	{
		try 
		{
			FileReader r = new FileReader(fileName);
			editor.read(r,null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e) 
		{
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName);
		}
	}
	
	private void saveFile(String fileName) 
	{
		try 
		{
			FileWriter w = new FileWriter(fileName);
			editor.write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
//			Save.setEnabled(false);
		}
		catch(IOException e) 
		{
			System.out.println ( e.getMessage() ) ;
			e.printStackTrace() ;
		}
	}

    public  static void main(String[] arg) {
		new editor();
	}
}

