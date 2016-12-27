package calculate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;


/**
 * @author F-zx
 * �����ÿ���ַ���Ҫռ�õ������������ʾÿһ�����ص���Ҫ�õ��ַ���//ȡASCII���33----125 92��
 * ȡû���ַ���sizeΪFONT_SIZE���������ͼƬ��widthΪFONT_SIZE*92
 */
public class CalculateCharArea {
	
	/**
	 * @author F-zx
	 * ��compareableʵ�֣�Ŀ�����ڶԸ���������򣬰���pixNum������ʵ���Բ�����������ֻ���븴ϰһ���ýӿڵ�ʵ��
	 */
	public static class CharArea implements Comparable<CharArea>{
		public int pixNum ;//���ַ����е���ɫ��ĸ���
		public char c;//���ַ�
		
		public CharArea(int pixNum , char c) {
			this.c = c;
			this.pixNum = pixNum;
		}
		
		

		@Override
		public int compareTo(CharArea o) {
			if(o.pixNum <= this.pixNum)
				return 1;
			else
				return -1;
		}
	}
	
	private final static int NUM = 92;
	private final static int HEIGHT = 50;
	private final static int FONT_SIZE = 50;
	private final static int WIDTH = FONT_SIZE * NUM ;
	
	private static StringBuffer buffer = null;
	private static File file = null;
	private static BufferedImage bImage  = null;
	
	private static CharArea[] chars = new CharArea[NUM];
	

	
	/** 
	 * ����һ���ַ�������
	 * @return
	 */
	private static String build(){
		buffer = new StringBuffer();
		
		//ȡASCII���33----125 92��
		for(int i = 33 ; i < 125 ; i++)
			buffer.append((char)i);
		
		return buffer.toString();
		
	}
	
	/**
	 * ���Ƴ���Ӧ��ͼƬ
	 */
	private static void paint(){
		file = new File("e://resources/char.jpg");	
		
		bImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_BYTE_BINARY);
		
		Font font = new Font(null, Font.PLAIN, FONT_SIZE);
		
		Graphics2D g = bImage.createGraphics();
		g.setBackground(Color.white);
		g.setColor(Color.black);
		g.setFont(font);
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		String strs = build();
		char[] c = strs.toCharArray();
		
		int i = 0 ;
		for (char d : c) {
			g.drawString(""+d, FONT_SIZE * ( i++ ), 40);
		}
		
		g.dispose();
		
		try {
			ImageIO.write(bImage, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
	}
	
	/**
	 * �����û���������Ȼ������һ�£��������û������size*size���Ĵ�С��������Ѱ����ɫ��ĸ�����Ȼ�����Щ������������һ�����ݽṹ,
	 * ps������ÿ���㣬-1��ʾ�õ�Ϊ��ɫ
	 * @throws IOException 
	 */
	private static void calculate() throws IOException{

		if(file == null)
			file = new File("e://resources/char.jpg");	
		if(bImage == null)
			bImage = ImageIO.read(file);
		
		int count = 0;//ÿ���ַ�����ɫ�����
		for(int j = 1 ; j <= NUM ; j ++){					//����ѭ��
			count = 0;
			chars[j-1] = new CharArea( 0 , (char) (j+32) ); //��Ϊ�ַ��Ǵ�33��ʼ�ģ�����j+32��Ӧ����33����ʼλ��
			for(int k = (j-1)*50 ; k < 50*j ; k++){			//û���ַ��Ŀ��ѭ��
				for(int m = 0 ; m < 50 ; m++ ){				//ÿ���ַ��ĸ߶�ѭ��
					int rgb = bImage.getRGB(k, m);
					
					count += (rgb != -1) ? 1: 0;
//					System.out.println(rgb); //���Ե����ɫ���
				}
			}
			chars[j-1].pixNum = count;
		}
	}
	
	/**
	 * @return ���ؼ���õ��ַ���
	 * @throws IOException
	 */
	public static CharArea[] getArrays() throws IOException{
		paint();
		calculate();
		Arrays.sort(chars);
		for (CharArea area : chars) {
			System.out.println(area.c+" "+area.pixNum);
		}
		
		return chars;
	}
	
	public static void main(String[] args) throws IOException {
		paint();
		calculate();
		
//		for (CharArea area : chars) {
//			System.out.println(area.c+" "+area.pixNum);
//		}
		
		Arrays.sort(chars);
		
		for (CharArea area : chars) {
			System.out.println(area.c+" "+area.pixNum);
		}
		
//		System.out.printf("%2s", "n");
	}
}

