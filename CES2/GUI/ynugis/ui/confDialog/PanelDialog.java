package ynugis.ui.confDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.OutputStream;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PanelDialog extends JPanel implements Printable {

	private double[] Dou;

	private String[] cellVal;

	private double[] cellSum;

	private double cellTol;

	private String[] Str;

	private int intDuan;

	private final String ss = "-";

	private final String ss1 = ":";

	public PanelDialog(double[] d, String[] S) {
		super();
		// TODO Auto-generated constructor stub
		Dou = d;
		Str = S;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawPage(g2);
	}

	public void drawPage(Graphics2D g2) {

		// super.paintComponent(g);
		// Graphics2D g2=(Graphics2D)g;
		int strNum = Str.length;
		int douNum = Dou.length;
		cellVal = new String[strNum + 1];
		double cellMax = Dou[0];
		cellVal[strNum] = (String.valueOf(cellMax)).substring(0, 5);
		cellSum = new double[strNum];
		cellTol = 0;
		for (int i = 0; i < strNum; i++) {
			String strCell = Str[i];
			int j = strCell.indexOf(ss, 1);
			int k = strCell.indexOf(ss1, 1);
			// System.out.println(strCell.substring(k+1,strCell.length()));
			cellSum[i] = Double.parseDouble(strCell.substring(k + 1, strCell
					.length()));
			cellTol = cellTol + cellSum[i];
			// System.out.println(cellTol);
			// System.out.println(j);
			// // String strNu=strCell.substring(1,j);;
			// System.out.println("sub:"+strCell.substring(0,j));
			cellVal[i] = strCell.substring(0, j);
			if (cellVal[i].length() > 5) {
				cellVal[i] = cellVal[i].substring(0, 5);
			}

			// System.out.println(cellVal[i]);
		}
		intDuan = (int) (300 / strNum);
		System.out.println(strNum);
		System.out.println(strNum);
		int leftX = 100;
		int topY = 100;
		int width = 200;
		int height = 200;
		double everyCellVal = cellTol / 10;

		// g2.drawRect(leftX,topY,width/4,height/4);
		if (douNum == 2) {
			// double cellMax=Dou[0];
			// double cellMin=Dou[1];
			g2.drawLine(30, 250, 360, 250);
			g2.drawLine(355, 247, 360, 250);
			g2.drawLine(355, 253, 360, 250);
			g2.drawString("统计值", 20, 20);
			g2.drawLine(30, 30, 30, 250);
			g2.drawLine(27, 35, 30, 30);
			g2.drawLine(33, 35, 30, 30);
			g2.drawString("分值", 360, 250);

			for (int i = 0; i < strNum + 1; i++) {

				// g2.drawRect(50+i*intDuan,(int)250-20*(cellSum[i]/cellTol),intDuan-10,(int)20*(cellSum[i]/cellTol));
				g2.drawString(cellVal[i], 30 + i * intDuan, 260);
				g2.drawLine(30 + i * intDuan, 250, 30 + i * intDuan, 245);
			}

			for (int i = 0; i < 11; i++) {
				g2.drawLine(30, 250 - 20 * i, 35, 250 - 20 * i);
				g2.drawString(String.valueOf(everyCellVal * i), 15,
						250 - 20 * i);
			}
			for (int i = 0; i < strNum; i++) {
				System.out.println("开始画直方图");
				System.out.println(cellSum[i]);
				System.out.println(cellTol);
				double topYY = 200 * (cellSum[i] / cellTol);
				int topY1 = (int) topYY;
				System.out.println(topYY);
				System.out.println(topY1);
				// g2.setPaint(new Color(256-i*15,256,256-i*10));
				// g2.drawRect(50+i*intDuan,250-topY1,intDuan-10,topY1);
				Rectangle2D rect = new Rectangle2D.Double((double) (30 + i
						* intDuan + 5), (double) (250 - topY1),
						(double) (intDuan - 10), (double) (topY1));
				g2.setPaint(Color.RED);
				// g2.setPaint();
				g2.fill(rect);
			}
		}

	}

	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		// TODO Auto-generated method stub
		if (page >= 1) {
			return Printable.NO_SUCH_PAGE;

		}
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(pf.getImageableX(), pf.getImageableY());
		g2.draw(new Rectangle2D.Double(0, 0, pf.getImageableWidth(), pf
				.getImageableHeight()));
		drawPage(g2);
		return Printable.PAGE_EXISTS;
	}

	private void write(JComponent myComponent, OutputStream out)
			throws Exception {
		int imgWidth = (int) myComponent.getSize().getWidth(), imgHeight = (int) myComponent
				.getSize().getHeight();
		Dimension size = new Dimension(imgWidth, imgHeight);
		BufferedImage myImage = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = myImage.createGraphics();
		myComponent.paint(g2);
		try {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(myImage);
			out.close();
		} catch (Exception e) {
			throw new Exception("GRAPHICS ERROR,CANNOT CREATE JPEG FORMAT");
		}
	}

}
