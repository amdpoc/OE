package ilink.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.net.URLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PDFConverter {
    protected final Log logger = LogFactory.getLog(getClass());
    private String outputPath;

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String convertPDF(String pdfPath) {
        String url = "";
        logger.debug("################ Start getAdocsPDF ----> " + (new java.util.Date()).toString());
        logger.debug("################ getAdocsPDF input ----> pdfPath: " + pdfPath);
        try {
            url = convertPDFtoGif(pdfPath);
        }
        catch (Exception ie) {
            logger.error("################ Error getAdocsPDF ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getAdocsPDF ----> " + (new java.util.Date()).toString());
        return url;
    }

    private String convertPDFtoGif(String pdfUrl) {
        String sResult = "";
        int startIndex = pdfUrl.lastIndexOf("/") + 1;
        String fileName = pdfUrl.substring(startIndex, pdfUrl.length() - 4);
        logger.debug("################ Start convertPDFtoGif :: pdfUrl = " + pdfUrl);
        //File pdfFile = new File(pdfUrl);
        try {
            //RandomAccessFile raf = new RandomAccessFile(pdfFile, "r");
            //FileChannel channel = raf.getChannel();
            //ByteBuffer bbuf = channel.map(FileChannel.MapMode.   READ_ONLY, 0, channel.size());
            ByteBuffer bbuf = getFileByteBuffer(pdfUrl);
            PDFFile pdff = new PDFFile(bbuf);

            int pagesL = pdff.getNumPages();

            for (int i = 1; i <= pagesL; i++) {
                PDFPage page = pdff.getPage(i);
                Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
                BufferedImage bufImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);

                Image image = page.getImage(rect.width, rect.height, rect, null, true, true);
                Graphics2D bufImageGraphics = bufImage.createGraphics();
                bufImageGraphics.drawImage(image, 0, 0, null);
                ImageIO.write(bufImage, "gif", new File(outputPath + fileName + "_" + i + ".gif"));
                sResult += (i == 1 ? "[\"" : ",\"") + fileName + "_" + i + ".gif\"";
            }
            sResult += "]";

        }
        catch (FileNotFoundException e) {
            logger.error("############# Error FileNotFoundException ----> " + e.getMessage());
            sResult = "[]";
        }
        catch (Exception e) {
            logger.error("############## Error IOException ----> " + e.getMessage());
            sResult = "[]";
        }
        logger.info("################ convertPDFtoGif :: pdfUrl = " + pdfUrl + " with result: " + sResult);        
        return sResult;
    }

    private String getPathToImages(String index) {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
        path = path.substring(0, path.indexOf(index));
        return path;
    }

    public ByteBuffer getFileByteBuffer(String pdfUrl) throws Exception {
        
        File file = new File(pdfUrl);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                // Writes len bytes from the specified byte array starting at
                // offset off to this byte array output stream.
            }
        } catch (IOException ex) {
            logger.error("################ ERROR PDFConverter: getFileByteBuffer() -> exception occured :" + ex.getMessage());
            throw ex;
        }
         byte[] array = bos.toByteArray();

        //Lines below used to test if file is corrupt
        //FileOutputStream fos = new FileOutputStream("C:\\abc.pdf");
        //fos.write(array);
        //fos.close();

        return ByteBuffer.wrap(array);
    }
    public ByteBuffer getFileByteBuffer2(String pdfUrl) throws Exception {
        URL url = new URL(pdfUrl);
        URLConnection connection = url.openConnection();
        // Since you get a URLConnection, use it to get the InputStream
        InputStream in = connection.getInputStream();
        // Now that the InputStream is open, get the content length
        int contentLength = connection.getContentLength();

        // To avoid having to resize the array over and over and over as
        // bytes are written to the array, provide an accurate estimate of
        // the ultimate size of the byte array
        ByteArrayOutputStream tmpOut;
        if (contentLength != -1) {
            tmpOut = new ByteArrayOutputStream(contentLength);
        } else {
            tmpOut = new ByteArrayOutputStream(16384); // Pick some appropriate size
        }

        byte[] buf = new byte[512];
        while (true) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }
            tmpOut.write(buf, 0, len);
        }
        in.close();
        tmpOut.close(); // No effect, but good to do anyway to keep the metaphor alive

        byte[] array = tmpOut.toByteArray();

        //Lines below used to test if file is corrupt
        //FileOutputStream fos = new FileOutputStream("C:\\abc.pdf");
        //fos.write(array);
        //fos.close();

        return ByteBuffer.wrap(array);
    }
}
