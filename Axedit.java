import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.UIManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Axedit{
    private JFrame frame;
    public int version = 1;
    public int coutt = 0;
    public boolean matrix = false;
    public File file_read;
    public File last_file;
    public JCheckBoxMenuItem chckbxmntmJasnyMotyw;
    public TextArea editor_area;
    public  File file = null;
    /**
    * TODO : Rename vars
    */
    public static void main(String[] args) {
        new Axedit().RunWindow();
    }
    void OTHERS_FRAME(){
        frame = new JFrame();
        frame.setBounds(100, 100, 190, 180);
        frame.getContentPane().setBackground(UIManager.getColor("CheckBox.BLACK"));
        frame.setTitle("Others");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JCheckBoxMenuItem chckb_light_theme = new JCheckBoxMenuItem("Light theme");
        frame.getContentPane().setLayout(null);
        frame.setBackground(SystemColor.BLACK);
        chckb_light_theme.setBounds(20, 50, 120, 50);
        frame.getContentPane().add(chckb_light_theme);
        chckb_light_theme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(chckb_light_theme.isSelected()){
                    editor_area.setBackground(SystemColor.white);
                    editor_area.setForeground(SystemColor.black);
                }
                else{
                    editor_area.setBackground(SystemColor.black);
                    editor_area.setForeground(SystemColor.green);
                }
            }
        });
        chckb_light_theme.setBorderPainted(false);
        chckb_light_theme.setBackground(UIManager.getColor("CheckBox.BLACK"));
        chckb_light_theme.setForeground(Color.WHITE);
        JCheckBoxMenuItem chckbxmntmMatrix = new JCheckBoxMenuItem("Matrix");
        chckbxmntmMatrix.setBounds(20, 20, 70, 20);
        frame.getContentPane().add(chckbxmntmMatrix);
        chckbxmntmMatrix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(chckbxmntmMatrix.isSelected()){
                    new Thread(() -> {
                        while(true){
                            matrix = true;
                            coutt++;
                            if(coutt == 56){
                                editor_area.append("\n");
                                coutt = 0;
                            }
                            editor_area.append("0" + "1");
                            if(matrix == false)
                    break; } } ).start();
                }
                else{
                    matrix=false;
                    editor_area.setText("");
                }
            }
        });
        chckbxmntmMatrix.setForeground(Color.WHITE);
        chckbxmntmMatrix.setBorderPainted(false);
        chckbxmntmMatrix.setBackground(UIManager.getColor("Button.BLACK"));
        JCheckBoxMenuItem chckbxmntmLock = new JCheckBoxMenuItem("Lock editor");
        chckbxmntmLock.setBounds(20, 85, 150, 70);
        chckbxmntmLock.setForeground(Color.WHITE);
        chckbxmntmLock.setBorderPainted(false);
        chckbxmntmLock.setBackground(UIManager.getColor("Button.BLACK"));
        chckbxmntmLock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(chckbxmntmLock.isSelected()){
                    editor_area.setEditable(false);
                }
                else{
                    editor_area.setEditable(true);
                }
        } });
        frame.getContentPane().add(chckbxmntmLock);
        frame.setVisible(true);
    }
    private void RunWindow() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.getContentPane().setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorSelectionForeground"));
        frame.setBackground(SystemColor.textText);
        frame.getContentPane().setBackground(UIManager.getColor("CheckBox.foreground"));
        frame.setTitle("Notepad by Axo 1.0");
        frame.setBounds(100, 100, 1072, 664);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        JButton load_button = new JButton("Load");
        load_button.setForeground(SystemColor.text);
        load_button.setBackground(SystemColor.windowText);
        load_button.setBounds(127, 0, 128, 25);
        frame.getContentPane().add(load_button);
        editor_area = new TextArea();
        editor_area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_CONTROL)
                editor_area.selectAll();
            }
        });
        editor_area.setBackground(Color.BLACK);
        editor_area.setForeground(SystemColor.window);
        editor_area.setFont(null);
        editor_area.setBounds(0, 25, 1068, 611);
        frame.getContentPane().add(editor_area);
        JPopupMenu popupMenu = new JPopupMenu();
        addPopup(editor_area, popupMenu);
        JMenuItem mntmWklej = new JMenuItem("Wklej");
        mntmWklej.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable s = clipboard.getContents(null);
                try {
                    editor_area.append( s.getTransferData(DataFlavor.stringFlavor).toString());
                    } catch (UnsupportedFlavorException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        popupMenu.add(mntmWklej);
        JMenuItem mntmKopiuj = new JMenuItem("Kopiuj");
        mntmKopiuj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection (editor_area.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
                clpbrd.setContents (stringSelection, null);
            }
        });
        popupMenu.add(mntmKopiuj);
        JButton about_file_button = new JButton("About File");
        about_file_button.setForeground(SystemColor.text);
        about_file_button.setBackground(SystemColor.textText);
        about_file_button.setBounds(372, 0, 128, 25);
        frame.getContentPane().add(about_file_button);
        JButton exit_button = new JButton("Exit");
        exit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exit_button.setBackground(SystemColor.textText);
        exit_button.setForeground(SystemColor.text);
        exit_button.setBounds(952, 0, 116, 25);
        frame.getContentPane().add(exit_button);
        JButton save_button = new JButton("Save");
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(file_read != null){
                    System.out.println(file_read.getName());
                    FileWriter b;
                    try {
                        b = new FileWriter(file_read.getAbsolutePath());
                        b.write(editor_area.getText());
                        b.close();
                        new Thread(() -> {  frame.setTitle("Saved"); try {
                                Thread.sleep(2000); frame.setTitle("Axedit " + file_read.getAbsolutePath());
                                } catch (Exception e1) {
                                e1.printStackTrace();
                        }  } ).start();
                        } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else{
                    JFileChooser a = new JFileChooser();
                    a.showSaveDialog(null);
                    file = a.getSelectedFile();
                    FileWriter b;
                    try {
                        b = new FileWriter(file);
                        b.write(editor_area.getText());
                        b.close();
                        } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        save_button.setForeground(Color.WHITE);
        save_button.setBackground(Color.BLACK);
        save_button.setBounds(0, 0, 128, 25);
        frame.getContentPane().add(save_button);
        editor_area.setForeground(SystemColor.green);
        JButton last_file_button = new JButton("Last file");
        last_file_button.setBackground(Color.BLACK);
        last_file_button.setForeground(Color.WHITE);
        last_file_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                last_file = file_read;
                frame.setTitle("Axedit " + file_read.getAbsolutePath());
                try{
                    List<String> tmp = new ArrayList<String>();
                    Scanner scanner = new Scanner(file_read);
                    while(scanner.hasNext()){
                        System.out.println(tmp.size());
                        tmp.add(scanner.nextLine());
                        tmp.add("\n");
                    }
                    scanner.close();
                    for(String zzz : tmp){
                        editor_area.append(zzz);
                    }
                }
                catch(Exception ex){
                    System.out.println("Error");
                }
            }
        });
        last_file_button.setBounds(251, 0, 133, 25);
        frame.getContentPane().add(last_file_button);
        JButton clear_button = new JButton("Clear");
        clear_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor_area.setText(null);
                file_read = null;
            }
        });
        clear_button.setBackground(Color.BLACK);
        clear_button.setForeground(Color.WHITE);
        clear_button.setBounds(487, 0, 147, 25);
        frame.getContentPane().add(clear_button);
        final JButton others_button = new JButton("Others");
        others_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                OTHERS_FRAME();
            }
        });
        others_button.setForeground(Color.WHITE);
        others_button.setBackground(Color.BLACK);
        others_button.setBounds(629, 0, 147, 25);
        frame.getContentPane().add(others_button);
        final JButton check_update_button = new JButton("Check Updates");
        check_update_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread( () -> {
                    try {
                        File file = new File("ver.dat");
                        FileUtils.copyURLToFile(new URL("http://evilnet.noip.me/JNotepad/ver.dat"), file);
                        Scanner a = new Scanner(file);
                        int teraz = a.nextInt();
                        a.close();
                        if(version == teraz){
                            FileUtils.forceDelete(file);
                            JOptionPane.showMessageDialog(null, "No need to update");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Not implemented");
                            FileUtils.forceDelete(file);
                        }
                        } catch (IOException e2) {
                        JOptionPane.showMessageDialog(check_update_button, "Error (possible 404)");
                    }
                }).start();
            }
        });
        check_update_button.setForeground(Color.WHITE);
        check_update_button.setBackground(Color.BLACK);
        check_update_button.setBounds(773, 0, 177, 25);
        frame.getContentPane().add(check_update_button);
        load_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                editor_area.setText(null);
                file_read = null;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                file_read = fileChooser.getSelectedFile();
                last_file = file_read;
                frame.setTitle("Axedit " + file_read.getAbsolutePath());
                try{
                    List<String> tmp = new ArrayList<String>();
                    Scanner scanner = new Scanner(file_read);
                    while(scanner.hasNext()){
                        System.out.println(tmp.size());
                        tmp.add(scanner.nextLine());
                        tmp.add("\n");
                    }
                    scanner.close();
                    for(String zzz : tmp){
                        editor_area.append(zzz);
                    }
                }
                catch(Exception ex){
                    System.out.println("Error");
                }
            }
        });
        about_file_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String info;
                try {
                    info = file_read.getPath() + "\n" + FileUtils.sizeOf(file_read)/1024 + " KB nMD5: " + DigestUtils.md5Hex(new FileInputStream(file_read));
                    if(info != null) JOptionPane.showMessageDialog(null, info);
                    } catch (IOException e1) {
                }
            }
        });
        frame.setVisible(true);
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
