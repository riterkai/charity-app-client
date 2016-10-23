package com.example.biglove.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class UpLoadUtil {
	 public static void uploadFile(int hdid, Context context, String actionUrl , String newName, File uploadFile)
     {
       String end ="\r\n";
       String twoHyphens ="--";
       String boundary ="*****";
       try
       {
         URL url =new URL(actionUrl);
         HttpURLConnection con=(HttpURLConnection)url.openConnection();
         /* 允许Input、Output，不使用Cache */
         con.setDoInput(true);
         con.setDoOutput(true);
         con.setUseCaches(false);
         /* 设置传送的method=POST */
         con.setRequestMethod("POST");
         /* setRequestProperty */
         con.setRequestProperty("Connection", "Keep-Alive");
         con.setRequestProperty("Charset", "UTF-8");
         con.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary="+boundary);
         /* 设置DataOutputStream */
         DataOutputStream ds =
           new DataOutputStream(con.getOutputStream());
         ds.writeBytes(twoHyphens + boundary + end);
         ds.writeBytes("Content-Disposi" +
         		"tion: form-data; "+
                       "name=\"file1\";filename=\""+
                       newName +"\""+ end);
         ds.writeByte(hdid);
         ds.writeBytes(end);  
         /* 取得文件的FileInputStream */
         FileInputStream fStream =new FileInputStream(uploadFile);
         /* 设置每次写入1024bytes */
         int bufferSize =1024;
         byte[] buffer =new byte[bufferSize];
         int length =-1;
         /* 从文件读取数据至缓冲区 */
         while((length = fStream.read(buffer)) !=-1)
         {
           /* 将资料写入DataOutputStream中 */
           ds.write(buffer, 0, length);
         }
         ds.writeBytes(end);
         ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
         /* close streams */
         fStream.close();
         ds.flush();
         /* 取得Response内容 */
         InputStream is = con.getInputStream();
         int ch;
         StringBuffer b =new StringBuffer();
         while( ( ch = is.read() ) !=-1 )
         {
           b.append( (char)ch );
         }
         /* 将Response显示于Dialog */
//         showDialog("上传成功"+b.toString().trim());
         /* 关闭DataOutputStream */
         Looper.prepare();
         Toast.makeText(context, "上传成功"+b.toString().trim(), Toast.LENGTH_SHORT).show();
         Looper.loop();
         Log.d(null,"上傳結果"+ b.toString().trim());
         ds.close();
       }
       catch(Exception e)
       {
    	   Looper.prepare();
    	   e.printStackTrace();
    	   Toast.makeText(context, "上传失败"+e, Toast.LENGTH_SHORT).show();
    	   Looper.loop();
//         showDialog("上传失败"+e);
       }
     }
}
