import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by donar on 16/6/8.
 */
public class ConvertUtils {
    public String quiverMD2gitblog(ArticleInfo articleInfo, String dir) throws Exception {
        File dirf = new File(dir);
        if (!dirf.exists()) throw new Exception("目录不存在");
        File[] files = dirf.listFiles();
        for (File f : files) {
            //处理markdown文件
            if (f.getName().endsWith(".md")) {
                File dest = new File(dirf.getAbsolutePath() + "/" + CharacterParser.getInstance().getSpelling(f.getName()).replace(" ", ""));
                BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
                BufferedReader reader = new BufferedReader(new FileReader(f));
                //跳过第一行
                reader.readLine();
                //写入元数据
                writer.write(generateHead(articleInfo));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                reader.close();
                writer.close();
            }
            f.delete();
        }
        int i = dir.lastIndexOf("/");
        String a = dir.substring(0, i);
        String b = dir.substring(i, dir.length());
        String newDir = a + CharacterParser.getInstance().getSpelling(b).replace(" ", "");
        dirf.renameTo(new File(newDir));
        return newDir;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public String generateHead(ArticleInfo articleInfo) {
        StringBuffer sb = new StringBuffer();

//         <!--
//         author: donar
//         head: http://pingodata.qiniudn.com/jockchou-avatar.jpg
//         date: 2016-04-28
//         title: Alfred+expect直连项目服务器
//         tags: GitBlog
//         category: GitBlog
//         status: publish
//         summary: GitBlog是一个简单易用的Markdown博客系统，它不需要数据库，没有管理后台功能，更新博客只需要添加你写好的Markdown文件即可。
//         -->

        sb.append("<!--\n");
        sb.append("  author:").append(articleInfo.getAuthor()).append("\n");
        sb.append("  head:").append(articleInfo.getHead()).append("\n");
        sb.append("  date:").append(format.format(articleInfo.getDate())).append("\n");
        sb.append("  title:").append(articleInfo.getTitle()).append("\n");
        sb.append("  tags:").append(articleInfo.getTags()).append("\n");
        sb.append("  category:").append(articleInfo.getCategory()).append("\n");
        sb.append("  status:").append(articleInfo.getStatus()).append("\n");
        sb.append("  summary:").append(articleInfo.getSummary()).append("\n");
        sb.append("-->\n");

        return sb.toString();

    }

    public void upload(ArticleInfo articleInfo, String sourceDir) throws Exception {

        String dir = quiverMD2gitblog(articleInfo, sourceDir);
        String resources = dir + "/resources";
        File[] res;
        File file = new File(resources);
        if (!file.exists()) {
            res = null;
        } else {
            res = file.listFiles();
        }
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (!name.contains("md"))
                    return false;
                return true;
            }
        };
        UploadUtil.upload(res, dir.substring(dir.lastIndexOf("/") + 1), new File(dir).listFiles(filenameFilter));
        File file2Del=new File(dir);
        deleteFile(file2Del);
    }

    public void deleteFile(File file){
        if(!file.isDirectory()){
            file.delete();
        }else{
            for(File f:file.listFiles()){
                deleteFile(f);
            }
        }
    }
}
