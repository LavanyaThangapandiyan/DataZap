package com.chainsys.core.vo;

import com.chainsys.appplatform.vo.AppVO;
import com.chainsys.base.annotation.EntityProperty;
import com.chainsys.base.annotation.IdEntityProperty;
import com.chainsys.base.annotation.SequenceEntityProperty;
import com.chainsys.base.annotation.TableEntityProperty;

@TableEntityProperty(name = "EMPLOYEE_3458")
public final class EmployeeDefinitionVO extends AppVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The employee id. */
    private int employeeId;

    /** The employee name. */
    private String employeeName;
 

    /** The Address. */
    private String address;


    /** The Employee Age. */
    private int age;

    @IdEntityProperty
    @EntityProperty(columnName="EMPLOYEE_ID")
	public int getEmployeeId() {
		return employeeId;
	}
    
	@SequenceEntityProperty(sequenceName="EMPLOYEE_ID_SEQ")
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@EntityProperty(columnName="EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@EntityProperty(columnName="EMPLOYEE_AGE")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	@EntityProperty(columnName="ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
