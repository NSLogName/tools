package com.xcty.tools.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PicCompression {

	public static String imageZipProce(String oldFile) {
	    int width = 200;
	    int height = 333;
		return imageZipProce(oldFile, width, height, (float) 0.7);
	}

	public static boolean checkSize(int width, int height) {
		return width == 480 && height == 800;
	}
	
	
	public static void main(String[] args) {
//		imageZipProce("D:/14.jpg");
		imageCompressAndCut("C:/Users/Administrator/Desktop/14.jpg", 200, 200, 1);
	}

	/**
	 * 压缩图片方法
	 *
	 * @param oldFile 将要压缩的图片
	 * @param width   压缩宽
	 * @param height  压缩长
	 * @param quality 压缩清晰度 <b>建议为1.0</b>
	 * @return
	 */
	public static String imageZipProce(String oldFile, int width, int height, float quality) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			File file = new File(oldFile);
			//文件不存在时
			if (!file.exists()) return null;
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(file);
			int new_w = 0, new_h = 0;
			//获取图片的实际大小 高度
			int h = (int) srcFile.getHeight(null);
			//获取图片的实际大小 宽度
			int w = (int) srcFile.getWidth(null);

//            if(!checkSize(w,h)){
//            	return "2";
//            }

			// 为等比缩放计算输出的图片宽度及高度
//            if((((double)w) >(double)width)||(((double)h)>(double) height))
//            {
			double rate = 0;//算出图片比例值
			//宽度大于等于高度
			if (w >= h) {
				rate = ((double) w) / (double) width;
			}
			//宽度小于高度
			else if (h > w) {
				rate = ((double) h) / (double) height;
			}
			//构造新的比例的图片高度与宽度值
			new_w = (int) (((double) w) / rate);
			new_h = (int) (((double) h) / rate);
			/* 压缩后的文件名 */
			String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.')) + "_small";
			String suffix = oldFile.substring(oldFile.lastIndexOf('.') + 1);
			newImage = filePrex + "." + suffix;
			/* 设置新图片参数 */
			BufferedImage src = ImageIO.read(new FileImageInputStream(file));
			BufferedImage tag = new BufferedImage(width, height, src.getType());
			tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
			/* 生成压缩后图片 */
			FileImageOutputStream out = new FileImageOutputStream(new File(newImage));
			ImageWriter imageWriter = ImageIO.getImageWritersBySuffix(suffix).next();
			ImageWriteParam param = imageWriter.getDefaultWriteParam();
			if (param.canWriteCompressed()) {
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionType(param.getCompressionTypes()[0]);
				param.setCompressionQuality(quality);
			}
			imageWriter.setOutput(out);
			IIOImage iio_image = new IIOImage(tag, null, null);
			imageWriter.write(null, iio_image, param);
			imageWriter.dispose();
			srcFile.flush();
			out.close();
//            }
		} catch (FileNotFoundException e) {
			LoggerUtils.getLogger().error(e.getMessage(), e);
			return "";
		} catch (IOException e) {
			LoggerUtils.getLogger().error(e.getMessage(), e);
			return "";
		}
		return newImage;
	}

	/**
	 * 
	 * @Title: imageZipProceAndCut
	 * @Description: 压缩图片并裁剪为正方形
	 * @param     
	 *
	 * @return    返回类型
	 */
	public static String imageCompressAndCut(String oldFile, int width, int height, float quality){
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			File file = new File(oldFile);
			//文件不存在时
			if (!file.exists()) return null;
			
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(file);
			/** 压缩后宽度  */
			int new_w = 0, new_h = 0;
			/** 坐标起点  */
			int x = 0, y = 0;
			/**获取图片的实际大小 宽度*/
			int w = (int) srcFile.getWidth(null);
			/**获取图片的实际大小 高度*/
			int h = (int) srcFile.getHeight(null);
			double rate = 0;//算出图片比例值
			if(w >= h){
				rate = ((double) h) / (double) height;
				new_w = (int) (((double) w) / rate);
				new_h = height;
				x = (new_w - new_h)/2;
			}else if(h > w){
				rate = ((double) w) / (double) width;
				new_w = width;
				new_h = (int) (((double) h) / rate);
				y = (new_h - new_w)/2;
			}
			newImage = drawCompressImage(oldFile, srcFile, file, new_w, new_h, x, y, quality);
			
		} catch (FileNotFoundException e) {
			LoggerUtils.getLogger().error(e.getMessage(), e);
			return "";
		} catch (IOException e) {
			LoggerUtils.getLogger().error(e.getMessage(), e);
			return "";
		}
		return newImage;
	}

	/**
	 * 
	 * @Title: drawCompressImage
	 * @Description: 生成压缩图片并裁剪<br/>
	 *  1、Graphics 类包含绘制矩形的方法<br/>
	 *  2、Rectangle 类作为拖拉的矩形区域以作剪裁<br/>
	 * @param oldFile  原始图片地址  
	 * @param srcFile  原始图片Image对象  
	 * @param file     原始图片文件
	 * @param width    等比缩放的宽度
	 * @param height   等比缩放的高度 
	 * @param x        裁剪时起始X坐标
	 * @param y        裁剪时起始Y坐标
	 * @param quality  压缩质量  
	 *
	 * @return    
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static String drawCompressImage(String oldFile, Image srcFile, File file, int width, int height, int x, int y, float quality) throws FileNotFoundException, IOException {
		String newImage = "";
		/* 压缩后的文件名 */
		String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.')) + "_small";
		String suffix = oldFile.substring(oldFile.lastIndexOf('.') + 1);
		newImage = filePrex + "." + suffix;
		/* 设置新图片参数 */
		BufferedImage src = ImageIO.read(new FileImageInputStream(file));
		int imageType = src.getType();
		
		/*压缩后的图片裁剪成正方形*/
		Rectangle rect = null;
		if(width >= height){
			rect = new Rectangle(x, 0, height, height);
		}else{
			rect = new Rectangle(0, y, width, width);
		}
		//按比例缩放后的图片尺寸
		BufferedImage tag = new BufferedImage(width, height, imageType);
		
		//在缩放后的矩形区域内绘制图片
		tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
		
		/** 生成压缩后图片 */
		FileImageOutputStream out = new FileImageOutputStream(new File(newImage));
		ImageWriter imageWriter = ImageIO.getImageWritersBySuffix(suffix).next();
		ImageWriteParam param = imageWriter.getDefaultWriteParam();
		//设置裁剪参数
		param.setSourceRegion(rect);
		if (param.canWriteCompressed()) {
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionType(param.getCompressionTypes()[0]);
			param.setCompressionQuality(quality);
		}
		imageWriter.setOutput(out);
		IIOImage iio_image = new IIOImage(tag, null, null);
		imageWriter.write(null, iio_image, param);
		imageWriter.dispose();
		srcFile.flush();
		out.close();
		return newImage;
	}
	
}  