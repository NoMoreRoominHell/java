package com.accumulate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpUtils {

	// 配置文件
	private static Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sftp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取sftp连接
	 * 
	 * @return
	 * @throws JSchException
	 */
	public static ChannelSftp getChannel() throws JSchException {
		String host = properties.getProperty("sftp.host");
		int port = Integer.parseInt(properties.getProperty("sftp.port"));
		String username = properties.getProperty("sftp.username");
		JSch jsch = new JSch();
		Session session = jsch.getSession(username, host, port);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(properties.getProperty("sftp.password"));
		session.connect();
		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
		channel.connect();
		return channel;
	}

	public static void close(Channel channel) {
		Session session = null;
		if (channel != null) {
			try {
				session = channel.getSession();
				channel.disconnect();
				session.disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws SftpException
	 * @throws JSchException
	 */
	public static String uploadFile(MultipartFile file) {
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadFile(file.getOriginalFilename(), is);
	}

	/**
	 * 文件上传
	 * 
	 * @param originalFilename：本地文件名
	 * @param is：上传文件流
	 * @return filename：保存在服务器上的文件名
	 * @throws JSchException
	 * @throws SftpException
	 */
	public static String uploadFile(String originalFilename, InputStream is) {
		return uploadFile(originalFilename, is, properties.getProperty("sftp.activity.path"));
	}

	/**
	 * 
	 * @Description:上传 用原始名称 不另外生成名称
	 * @author yangling
	 * @date 2018年3月2日下午4:03:28
	 */
	public static String uploadFileUseOriginalName(String originalFilename, InputStream is) {
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			channel._put(is, properties.getProperty("sftp.bank.path") + originalFilename, null, 0);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		close(channel);
		return originalFilename;
	}
	
	/**
	 * 文件上传
	 * 
	 * @param originalFilename：本地文件名
	 * @param is：上传文件流
	 * @param path：文件保存路径
	 * @return filename：保存在服务器上的文件名
	 * @throws JSchException
	 * @throws SftpException
	 */
	public static String uploadFile(String originalFilename, InputStream is, String path) {
		ChannelSftp channel = null;
		String filename = generateFileUUIDName(originalFilename);
		try {
			channel = getChannel();
			channel._put(is, path + filename, null, 0);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}
		close(channel);
		return filename;
	}

	/**
	 * 文件下载
	 * 
	 * @param filename
	 *            ：待下载的文件名（没有扩展名）
	 * @param os：响应的输出流
	 * @return filename :待下载的文件名（扩展名）
	 * @throws JSchException
	 * @throws SftpException
	 */
	public static String downFile(String filename, OutputStream os) {
		return downFile(filename, os, properties.getProperty("sftp.activity.path"));
	}

	/**
	 * 文件下载
	 * 
	 * @param filename
	 *            ：待下载的文件名（没有扩展名）
	 * @param os：响应的输出流
	 * @return filename :待下载的文件名（扩展名）
	 * @throws SftpException
	 * @throws JSchException
	 */
	@SuppressWarnings("rawtypes")
	public static String downFile(String filename, OutputStream os, String path) {
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			Vector ls = channel.ls(path);
			if (ls != null && ls.size() > 0) {
				for (int i = 0; i < ls.size(); i++) {
					LsEntry entry = (LsEntry) ls.get(i);
					if (entry.getFilename().contains(filename)) {
						filename = entry.getFilename();
						channel.get(path + filename, os, null);
						close(channel);
						return filename;
					}
				}
			}
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		close(channel);
		return null;
	}

	/**
	 * 生成保存在服务器上的文件名
	 * 
	 * @param originalFilename
	 * @return
	 */
	public static String generateFileUUIDName(String originalFilename) {
		return UUID.randomUUID().toString().replaceAll("-", "") + originalFilename.substring(originalFilename.indexOf("."));
	}
}
