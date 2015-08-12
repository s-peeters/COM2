package bean;

/**
 * 
 * @author matteo
 *
 */
public class Evidence {
	
	private String subject_name;
	private String object_name;
	private String subject_type;
	private String object_type;
	private String pattern;
	private String relations;
	
	/**
	 * 
	 * @param subject_name
	 * @param object_name
	 * @param subject_type
	 * @param object_type
	 * @param pattern
	 */
	public Evidence(String subject_name, String object_name, String subject_type, 
			String object_type, String pattern){
		this.subject_name = subject_name;
		this.object_name = object_name;
		this.subject_type = subject_type;
		this.object_type = object_type;
		this.pattern = pattern;
	}
	
	/**
	 * 
	 */
	public Evidence(){}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getObject_name() {
		return object_name;
	}

	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}

	public String getSubject_type() {
		return subject_type;
	}

	public void setSubject_type(String subject_type) {
		this.subject_type = subject_type;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}
	
	public String toString(){
		String print = this.subject_name +" --- "+ this.subject_type +" --- "+ this.object_name +" --- "+ this.object_type +" --- "+ this.pattern;
		return print;
	}


}
