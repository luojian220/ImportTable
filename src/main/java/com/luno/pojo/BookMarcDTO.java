package com.luno.pojo;

public class BookMarcDTO {

	/**
	 * marc文件字段编号ID
	 */
	private String id;
	/**
	 * 编号ID对应的含义
	 */
	private String mean;
	/**
	 * 对应的数据信息
	 */
	private String content;
	/**
	 * 通过数据库查询的marc数据
	 */
	private String marc;
	
	/**
	 * 标识符
	 */
	private String flag;
	
	public String getMarc() {
		return marc;
	}
	public void setMarc(String marc) {
		this.marc = marc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "BookMarcDTO [id=" + id + ", mean=" + mean + ", content=" + content + ", marc=" + marc + ", flag=" + flag
				+ "]";
	}
	
	
	
}
