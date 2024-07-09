package in.co.rays.project_3.dto;

import java.util.Date;

public class LessonDTO extends BaseDTO {
	
	private String name;
	private String chapterNo;
	private String subject;
	private Date date;
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChapterNo() {
		return chapterNo;
	}

	public void setChapterNo(String chapterNo) {
		this.chapterNo = chapterNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
