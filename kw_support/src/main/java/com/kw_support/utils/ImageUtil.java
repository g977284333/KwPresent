package com.kw_support.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

import com.kw_support.constants.LibConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * @version 1.0
 * @author: gechen
 * @discription:	ImageUtil
 * @date：2014-11-23 9:35:17
 * 
 */
public class ImageUtil {

	public static byte[] bitmapToByte(Bitmap bitmap) {
		if(null == bitmap) {
			return null;
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}
	
	public static Bitmap byteToBitmap(byte[] b) {
		return (null == b || 0 == b.length) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
	}
	
	public static Bitmap drawableToBitmap(Drawable d) {
		return null == d ? null : ((BitmapDrawable)d).getBitmap();
	}
	
	public static Bitmap drawable2Bitmap(Drawable d) {
		int width = d.getIntrinsicWidth();
		int height = d.getIntrinsicHeight();
		
		Bitmap bm = Bitmap.createBitmap(width, height,
				d.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.ARGB_4444);
		Canvas canvas = new Canvas(bm);
		d.setBounds(0, 0, width, height);
		d.draw(canvas);
		return bm;
	}
	
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return null == bitmap ? null : new BitmapDrawable(bitmap);
	}
	
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}
	
	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}
	
	public static Bitmap zoomImageTo(Bitmap bitmap, float newWidth, float newHeight) {
		return zoomImage(bitmap, newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
	}
	
	public static Bitmap zoomImage(Bitmap bitmap, float scaleWidth, float scaleHeight) {
		if(null == bitmap) {
			return bitmap;
		}
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/**
	 * 将图片设置为圆角
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int corner) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		int color = 0xff424242;
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, width, height);
		RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, corner, corner, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return result;
	}

	public static String compressImage(Context context, String filePath, int quality) {
		String result = context.getFilesDir().toString() + "/temp/upload.jpg";
		Bitmap bitmap = loadAndAdjustBitmap(filePath);
		saveBitmap(bitmap, result, quality);
		bitmap.recycle();
		return result;
	}

	public static Bitmap loadAndAdjustBitmap(String filePath) {
		Bitmap bitmap = loadBitmap(filePath);
		if (bitmap == null)
			return null;

		int degree = readPictureDegree(filePath);
		if (degree == 0)
			return bitmap;

		Bitmap adjustBitmap = rotateBitmap(bitmap, degree);
		bitmap.recycle();
		return adjustBitmap;
	}

	public static Bitmap loadBitmap(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		int maxLength = Math.max(options.outWidth, options.outHeight);

		int scale = 1;
		while (maxLength / scale > 1024)
			scale *= 2;

		Bitmap bitmap = null;
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;

		try {
			bitmap = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public static boolean saveBitmap(Bitmap bitmap, String filePath, int quality) {
		int index = filePath.lastIndexOf("/");
		File fileDir = new File(filePath.substring(0, index));
		if (!fileDir.exists() && !fileDir.mkdirs())
			return false;

		try {
			File file = new File(filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(file, false);
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int readPictureDegree(String filePath) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(filePath);
			String tag = ExifInterface.TAG_ORIENTATION;
			int value = ExifInterface.ORIENTATION_NORMAL;
			int orientation = exifInterface.getAttributeInt(tag, value);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

    /**
     * 压缩图片
     * @param activity 用于获取手机屏幕分辨率
     * @param srcPic 原始图片地址
     * @param path 压缩后的图片存放路径
     * @return 压缩后的图片地址
     */
    public static String compressPicture(Activity activity, String srcPic, String path) {
        Display display = null;
        int reqWidth = 0;
        int reqHeight = 0;
        Bitmap tagBitmap = null;
        String tagPath = null;
        int maxFileSize = 100;  // 100kb

        try {
            // 获取当前手机分辨率
            display = activity.getWindowManager().getDefaultDisplay();
            reqWidth = display.getWidth();
            reqHeight = display.getHeight();
            // 压缩图片尺寸
            tagBitmap = compressBitmap(srcPic, reqWidth, reqHeight);
            // 压缩图片质量
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + path;
            tagPath = compressBitmapToFile(tagBitmap, path, UUID.randomUUID().toString(), maxFileSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return tagPath;
    }

    public static boolean recycleBitmap(Bitmap bitmap) {
        try {
            if(null != bitmap && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	public static Bitmap compressBitmap(String filePath, int newWidth, int newHeight) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		
		opts.inSampleSize = calculateInSamepleSize(opts, newWidth, newHeight);
		
		opts.inJustDecodeBounds = false;
        opts.inInputShareable = true;   // 当系统内存不够时候图片自动被回收

		return BitmapFactory.decodeFile(filePath, opts);
	}

	private static int calculateInSamepleSize(Options opts, int newWidth,
			int newHeight) {
		int oldWidth = opts.outWidth;
		int oldHeight = opts.outHeight;
		int inSampleSize = 1;
		
		if(oldWidth > newWidth || oldHeight > newHeight) {
			int widhtRadio = oldWidth / newWidth;
			int heightRadio = oldHeight / newHeight;
			inSampleSize = widhtRadio > heightRadio ? widhtRadio : heightRadio;
		}
		return inSampleSize;
	}
	
	/**
	 * 压缩图片到100kb并保存到sd卡中 有损
	 */
	public static String compressBitmapToFile(final String filePath) {
		if(TextUtils.isEmpty(filePath)) {
			return null;
		}
		final File file = new File(LibConfig.PATH_IMAGE
				+ "small_pic" + UUID.randomUUID() + ".png");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Bitmap decBitmap = BitmapFactory.decodeFile(filePath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					decBitmap.compress(CompressFormat.JPEG, 80, baos);
					int option = 100;
					while( baos.toByteArray().length / 1024 > 100) { // 如果大于100kb则继续压缩
						baos.reset();
						decBitmap.compress(CompressFormat.JPEG, option, baos);
						if(option < 20) {
							break;
						} else {
							option -= 10;
						}
					}
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(baos.toByteArray());
					fos.flush();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return filePath;
	}

    /**
     *  将bitmap保存到指定目录下 有损压缩
     */
    public static String compressBitmapToFile(Bitmap bitmap, String path, String preFixName, int maxFileSize) {
        if (bitmap == null || TextUtils.isEmpty(path) || TextUtils.isEmpty(preFixName)) {
            return null;
        }
        File file = null;
        RandomAccessFile accessFile = null;
        ByteArrayOutputStream steam = null;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        path = path + "/" + preFixName + ".jpg";
        file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                steam = new ByteArrayOutputStream();

                int option = 100;
                bitmap.compress(CompressFormat.JPEG, option, steam);
                while (steam.toByteArray().length / 1024 > maxFileSize) {
                    if (option <= 30) {       // 最低压缩质量为30
                        break;
                    } else {
                        option -= 10;
                    }
                    steam.reset();
                    bitmap.compress(CompressFormat.JPEG, option, steam);
                }
                byte[] buffer = steam.toByteArray();

                accessFile = new RandomAccessFile(file, "rw");
                accessFile.write(buffer); }
            catch (Exception e){
                e.printStackTrace();
                return null;
            } finally {
                try {
                    steam.close();
                    accessFile.close();
                    recycleBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getPath();
    }

    public static Bitmap clipBitmapByScale(Bitmap tarBitmap, float scale) {
        if(null == tarBitmap) {
            return null;
        }

        try {
            if(null != tarBitmap) {
                int tarHeight = tarBitmap.getHeight();
                int tarWidth = tarBitmap.getWidth();

                if(tarWidth  * scale < tarHeight) {
                    int newHeight = (int) (tarWidth * scale);
                    int rectTop = (tarHeight - newHeight) / 2;
                    tarBitmap = Bitmap.createBitmap(tarBitmap,0, rectTop, tarWidth, rectTop + newHeight);
                } else {
                    int newWidth = (int) (tarHeight / scale);
                    int rectLeft = (tarWidth - newWidth) / 2;
                    tarBitmap = Bitmap.createBitmap(tarBitmap, rectLeft, 0, rectLeft + newWidth, tarHeight);
                }
            }
            return tarBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		final int color = 0xff424242;
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static Bitmap getRoundCornerBitmap(Context context, int color) {
		if(null == context) {
			return null;
		}
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = Math.round(UiUtil.applyDimension(
				UiUtil.COMPLEX_UNIT_DIP, 12.0f, metrics));
		int height = Math.round(UiUtil.applyDimension(
				UiUtil.COMPLEX_UNIT_DIP, 4.0f, metrics));
		int round = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 2.0f, metrics));

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		canvas.drawRoundRect(new RectF(0, 0, width, height) , round,
				round, paint);
		return bitmap;
	}

	public static Bitmap getCircleBitmap(Bitmap bitmap) {
		if(null == bitmap) {
			return null;
		}
		
		int defaultColor = 0xFFFFFFFF;
		int mBorderOutsideColor = 0; // 外圆边框颜色
		int mBorderInsideColor = 0; // 内圆边框颜色
		
		int mBorderThickness = 2; // 边框厚度
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int radius = 0;
		
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		if(mBorderInsideColor != defaultColor
				&& mBorderOutsideColor != defaultColor) { // 内外边框和默认边框不一样，则画两个边框
			radius = (width < height ? width : height) / 2 - 2 * mBorderThickness;
			// 画内边框
			drawCircleBorder(canvas, width, height, radius, mBorderInsideColor, mBorderThickness);
			// 画外边框
			drawCircleBorder(canvas, width, height, radius, mBorderOutsideColor, mBorderThickness);
		} else if(mBorderInsideColor != defaultColor
				&& mBorderOutsideColor == defaultColor) {
			radius = (width < height ? width : height) / 2 - mBorderThickness;
			drawCircleBorder(canvas, width, height, radius, mBorderInsideColor, mBorderThickness);
		} else if(mBorderInsideColor == defaultColor
				&& mBorderOutsideColor != defaultColor) {
			radius = (width < height ? width : height) / 2 - mBorderThickness;
			drawCircleBorder(canvas, width, height, radius, mBorderOutsideColor, mBorderThickness);
		} else { // 没有边框
			radius = (width < height ? width : height) / 2 - mBorderThickness;
		}
		
		Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap, width / 2 - radius, height
				/ 2 - radius, null);
		return output;
	}

	private static Bitmap getCroppedRoundBitmap(Bitmap bitmap, int radius) {
		Bitmap scaledSrcBmp = null;
		int diameter = radius * 2;
		
		// 截取长方形中处于中间位置最大的正方形图片
		int bmWidth = bitmap.getWidth();
		int bmHeight = bitmap.getHeight();
		int squareWidth = 0, squareHeight = 0;  
	    int x = 0, y = 0;  
	    
	    Bitmap squareBitmap;  
	    if (bmHeight > bmWidth) {
	        squareWidth = squareHeight = bmWidth;  
	        x = 0;  
	        y = (bmHeight - bmWidth) / 2;  
	        // 截取正方形图片  
	        squareBitmap = Bitmap.createBitmap(bitmap, x, y, squareWidth,  
	        squareHeight);  
	    } else if (bmHeight < bmWidth) {// 宽大于高  
	        squareWidth = squareHeight = bmHeight;  
	        x = (bmWidth - bmHeight) / 2;  
	        y = 0;  
	        squareBitmap = Bitmap.createBitmap(bitmap, x, y, squareWidth,  
	                squareHeight);  
	    } else {  
	        squareBitmap = bitmap;  
	    }  
	  
	    if (squareBitmap.getWidth() != diameter  
	            || squareBitmap.getHeight() != diameter) {  
	    	scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,  
	                diameter, true);  
	    } else {  
	        scaledSrcBmp = squareBitmap;  
        }  
	    Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),  
	                scaledSrcBmp.getHeight(), Config.ARGB_8888);  
	    Canvas canvas = new Canvas(output);  
	  
	    Paint paint = new Paint();  
	    Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),  
	                scaledSrcBmp.getHeight());  
	  
	    paint.setAntiAlias(true);  
	    paint.setFilterBitmap(true);  
	    paint.setDither(true);  
	    canvas.drawARGB(0, 0, 0, 0);  
	    canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
	    canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);

		recycleBitmap(bitmap);
		recycleBitmap(squareBitmap);
		recycleBitmap(scaledSrcBmp);
	    return output; 
	}

	private static void drawCircleBorder(Canvas canvas, int width, int height,
			int radius, int color, int mBorderThickness) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		paint.setStyle(Style.STROKE); // 空心
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(width / 2, height / 2, radius, paint);
	}
	
	/**
	 * 图片倒影效果
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		//倒影的间距
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Matrix matrix = new Matrix();
		// 图片缩放，X轴变为原来的1倍，Y轴变为原来的-1倍，实现图片翻转
		matrix.preScale(1, -1);
		
		// 创建反转后的bitmap图像，图片高是原图的一半
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);
		// 创建标准的bitmap图像，宽和原图一直，高位原图的1.5倍，做为原图和倒影的底板
		Bitmap bitmapReflection = Bitmap.createBitmap(width, 
				(height + height / 2) , Config.ARGB_8888);
		
		// 以bitmapReflection为底板的画布
		Canvas canvas = new Canvas(bitmapReflection);
		// 画原始图片
		canvas.drawBitmap(bitmap, 0, 0, null);
		// 画间隔矩形
		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		// 画倒影图片
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		
		// 实现倒影渐变效果
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(),
				0, bitmapReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// 覆盖效果
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmap.getHeight() + reflectionGap, paint);
		return bitmapReflection;
	}
	
	public static Bitmap readRawBitmap(Context context, int resId) {
		Options options = new Options();
		options.inPreferredConfig = Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, options);
	}
	
	public static Bitmap readSdcardBitmap(String path) {
		if(!SdCardUtil.isMounted() || TextUtils.isEmpty(path)) {
			return null;
		}
		
		File filePath = new File(path);
		if(filePath.exists()) {
			Options options = new Options();
			options.inPreferredConfig = Config.ARGB_8888;
	
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			filePath.setLastModified(System.currentTimeMillis());
			return bm;
		}
		return null;
	}
	
	public static Bitmap rotateBitmap(Bitmap bitmap, float degree) {
		if(null == bitmap) {
			return null;
		}
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		return Bitmap.createBitmap(bitmap, 0, 0, 
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	public static Bitmap reverseBitmap(Bitmap bitmap, int flag) {
		if(null == bitmap) {
			return null;
		}
		
		float[] floats = null;
		switch (flag) {
		case 0: // 水平翻转
			floats = new float[]{-1f, 0f, 0f,
								  0f, 1f, 0f,
								  0f, 0f, 1f};
			break;
		case 1: // 垂直翻转
			floats = new float[]{1f, 0f, 0f,
					  			 0f, -1f, 0f,
					  			 0f, 0f, 1f};
			break;
		}
		if(null != floats) {
			Matrix matrix = new Matrix();
			matrix.setValues(floats);
			return Bitmap.createBitmap(bitmap, 0, 0, 
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
		return bitmap;
	}
	
	public static Bitmap waterMarkBitmap(Bitmap srcBitmap, Bitmap waterMark, int offSetX, int offSetY) {
		if(null != srcBitmap && null != waterMark) {
			Bitmap newBitmap = Bitmap.createBitmap(srcBitmap.getWidth(),
					srcBitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(srcBitmap, 0, 0, null);
			canvas.drawBitmap(waterMark, offSetX, offSetY, null);
			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
			return newBitmap;
		} else if(null != srcBitmap && null == waterMark) {
			return srcBitmap;
		} else {
			return null;
		}
	}
	
	public static Bitmap drawTextOnBitmap(Bitmap srcBitmap, String msg,
			int offSetX, int offSetY, int color) {
		if(null != srcBitmap && null != msg) {
			Bitmap newBitmap = Bitmap.createBitmap(srcBitmap.getWidth(),
					srcBitmap.getHeight(), Config.ARGB_8888);
			Paint paint = new Paint();
			paint.setColor(color);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawText(msg, offSetX, offSetY, paint);
			canvas.save();
			canvas.restore();
			return newBitmap;
		} else if(null != srcBitmap && null == msg) {
			return srcBitmap;
		} else {
			return null;
		}
	}

}
