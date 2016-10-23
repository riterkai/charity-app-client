package com.example.biglove.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpLoadUtil2 {
	
	
	
	    TextView messageText;
	    Button uploadButton;
	    static int serverResponseCode = 0;
	    static ProgressDialog dialog = null;
		private static String serverResponseMessage;
	        
	    String upLoadServerUri = null;
	     
	    /**********  File Path *************/
	    final String uploadFilePath = "/mnt/sdcard/";
	    final String uploadFileName = "qq.png";
	
	
	public static String uploadFile(int hdid ,String upLoadServerUri,File sourceFile,int Flag) {
		String fileName;
        if(Flag == 1){
          fileName = "h"+String.valueOf(hdid)+".png";
        }else{
          fileName = "a"+String.valueOf(hdid)+".png";	
        }
        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        //File sourceFile = new File(sourceFileUri); 
         
            try { 
                  
                   // open a URL connection to the Servlet
                 FileInputStream fileInputStream = new FileInputStream(sourceFile);
                 URL url = new URL(upLoadServerUri);
                  
                 // Open a HTTP  connection to  the URL
                 conn = (HttpURLConnection) url.openConnection(); 
                 conn.setDoInput(true); // Allow Inputs
                 conn.setDoOutput(true); // Allow Outputs
                 conn.setUseCaches(false); // Don't use a Cached Copy
                 conn.setRequestMethod("POST");
                 conn.setRequestProperty("Connection", "Keep-Alive");
                 conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                 conn.setRequestProperty("uploaded_file", fileName); 
                  
                 dos = new DataOutputStream(conn.getOutputStream());
        
                 dos.writeBytes("Content-Disposition: form-data; name=\"username\"\r\n\r\n");
                 dos.writeBytes(String.valueOf(hdid)+"\r\n");
                 //dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                 
                 
                 dos.writeBytes(twoHyphens + boundary + lineEnd); 
                 dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
                 
                 dos.writeBytes(lineEnd);
                 
              
                 // create a buffer of  maximum size
                 bytesAvailable = fileInputStream.available(); 
        
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 buffer = new byte[bufferSize];
        
                 // read file and write it into form...
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                    
                 while (bytesRead > 0) {
                      
                   dos.write(buffer, 0, bufferSize);
                   bytesAvailable = fileInputStream.available();
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                    
                  }
        
                 // send multipart form data necesssary after file data...
                 dos.writeBytes(lineEnd);
                 dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                  
                
                 // Responses from the server (code and message)
                 serverResponseCode = conn.getResponseCode();
                 serverResponseMessage = conn.getResponseMessage();
                   
                 Log.i("uploadFile", "HTTP Response is : "
                         + serverResponseMessage + ": " + serverResponseCode);
                  
//                 if(serverResponseCode == 200){
//                      
//                     runOnUiThread(new Runnable() {
//                          public void run() {
//                               
//                              String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//                                            +" http://www.androidexample.com/media/uploads/"
//                                            +uploadFileName;
//                               
//                              messageText.setText(msg);
////                              Toast.makeText(this, "File Upload Complete.", 
////                                           Toast.LENGTH_SHORT).show();
//                          }
//                      });                
//                 }    
                  
                 //close the streams //
                 fileInputStream.close();
                 dos.flush();
                 dos.close();
                   
            } catch (MalformedURLException ex) {
                 
                dialog.dismiss();  
                ex.printStackTrace();
                 
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        messageText.setText("MalformedURLException Exception : check script url.");
////                        Toast.makeText(MainActivity.this, "MalformedURLException", 
////                                                            Toast.LENGTH_SHORT).show();
//                    }
               // }
          //  );
                 
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {
                 
                dialog.dismiss();  
                e.printStackTrace();
                 
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
////                        Toast.makeText(MainActivity.this, "Got Exception : see logcat ", 
////                                Toast.LENGTH_SHORT).show();
//                    }
//                });
                Log.e("Upload file to server Exception", "Exception : "
                                                 + e.getMessage(), e);  
            }
           // dialog.dismiss();       
            return serverResponseMessage; 
             
         } // End else block 
        
}
