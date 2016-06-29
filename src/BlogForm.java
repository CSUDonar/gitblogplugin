import constant.CategoryEnum;
import constant.StatusEnum;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by donar on 16/6/8.
 */
public class BlogForm {
    static JFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panel8;
    private JPanel panel9;
    private JTextField author;
    private JComboBox<String> category;
    private JTextField title;
    private JTextField tags;
    private JTextField dir;
    private JComboBox<String> status;
    private JTextField summary;
    private JButton publish;
    private JButton watch;
    private JButton select;
    private JList logs;
    private String authorstr;
    private String categorystr;
    private String titlestr;
    private String tagsstr;
    private String dirstr;
    private String statusstr;
    private String summarystr;

    private Vector<String> loglist=new Vector<>();
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void updateLog(String string){
        loglist.add(format.format(new Date())+"\t"+string);
        logs.setListData(loglist);
    }
    public BlogForm(){
        author.setText("donar");
        for(CategoryEnum categoryEnum:CategoryEnum.values()){
            category.addItem(categoryEnum.toString());
        }
        for(StatusEnum statusEnum:StatusEnum.values()){
            status.addItem(statusEnum.toString());
        }
        publish.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(check()){
                    ArticleInfo articleInfo=new ArticleInfo()
                    .setTitle(titlestr)
                    .setSummary(summarystr)
                    .setStatus(statusstr)
                    .setTags(tagsstr)
                    .setAuthor(authorstr).setCategory(categorystr)
                    .setHead("http://www.easyicon.net/api/resizeApi.php?id=556429&size=128")
                    .setDate(new Date());
                    try {
                        updateLog("准备上传");
                        new ConvertUtils().upload(articleInfo, dirstr.trim());
                        updateLog("上传成功");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        updateLog("上传出错"+e1.getMessage());
                    }
                }else {
                    System.out.println("信息不完整");
                }
            }
        });


        select.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JFileChooser fileChooser=new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showDialog(frame, null);
                File file =fileChooser.getSelectedFile();
                dir.setText(file.getAbsolutePath());
                for(File f:file.listFiles()){
                    if(f.getName().endsWith(".md")){;
                        BufferedReader br = null;
                        try {
                            br=new BufferedReader(new FileReader(f));
                            String t=br.readLine();
                            title.setText(t.replace("#",""));
                            br.close();
                        } catch (Exception e1) {
                            System.out.println("读取文章标题异常");
                        }
                    }
                }
            }
        });


    }

    public boolean check(){
        authorstr =author.getText();
        categorystr=(String)category.getSelectedItem();
        titlestr=title.getText();
        tagsstr=tags.getText();
        dirstr=dir.getText();
        statusstr=(String)status.getSelectedItem();
        summarystr=summary.getText();
        if(authorstr.trim()!=""&
                categorystr.trim()!=""&
                titlestr.trim()!=""&
                tagsstr.trim()!=""&
                dirstr.trim()!=""&
                statusstr.trim()!=""&
                summarystr.trim()!="")return true;
        else return false;
    }
    public static void main(String[] args) {
        JFrame frame= new JFrame("BlogForm");
        frame.setContentPane(new BlogForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        BlogForm.frame=frame;
    }
}
