package com.accumulate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SftpUtils {

	private static Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sftp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
	 * 创建单文件夹
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean mkdir(String folder) {
		ChannelSftp sftp = null;
		try {
			sftp = getChannel();
			sftp.mkdir(properties.getProperty("sftp.elec.contract.path") + folder);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			close(sftp);
		}
		return true;
	}

	/**
	 * 创建目录
	 * 
	 * @param createpath
	 * @return
	 */
	public static boolean createDir(String createpath) {
		ChannelSftp sftp = null;
		try {
			sftp = getChannel();
			if (isExist(createpath)) {
				sftp.cd(createpath);
				return true;
			}
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isExist(filePath.toString())) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}

			}
			sftp.cd(createpath);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			close(sftp);
		}
		return false;
	}

	/**
	 * 获取目录列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Vector<LsEntry> listFiles() {
		ChannelSftp sftp = null;
		try {
			sftp = getChannel();
			return (Vector<LsEntry>) sftp.ls(properties.getProperty("sftp.elec.contract.path"));
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			close(sftp);
		}
		return null;
	}

	/**
	 * 该文件夹是否存在
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean isExist(String folder) {
		ChannelSftp sftp = null;
		boolean isDirExistFlag = false;
		try {
			sftp = getChannel();
			SftpATTRS sftpATTRS = sftp.lstat(folder);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		} finally {
			close(sftp);
		}
		return isDirExistFlag;
	}

	/**
	 * 上传文件
	 * 
	 * @param originalFilename
	 * @param is
	 * @param path
	 * @return
	 */
	public static String uploadFile(String originalFilename, InputStream is, String path) {
		ChannelSftp channel = null;
		String filename = originalFilename;
		try {
			channel = getChannel();
			channel._put(is, path + filename, null, 0);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			close(channel);
		}
		return filename;
	}

	public static Properties getProperties() {
		return properties;
	}
}
