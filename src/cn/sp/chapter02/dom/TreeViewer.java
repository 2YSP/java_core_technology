package cn.sp.chapter02.dom;

import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by 2YSP on 2018/1/10.
 */
public class TreeViewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new DOMTreeFrame();
                frame.setTitle("TreeViewer");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class DOMTreeFrame extends JFrame{

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private DocumentBuilder builder;

    public DOMTreeFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(exitItem);

        JMenuBar bar = new JMenuBar();
        bar.add(fileMenu);
        setJMenuBar(bar);
    }

    public void openFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("dom"));

        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return "XML file";
            }
        });

        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION){
            return;
        }
        final File file = chooser.getSelectedFile();

        new SwingWorker<Document,Void>(){
            @Override
            protected Document doInBackground() throws Exception {
                if (builder == null){
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    builder = factory.newDocumentBuilder();
                }
                return builder.parse(file);
            }

            @Override
            protected void done() {
                try {
                    Document doc = get();
                    JTree tree = new JTree(new DOMTreeModel(doc));
                    tree.setCellRenderer(new DOMTreeCellRenderer());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }
}

class DOMTreeModel implements TreeModel{

    private Document doc;

    public DOMTreeModel(Document doc){
        this.doc = doc;
    }


    @Override
    public Object getRoot() {
        return doc.getDocumentElement();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Node node = (Node)parent;
        NodeList list = node.getChildNodes();

        return list.item(index);
    }

    @Override
    public int getChildCount(Object parent) {
        Node node = (Node)parent;
        NodeList list = node.getChildNodes();
        return list.getLength();
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }


    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Node node = (Node)parent;
        NodeList list = node.getChildNodes();
        for (int i=0;i<list.getLength();i++){
            if (getChild(node,i) == child){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}

class DOMTreeCellRenderer extends DefaultTreeCellRenderer{
    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean selected,
                                                  boolean expanded,boolean leaf,int row,boolean hasFocus){
        Node node = (Node)value;
        if (node instanceof Element){
            return elementPanel((Element)node);
        }
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);
        if (node instanceof CharacterData){
            setText(characterString((CharacterData) node));
        }else {
            setText(node.getClass()+": "+node.toString());
        }
        return this;
    }

    public static JPanel elementPanel(Element e){
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Element:"+e.getTagName()));
        final NamedNodeMap map = e.getAttributes();
        jPanel.add(new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return map.getLength();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return columnIndex == 0?map.item(rowIndex).getNodeName() :map.item(rowIndex).getNodeValue();
            }
        }));
        return jPanel;
    }

    public static String characterString(CharacterData node){
        StringBuilder builder = new StringBuilder(node.getData());
        for (int i=0;i<builder.length();i++){
            if (builder.charAt(i) == '\r'){
                builder.replace(i,i+1,"\\r");
                i++;
            }else if (builder.charAt(i) == '\n'){
                builder.replace(i,i+1,"\\n");
                i++;
            }else if (builder.charAt(i) == '\t'){
                builder.replace(i,i+1,"\\t");
                i++;
            }
        }

        if (node instanceof CDATASection){
            builder.insert(0,"CDATASection:");
        }else if (node instanceof Text){
            builder.insert(0,"Text:");
        }else if (node instanceof Comment){
            builder.insert(0,"Comment:");
        }

        return builder.toString();
    }
}