package com.security.core.validate.code.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.ValidateCodeGenerator;

/**
 * @author zhailiang
 *
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

	/**
	 * 系统配置
	 */
	@Autowired
	private SecurityProperties securityProperties;

	private char[] characters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };

	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
				securityProperties.getCode().getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				securityProperties.getCode().getImage().getHeight());
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		Random random = new Random();

		//g.setColor(getRandColor(200, 250));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.setFont(new Font("Times New Roman", Font.ITALIC, 22));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 40; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
			int c = random.nextInt(characters.length);
			String rand = String.valueOf(characters[c]);
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 16 * i + 10, 20);
		}

		g.dispose();

		return new ImageCode(image, sRand, securityProperties.getCode().getImage().getExpireIn());
	}

	/**
	 * 生成随机背景条纹
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	public static void main(String[] args) {
		int i = 0;
		for (i = 'a'; i <= 'z'; i++) {
			System.out.print("'" + (char) i + "',");
		}
		for (i = 'A'; i <= 'Z'; i++) {
			System.out.print("'" + (char) i + "',");
		}
		for (i = 0; i <= 9; i++) {
			System.out.print("'" + i + "',");
		}
	}

}
