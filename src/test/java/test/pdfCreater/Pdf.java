package test.pdfCreater;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.neuedu.pojo.Word;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Pdf {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(PageSize.B5);
        Document document=new Document(rect);
        document.setMargins(13, 13, 5, 5);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\zxy\\Desktop\\resouces\\pdftest.pdf"));
            document.open();
            BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font blueFont = new Font(bfChinese);
            blueFont.setColor(BaseColor.BLUE);


            //绿色字体
            Font greenFont = new Font(bfChinese);
            greenFont.setColor(BaseColor.GREEN);
            greenFont.setStyle(2);

            Word word=new Word();
            word.setSpell("test");
            word.setPhonetic("test");
            word.setParaphrase("v.动词;n.名词");
            word.setSentence("adasdfasdfsadfsadfasdffsd sadfasdfasfasdf 我哇撒打发时间和地方还是发哈撒旦发射点");

            Paragraph chapterTitle = new Paragraph("段落标题111", greenFont);
            chapterTitle.add(new Chunk(new LineSeparator()));
            Chapter chapter1 = new Chapter(chapterTitle, 1);
            chapter1.setNumberDepth(0);
            for(int i=0;i<20;i++){
                Chunk chunk=new Chunk(word.getSpell(),blueFont);
                Chunk chunk1=new Chunk(word.getPhonetic(),greenFont);
                Paragraph sectionTitle = new Paragraph();
                sectionTitle.add(chunk);
                sectionTitle.add(chunk1);
                Section section1 = chapter1.addSection(sectionTitle);
                Paragraph sectionContent = new Paragraph(word.getParaphrase(), blueFont);
                section1.add(sectionContent);
                Paragraph sentence=new Paragraph(word.getSentence(),blueFont);
                section1.add(sentence);

            }
             //将章节添加到文章中
             document.add(chapter1);
             //关闭文档
             document.close();
             //关闭书写器
             writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PdfReader reader = new PdfReader("C:\\Users\\zxy\\Desktop\\resouces\\pdftest.pdf");
            PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("C:\\Users\\zxy\\Desktop\\resouces\\outpdftest.pdf"));
            Image img = Image.getInstance("C:\\Users\\zxy\\Desktop\\resouces\\back.jpg");

            img.setAbsolutePosition(12, 12);
            PdfContentByte under2 = stamp.getUnderContent(1);
            under2.addImage(img);
            stamp.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
